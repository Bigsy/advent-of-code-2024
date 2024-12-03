(ns advent-of-code-2024-clj.day3
  (:require 
   [hashp.core]
   [clojure.string :as str]))

(defn solve-part1 [input]
  (let [mul-pattern #"mul\((\d{1,3}),(\d{1,3})\)"]
    (->> input
         (re-seq mul-pattern)
         (map (fn [[_ x y]]
                (* (parse-long x)
                   (parse-long y))))
         (reduce + 0))))

(defn solve-part2 [input]
  (let [command-pattern #"(?:mul\((\d+),(\d+)\)|(?:(do|don't)\(\)))"
        find-commands (fn [pattern]
                       (loop [matcher (re-matcher pattern input)
                              commands []]
                         (if (.find matcher)
                           (let [[_ x y cmd] (re-groups matcher)]
                             (recur 
                              matcher
                              (conj commands
                                    {:pos (.start matcher)
                                     :cmd (cond
                                           x [:mul (parse-long x) (parse-long y)]
                                           cmd [(if (= cmd "do") :enable :disable)])})))
                           commands)))]
    (->> (find-commands command-pattern)
         (sort-by :pos)
         (reduce (fn [{:keys [enabled? sum]} {:keys [cmd]}]
                  (let [[op & args] cmd]
                    (case op
                      :mul {:enabled? enabled? 
                           :sum (if enabled? 
                                 (+ sum (apply * args))
                                 sum)}
                      :enable {:enabled? true :sum sum}
                      :disable {:enabled? false :sum sum})))
                {:enabled? true :sum 0})
         :sum)))

(comment
  (solve-part1 (slurp (clojure.java.io/resource "day3.txt"))) ;; 167650499
  (solve-part2 (slurp (clojure.java.io/resource "day3.txt"))) ;; 95846796
  )
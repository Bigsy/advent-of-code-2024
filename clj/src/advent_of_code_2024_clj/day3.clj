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
        commands (->> (loop [matcher (re-matcher command-pattern input)
                           result []]
                      (if (.find matcher)
                        (let [[_ x y cmd] (re-groups matcher)]
                          (recur matcher
                                (conj result
                                      {:pos (.start matcher)
                                       :cmd (if x
                                             [:mul (parse-long x) (parse-long y)]
                                             [(if (= cmd "do") :enable :disable)])})))
                        result))
                     (sort-by :pos))]
    (:sum
     (reduce (fn [{:keys [enabled? sum]} {:keys [cmd]}]
              (let [[op & args] cmd]
                (case op
                  :mul {:enabled? enabled?
                       :sum (if enabled?
                             (+ sum (apply * args))
                             sum)}
                  :enable {:enabled? true :sum sum}
                  :disable {:enabled? false :sum sum})))
            {:enabled? true :sum 0}
            commands))))
(comment
  (solve-part1 (slurp (clojure.java.io/resource "day3.txt"))) ;; 167650499
  (solve-part2 (slurp (clojure.java.io/resource "day3.txt"))) ;; 95846796
  )
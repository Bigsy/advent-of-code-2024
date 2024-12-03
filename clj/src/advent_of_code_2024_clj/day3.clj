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
  (let [commands (for [match (re-seq #"mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)" input)]
                   (cond
                     (= (first match) "do()") [:enable]
                     (= (first match) "don't()") [:disable]
                     :else [:mul (parse-long (nth match 1))
                            (parse-long (nth match 2))]))]
    (loop [cmds commands
           enabled? true
           sum 0]
      (if (empty? cmds)
        sum
        (let [[op & args] (first cmds)]
          (case op
            :mul (recur (rest cmds)
                        enabled?
                        (if enabled?
                          (+ sum (apply * args))
                          sum))
            :enable (recur (rest cmds) true sum)
            :disable (recur (rest cmds) false sum)))))))

(comment
  (solve-part1 (slurp (clojure.java.io/resource "day3.txt"))) ;; 167650499
  (solve-part2 (slurp (clojure.java.io/resource "day3.txt"))) ;; 95846796
  )
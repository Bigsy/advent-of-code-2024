(ns advent-of-code-2024-clj.day7
  (:require
    [hashp.core]
    [clojure.string :as str]))

(defn parse-line [line]
  (let [[target nums] (str/split line #":")
        target (parse-long target)
        nums (map parse-long (str/split (str/trim nums) #"\s+"))]
    [target nums]))

(defn evaluate [nums operators]
  (reduce
    (fn [result [n op]]
      ((case op \+ + \* *) result n))
    (first nums)
    (map vector (rest nums) operators)))

(defn generate-operator-combinations [n]
  (if (zero? n)
    [[]]
    (for [combo (generate-operator-combinations (dec n))
          op [\+ \*]]
      (conj combo op))))

(defn can-equation-be-solved? [[target nums]]
  (let [operator-count (dec (count nums))
        operator-combinations (generate-operator-combinations operator-count)]
    (some #(= target (evaluate nums %)) operator-combinations)))

(defn solve-part1 [input]
  (->> input
       str/split-lines
       (map parse-line)
       (filter can-equation-be-solved?)
       (map first)
       (reduce +)))

(defn solve-part2 [input]
  )

(comment
  (solve-part1 (slurp (clojure.java.io/resource "day7.txt"))) ;;14711933466277
  (solve-part2 (slurp (clojure.java.io/resource "day7.txt")))) ;;1309

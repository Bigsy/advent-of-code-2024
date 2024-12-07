(ns advent-of-code-2024-clj.day7
  (:require
    [hashp.core]
    [clojure.string :as str]))

(defn parse-line [line]
  (let [[test-value numbers] (str/split line #":")
        numbers (map parse-long (str/split (str/trim numbers) #"\s+"))]
    [(parse-long test-value) numbers]))

(defn parse-input [input]
  (map parse-line (str/split-lines input)))


(defn evaluate [numbers operators]
  (reduce (fn [result [op num]]
            (case op
              \+ (+ result num)
              \* (* result num)))
          (first numbers)
          (map vector operators (rest numbers))))


(defn generate-operator-combinations [n]
  (if (zero? n)
    [[]]
    (for [ops (generate-operator-combinations (dec n))
          op [\+ \*]]
      (conj ops op))))


(defn can-make-value? [target numbers]
  (let [required-ops (dec (count numbers))
        possible-ops (generate-operator-combinations required-ops)]
    (some #(= target (evaluate numbers %)) possible-ops)))

(defn solve-part1 [input]
  (->> (parse-input input)
       (filter (fn [[target numbers]] (can-make-value? target numbers)))
       (map first)
       (reduce +)))



(defn solve-part2 [input]
  )

(comment
  (solve-part1 (slurp (clojure.java.io/resource "day7.txt"))) ;;14711933466277
  (solve-part2 (slurp (clojure.java.io/resource "day7.txt")))) ;;1309

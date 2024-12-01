(ns advent-of-code-2024-clj.day1
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn parse-line [line]
  (let [[left right] (str/split line #"\s+")]
    [(parse-long left) (parse-long right)]))

(defn parse-input [input]
  (->> input
       str/split-lines
       (map parse-line)))

(defn calculate-distance [pairs]
  (->> pairs
       (map (fn [[left right]]
              (Math/abs (- left right))))
       (reduce +)))

(defn solve-part1 [input]
  (let [parsed-data (parse-input input)
        left-numbers (map first parsed-data)
        right-numbers (map second parsed-data)
        sorted-left (sort left-numbers)
        sorted-right (sort right-numbers)
        pairs (map vector sorted-left sorted-right)]
    (calculate-distance pairs)))

(defn solve [opts]
  (let [input-file (:input-file opts)
        input (slurp (io/resource input-file))]
    (solve-part1 input)))

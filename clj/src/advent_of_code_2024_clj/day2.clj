(ns advent-of-code-2024-clj.day2
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn parse-line [line]
  (mapv #(Integer/parseInt %) (str/split (str/trim line) #"\s+")))

(defn parse-input [input]
  (->> (str/split-lines input)
       (mapv parse-line)))

(defn valid-differences? [numbers]
  (let [diffs (map - (rest numbers) numbers)]
    (and (every? #(<= (Math/abs %) 3) diffs)
         (every? #(>= (Math/abs %) 1) diffs)
         (or (every? pos? diffs)
             (every? neg? diffs)))))

(defn valid-with-dampener? [numbers]
  (or (valid-differences? numbers)
      (some valid-differences? 
            (map #(vec (concat (subvec numbers 0 %) 
                              (subvec numbers (inc %) (count numbers))))
                 (range (count numbers))))))

(defn solve-part1 [input]
  (let [data (parse-input input)]
    (->> data
         (filter valid-differences?)
         count)))

(defn solve-part2 [input]
  (let [data (parse-input input)]
    (->> data
         (filter valid-with-dampener?)
         count)))

(solve-part1 (slurp (io/resource "day2.txt"))) ;; 411
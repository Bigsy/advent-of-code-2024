(ns advent-of-code-2024-clj.day5
  (:require
    [hashp.core]
    [clojure.string :as str]))

(defn parse-rule [rule]
  (let [[before after] (str/split rule #"\|")]
    [(parse-long before) (parse-long after)]))

(defn parse-update [update]
  (mapv parse-long (str/split update #",")))

(defn parse-input [input]
  (let [[rules-section updates-section] (str/split input #"\n\n")
        rules (map parse-rule (str/split-lines rules-section))
        updates (map parse-update (str/split-lines updates-section))]
    {:rules rules :updates updates}))

(defn valid-order? [rules pages]
  (let [page-indices (into {} (map-indexed (fn [idx page] [page idx]) pages))]
    (every? (fn [[before after]]
              (or 
               (not (contains? page-indices before))
               (not (contains? page-indices after))
               (< (get page-indices before) (get page-indices after))))
            rules)))

(defn middle-number [nums]
  (nth nums (quot (count nums) 2)))

(defn solve-part1 [input]
  (let [{:keys [rules updates]} (parse-input input)]
    (->> updates
         (filter #(valid-order? rules %))
         (map middle-number)
         (reduce +))))

(defn solve-part2 [input]
  )

(comment
  (solve-part1 (slurp (clojure.java.io/resource "day5.txt"))) ;; 
  (solve-part2 (slurp (clojure.java.io/resource "day5.txt")))) ;;

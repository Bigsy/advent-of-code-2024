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

(defn sort-by-rules [rules pages]
  (let [page-set (set pages)
        relevant-rules (filter (fn [[before after]]
                               (and (contains? page-set before)
                                    (contains? page-set after)))
                             rules)]
    (loop [current-pages (vec pages)]
      (let [needs-swap? (some (fn [[before after]]
                               (let [before-idx (.indexOf current-pages before)
                                     after-idx (.indexOf current-pages after)]
                                 (and (>= before-idx 0)
                                      (>= after-idx 0)
                                      (> before-idx after-idx))))
                             relevant-rules)]
        (if needs-swap?
          (let [[before after] (first (filter (fn [[before after]]
                                              (let [before-idx (.indexOf current-pages before)
                                                    after-idx (.indexOf current-pages after)]
                                                (and (>= before-idx 0)
                                                     (>= after-idx 0)
                                                     (> before-idx after-idx))))
                                            relevant-rules))
                before-idx (.indexOf current-pages before)
                after-idx (.indexOf current-pages after)]
            (recur (assoc current-pages
                         after-idx before
                         before-idx after)))
          current-pages)))))

(defn solve-part2 [input]
  (let [{:keys [rules updates]} (parse-input input)]
    (->> updates
         (filter #(not (valid-order? rules %)))
         (map #(sort-by-rules rules %))
         (map middle-number)
         (reduce +))))

(comment
  (solve-part1 (slurp (clojure.java.io/resource "day5.txt"))) ;; 5639
  (solve-part2 (slurp (clojure.java.io/resource "day5.txt")))) ;;

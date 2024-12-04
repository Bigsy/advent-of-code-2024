(ns advent-of-code-2024-clj.day4
  (:require 
   [hashp.core]
   [clojure.string :as str]))

(defn parse-grid [input]
  (let [lines (str/split-lines input)]
    (mapv vec lines)))

(defn get-char [grid row col]
  (when (and (>= row 0) 
             (< row (count grid))
             (>= col 0)
             (< col (count (first grid))))
    (nth (nth grid row) col)))

(defn check-direction [grid row col [dx dy] pattern]
  (let [chars (for [i (range (count pattern))]
                (get-char grid 
                         (+ row (* i dy))
                         (+ col (* i dx))))]
    (when (every? some? chars)
      (let [str-chars (apply str chars)]
        (when (= pattern str-chars)
          (println "Found pattern" pattern "at" row col "in direction" dx dy "chars:" str-chars))
        (= pattern str-chars)))))

(defn count-pattern-at-position [grid row col pattern]
  (let [directions [[1 0]   ; right
                   [0 1]    ; down
                   [1 1]    ; diagonal down-right
                   [-1 1]   ; diagonal down-left
                   [-1 0]   ; left
                   [0 -1]   ; up
                   [-1 -1]  ; diagonal up-left
                   [1 -1]]] ; diagonal up-right
    (->> directions
         (filter #(check-direction grid row col % pattern))
         count)))

(defn solve-part1 [input]
  (let [grid (parse-grid input)]
    (->> (for [row (range (count grid))
               col (range (count (first grid)))]
           (count-pattern-at-position grid row col "XMAS"))
         (reduce +))))

(defn check-mas-at-point [grid row col [dx dy]]
  (check-direction grid row col [dx dy] "MAS"))

(defn check-x-mas [grid row col]
  (let [center (get-char grid row col)]
    (when (= \A center)
      (let [diagonals [[[1 1] [-1 -1]]   ; top-left to bottom-right AND bottom-right to top-left
                       [[1 1] [1 -1]]     ; top-left to bottom-right AND bottom-left to top-right
                       [[1 -1] [-1 -1]]   ; bottom-left to top-right AND bottom-right to top-left
                       [[1 -1] [-1 1]]    ; bottom-left to top-right AND top-right to bottom-left
                       [[-1 1] [1 1]]     ; top-right to bottom-left AND top-left to bottom-right
                       [[-1 1] [1 -1]]    ; top-right to bottom-left AND bottom-left to top-right
                       [[-1 -1] [1 1]]    ; bottom-right to top-left AND top-left to bottom-right
                       [[-1 -1] [1 -1]]]  ; bottom-right to top-left AND bottom-left to top-right
            valid-pairs (for [[d1 d2] diagonals
                             :when (and (check-mas-at-point grid row col d1)
                                      (check-mas-at-point grid row col d2))]
                         [d1 d2])]
        (when (seq valid-pairs)
          (println "Found X-MAS at" row col "with directions" (first valid-pairs)))
        (count valid-pairs)))))

(defn solve-part2 [input]
  (let [grid (parse-grid input)
        rows (count grid)
        cols (count (first grid))]
    (println "Grid size:" rows "x" cols)
    (->> (for [row (range rows)
               col (range cols)]
           (or (check-x-mas grid row col) 0))
         (reduce +))))

(comment
  (solve-part1 (slurp (clojure.java.io/resource "day4.txt"))) ;; 2493
  (solve-part2 (slurp (clojure.java.io/resource "day4.txt"))) ;; 
  )
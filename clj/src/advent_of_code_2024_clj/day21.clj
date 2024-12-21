(ns advent-of-code-2024-clj.day21
  (:require
   [clojure.java.io :as io]
   [hashp.core]
   [clojure.string :as str]))


(defn some-bfs [code]
  ; Compute or approximate the shortest button-press sequence length...
  42) ; Placeholder

(defn solve-part1 [input]
  (let [lines (clojure.string/split-lines input)]
    (->> lines
         (map (fn [line]
                (let [num (Integer. (re-find #"\d+" line))
                      shortest-len (some-bfs line)]
                  (* num shortest-len))))
         (reduce +))))

(defn solve-part2 [input]
  )

(comment
  (solve-part1 (str/trim (slurp (io/resource "day21.txt")))) ;; 142884 low
  (solve-part2 (str/trim (slurp (io/resource "day21.txt"))))) ;;
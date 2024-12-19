(ns advent-of-code-2024-clj.day19
  (:require
   [clojure.java.io :as io]
   [hashp.core]
   [clojure.string :as str]))

(comment
  (solve-part1 (str/trim (slurp (io/resource "day18.txt")))) ;;
  (solve-part2 (str/trim (slurp (io/resource "day18.txt"))))) ;;
(ns advent-of-code-2024-clj.day22
  (:require
   [clojure.java.io :as io]
   [hashp.core]
   [clojure.string :as str]))

(defn solve-part1 [input]
  (let [nums (map #(Long/parseLong %) (clojure.string/split-lines input))
        next-secret (fn [s]
                      (let [step1 (mod (bit-xor s (* 64 s)) 16777216)
                            step2 (mod (bit-xor step1 (quot step1 32)) 16777216)
                            step3 (mod (bit-xor step2 (* 2048 step2)) 16777216)]
                        step3))
        get-secret (fn [init] (nth (iterate next-secret init) 2000))]
    (reduce + (map get-secret nums))))

(defn solve-part2 [input]
  )

(comment
  (solve-part1 (str/trim (slurp (io/resource "day22.txt")))) ;; 14273043166
  (solve-part2 (str/trim (slurp (io/resource "day22.txt"))))) ;;
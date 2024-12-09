(ns advent-of-code-2024-clj.day9
  (:require
    [hashp.core]
    [clojure.string :as str]))

(defn parse-input [input]
  (mapv #(Character/digit % 10) input))

(defn create-disk-map [lengths]
  (let [total-size (reduce + lengths)
        result (transient (vec (repeat total-size ".")))]
    (loop [pos 0
           file-id 0
           [len & rest-lens] lengths]
      (if (or (nil? len) (neg? len))
        (persistent! result)
        (do
          (dotimes [i len]
            (assoc! result (+ pos i) file-id))
          (recur (+ pos len (if (seq rest-lens) (first rest-lens) 0))
                 (inc file-id)
                 (rest rest-lens)))))))

(defn find-leftmost-free-space [disk]
  (.indexOf disk "."))

(defn find-rightmost-file [disk]
  (loop [pos (dec (count disk))]
    (if (< pos 0)
      nil
      (if (number? (disk pos))
        pos
        (recur (dec pos))))))

(defn move-file-left [disk from to length]
  (let [file-id (disk from)
        result (transient disk)]
    (assoc! result from ".")
    (assoc! result to file-id)
    (persistent! result)))

(defn compact-disk [disk]
  (loop [current disk
         free-pos (find-leftmost-free-space current)
         file-pos (find-rightmost-file current)]
    (if (or (nil? free-pos)
            (nil? file-pos)
            (>= free-pos file-pos))
      current
      (let [file-id (current file-pos)
            file-length (count (take-while #(= file-id %) (subvec current file-pos)))
            next-disk (move-file-left current file-pos free-pos file-length)]
        (recur next-disk
               (find-leftmost-free-space next-disk)
               (find-rightmost-file next-disk))))))

(defn calculate-checksum [disk]
  (let [len (count disk)]
    (loop [idx 0
           sum 0]
      (if (>= idx len)
        sum
        (let [val (disk idx)]
          (recur (inc idx)
                 (if (number? val)
                   (+ sum (* idx val))
                   sum)))))))

(defn solve-part1 [input]
  (let [lengths (parse-input (str/trim input))  ; Make sure to trim the input
        initial-disk (create-disk-map lengths)
        compacted-disk (compact-disk initial-disk)]
    (calculate-checksum compacted-disk)))

(defn solve-part2 [input]
  )

(comment
  (solve-part1 (str/trim (slurp (clojure.java.io/resource "day9.txt")))) ;; 6415184586041
  (solve-part2 (slurp (clojure.java.io/resource "day9.txt")))) ;;

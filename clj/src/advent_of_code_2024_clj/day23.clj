(ns advent-of-code-2024-clj.day23
  (:require
   [clojure.java.io :as io]
   [hashp.core]
   [clojure.string :as str]))

(defn parse-connections [input]
  (->> (str/split-lines input)
       (reduce (fn [acc line]
                 (let [[a b] (str/split line #"-")]
                   (-> acc
                       (update a (fnil conj #{}) b)
                       (update b (fnil conj #{}) a))))
               {})))

(defn find-connected-triples [graph]
  (let [nodes (keys graph)]
    (->> (for [a nodes
               b nodes
               c nodes
               :when (and (not= a b)
                         (not= b c)
                         (not= a c)
                         (contains? (get graph a) b)
                         (contains? (get graph b) c)
                         (contains? (get graph a) c))]
           #{a b c})
         (into #{}))))

(defn has-t-computer? [triple]
  (some #(str/starts-with? % "t") triple))

(defn solve-part1 [input]
  (->> input
       parse-connections
       find-connected-triples
       (filter has-t-computer?)
       count))

(defn solve-part2 [input]
  )

(comment
  (solve-part1 (str/trim (slurp (io/resource "day23.txt")))) ;; 1083
  (solve-part2 (str/trim (slurp (io/resource "day23.txt"))))) ;;
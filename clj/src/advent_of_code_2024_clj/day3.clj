(ns advent-of-code-2024-clj.day3
  (:require 
   [hashp.core]
   [clojure.string :as str]))

(defn solve-part1 [input]
  (let [pattern #"mul\((\d{1,3}),(\d{1,3})\)"
        matches (re-seq pattern input)]
    (->> matches
         (map #(let [[_ x y] %]
                 (* (Integer/parseInt x)
                    (Integer/parseInt y))))
         (reduce + 0))))

(defn solve-part2 [input]
  (let [find-all (fn [pattern]
                   (loop [m (re-matcher pattern input), res []]
                     (if (.find m)
                       (recur m (conj res {:start (.start m)
                                         :type (cond 
                                                (.group m 1) :mul
                                                (.group m 3) (if (= (.group m 3) "do") :do :dont))
                                         :value (when (.group m 1) 
                                                 [(Integer/parseInt (.group m 1))
                                                  (Integer/parseInt (.group m 2))])}))
                       res)))]
    (->> (find-all #"(?:mul\((\d+),(\d+)\)|(?:(do|don't)\(\)))")
         (sort-by :start)
         (reduce (fn [{:keys [enabled? sum] :as state} {:keys [type value]}]
                  (case type
                    :mul (update state :sum #(if enabled? (+ % (apply * value)) %))
                    :do (assoc state :enabled? true)
                    :dont (assoc state :enabled? false)))
                {:enabled? true :sum 0})
         :sum)))

(solve-part1 (slurp (clojure.java.io/resource "day3.txt"))) ;; 167650499
(solve-part2 (slurp (clojure.java.io/resource "day3.txt"))) ;; 95846796
(ns advent-of-code-2024-clj.day15-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2024-clj.day15 :refer [solve-part1]]))

(def example-input "########
#..O.O.#
##@.O..#
#...O..#
#.#.O..#
#...O..#
#......#
########

<^^>>>vv<v>>v<<")

(deftest part1-test
  (testing "Example input gives correct result"
    (is (= 2028 (solve-part1 example-input)))))

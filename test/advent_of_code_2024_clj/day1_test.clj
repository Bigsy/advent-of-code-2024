(ns advent-of-code-2024-clj.day1-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-code-2024-clj.day1 :as day1]
            [clojure.string :as str]))

(deftest sample-test
  (testing "Basic test to get started"
    (is (= 42 42))))

(deftest hello-test
  (testing "hello function prints 'Hello, World!'"
    (let [output (with-out-str (day1/hello))]
      (is (= "Hello, World!\n" output)))))

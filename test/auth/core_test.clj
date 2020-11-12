(ns auth.core-test
  (:require [auth.routes :refer [dummy-handler]]
            [clojure.test :refer [deftest
                                  testing
                                  is]]))

(deftest sample
  (testing "FIXME: I Fail"
    (is (= 1 1))))

(deftest test-dummy-handler
  (testing "dummy handler returns stuff"
    (is (= (dummy-handler {:parameters {:body {:username "username"
                                               :password "password"}}})
           {:status 200
            :body {:body {:username "username" :password "password"}}}))))


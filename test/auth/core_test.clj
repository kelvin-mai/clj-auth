(ns auth.core-test
  (:require [auth.core :refer [app]]
            [clojure.test :refer [deftest
                                  testing
                                  is]]))

(def headers {"accept" "application/edn"})
(def read-body (comp read-string slurp :body))

(deftest test-ping-route
  (testing "application works"
    (is (= (-> {:headers headers :request-method :get :uri "/api/ping"}
               app
               read-body)
           {:hello "world" :ping "pong"}))))

(deftest test-unauthorized
  (testing "users route should return unauthorized"
    (is (= (-> {:headers headers :request-method :get :uri "/api/users"}
               app
               read-body)
           {:error "Unauthorized"}))))


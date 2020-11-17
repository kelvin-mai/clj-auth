(ns auth.routes
  (:require [auth.handlers :as handle]
            [auth.utils :refer [wrap-jwt-authentication auth-middleware]]
            [schema.core :as s]))

(def ping-route
  ["/ping" {:name ::ping
            :get handle/ping}])

(def auth-routes
  [["/users" {:get {:middleware [wrap-jwt-authentication auth-middleware]
                    :handler handle/get-all-users}}]
   ["/me" {:get {:middleware [wrap-jwt-authentication auth-middleware]
                 :handler handle/me}}]
   ["/register" {:post {:parameters {:body {:username s/Str
                                            :password s/Str
                                            :email s/Str}}
                        :handler handle/register}}]
   ["/login" {:post {:parameters {:body {:username s/Str
                                         :password s/Str}}
                     :handler handle/login}}]])


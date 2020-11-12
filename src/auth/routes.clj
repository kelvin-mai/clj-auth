(ns auth.routes
  (:require [schema.core :as s]))

(defn dummy-handler
  [{:keys [parameters]}]
  (let [data (:body parameters)]
    {:status 200
     :body {:body data}}))

(def ping-route
  ["/ping" {:name ::ping
            :get (fn [_]
                   {:status 200
                    :body {:hello "world"
                           :something-else "ok"}})}])

(def auth-routes
  [["/register" {:post {:parameters {:body {:username s/Str
                                            :password s/Str
                                            :email s/Str}}
                        :handler dummy-handler}}]
   ["/login" {:post {:parameters {:body {:username s/Str
                                         :password s/Str}}
                     :handler dummy-handler}}]])


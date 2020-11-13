(ns auth.handlers
  (:require [auth.db :as db]
            [auth.utils :refer [create-token]]))

(defn ping [_]
  {:status 200
   :body {:hello "world"
          :ping "pong"}})

(defn get-all-users [{:keys [headers]}]
  {:status 200
   :body {:users (db/get-all-users)
          :headers headers}})

(defn register
  [{:keys [parameters]}]
  (let [data (:body parameters)
        user (db/create-user data)]
    {:status 200
     :body {:user user
            :token (create-token user)}}))

(defn login
  [{:keys [parameters]}]
  (let [data (:body parameters)
        user (db/get-user data)]
    (if (nil? user)
      {:status 404
       :body {:error "Invalid credentials"}}
      {:status 200
       :body {:user user
              :token (create-token user)}})))

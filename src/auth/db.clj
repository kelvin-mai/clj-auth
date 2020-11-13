(ns auth.db
  (:require [buddy.hashers :refer [encrypt check]]
            [honeysql.core :as h]
            [honeysql.helpers :as hh]
            [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]))

(def db-config
  {:dbtype "postgresql"
   :dbname "clj_auth"
   :host "localhost"
   :user "postgres"
   :password "postgres"})

(def db (jdbc/get-datasource db-config))

(defn db-query [sql]
  (jdbc/execute! db sql
                 {:return-keys true
                  :builder-fn rs/as-unqualified-maps}))

(defn db-query-one [sql]
  (jdbc/execute-one! db sql
                     {:return-keys true
                      :builder-fn rs/as-unqualified-maps}))

(defn create-user
  [{:keys [email username password]}]
  (let [hashed-password (encrypt password)
        created-user (->
                       (hh/insert-into :users)
                       (hh/columns :email :username :password)
                       (hh/values [[email username hashed-password]])
                       h/format
                       db-query-one)
        sanitized-user (dissoc created-user :password)]
    sanitized-user))

(defn get-user
  [{:keys [username password]}]
  (let [user (-> (hh/select :*)
                 (hh/from :users)
                 (hh/where := :username username)
                 h/format
                 db-query-one)
        sanitized-user (dissoc user :password)]
    (if (and user (check password (:password user)))
      sanitized-user
      nil)))

(defn get-all-users []
  (let [users (->
                (hh/select :*)
                (hh/from :users)
                h/format
                db-query)
        sanitized-user (map #(dissoc % :password) users)]
    sanitized-user))


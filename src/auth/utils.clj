(ns auth.utils
  (:require [buddy.auth.backends :as backends]
            [buddy.auth.middleware :refer [wrap-authentication]]
            [buddy.sign.jwt :as jwt]))

(def jwt-secret "JWT_SECRET")
(def backend (backends/jws {:secret jwt-secret}))
(defn wrap-jwt-authentication
  [handler]
  (wrap-authentication handler backend))

(defn create-token [payload]
  (jwt/sign payload jwt-secret))

(defn decode-token [token]
  (jwt/unsign token jwt-secret))


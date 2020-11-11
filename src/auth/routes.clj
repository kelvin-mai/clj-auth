(ns auth.routes)

(def ping-route
  ["/ping" {:name ::ping
            :get (fn [_]
                   {:status 200
                    :body "ok"})}])

(def register-route
  ["/register" {:post (fn [_]
                        {:status 201
                         :body "registered!"})}])

(def login-route
  ["/login" {:post (fn [_]
                     {:status 200
                      :body "logined!"})}])

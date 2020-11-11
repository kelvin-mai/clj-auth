(ns auth.core
  (:require [auth.routes :refer [ping-route
                                 login-route
                                 register-route]]
            [org.httpkit.server :refer [run-server]]
            [reitit.ring :as ring]
            [reitit.ring.middleware.exception :refer [exception-middleware]]
            [reitit.ring.middleware.muuntaja :refer [format-request-middleware
                                                     format-response-middleware
                                                     format-negotiate-middleware]]))

(defonce server
  (atom nil))

(def app
  (ring/ring-handler
    (ring/router
      [["/api"
        ping-route
        login-route
        register-route]]
      {:data {:middleware [format-negotiate-middleware
                           format-response-middleware
                           exception-middleware
                           format-request-middleware]}})
    (ring/routes
      (ring/redirect-trailing-slash-handler)
      (ring/create-default-handler
        {:not-found (constantly {:status 404 :body "Route not found"})}))))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn -main []
  (println "Server started")
  (reset! server (run-server app {:port 8080})))

(comment
  @server
  (-main)
  (stop-server)
  ())

(ns play.core
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jetty]
            [camel-snake-kebab.core :refer [->kebab-case-keyword]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
            [ring.util.response :as r]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route])
  (:gen-class))

(defn handle-bad-route []
  (r/response {:error "Bad route!"}))

(defn handle-status []
  (r/response {:status "OK"}))

(defroutes routes
  (GET "/status" [_] (handle-status))
  (route/not-found (handle-bad-route)))

(defn app []
  (-> routes
      (wrap-json-body {:keywords? ->kebab-case-keyword
                       :bigdecimals? true})
      (wrap-json-response {:pretty true})
      (wrap-defaults api-defaults)))

(defrecord Server [port server]
  component/Lifecycle

  (start [this]
    (if server
      this
      (let [server (jetty/run-jetty (app)
                                    {:port port :join? false})]
        (assoc this :server server))))

  (stop [this]
    (if server
      (do (.stop server)
          (.join server)
          (assoc this :server nil))
      this)))

(defn new-server [cfg]
  (map->Server {:port (:ws-port cfg)}))

(defn new-system []
  (component/system-map
   :server (new-server {:ws-port 3000})))

(defn -main [& args]
  (component/start-system (new-system)))

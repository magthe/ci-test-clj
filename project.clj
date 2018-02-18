(defproject ci-test-clj "0.1.0-SNAPSHOT"
  :description "The simplest of services based on Ring/Compojure/Component"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [compojure "1.6.0"]
                 [ring/ring-defaults "0.3.1"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [com.stuartsierra/component "0.3.2"]
                 [camel-snake-kebab "0.4.0"]
                 ;; [ring/ring-core "1.6.3"]
                 [ring/ring-json "0.5.0-beta1"]
                 [com.stuartsierra/component "0.3.2"]

                 ]
  :main ^:skip-aot play.core

  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [com.stuartsierra/component.repl "0.2.0"]
                                  [ring/ring-mock "0.3.2"]]}
             :uberjar {:aot :all}})

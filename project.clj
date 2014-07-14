(defproject ticker-client "0.1.0-SNAPSHOT"
  :description "A websocket client for a simulated stock ticker"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2268"]
                 [org.clojure/core.async "0.1.303.0-886421-alpha"]
                 [om "0.6.5"]
                 [jarohen/chord "0.4.2"]
                 [sablono "0.2.18"]]

  :plugins [[lein-cljsbuild "1.0.3"]]

  :source-paths ["src"]

  :cljsbuild {
    :builds [{:id "ticker-client"
              :source-paths ["src"]
              :compiler {
                :output-to "ticker_client.js"
                :output-dir "out"
                :optimizations :none
                :source-map true}}]})

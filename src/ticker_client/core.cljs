(ns ticker-client.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

(def app-state (atom {:text "Hello world!"}))

(def ws (js/WebSocket. "ws://localhost:8080/ticker"))
(aset ws "onmessage" (fn [m] (.log js/console (.-data m))) )



(om/root
  (fn [app owner]
    (dom/h1 nil (:text app)))
  app-state
  {:target (. js/document (getElementById "app"))})

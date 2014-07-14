(ns ticker-client.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [chord.client :refer [ws-ch]]
            [cljs.core.async :refer [<! >! put! close! chan]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(enable-console-print!)
(def +ws-url+ "ws://localhost:8080/ticker")

(def app-state (atom {:text "Hello again!"
                      :trades []}))

(defn make-ticker [c]
(go
 (let [{:keys [ws-channel]} (<! (ws-ch +ws-url+))]
   (loop []
     (let [{:keys [message]} (<! ws-channel)]
       (>! c message)
       (recur))))))

(defn master-view [app owner]
  (reify
    om/IInitState
    (init-state [_]
      (let [ticker (chan)]
        (make-ticker ticker)
        {:ticker ticker}))
    om/IWillMount
    (will-mount [_]
      (let [ticker (om/get-state owner :ticker)]
        (go (loop []
              (let [tick (<! ticker)]
                (om/transact! app :trades #(conj % tick)))
              (recur)))))
    om/IRenderState
    (render-state [this state]
      (dom/div nil
       (dom/h1 nil (:text app))
       (dom/h2 nil (pr-str (last (:trades app))))))))

(om/root
 master-view
  app-state
  {:target (. js/document (getElementById "app"))})

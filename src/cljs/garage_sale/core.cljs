(ns garage-sale.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [re-frisk.core :refer [enable-re-frisk!]]
              [garage-sale.events]
              [garage-sale.subs]
              [garage-sale.routes :as routes]
              [garage-sale.views :as views]
              [garage-sale.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (enable-re-frisk!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch [:initialize-db])
  (re-frame/dispatch [:fetch-games])
  (dev-setup)
  (mount-root))

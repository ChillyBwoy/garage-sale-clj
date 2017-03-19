(ns garage-sale.events
    (:require [re-frame.core :as rf]
              [clojure.walk :refer [keywordize-keys]]
              [ajax.core :as ajax]
              [garage-sale.db :as db]))

(rf/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(rf/reg-event-db
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(rf/reg-event-db
  :set-name
  (fn [db [_ name]]
    (assoc db :name name)))


(rf/reg-event-db
  :toggle-sell-game
  (fn [db [_ game]]
    (let [id (:id game)
          games (map #(if (= id (:id %)) (assoc % :sold (not (:sold %))) %)
                     (:games db))]
      (assoc db :games games))))

(rf/reg-event-db
  :fetch-games
  (fn [db _]
    (ajax/GET "/games.json"
      {:handler #(rf/dispatch [:fetch-games-success %1])
       :error-handler #(rf/dispatch [:fetch-games-error %1])})
    (assoc db :loading? true)))

(rf/reg-event-db
  :fetch-games-success
  (fn [db [_ response]]
    (-> db
        (assoc :loading? false)
        (assoc :games (map #(keywordize-keys %) (js->clj response))))))

(rf/reg-event-db
  :fetch-games-error
  (fn [db _]
    (-> db
        (assoc :loading? false))))

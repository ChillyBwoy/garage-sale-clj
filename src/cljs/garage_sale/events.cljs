(ns garage-sale.events
    (:require [re-frame.core :as rf]
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

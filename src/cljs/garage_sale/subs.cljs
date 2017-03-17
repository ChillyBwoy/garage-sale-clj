(ns garage-sale.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as rf]))

(rf/reg-sub
 :active-panel
 (fn [db _]
   (:active-panel db)))

(rf/reg-sub
  :games
  (fn [db _]
    (:games db)))

(rf/reg-sub
  :sold-total
  (fn [db _]
    (let [games (:games db)
          games-sold (filter #(:sold %) games)]
      (reduce + (map #(:price %) games-sold)))))

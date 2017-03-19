(ns garage-sale.games.views
    (:require [re-frame.core :as rf]))

; #(rf/dispatch [:set-name (-> % .-target .-value)])
(defn- table-row [index game]
  ^{:key (:id game)}
  [:tr
    [:td (str (inc index))]
    [:td
      [:input {:type "checkbox"
               :checked (:sold game)
               :on-change #(rf/dispatch [:toggle-sell-game game])}]]
    [:td (str (:id game))]
    [:td (str (:name game))]
    [:td (str (:comment game))]
    [:td (str (:price game))]])

(defn- table []
  (let [games (rf/subscribe [:games])
        total (rf/subscribe [:total])
        total-sold (rf/subscribe [:total-sold])]
    (fn []
      [:table
        [:thead
          [:tr
            [:th "#"]
            [:th "Sold"]
            [:th "Id"]
            [:th "Name"]
            [:th "Comment"]
            [:th "Price"]]]
        [:tbody
          (map-indexed table-row @games)]
        [:tfoot
          [:tr
            [:td {:colSpan 6}
                 (str "Total:" @total-sold " / " @total)]]]])))

(defn games-view []
  (fn []
    [table]))

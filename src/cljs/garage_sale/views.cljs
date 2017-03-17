(ns garage-sale.views
    (:require [re-frame.core :as rf]))


(defn games-list []
  (let [games (rf/subscribe [:games])
        total (rf/subscribe [:sold-total])]
    (fn []
      [:table
        [:thead
          [:tr
            [:th "Sold?"]
            [:th "Serial"]
            [:th "Name"]
            [:th "Comment"]
            [:th "Price"]]]
        [:tfoot
          [:tr
            [:td {:colSpan 5} (str "Total:" @total)]]]
        [:tbody
          (for [game @games]
           ^{:key (:id game)}
            [:tr
              [:td
                [:input {:type "checkbox"
                         :checked (:sold? game)
                         :on-change #(rf/dispatch [:toggle-sell-game game])}]]
              [:td (str (:id game))]
              [:td (str (:name game))]
              [:td (str (:comment game))]
              [:td (str (:price game))]])]])))


;; home
; #(rf/dispatch [:set-name (-> % .-target .-value)])

(defn home-panel []
  (fn []
    [:div
     [games-list]
     [:div [:a {:href "#/about"} "go to About Page"]]]))


;; about

(defn about-panel []
  (fn []
    [:div "This is the About Page."
     [:div [:a {:href "#/"} "go to Home Page"]]]))


;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (rf/subscribe [:active-panel])]
    (fn []
      [show-panel @active-panel])))

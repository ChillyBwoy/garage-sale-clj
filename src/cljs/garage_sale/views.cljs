(ns garage-sale.views
    (:require [re-frame.core :as rf]))



(defn games-list []
  (let [games (rf/subscribe [:games])]
    (fn []
      [:table
        [:thead
          [:tr
            [:th "Serial"]
            [:th "Name"]
            [:th "Comment"]
            [:th "Price"]]]
        [:tbody
          (for [game @games]
            ^{:key (:serial game)}
            [:tr
              [:td (str (:serial game))]
              [:td (str (:name game))]
              [:td (str (:comment game))]
              [:td (str (:price game))]])]])))


(defn edit-name []
  (let [name (rf/subscribe [:name])]
    (fn []
      [:label
        [:span "Application name: "]
        [:input {:type "text"
                 :value @name
                 :on-change #(rf/dispatch [:set-name (-> % .-target .-value)])}]])))

;; home

(defn home-panel []
  (let [name (rf/subscribe [:name])]
    (fn []
      [:div (str "Hello from " @name ". This is the Home Page.")
       [edit-name]
       [games-list]
       [:div [:a {:href "#/about"} "go to About Page"]]])))


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

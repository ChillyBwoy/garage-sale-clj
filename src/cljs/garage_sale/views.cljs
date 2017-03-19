(ns garage-sale.views
  (:require [re-frame.core :as rf]
            [garage-sale.games.views :as games]))

(defn home-panel []
  (fn []
    [games/games-view]))

(defn about-panel []
  (fn []
    [:div "This is the About Page."]))

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn navigation []
  (let [total (rf/subscribe [:total])
        total-sold (rf/subscribe [:total-sold])]
    (fn []
      [:nav
        [:ul
          [:li
            [:a {:href "#/"} "Home"]]
          [:li
            [:a {:href "#/about"} "About"]]]])))


(defn main-panel []
  (let [active-panel (rf/subscribe [:active-panel])]
    (fn []
      [:div {:class "layout"}
        [navigation]
        [:h1 "Garage Sale"]
        [show-panel @active-panel]])))

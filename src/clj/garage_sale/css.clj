(ns garage-sale.css
  (:require [garden.def :as d]
            [garden.units :as u]
            [garden.selectors :as $]))

(d/defstyles screen
  [:.layout {:width (u/px 960)
             :margin "0 auto"}])

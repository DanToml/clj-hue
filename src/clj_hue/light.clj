(ns clj-hue.light
  (:require
   [clojure.data.json :as json]
   [clj-http.client :as http]
   [clj-hue.bridge :as hue]))

(defn all
  "Returns the list of lights for a given bridge."
  [bridge]
  (-> (hue/build-url bridge "lights")
      (http/get {:as :json})
      :body))

(defn by-index
  "Returns the list of lights for a given bridge."
  [bridge index]
  (-> (hue/build-url bridge (str "lights/" (name index)))
      (http/get {:as :json})
      :body))

(defn rename
  "Rename the light at a given index"
  [bridge index new-name]
  (-> (hue/build-url bridge (str "lights/" (name index)))
      (http/put {:form-params {:name new-name}
                 :content-type :json
                 :as :json})
      :body))

(defn set-state
  "Overwrite the state of a given light. Returns a json diff of success/fails."
  [bridge index state]
  (-> (hue/build-url bridge (str "lights/" (name index) "/state"))
      (http/put {:form-params state
                 :content-type :json
                 :as :json})
      :body))

(defn set-brightness
  "Set the brightness of a given light."
  [bridge index brightness]
  (set-state bridge index {:bri brightness}))

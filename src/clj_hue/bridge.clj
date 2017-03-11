(ns clj-hue.bridge
  (:require
   [clojure.data.json :as json]
   [clj-http.client :as http]))

(def ^:private hue-discovery-url "https://www.meethue.com/api/nupnp")

(defn build-url
  [{:keys [ip-address username]} path]
  (if (nil? username)
    (str "http://" ip-address "/api/" path)
    (str "http://" ip-address "/api/" username "/" path)))

(defn from-ip
  ([ip-address]
   {:ip-address ip-address})
  ([ip-address username]
   {:ip-address ip-address :username username}))

(defn discover
  "Attempts to find a bridge on the local network using the Philips UPnP tool."
  []
  (-> (http/get hue-discovery-url {:as :json})
      :body
      first
      :internalipaddress
      from-ip))

(defn register
  "Attempts to register a new account on the Hub for the control API."
  [bridge]
  (-> (build-url bridge "")
      (http/post {:body (json/write-str {:devicetype "cljhue"})})
      :body
      first
      :success
      :username
      (#(assoc bridge :username %))))

(defn config
  "Returns the configuration information for a Bridge from the API."
  [bridge]
  (-> (build-url bridge "config")
      http/get
      :body))

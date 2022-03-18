;; # Preprocess notebook
(ns preprocess
  (:require
   [clojure.core.matrix :as m]
   [clojure.string :as str]
   [miles.preprocess.io :as preprocess.io]
   [nextjournal.clerk :as clerk])
  (:import [java.awt.image BufferedImage]))

(defn- apply-function-values
  [values]
  (reduce (fn [acc val]
            (conj acc (clojure.edn/read-string val)))
          []
          values))

(def tall-info
  (let [raw-matrix (preprocess.io/read-matrix "resources/tall_set.txt")
        samples (first raw-matrix)
        genes (->> raw-matrix rest (map first))]
    {:samples samples
     :genes genes
     :tgexp (->> (reduce (fn [acc [rowname & values]]
                          (conj acc (apply-function-values values)))
                        []
                        (rest raw-matrix))
                m/array
                m/transpose)}))

(let [width 150
      height 145
      values (-> tall-info :tgexp first)
      old-max (apply max values)
      old-min (apply min values)
      old-range (- old-max old-min)
      new-range (- 1 0)
      img (BufferedImage. width height BufferedImage/TYPE_INT_RGB)
      m (m/reshape values [width height])]
  (doseq [x (range width)
          y (range height)]
    (let [old-value (m/mget m x y)
          new-value (+ (/ (- old-value old-min) old-range) 0)]
      (.setRGB img x y (.getRGB (java.awt.Color/getHSBColor new-value new-value new-value)))))
  img)

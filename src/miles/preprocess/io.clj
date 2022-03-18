(ns miles.preprocess.io
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn read-matrix
  [file-path]
  (->> (line-seq (io/reader file-path))
       (sequence (comp (map #(str/split % #"\t"))))))

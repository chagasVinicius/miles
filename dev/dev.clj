(ns dev
  (:require
   [nextjournal.clerk :as clerk]))

(comment
  (clerk/serve! {:watch-paths ["notebooks" "src"]})

  #_(clerk/show! "notebooks/index.md")
  (clerk/show! "notebooks/preprocess.clj")
  )

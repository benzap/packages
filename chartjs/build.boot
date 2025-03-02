(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.10.4" :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "2.9.2")
(def +version+ (str +lib-version+ "-0"))

(task-options!
  pom {:project 'cljsjs/chartjs
       :version +version+
       :description "Chart.js - Simple yet flexible JavaScript charting for designers & developers
"
       :url "http://www.chartjs.org/"
       :license {"MIT" "http://opensource.org/licenses/MIT"}
       :scm {:url "https://github.com/cljsjs/packages"}})

(defn cdn-ver [file]
  (str "https://github.com/chartjs/Chart.js/releases/download/v"
       +lib-version+ "/" file))

(deftask package []
  (comp
    (download :url (cdn-ver "Chart.js"))
    (download :url (cdn-ver "Chart.min.js"))
    (sift :move
          {#"Chart.js" "cljsjs/chartjs/development/Chart.inc.js"
           #"Chart.min.js" "cljsjs/chartjs/production/Chart.min.inc.js"})
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.chartjs")
    (pom)
    (jar)
    (validate)))

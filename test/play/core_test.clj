(ns play.core-test
  (:require [clojure.test :as t]
            [ring.mock.request :as mr]
            [ring.util.response :as r]
            [cheshire.core :as json]
            [camel-snake-kebab.core :refer [->kebab-case-keyword]]
            [play.core :as sut]))

(t/deftest test-status
  (t/testing "GET /status"
    (let [result (-> ((sut/app) (mr/request :get "/status"))
                     (update :body #(json/parse-string % ->kebab-case-keyword)))
          expected (-> (r/response {:status "OK"})
                       (r/content-type "application/json; charset=utf-8"))]
      (t/is (= expected result)))))

(t/deftest test-bad-route
  (t/testing "GET /a-bad-route"
    (let [result (-> ((sut/app) (mr/request :get "/a-bad-route"))
                     (update :body #(json/parse-string % ->kebab-case-keyword)))
          expected (-> (r/response {:error "Bad route!"})
                       (r/content-type "application/json; charset=utf-8")
                       (r/status 404))]
      (t/is (= expected result))))

  (t/testing "POST /a-bad-route"
    (let [result (-> ((sut/app) (-> (mr/request :post "/a-bad-route")
                                    (mr/content-type "application/json")
                                    (mr/json-body {:data "some data"})))
                     (update :body #(json/parse-string % ->kebab-case-keyword)))
          expected (-> (r/response {:error "Bad route!"})
                       (r/content-type "application/json; charset=utf-8")
                       (r/status 404))]
      (t/is (= expected result)))))

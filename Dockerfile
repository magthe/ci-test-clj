FROM clojure:lein-2.7.1

WORKDIR /app
COPY project.clj project.clj
RUN lein deps
COPY src src
RUN lein uberjar

CMD java -jar ./target/ci-test-clj-0.1.0-SNAPSHOT-standalone.jar

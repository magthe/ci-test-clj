FROM clojure:lein-2.9.1

WORKDIR /app
COPY target/ci-test-*-standalone.jar .

CMD java -jar ./ci-test-clj-0.1.0-SNAPSHOT-standalone.jar

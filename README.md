mvn clean install -> to run tests before artifact is generated
mvn clean install -DskipTests

java -jar query-1.0-jar-with-dependencies.jar -Dapi=imdb -Dmovie="indiana jones"

java -jar query-1.0-jar-with-dependencies.jar -Dapi=spotify -Dmovie="roadhouse blues"
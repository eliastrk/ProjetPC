Setup la PostgreSQL db déployée avec Docker Compose


Start db

docker-compose up -d


Run app

mvn clean package
mvn exec:java -Dexec.mainClass=Application


Check la db

docker exec -it foodfast-db psql -U postgres -d foodfast -c "SELECT * FROM orders;"

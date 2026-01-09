# Setup la db PostgreSQL déployée avec Docker Compose


## Start db

```bash
docker-compose up -d
```

## Run app

```bash
mvn clean package
mvn exec:java -Dexec.mainClass=Application
```

## Check la db

```bash
docker exec -it foodfast-db psql -U postgres -d foodfast -c "SELECT * FROM orders;"
```

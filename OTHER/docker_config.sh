#!/usr/bin/env bash

# Con estos comandos se puede levantar una instancia de docker con la base de datos para el proyecto

docker run -d --name dbdata mysql:5.7 echo "Data-only container for YahooWeatherREST"
docker run -d -p 3306:3306 --volumes-from dbdata -e MYSQL_ROOT_PASSWORD=root --name t mysql:5.7
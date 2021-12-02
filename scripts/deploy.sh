#!/bin/bash

sudo docker ps -aq --filter "name=springbootapp" | grep -q . && docker stop springbootapp && docker rm springbootapp | true

sudo docker rmi kobeomseok95/springbootapp:latest

sudo docker pull kobeomseok95/springbootapp:latest

docker run -d -p 8080:8080 -e TZ=Asia/Seoul --name springbootapp kobeomseok95/springbootapp:latest

docker rmi -f $(docker images -f "dangling=true" -q) || true

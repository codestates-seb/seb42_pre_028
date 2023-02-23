#!/usr/bin/env bash
# DeployServer-0.0.1-SNAPSHOT.jar 을 실행
cd /home/ubuntu/build
sudo nohup java -jar server-0.0.1-SNAPSHOT.jar > /dev/null 2> /dev/null < /dev/null &
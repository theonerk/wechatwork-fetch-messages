#!/bin/bash

mvn clean package -Dmaven.test.skip=true
sudo docker build -t nike-wechatwork-messages  .

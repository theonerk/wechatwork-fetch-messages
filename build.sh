#!/bin/bash

mvn clean package -Dmaven.test.skip=true
docker build -t rongk-wechatwork-messages  .

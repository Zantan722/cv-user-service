#!/bin/bash

# 等待MySQL啟動
#echo "等待MySQL啟動..."
#while ! mysqladmin ping -h db -u root -pAaa123123 --silent; do
#    sleep 1
#done
#echo "MySQL已就緒"

# 啟動Java應用
#cd /app
#mvn liquibase:update
#echo "執行 Liquibase 更新..."
#mvn liquibase:update -Dliquibase.propertyFile=./liquibase.properties
java -jar /app/app.jar
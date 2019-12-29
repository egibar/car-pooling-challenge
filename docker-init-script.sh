#!/bin/bash
mysqld_safe --skip-grant-tables &
java -jar /opt/app/app.jar --spring.mysqlhost=${MYSQL_HOST} --spring.mysqlport=${MYSQL_PORT} --spring.mysqldb=${MYSQL_DB}
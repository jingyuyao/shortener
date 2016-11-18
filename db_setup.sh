#!/usr/bin/env bash

echo "Don't run this in prod";
read -s -p "Enter mysql root password: " mysqlpassword
mysql -e "DROP DATABASE IF EXISTS shortener_primary;" -uroot -p${mysqlpassword}
mysql -e "CREATE DATABASE shortener_primary CHARACTER SET utf8;" -uroot -p${mysqlpassword}
mysql -e "GRANT ALL PRIVILEGES ON shortener_primary.* TO 'tester'@'localhost' IDENTIFIED BY 'test';" -uroot -p${mysqlpassword}
echo "DB setup succeed."
VERSION_FILE:=target/classes/version.txt
VERSION:=${shell head -n 1 ${VERSION_FILE}}
TARGET_JAR=target/shortener-${VERSION}.jar
JAVA_TARGET:=java -jar ${TARGET_JAR}
CONFIG:=config.yml

default: package run

package:
	mvn clean package

run:
	${JAVA_TARGET} server ${CONFIG}

local:
	./db_setup.sh

migrate: package
	${JAVA_TARGET} db migrate ${CONFIG}
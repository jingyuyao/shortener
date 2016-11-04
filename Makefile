VERSION_FILE:=target/classes/version.txt
VERSION:=${shell head -n 1 ${VERSION_FILE}}
TARGET_JAR=target/shortener-${VERSION}.jar
CONFIG:=config.yml
JAVA_JAR:=java -jar

default: package run

package:
	mvn clean package

run:
	${JAVA_JAR} ${TARGET_JAR} server ${CONFIG}


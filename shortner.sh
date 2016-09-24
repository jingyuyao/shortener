#!/usr/bin/env bash

# Runs the generate jar
# e.g. ./shortner.sh server config.yml
version=$(head -n 1 target/classes/version.txt)
java -jar target/shortner-${version}.jar "$@"
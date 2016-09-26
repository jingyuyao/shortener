#!/usr/bin/env bash

# Runs the generate jar with config.yml
# e.g. ./shortener.sh server
version=$(head -n 1 target/classes/version.txt)
java -jar target/shortener-${version}.jar "$@"
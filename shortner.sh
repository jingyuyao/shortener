#!/usr/bin/env bash

# Runs the generate jar with config.yml
# e.g. ./shortner.sh server
version=$(head -n 1 target/classes/version.txt)
java -jar target/shortner-${version}.jar "$@" config.yml
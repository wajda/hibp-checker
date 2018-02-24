#!/bin/sh

JAR_FILE=target/hibp-checker-1.0.jar

if [ ! -f $JAR_FILE ]; then
    echo "Building executables..."
    mvn install
    echo "Running HIBP Checker..."
fi

java -jar $JAR_FILE
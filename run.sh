#!/bin/sh
export PORT=${PORT:-8080}
echo http://localhost:$PORT
java -jar build/libs/shop-demo-uberjar.jar

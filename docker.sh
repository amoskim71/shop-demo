#!/bin/sh
docker build -t shop-demo . 
docker run -p8080:8080 shop-demo

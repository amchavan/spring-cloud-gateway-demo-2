#!/bin/bash
ids='0 1 2 3 4 5 6 7 8 9'
for i in ${ids}
do
    curl http://localhost:9999/next-meteo
    echo
    sleep 3
done
#!/bin/bash
for i in {1..20}
do
    curl http://localhost:9999/next-meteo
    echo
    sleep 3
done

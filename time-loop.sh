#!/bin/bash

url=http://localhost:9999/current-datetime
for i in {1..50}
do
    status=$(curl -o /dev/null -I -L -s -w "%{http_code}" ${url})
    if [ "${status}" -eq "200" ] ; then
      echo -n "."
    else
      echo -n "!"
      sleep 0.25
    fi
done
echo

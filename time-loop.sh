#!/bin/bash
ids='0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 '
url=http://localhost:9999/current-datetime
for i in ${ids}
do
    status=$(curl -o -I -L -s -w "%{http_code}" ${url})
    if [ ${status} -eq "200" ] ; then
      echo -n "."
    else
      echo -n "!"
      sleep 0.25
    fi
done
echo

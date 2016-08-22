#!/bin/bash
set -m

cd DemoSite/admin
	ant tomcat &

cd ../site && \
	ant tomcat &

sleep 60

ip=$(ifconfig | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -Eo '([0-9]*\.){3}[0-9]*' | grep -v '127.0.0.1')

echo "Server started"

echo "Home: http://$ip:8080"
echo "Admin: http://$ip:8081/admin/ (Username: admin, Password: admin)"

fg %2  > /dev/null
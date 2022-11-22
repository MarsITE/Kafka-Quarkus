#!/bin/bash
for i in comment-producer comment-consumer blacklist-service 
do
	pushd $i
	mvn clean package
 	popd
done

docker-compose -f compose-all.yml up

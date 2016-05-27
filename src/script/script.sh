#!/usr/bin/env bash

subjects=(rc4 tea)
for i in "${subjects[@]}"
do
    echo $i
    mvn exec:java -Dexec.mainClass="experiment.Main" -Dexec.args="-v -s $i -run tasknumber"
    mvn exec:java -Dexec.mainClass="experiment.Main" -Dexec.args="-v -s $i -run tasksize"
done

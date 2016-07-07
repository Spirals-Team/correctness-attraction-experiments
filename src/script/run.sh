#!/usr/bin/env bash

#mvn exec:java -Dexec.mainClass="experiment.Runner -Dexec.args="  -s qs

#subjects=(quicksort zip sudoku md5 rsa rc4 canny lcs laguerre classifier)
subjects=(laguerre)

for i in "${subjects[@]}"
do
    echo $i
    mvn exec:java -Dexec.mainClass="experiment.Main" -Dexec.args="-v -s $i -size 50 -nb 25 -exp call boolean"
    mvn exec:java -Dexec.mainClass="experiment.Main" -Dexec.args="-v -s $i -size 50 -nb 25 -exp call pone"
    mvn exec:java -Dexec.mainClass="experiment.Main" -Dexec.args="-v -s $i -size 50 -nb 25 -exp call mone"
done

mvn exec:java -Dexec.mainClass="experiment.Main" -Dexec.args="-v -s btc -run bandit -policy ucb -budget time 216000000"
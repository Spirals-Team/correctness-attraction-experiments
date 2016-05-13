#!/usr/bin/env bash

i=(md5 quicksort mersenne sudoku zip)
for file in "${i[@]}" ; do
    mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -s $file -exp rnd one"
    mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -s $file -exp rnd boolean"
done

echo rsa
mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -nb 100 -s rsa -exp rnd one"
mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -nb 100 -s rsa -exp rnd boolean"

echo classifier
mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -nb 7 -s classifier -exp rnd one"
mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -nb 7 -s classifier -exp rnd boolean"

echo optimizer
mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -s optimizer -exp rnd one"
mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -s optimizer -exp rnd boolean"
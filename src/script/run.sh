#!/usr/bin/env bash

#mvn exec:java -Dexec.mainClass="experiment.Runner -Dexec.args="-v -s qs

subjects=(md5 mersenne zip sudoku)

for i in "${subjects[@]}"
do
    echo $i
    java -cp target/classes experiment.Runner -v -s $i -run tasksize
    java -cp target/classes experiment.Runner -v -s $i -run tasknumber
    java -cp target/classes experiment.Runner -v -s $i -exp call one
    java -cp target/classes experiment.Runner -v -s $i -exp call magnitude
    java -cp target/classes experiment.Runner -v -s $i -exp rnd one
    java -cp target/classes experiment.Runner -v -s $i -exp hm
    java -cp target/classes experiment.Runner -v -s $i -exp call boolean
    java -cp target/classes experiment.Runner -v -s $i -exp rnd boolean
done

subjects=(rsa bayes optimizer)
for i in "${subjects[@]}"
do
    echo $i
    mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -s $i -run tasksize"
    mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -s $i -run tasknumber"
    mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -s $i -exp call one"
    mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -s $i -exp call magnitude"
    mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -s $i -exp rnd one"
    mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -s $i -exp hm"
    mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -s $i -exp call boolean"
    mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -s $i -exp rnd boolean"
done
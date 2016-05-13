#!/usr/bin/env bash

mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -nb 1 -time 60 -s torrent -exp call boolean"
mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -nb 1 -time 60 -s torrent -exp rnd one"
mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -nb 1 -time 60 -s torrent -exp rnd boolean"
mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -nb 1 -time 60 -s torrent -exp call magnitude"

#!/usr/bin/env bash

mvn exec:java -Dexec.mainClass="experiment.Runner" -Dexec.args="-v -time 60 -nb 5 -s torrent -exp call boolean"

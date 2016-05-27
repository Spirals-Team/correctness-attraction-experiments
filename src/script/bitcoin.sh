#!/usr/bin/env bash

mvn exec:java -Dexec.mainClass="experiment.Main" -Dexec.args="-v -s bitcoin -size 3 -exp rnd one"

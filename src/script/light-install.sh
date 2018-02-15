#!/usr/bin/env bash

#Install jPerturb
git clone http://github.com/Spirals-Team/jPerturb.git 2>/dev/null
cd jPerturb
git pull
mvn clean install
cd ..

mvn compile

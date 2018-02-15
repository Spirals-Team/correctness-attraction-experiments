#!/usr/bin/env bash

#Install jPerturb
git clone http://github.com/Spirals-Team/jPerturb.git 2>/dev/null
cd jPerturb
git pull
mvn clean install
cd ..

#Now RM huge packages
path=src/main/java
# packages=(bitcoin classifier laguerre rc4 linreg rsa sat simplex torrent)
packages=(laguerre simplex)
for file in "${packages[@]}"
do
    echo "rm -rf $path/$file"
    rm -rf $path/$file
done

mvn compile

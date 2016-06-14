#!/usr/bin/env bash

#Install jPerturb
git clone http://github.com/Spirals-Team/jPerturb.git
cp -r jPerturb/src/main/java/perturbation src/main/java/

#Now RM huge packages
path=src/main/java
packages=(bitcoin classifier laguerre rc4 rsa sat simplex torrent)
for file in "${packages[@]}"
do
    echo "rm -rf $path/$file"
    rm -rf $path/$file
done

#Rewriting the pom.xml in order to remove dependencies
head -n 28 pom.xml > npom.xml
echo "</dependencies>" >> npom.xml
echo "</project>" >> npom.xml
rm -f pom.xml
mv npom.xml pom.xml

mvn compile
#!/usr/bin/env bash

spoon=spoon-core-5.1.0-jar-with-dependencies.jar
jPerturb=jPerturb/target/jPerturb-0.0.1-SNAPSHOT.jar
perturbation=jPerturb/src/main/java/perturbation/
processors=processor.AssignmentProcessor:processor.VariableCaster:processor.PerturbationProcessor

git clone https://github.com/mpetazzoni/ttorrent.git
cd ttorrent
mvn package -Dmaven.javadoc.skip=true
lib=($(mvn dependency:build-classpath | grep -v '\[INFO\]'))
cd ..
mv ttorrent/core/target/ttorrent-core-1.6-SNAPSHOT.jar ./

path=ttorrent/core/src/main/java/
file=com/turn/torrent/client/Client.java
java -classpath $lib:ttorrent-core-1.6-SNAPSHOT.jar:$spoon:$jPerturb spoon.Launcher -i $path/$file:$perturbation -o $path -p $processors

cp -R ttorrent/core/src/main/java/com/ src/main/java/
#rm -Rf ttorrent

#!/usr/bin/env bash

spoon=spoon-core-5.1.0-jar-with-dependencies.jar
jPerturb=jPerturb/target/jPerturb-0.0.1-SNAPSHOT.jar
perturbation=jPerturb/src/main/java/perturbation/
processors=processor.AssignmentProcessor:processor.VariableCaster:processor.PerturbationProcessor

git clone https://github.com/mpetazzoni/ttorrent.git
cd ttorrent
mvn package -Dmaven.javadoc.skip=true
cd ..
mv ttorrent/core/target/ttorrent-core-1.6-SNAPSHOT.jar ./

#M2_REPO=($(mvn help:evaluate -Dexpression=settings.localRepository | grep -v '\[INFO\]' | tail -n 1))
M2_REPO=$HOME/.m2
lib=$M2_REPO/commons-io/commons-io/2.4/commons-io-2.4.jar:$M2_REPO/org/simpleframework/simple/4.1.21/simple-4.1.21.jar:$M2_REPO/org/slf4j/slf4j-api/1.6.4/slf4j-api-1.6.4.jar:$M2_REPO/org/slf4j/slf4j-log4j12/1.6.4/slf4j-log4j12-1.6.4.jar

path=ttorrent/core/src/main/java/
files=(com/turn/ttorrent/client/Client.java)

for i in "${files[@]}"
do
   echo $i
   java -classpath $lib:ttorrent-core-1.6-SNAPSHOT.jar:$spoon:$jPerturb spoon.Launcher -i $path/$i:$perturbation -o $path -p $processors
done

cp -R ttorrent/core/src/main/java/com/ src/main/java/
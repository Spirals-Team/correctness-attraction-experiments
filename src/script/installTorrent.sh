#!/usr/bin/env bash

spoon=spoon-core-5.1.0-jar-with-dependencies.jar
jPerturb=jPerturb/target/jPerturb-0.0.1-SNAPSHOT.jar
perturbation=jPerturb/src/main/java/perturbation/
processors=processor.AssignmentProcessor:processor.VariableCaster:processor.PerturbationProcessor

git clone https://github.com/mpetazzoni/ttorrent.git
cd ttorrent
mvn package -Dmaven.javadoc.skip=true -q
lib=($(mvn dependency:build-classpath | grep -v '\[INFO\]'))
echo $lib
cd ..

ttorrentjar=ttorrent/core/target/ttorrent-core-1.6-SNAPSHOT.jar
path=ttorrent/core/src/main/java/
files=(com/turn/ttorrent/)
output=src/main/java/

echo classpath is
echo $lib
echo $jPerturb
echo $spoon
echo $ttorrentjar

for i in "${files[@]}"
do
   echo spooning $i
   echo java -classpath $lib:$ttorrentjar:$spoon:$jPerturb spoon.Launcher -i $path/$i:$perturbation -o $output -p $processors
   java -classpath $lib:$ttorrentjar:$spoon:$jPerturb spoon.Launcher -i $path/$i:$perturbation -o $output -p $processors
done

#cp -R ttorrent/core/src/main/java/com/ src/main/java/
#rm -Rf ttorrent

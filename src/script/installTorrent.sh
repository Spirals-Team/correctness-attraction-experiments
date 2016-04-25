#!/usr/bin/env bash

spoon=spoon-core-5.1.0-jar-with-dependencies.jar
jPerturb=jPerturb/target/jPerturb-0.0.1-SNAPSHOT.jar
perturbation=jPerturb/src/main/java/perturbation/
processors=processor.AssignmentProcessor:processor.VariableCaster:processor.PerturbationProcessor
m2_repo=$HOME/.m2/repository

git clone https://github.com/mpetazzoni/ttorrent.git
cd ttorrent
mvn package -Dmaven.javadoc.skip=true -q

lib=$m2_repo/com/beust/jcommander/1.12/jcommander-1.12.jar:$m2_repo/commons-io/commons-io/2.4/commons-io-2.4.jar:$m2_repo/junit/junit/3.8.1/junit-3.8.1.jar:$m2_repo/org/beanshell/bsh/2.0b4/bsh-2.0b4.jar:$m2_repo/org/simpleframework/simple/4.1.21/simple-4.1.21.jar:$m2_repo/org/slf4j/slf4j-api/1.6.4/slf4j-api-1.6.4.jar:$m2_repo/org/testng/testng/6.1.1/testng-6.1.1.jar:$m2_repo/org/yaml/snakeyaml/1.6/snakeyaml-1.6.jar
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

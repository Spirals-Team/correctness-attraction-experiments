#!/usr/bin/env bash

jPerturb=jPerturb/target/jPerturb-0.0.1-SNAPSHOT-jar-with-dependencies.jar
perturbation=jPerturb/src/main/java/perturbation/
processors=processor.AssignmentProcessor:processor.VariableCaster:processor.PerturbationProcessor
m2_repo=$HOME/.m2/repository

#Install Sponn
git clone https://github.com/INRIA/spoon.git
cd spoon
mvn install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true

cd ..

#Install jPerturb
git clone http://github.com/Spirals-Team/jPerturb.git
cd jPerturb
mvn package -Dmaven.test.skip=true

cd ..

#Spoon little exp we spoon only Quicksort in order to add perturbation package to jPerturb-experiments.
i=(quicksort/QuickSort.java)
#i=(quicksort/QuickSort.java md5/MD5.java mersenne/MersenneTwister.java sudoku/Sudoku.java zip/LZW.java canny/CannyEdgeDetector.java lcs/LCS.java)
for file in "${i[@]}"
do
    echo "java -jar $jPerturb -i src/main/java/$file:$perturbation -o src/main/java --with-imports -p $processors"
    java -jar $jPerturb -i src/main/java/$file:$perturbation -o src/main/java --with-imports -p processor.RenameProcessor:$processors
done

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
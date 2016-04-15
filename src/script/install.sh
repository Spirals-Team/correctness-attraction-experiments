#!/usr/bin/env bash

git clone http://github.com/Spirals-Team/jPerturb.git
cd jPerturb
mvn install
cd ..
wget https://gforge.inria.fr/frs/download.php/latestzip/86/Spoon-latest.zip
unzip Spoon-latest.zip
wget http://mirror.trisect.eu/Apache//commons/math/source/commons-math3-3.6.1-src.zip
unzip commons-math3-3.6.1-src.zip 2>/dev/null 1>/dev/null
cp -R commons-math3-3.6.1-src/src/main/java/org/ src/main/java/
rm -Rf commons-math3-3.6.1-src
#java -classpath spoon-core-5.1.0-jar-with-dependencies.jar:jPerturb/target/jPerturb-0.0.1-SNAPSHOT.jar spoon.Launcher -i resources/java:jPerturb/src/main/java/perturbation/ -o src/main/java --with-imports --compile -d bin -p processor.AssignmentProcessor:processor.VariableCaster:processor.PerturbationProcessor
java -classpath spoon-core-5.1.0-jar-with-dependencies.jar:jPerturb/target/jPerturb-0.0.1-SNAPSHOT.jar spoon.Launcher -i resources/java:jPerturb/src/main/java/perturbation/ -o src/main/java -x --with-imports -p processor.AssignmentProcessor:processor.VariableCaster:processor.PerturbationProcessor
mvn compile
gcc ./src/script/emps.c -o emps
./src/script/uncompressh.sh
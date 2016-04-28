#!/usr/bin/env bash

jPerturb=jPerturb/target/jPerturb-0.0.1-SNAPSHOT-jar-with-dependencies.jar
perturbation=jPerturb/src/main/java/perturbation/
processors=processor.AssignmentProcessor:processor.VariableCaster:processor.PerturbationProcessor

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

#Install Commons Math
wget http://mirror.trisect.eu/Apache/commons/math/source/commons-math3-3.6.1-src.zip
unzip commons-math3-3.6.1-src.zip 2>/dev/null 1>/dev/null
cd commons-math3-3.6.1-src
mvn package -DskipTests -Dmaven.javadoc.skip=true
jar_math=commons-math3-3.6.1-src/target/commons-math3-3.6.1.jar
path_math=commons-math3-3.6.1-src/src/main/java/

cd ..

#Spooning math
i=org/apache/commons/math3/optim/linear/SimplexSolver.java
echo "java -cp $jar_math:$jPerturb spoon.Launcher -i $path_math/$i:$perturbation -o $path_math --with-imports -p processor.RenameProcessor:$processors"
java -cp $jar_math:$jPerturb spoon.Launcher -i $path_math/$i:$perturbation -o $path_math --with-imports -p processor.RenameProcessor:$processors

#Install Instrumented Math
cd commons-math3-3.6.1-src
mvn install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -q

cd ..

mvn compile
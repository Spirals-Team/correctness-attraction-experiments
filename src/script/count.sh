#!/usr/bin/env bash

jPerturb=jPerturb/target/jPerturb-0.0.1-SNAPSHOT-jar-with-dependencies.jar
perturbation=jPerturb/src/main/java/perturbation/
processors=processor.AssignmentProcessor:processor.VariableCaster:processor.PerturbationProcessor
m2_repo=$HOME/.m2/repository


path=commons-math3-3.6.1-src/src/main/java/
i=$path/org/apache/commons/math3/analysis/solvers/LaguerreSolver.java
echo $path/$i
java -jar $jPerturb -i $i -x -p processor.CountProcessor
#!/usr/bin/env bash

jPerturb=jPerturb/target/jPerturb-0.0.1-SNAPSHOT-jar-with-dependencies.jar
perturbation=jPerturb/src/main/java/perturbation/
processors=processor.AssignmentProcessor:processor.VariableCaster:processor.PerturbationProcessor
m2_repo=$HOME/.m2/repository

path=bc-java/core/src/main/java
i=org/
echo $path/$i
java -jar $jPerturb -i $path/$i -o $path/$i -x -p processor.CountProcessor
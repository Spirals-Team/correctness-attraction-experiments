#!/usr/bin/env bash

jPerturb=jPerturb/target/jPerturb-0.0.1-SNAPSHOT-jar-with-dependencies.jar
perturbation=jPerturb/src/main/java/perturbation/
processors=processor.AssignmentProcessor:processor.VariableCaster:processor.PerturbationProcessor
m2_repo=$HOME/.m2/repository

#Install jPerturb
i=(md5/MD5.java quicksort/QuickSort.java mersenne/MersenneTwister.java sudoku/Sudoku.java zip/LZW.java)
for file in "${i[@]}"
do
    echo "java -jar $jPerturb -i src/main/java/$file:$perturbation -o src/main/java --with-imports -p $processors"
    java -jar $jPerturb -i src/main/java/$file:$perturbation -o src/main/java --with-imports -p processor.RenameProcessor:$processors
done
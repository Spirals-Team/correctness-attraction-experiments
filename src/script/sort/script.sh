#/bin/bash

git clone http://github.com/Spirals-Team/jPerturb.git
cd jPerturb
mvn install
cd ..

wget https://gforge.inria.fr/frs/download.php/latestzip/86/Spoon-latest.zip
unzip Spoon-latest.zip

java -classpath spoon-core-5.1.0-jar-with-dependencies.jar:jPerturb/target/jPerturb-0.0.1-SNAPSHOT.jar spoon.Launcher -i resources/sort/:jPerturb/src/main/java/perturbation/ -o src/main/java --with-imports --compile -d bin -p processor.AssignmentProcessor:processor.VariableCaster:processor.PerturbationProcessor

javac -d bin -cp bin src/main/java/sort/*.java

java -cp bin sort.Main tmp

diff results/sort/resultNCall results/sort/tmpresultNCall | wc -l

#clean
rm -Rf jPerturb/
rm -Rf bin/
rm -f results/sort/tmp*
rm -Rf src/main/java/perturbation
rm -f src/main/java/sort/QuickSort.java
rm -f spoon*
rm -f Spoon-latest.zip
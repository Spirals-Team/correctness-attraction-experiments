#!/usr/bin/env bash

spoon=spoon-core-5.1.0-jar-with-dependencies.jar
jPerturb=jPerturb/target/jPerturb-0.0.1-SNAPSHOT.jar
perturbation=jPerturb/src/main/java/perturbation/
processors=processor.AssignmentProcessor:processor.VariableCaster:processor.PerturbationProcessor
m2_repo=$HOME/.m2/repository

git clone https://github.com/bitcoinj/bitcoinj.git
cd bitcoinj
git checkout release-0.13
mvn clean package -q

lib=$m2_repo/junit/junit/4.12/junit-4.12.jar:$m2_repo/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:$m2_repo/org/easymock/easymock/3.2/easymock-3.2.jar:$m2_repo/cglib/cglib-nodep/2.2.2/cglib-nodep-2.2.2.jar:$m2_repo/org/objenesis/objenesis/1.3/objenesis-1.3.jar:$m2_repo/org/slf4j/slf4j-jdk14/1.7.7/slf4j-jdk14-1.7.7.jar:$m2_repo/com/fasterxml/jackson/core/jackson-databind/2.5.2/jackson-databind-2.5.2.jar:$m2_repo/com/fasterxml/jackson/core/jackson-annotations/2.5.0/jackson-annotations-2.5.0.jar:$m2_repo/com/fasterxml/jackson/core/jackson-core/2.5.1/jackson-core-2.5.1.jar:$m2_repo/com/h2database/h2/1.3.167/h2-1.3.167.jar:$m2_repo/com/madgag/spongycastle/core/1.51.0.0/core-1.51.0.0.jar:$m2_repo/com/google/protobuf/protobuf-java/2.5.0/protobuf-java-2.5.0.jar:$m2_repo/com/google/guava/guava/16.0.1/guava-16.0.1.jar:$m2_repo/com/google/code/findbugs/jsr305/2.0.1/jsr305-2.0.1.jar:$m2_repo/net/jcip/jcip-annotations/1.0/jcip-annotations-1.0.jar:$m2_repo/com/lambdaworks/scrypt/1.4.0/scrypt-1.4.0.jar:$m2_repo/postgresql/postgresql/9.1-901.jdbc4/postgresql-9.1-901.jdbc4.jar:$m2_repo/mysql/mysql-connector-java/5.1.33/mysql-connector-java-5.1.33.jar:$m2_repo/org/fusesource/leveldbjni/leveldbjni-all/1.8/leveldbjni-all-1.8.jar:$m2_repo/org/bitcoinj/orchid/1.1.1/orchid-1.1.1.jar:$m2_repo/com/squareup/okhttp/okhttp/2.2.0/okhttp-2.2.0.jar:$m2_repo/com/squareup/okio/okio/1.2.0/okio-1.2.0.jar:$m2_repo/org/slf4j/slf4j-api/1.7.7/slf4j-api-1.7.7.jar
echo $lib
cd ..

bitcoinjjar=bitcoinj/core/target/bitcoinj-core-0.13.6.jar
path=bitcoinj/core/src/main/java/
files=(org/)
output=src/main/java/

echo classpath is
echo $lib
echo $jPerturb
echo $spoon
echo $bitcoinjjar

for i in "${files[@]}"
do
   echo spooning $i
   java -classpath $lib:$bitcoinjjar:$spoon:$jPerturb spoon.Launcher --with-imports -i $path/$i:$perturbation -o $output -p $processors
done
#!/usr/bin/env bash


jPerturb=jPerturb/target/jPerturb-0.0.1-SNAPSHOT-jar-with-dependencies.jar
perturbation=jPerturb/src/main/java/perturbation/
m2_repo=$HOME/.m2/repository

#####
#Install SAT4J
#####
wget http://download.forge.ow2.org/sat4j/sat4j-core-v20130525.zip
unzip sat4j-core-v20130525.zip 1>/dev/null
mkdir sat-src
cp org.sat4j.core-src.jar sat-src/
cd sat-src
jar -xf org.sat4j.core-src.jar
cd ..

path_sat=sat-src
file=$path_sat/org/sat4j/minisat/core/Solver.java
echo $file
#Spooning sat4j
echo "java -cp $jPerturb:org.sat4j.core.jar main.Main -type IntNum:boolean -i $file -o $path_sat -x"
java -cp $jPerturb:org.sat4j.core.jar main.Main -type IntNum:boolean -i $file -o $path_sat -x

cp -r $perturbation $path_sat/

#Install instrumented sat4j
cd sat-src
find -name "*.java" > sources.txt
mkdir bin
javac @sources.txt -d bin
cd bin
jar cvf ../sat4j-core-2.3.5.jar org
cd ../..
mvn install:install-file -Dfile=sat-src/sat4j-core-2.3.5.jar -DgroupId=org.sat4j -DartifactId=core -Dversion=2.3.5 -Dpackaging=jar
cd resources/sat/
tar -xf CBS_k3_n100_m403_b10.tar.gz
cd ../..

#####
#Install ttorrent
#####
git clone https://github.com/mpetazzoni/ttorrent.git
cd ttorrent
git reset --hard e1daf413c9700607b2074cc3c566389cfaae756f
mvn package -Dmaven.javadoc.skip=true -DskipTests
mvn_dep_torrent=$m2_repo/com/beust/jcommander/1.12/jcommander-1.12.jar:$m2_repo/commons-io/commons-io/2.4/commons-io-2.4.jar:$m2_repo/junit/junit/3.8.1/junit-3.8.1.jar:$m2_repo/org/beanshell/bsh/2.0b4/bsh-2.0b4.jar:$m2_repo/org/simpleframework/simple/4.1.21/simple-4.1.21.jar:$m2_repo/org/slf4j/slf4j-api/1.6.4/slf4j-api-1.6.4.jar:$m2_repo/org/testng/testng/6.1.1/testng-6.1.1.jar:$m2_repo/org/yaml/snakeyaml/1.6/snakeyaml-1.6.jar
cd ..

#Spooning ttorrent
path_torrent=ttorrent/core/src/main/java/
i=com/turn/ttorrent/
jar_torrent=ttorrent/core/target/ttorrent-core-1.6-SNAPSHOT.jar
echo "java -cp $mvn_dep_torrent:$jar_torrent:$jPerturb main.Main -type IntNum:boolean -i $path_torrent/$i -o $path_torrent"
java -cp $mvn_dep_torrent:$jar_torrent:$jPerturb main.Main -type IntNum:boolean -i $path_torrent/$i -o $path_torrent

cp -r $perturbation $path_torrent/

cd ttorrent
mvn install -Dmaven.javadoc.skip=true -DskipTests
cd ..

#####
#Install Bitcoinj
#####
git clone https://github.com/bitcoinj/bitcoinj.git
cd bitcoinj
git checkout release-0.13
git reset --hard fc82bece027ff19afd2a9c51a5739f595629f347
mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true
cd ..

#Spooning Bitcoinj
mvn_dep_bitcoin=$m2_repo/junit/junit/4.12/junit-4.12.jar:$m2_repo/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:$m2_repo/org/easymock/easymock/3.2/easymock-3.2.jar:$m2_repo/cglib/cglib-nodep/2.2.2/cglib-nodep-2.2.2.jar:$m2_repo/org/objenesis/objenesis/1.3/objenesis-1.3.jar:$m2_repo/org/slf4j/slf4j-jdk14/1.7.7/slf4j-jdk14-1.7.7.jar:$m2_repo/com/fasterxml/jackson/core/jackson-databind/2.5.2/jackson-databind-2.5.2.jar:$m2_repo/com/fasterxml/jackson/core/jackson-annotations/2.5.0/jackson-annotations-2.5.0.jar:$m2_repo/com/fasterxml/jackson/core/jackson-core/2.5.1/jackson-core-2.5.1.jar:$m2_repo/com/h2database/h2/1.3.167/h2-1.3.167.jar:$m2_repo/com/madgag/spongycastle/core/1.51.0.0/core-1.51.0.0.jar:$m2_repo/com/google/protobuf/protobuf-java/2.5.0/protobuf-java-2.5.0.jar:$m2_repo/com/google/guava/guava/16.0.1/guava-16.0.1.jar:$m2_repo/com/google/code/findbugs/jsr305/2.0.1/jsr305-2.0.1.jar:$m2_repo/net/jcip/jcip-annotations/1.0/jcip-annotations-1.0.jar:$m2_repo/com/lambdaworks/scrypt/1.4.0/scrypt-1.4.0.jar:$m2_repo/postgresql/postgresql/9.1-901.jdbc4/postgresql-9.1-901.jdbc4.jar:$m2_repo/mysql/mysql-connector-java/5.1.33/mysql-connector-java-5.1.33.jar:$m2_repo/org/fusesource/leveldbjni/leveldbjni-all/1.8/leveldbjni-all-1.8.jar:$m2_repo/org/bitcoinj/orchid/1.1.1/orchid-1.1.1.jar:$m2_repo/com/squareup/okhttp/okhttp/2.2.0/okhttp-2.2.0.jar:$m2_repo/com/squareup/okio/okio/1.2.0/okio-1.2.0.jar:$m2_repo/org/slf4j/slf4j-api/1.7.7/slf4j-api-1.7.7.jar
jar_bitcoin=bitcoinj/core/target/bitcoinj-core-0.13.6.jar
path_bitcoin=bitcoinj/core/src/main/java/
i=$path_bitcoin/org/bitcoinj
echo "java -cp $mvn_dep_bitcoin:$jar_bitcoin:$jPerturb main.Main -type IntNum:boolean -x -i $i -o $path_bitcoin"
java -cp $mvn_dep_bitcoin:$jar_bitcoin:$jPerturb main.Main -type IntNum:boolean -x -i $i -o $path_bitcoin

cp -r $perturbation $path_bitcoin

#Install instrumented Bitcoinj
cd bitcoinj
mvn install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true
cd ..

#Install bitcoinD
wget https://bitcoin.org/bin/bitcoin-core-0.12.1/bitcoin-0.12.1-linux64.tar.gz
tar -xf bitcoin-0.12.1-linux64.tar.gz

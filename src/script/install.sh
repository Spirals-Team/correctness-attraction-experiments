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

#Spoon little exp
i=(md5/MD5.java quicksort/QuickSort.java mersenne/MersenneTwister.java sudoku/Sudoku.java zip/LZW.java)
for file in "${i[@]}"
do
    echo "java -jar $jPerturb -i src/main/java/$file:$perturbation -o src/main/java --with-imports -p $processors"
    java -jar $jPerturb -i src/main/java/$file:$perturbation -o src/main/java --with-imports -p processor.RenameProcessor:$processors
done

#Install Commons Math
wget http://mirror.trisect.eu/Apache/commons/math/source/commons-math3-3.6.1-src.zip
unzip commons-math3-3.6.1-src.zip 2>/dev/null 1>/dev/null
cd commons-math3-3.6.1-src
mvn package -DskipTests -Dmaven.javadoc.skip=true
jar_math=commons-math3-3.6.1-src/target/commons-math3-3.6.1.jar
path_math=commons-math3-3.6.1-src/src/main/java/
cd ..

#Spooning math
simplex=org/apache/commons/math3/optim/linear/SimplexSolver.java
laguerre=org/apache/commons/math3/analysis/solvers/LaguerreSolver.java
echo "java -cp $jar_math:$jPerturb spoon.Launcher -i $path_math/$simplex:$perturbation -o $path_math --with-imports -p processor.RenameProcessor:$processors"
java -cp $jar_math:$jPerturb spoon.Launcher -i $path_math/$simplex:$perturbation -o $path_math --with-imports -p processor.RenameProcessor:$processors
echo "java -cp $jar_math:$jPerturb spoon.Launcher -i $path_math/$laguerre:$perturbation -o $path_math --with-imports -p $processors"
java -cp $jar_math:$jPerturb spoon.Launcher -i $path_math/$laguerre:$perturbation -o $path_math --with-imports -p $processors

#Install Instrumented Math
cd commons-math3-3.6.1-src
mvn install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -q
cd ..

#Install Weka
wget http://downloads.sourceforge.net/project/weka/weka-3-8/3.8.0/weka-3-8-0.zip
unzip weka-3-8-0.zip 1>/dev/null
cd weka-3-8-0
mkdir src
cp weka-src.jar src/
cd src
unzip weka-src.jar 1>/dev/null
lib_weka=$(ls lib)
lib_weka=$(echo $lib_weka | tr " " :)

cd ../../

mvn_dep_weka=$m2_repo/com/github/fommil/jniloader/1.1/jniloader-1.1.jar:$m2_repo/com/github/fommil/netlib/all/1.1.2/all-1.1.2.pom:$m2_repo/com/github/fommil/netlib/core/1.1/core-1.1.jar:$m2_repo/com/github/fommil/netlib/native_ref-java/1.1/native_ref-java-1.1.jar:$m2_repo/com/github/fommil/netlib/native_system-java/1.1/native_system-java-1.1.jar:$m2_repo/com/github/fommil/netlib/netlib-native_ref-linux-armhf/1.1/netlib-native_ref-linux-armhf-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_ref-linux-i686/1.1/netlib-native_ref-linux-i686-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_ref-linux-x86_64/1.1/netlib-native_ref-linux-x86_64-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_ref-osx-x86_64/1.1/netlib-native_ref-osx-x86_64-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_ref-win-i686/1.1/netlib-native_ref-win-i686-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_ref-win-x86_64/1.1/netlib-native_ref-win-x86_64-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_system-linux-armhf/1.1/netlib-native_system-linux-armhf-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_system-linux-i686/1.1/netlib-native_system-linux-i686-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_system-linux-x86_64/1.1/netlib-native_system-linux-x86_64-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_system-osx-x86_64/1.1/netlib-native_system-osx-x86_64-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_system-win-i686/1.1/netlib-native_system-win-i686-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_system-win-x86_64/1.1/netlib-native_system-win-x86_64-1.1-natives.jar:$m2_repo/com/googlecode/matrix-toolkits-java/mtj/1.0.4/mtj-1.0.4.jar:$m2_repo/com/googlecode/netlib-java/netlib-java/1.1/netlib-java-1.1.jar:$m2_repo/junit/junit/3.8.2/junit-3.8.2.jar:$m2_repo/net/sourceforge/f2j/arpack_combined_all/0.1/arpack_combined_all-0.1.jar:$m2_repo/nz/ac/waikato/cms/weka/thirdparty/bounce/0.18/bounce-0.18.jar:$m2_repo/nz/ac/waikato/cms/weka/thirdparty/java-cup-11b/2015.03.26/java-cup-11b-2015.03.26.jar:$m2_repo/nz/ac/waikato/cms/weka/thirdparty/java-cup-11b-runtime/2015.03.26/java-cup-11b-runtime-2015.03.26.jar:$m2_repo/org/apache/commons/commons-compress/1.10/commons-compress-1.10.jar
jar_weka=weka-3-8-0/weka.jar
path_weka=weka-3-8-0/src/src/main/java/

#Spooning Weka
i=weka/experiment/CrossValidationResultProducer.java
echo "java -classpath $jar_weka:$mvn_dep_weka:$lib_weka:$jPerturb spoon.Launcher -i $path_weka/$i:$perturbation -o $path_weka --with-imports -p $processors"
java -classpath $jar_weka:$mvn_dep_weka:$lib_weka:$jPerturb spoon.Launcher -i $path_weka/$i:$perturbation -o $path_weka --with-imports -p $processors
cd weka-3-8-0/src
mvn install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true
cd ../../

#Install ttorrent
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
echo "java -classpath $mvn_dep_torrent:$jar_torrent:$jPerturb spoon.Launcher -i $path_torrent/$i:$perturbation -o $path_torrent --with-imports -p $processors"
java -classpath $mvn_dep_torrent:$jar_torrent:$jPerturb spoon.Launcher -i $path_torrent/$i:$perturbation -o $path_torrent --with-imports -p $processors

cd ttorrent
mvn install -Dmaven.javadoc.skip=true -DskipTests
cd ..

#Install Bitcoinj
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
i=$path_bitcoin/org/bitcoinj/core/ECKey.java:$path_bitcoin/org/bitcoinj/crypto/DeterministicKey.java:$path_bitcoin/org/bitcoinj/core/Sha256Hash.java:$path_bitcoin/org/bitcoinj/wallet/BasicKeyChain.java:$path_bitcoin/org/bitcoinj/core/UnsafeByteArrayOutputStream.java:$path_bitcoin/org/bitcoinj/script/ScriptChunk.java
#i=$path_bitcoin/org/bitcoinj
echo "java -classpath $mvn_dep_bitcoin:$jar_bitcoin:$jPerturb spoon.Launcher --with-imports -i $path_bitcoin/$i:$perturbation -o $path_bitcoin -p $processors"
java -classpath $mvn_dep_bitcoin:$jar_bitcoin:$jPerturb spoon.Launcher --with-imports -x -i $i:$perturbation -o $path_bitcoin -p $processors

#Install instrumented Bitcoinj
cd bitcoinj
mvn install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true
cd ..

#Install bitcoinD
wget https://bitcoin.org/bin/bitcoin-core-0.12.1/bitcoin-0.12.1-linux64.tar.gz
tar -xf bitcoin-0.12.1-linux64.tar.gz

#Install Bouncy Castle
git clone https://github.com/bcgit/bc-java.git
cd bc-java
gradle core:jar
cd ..


#Install Bouncy Castle
git clone https://github.com/bcgit/bc-java.git/
cd bc-java
git reset --hard 3db7ef3619f0e8e5d1521b596e8305d1de989499
gradle core:jar
cd ..

#Spooning Bouncy Castle
mvn_dep_bitcoin=$m2_repo/junit/junit/4.12/junit-4.12.jar:$m2_repo/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:$m2_repo/org/easymock/easymock/3.2/easymock-3.2.jar:$m2_repo/cglib/cglib-nodep/2.2.2/cglib-nodep-2.2.2.jar:$m2_repo/org/objenesis/objenesis/1.3/objenesis-1.3.jar:$m2_repo/org/slf4j/slf4j-jdk14/1.7.7/slf4j-jdk14-1.7.7.jar:$m2_repo/com/fasterxml/jackson/core/jackson-databind/2.5.2/jackson-databind-2.5.2.jar:$m2_repo/com/fasterxml/jackson/core/jackson-annotations/2.5.0/jackson-annotations-2.5.0.jar:$m2_repo/com/fasterxml/jackson/core/jackson-core/2.5.1/jackson-core-2.5.1.jar:$m2_repo/com/h2database/h2/1.3.167/h2-1.3.167.jar:$m2_repo/com/madgag/spongycastle/core/1.51.0.0/core-1.51.0.0.jar:$m2_repo/com/google/protobuf/protobuf-java/2.5.0/protobuf-java-2.5.0.jar:$m2_repo/com/google/guava/guava/16.0.1/guava-16.0.1.jar:$m2_repo/com/google/code/findbugs/jsr305/2.0.1/jsr305-2.0.1.jar:$m2_repo/net/jcip/jcip-annotations/1.0/jcip-annotations-1.0.jar:$m2_repo/com/lambdaworks/scrypt/1.4.0/scrypt-1.4.0.jar:$m2_repo/postgresql/postgresql/9.1-901.jdbc4/postgresql-9.1-901.jdbc4.jar:$m2_repo/mysql/mysql-connector-java/5.1.33/mysql-connector-java-5.1.33.jar:$m2_repo/org/fusesource/leveldbjni/leveldbjni-all/1.8/leveldbjni-all-1.8.jar:$m2_repo/org/bitcoinj/orchid/1.1.1/orchid-1.1.1.jar:$m2_repo/com/squareup/okhttp/okhttp/2.2.0/okhttp-2.2.0.jar:$m2_repo/com/squareup/okio/okio/1.2.0/okio-1.2.0.jar:$m2_repo/org/slf4j/slf4j-api/1.7.7/slf4j-api-1.7.7.jar
jar_bc=bc-java/core/build/libs/core-1.52.jar
path_bc=bc-java/core/src/main/java/
i=$path_bc/org/bouncycastle/crypto/engines/RSACoreEngine.java:$path_bc/org/bouncycastle/crypto/engines/RC4Engine.java
echo "java -classpath $jar_bc:$jPerturb spoon.Launcher --with-imports -i $path_bc/$i:$perturbation -o $path_bc -p $processors"
java -classpath $jar_bc:$jPerturb spoon.Launcher --with-imports -i $i:$perturbation -o $path_bc -p $processors

cd bc-java
gradle core:clean
gradle core:jar
cd ..
mvn install:install-file -Dfile=$jar_bc -DgroupId=org.bouncycastle -DartifactId=core -Dversion=1.52 -Dpackaging=jar
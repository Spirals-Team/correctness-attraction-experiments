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

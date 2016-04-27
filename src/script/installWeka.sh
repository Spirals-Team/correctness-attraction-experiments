#!/usr/bin/env bash

spoon=../../spoon-core-5.2.0-SNAPSHOT-jar-with-dependencies.jar
jPerturb=../../jPerturb/target/jPerturb-0.0.1-SNAPSHOT.jar
perturbation=../../jPerturb/src/main/java/perturbation/
processors=processor.AssignmentProcessor:processor.VariableCaster:processor.PerturbationProcessor

wget http://downloads.sourceforge.net/project/weka/weka-3-8/3.8.0/weka-3-8-0.zip
unzip weka-3-8-0.zip 1>/dev/null
cd weka-3-8-0
mkdir src
cp weka-src.jar src/
cd src
unzip weka-src.jar 1>/dev/null

m2_repo=$HOME/.m2/repository

mvn_dependencies=$m2_repo/com/github/fommil/jniloader/1.1/jniloader-1.1.jar:$m2_repo/com/github/fommil/netlib/all/1.1.2/all-1.1.2.pom:$m2_repo/com/github/fommil/netlib/core/1.1/core-1.1.jar:$m2_repo/com/github/fommil/netlib/native_ref-java/1.1/native_ref-java-1.1.jar:$m2_repo/com/github/fommil/netlib/native_system-java/1.1/native_system-java-1.1.jar:$m2_repo/com/github/fommil/netlib/netlib-native_ref-linux-armhf/1.1/netlib-native_ref-linux-armhf-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_ref-linux-i686/1.1/netlib-native_ref-linux-i686-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_ref-linux-x86_64/1.1/netlib-native_ref-linux-x86_64-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_ref-osx-x86_64/1.1/netlib-native_ref-osx-x86_64-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_ref-win-i686/1.1/netlib-native_ref-win-i686-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_ref-win-x86_64/1.1/netlib-native_ref-win-x86_64-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_system-linux-armhf/1.1/netlib-native_system-linux-armhf-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_system-linux-i686/1.1/netlib-native_system-linux-i686-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_system-linux-x86_64/1.1/netlib-native_system-linux-x86_64-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_system-osx-x86_64/1.1/netlib-native_system-osx-x86_64-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_system-win-i686/1.1/netlib-native_system-win-i686-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_system-win-x86_64/1.1/netlib-native_system-win-x86_64-1.1-natives.jar:$m2_repo/com/googlecode/matrix-toolkits-java/mtj/1.0.4/mtj-1.0.4.jar:$m2_repo/com/googlecode/netlib-java/netlib-java/1.1/netlib-java-1.1.jar:$m2_repo/junit/junit/3.8.2/junit-3.8.2.jar:$m2_repo/net/sourceforge/f2j/arpack_combined_all/0.1/arpack_combined_all-0.1.jar:$m2_repo/nz/ac/waikato/cms/weka/thirdparty/bounce/0.18/bounce-0.18.jar:$m2_repo/nz/ac/waikato/cms/weka/thirdparty/java-cup-11b/2015.03.26/java-cup-11b-2015.03.26.jar:$m2_repo/nz/ac/waikato/cms/weka/thirdparty/java-cup-11b-runtime/2015.03.26/java-cup-11b-runtime-2015.03.26.jar:$m2_repo/org/apache/commons/commons-compress/1.10/commons-compress-1.10.jar

wekajar=../weka.jar

files=(weka/experiment/CrossValidationResultProducer.java)
path=src/main/java/

lib=$(ls lib)
lib=$(echo $lib | tr " " :)

for i in "${files[@]}"
do
    echo $path/$i
    java -classpath $wekajar:$mvn_dependencies:$lib:$spoon:$jPerturb spoon.Launcher -i $path/$i:$perturbation -o $path --with-imports -p $processors --level ALL
done

mvn package -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -q

mvn install:install-file -Dfile=dist/weka-stable-3.8.0-SNAPSHOT.jar -DpomFile=pom.xml -q


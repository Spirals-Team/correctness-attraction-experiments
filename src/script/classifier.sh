#!/usr/bin/env bash

jPerturb=jPerturb/target/jPerturb-0.0.1-SNAPSHOT-jar-with-dependencies.jar
perturbation=jPerturb/src/main/java/perturbation/
processors=processor.AssignmentProcessor:processor.VariableCaster:processor.PerturbationProcessor
m2_repo=$HOME/.m2/repository

#Install Weka
cd weka-3-8-0
cd src
lib_weka=$(ls lib)
lib_weka=$(echo $lib_weka | tr " " :)

cd ../../

mvn_dep_weka=$m2_repo/com/github/fommil/jniloader/1.1/jniloader-1.1.jar:$m2_repo/com/github/fommil/netlib/all/1.1.2/all-1.1.2.pom:$m2_repo/com/github/fommil/netlib/core/1.1/core-1.1.jar:$m2_repo/com/github/fommil/netlib/native_ref-java/1.1/native_ref-java-1.1.jar:$m2_repo/com/github/fommil/netlib/native_system-java/1.1/native_system-java-1.1.jar:$m2_repo/com/github/fommil/netlib/netlib-native_ref-linux-armhf/1.1/netlib-native_ref-linux-armhf-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_ref-linux-i686/1.1/netlib-native_ref-linux-i686-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_ref-linux-x86_64/1.1/netlib-native_ref-linux-x86_64-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_ref-osx-x86_64/1.1/netlib-native_ref-osx-x86_64-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_ref-win-i686/1.1/netlib-native_ref-win-i686-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_ref-win-x86_64/1.1/netlib-native_ref-win-x86_64-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_system-linux-armhf/1.1/netlib-native_system-linux-armhf-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_system-linux-i686/1.1/netlib-native_system-linux-i686-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_system-linux-x86_64/1.1/netlib-native_system-linux-x86_64-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_system-osx-x86_64/1.1/netlib-native_system-osx-x86_64-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_system-win-i686/1.1/netlib-native_system-win-i686-1.1-natives.jar:$m2_repo/com/github/fommil/netlib/netlib-native_system-win-x86_64/1.1/netlib-native_system-win-x86_64-1.1-natives.jar:$m2_repo/com/googlecode/matrix-toolkits-java/mtj/1.0.4/mtj-1.0.4.jar:$m2_repo/com/googlecode/netlib-java/netlib-java/1.1/netlib-java-1.1.jar:$m2_repo/junit/junit/3.8.2/junit-3.8.2.jar:$m2_repo/net/sourceforge/f2j/arpack_combined_all/0.1/arpack_combined_all-0.1.jar:$m2_repo/nz/ac/waikato/cms/weka/thirdparty/bounce/0.18/bounce-0.18.jar:$m2_repo/nz/ac/waikato/cms/weka/thirdparty/java-cup-11b/2015.03.26/java-cup-11b-2015.03.26.jar:$m2_repo/nz/ac/waikato/cms/weka/thirdparty/java-cup-11b-runtime/2015.03.26/java-cup-11b-runtime-2015.03.26.jar:$m2_repo/org/apache/commons/commons-compress/1.10/commons-compress-1.10.jar
jar_weka=weka-3-8-0/weka.jar
path_weka=weka-3-8-0/src/src/main/java/

#Spooning Weka
i=weka/core/Instances.java
echo "java -classpath $jar_weka:$mvn_dep_weka:$lib_weka:$jPerturb spoon.Launcher -i $path_weka/$i:$perturbation -o $path_weka --with-imports -p $processors"
java -classpath $jar_weka:$mvn_dep_weka:$lib_weka:$jPerturb spoon.Launcher -i $path_weka/$i:$perturbation -o $path_weka --with-imports -p $processors
cd weka-3-8-0/src
mvn install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true
cd ../../
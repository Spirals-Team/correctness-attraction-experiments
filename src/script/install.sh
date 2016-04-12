#!/usr/bin/env bash

git clone http://github.com/Spirals-Team/jPerturb.git
cd jPerturb
mvn install
cd ..
wget https://gforge.inria.fr/frs/download.php/latestzip/86/Spoon-latest.zip
unzip Spoon-latest.zip
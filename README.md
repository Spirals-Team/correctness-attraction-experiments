[![Travis Build Status](https://api.travis-ci.org/Spirals-Team/jPerturb-experiments.svg?branch=master)](https://travis-ci.org/Spirals-Team/jPerturb-experiments)

# jPerturb-experiments

This project contains experiments for [jPerturb](https://github.com/Spirals-Team/jPerturb)

* quicksort: Quicksort without memory version.
* zip: LZW compression.
* md5: MD5 hash function.
* sudoku: a Sudoku solver.
* simplex: solver of linear program with the Simplex algorithm from [math3](https://commons.apache.org/proper/commons-math/) of Apache lib.
* laguerre: root finder of polynomial function from [math3](https://commons.apache.org/proper/commons-math/) of Apache lib.
* mersenne: an implementation of the Mersenne-Twister PRNG: [link](http://www.java2s.com/Code/Java/Development-Class/MersenneTwisterRandom.htm)
* rsa: asymmetric cryptosystem from [Bouncy Castle](https://github.com/bcgit/bc-java.git/)
* rc4: symetric key stream cipher from [Bouncy Castle](https://github.com/bcgit/bc-java.git/)
* tea: tiny encription algorithm in java, translation of this [implementation](https://en.wikipedia.org/wiki/Tiny_Encryption_Algorithm)
* torrent: implementation of bittorent protocol retrieve [here](https://github.com/mpetazzoni/ttorrent).
* bitcoin: implementation of bitcoin node in java found [here](https://github.com/bitcoinj/bitcoinj).
* classifier: a Naive bayes classifier from [Weka](http://www.cs.waikato.ac.nz/ml/weka/).
* canny : edges detector : [source](http://www.tomgibara.com/computer-vision/CannyEdgeDetector.java)
* sat : SAT4J solver find [here](http://www.sat4j.org/)
* lcs : longuest common subsequence, dynamic programming version.

If the source is not specified, sources come from [RosettaCode](http://rosettacode.org/)

about quicksort-visualization:
This package is out of the experiments because it is used to draw perturbation envelop of quicksort algorithm.

## Experiments

The experiments are the exploration of the perturbation envelop of each project.

In order to explore it, we use 5 explorations:

   * Add 1 at each call of each perturbation Point.
   * Add N, an increasing magnitude at each call of each perturbation points.
   * Add 1 with an increasing random probability.
   * Negation of Boolean at each call of each perturbation Point.
   * Negation of Boolean with an increasing random probability.

Additionnal exploration :
   * Minus 1 at each call of each perturbation Point.

## Install

You can use:
```
./src/script/light-install.sh
```
To have a quick start of jPerturb-experiments. Only little exp will remains in the project : quicksort sudoku zip md5 mersenne tea canny and lcs.
The gui is available too.

In order to fully install, launch the following script:
```
./src/script/install.sh
```
It will install everything you will need to run experiments but it will take a while and need at least gradle version 2.13 installed and in your path.

## Run

### Commands

```
mvn exec:java -Dexec.mainClass="experiment.Main" -Dexec.args="<options>"
```

## Example of running commands

Run the systematical call exploration with plus one perturbator model on quicksort, with 50 array of 200 integers with verbose mode:

```
mvn exec:java -Dexec.mainClass="experiment.Main" -Dexec.args="-v -s qs -nb 50 -size 200 -exp call one"
```

Run the gui on zip:

```
mvn exec:java -Dexec.mainClass="experiment.Main" -Dexec.args="-s zip -run gui"
```

Run the task size exploration on md5:

```
mvn exec:java -Dexec.mainClass="experiment.Main" -Dexec.args="-s md5 -run tasksize"
```

### Options

Options available :
* -size \<integer> specify the size of each task
* -nb \<integer> specify the number of task
* -time \<integer> specify the number of seconds to wait until timeout
* -v or -verbose to active Runner verbose mode
* -s \<subject> to specify the subject
* value for \<subject> : qs zip rc4 laguerre tea torrent rsa sudoku bayes simplex mt md5 bc
* -exp \<exp> specify the exp
* value for \<exp> call rnd heatmap
* for call and rnd exp you have to specify which \<exploration> just after it.
* value for \<exploration> : pone mone magnitude boolean
* you can specify an array of magnitude to be used just after the key-word magnitude
* a list of integer separated with ":" (1:2:3 for example)
* after the exploration, you can specify the random rates list used by rnd explorer just as for the magnitude (but with float)
* -run tasksize tasknumber to run the exploration of the impact of the size or the number of task
* runs will be executed before everything else, you must specify a subject (with -s) before
* you can type -run gui to run the demo gui with success meter
* -help display this help

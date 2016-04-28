[![Travis Build Status](https://api.travis-ci.org/Spirals-Team/jPerturb-experiments.svg?branch=master)](https://travis-ci.org/Spirals-Team/jPerturb-experiments)

# jPerturb-experiments

This project contains experiments for jPerturb

* quicksort : Perturbation on Quicksort.
* zip : Perturbation on LZW compression.
* md5 : Perturbation on MD5 hash function.
* sudoku : Perturbation on a Sudoku solver.
* optimizer : Solver of linear program with the Simplex algorithm from [math3](https://commons.apache.org/proper/commons-math/) of Apache lib.
* mersenne : an implementation of the Mersenne-Twister PRNG : [link](http://www.java2s.com/Code/Java/Development-Class/MersenneTwisterRandom.htm)
* torrent : implementation of bittorent protocol retrieve [here](https://github.com/mpetazzoni/ttorrent).
* bitcoin : implementation of bitcoin node in java found [here](https://github.com/bitcoinj/bitcoinj).
* classifier : a Naive bayes classifier from [Weka](http://www.cs.waikato.ac.nz/ml/weka/).

If the source is not specified, sources come from [RosettaCode](http://rosettacode.org/)

about quicksort-visualization :
This package is out of the experiments because it is used to draw perturbation envelop of quicksort algorithm.

## Experiments

The experiments are the exploration of the perturbation envelop of each project.

In order to explore it, we use 5 campaigns :

   * Add 1 at each call of each perturbation Point.
   * Add N, an increasing magnitude at each call of each perturbation points.
   * Add 1 with an increasing random probability.
   * Negation of Boolean at each call of each perturbation Point.
   * Negation of Boolean with an increasing random probability.

## Install & Running

In order to install, just launch the following script :

```
./src/script/install.sh
```

It will install everything you will need to run experiments (nearly 7 minutes).

In order to try, run :

```
java -cp target/classes quicksort.Main -v
```

It about 5 minutes to execute and run all campaigns on the quicksort subject, with 20 arrays of 100 integers.

## Options

You can run each subject separately, or all of it with the Runner. Options remains the same.

Several options are available :
* -size <integer> specify the size of each task
* -nb <integer> specify the number of task
* -time <integer> specify the number of seconds to wait until timeout
* -v or -verbose to active Runner verbose mode
* -exp <exp> specify the exp. If no exp is specified, the program will run all of it. values available for <exp> are : addOne addN BoolCall IntRnd BoolRnd
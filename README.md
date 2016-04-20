[![Travis Build Status](https://api.travis-ci.org/Spirals-Team/jPerturb-experiments.svg?branch=master)](https://travis-ci.org/Spirals-Team/jPerturb-experiments)

# jPerturb-experiments

This project contains experiments for jPerturb

* quicksort : Perturbation on Quicksort.
* zip : Perturbation on LZW compression.
* md5 : Perturbation on MD5 hash function.
* sudoku : Perturbation on a Sudoku solver.
* optimizer : Solver of linear program with the Simplex algorithm.
* mersenne : an implementation of the Mersenne-Twister PNRG.

about quicksort-visualization :
This package is out of the experiments because it is used to draw perturbation envelop of quicksort algorithm.

## Experiments

The experiments are the exploration of the perturbation envelop of each project.

In order to explore it, we use 3 campaigns :

   * Add 1 at each call of each perturbation Point.
   * Add N, an increasing magnitude at each call of each perturbation points.
   * Add 1 with an increasing random probability.

## Install & Running

Before running experimentation, you need to retrieve jPerturb and Spoon. A shell script is available.

```
./src/script/install.sh
```

It will clone jPerturb from github, install it and retrieve the last jar of Spoon. Then, it will process java classes and compile the project.

In order to try, run :

```
java -cp target/classes quicksort.Main
```
It about 3 minutes to execute and run all campaigns on the quicksort subject, with 20 arrays of 100 integers.

## Options

You can run each subject separatly, or all of it with the Runner. Options remains the same.

Several options are available :
* -size <integer> specify the size of each task
* -nb <integer> specify the number of task
* -time <integer> specify the number of seconds to wait until timeout
* -v or -verbose to active Runner verbose mode
* -exp <exp> specify the exp. If no exp is specified, the program will run all of it. values available for <exp> are : addOne addN Rnd

You can run all experiment with (more than 15 minutes) :

```
./src/script/run.sh
```


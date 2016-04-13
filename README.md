[![Travis Build Status](https://api.travis-ci.org/Spirals-Team/jPerturb-experiments.svg?branch=master)](https://travis-ci.org/Spirals-Team/jPerturb-experiments)

# jPerturb-experiments

This project contains experiments for jPerturb

* sort : Perturbation on Quicksort.
* zip : Perturbation on LZW compression.
* md5 : Perturbation on MD5 hash function.
* sudoku : Perturbation on a Sudoku solver.

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

It will clone jPerturb from github, install it and retrieve the last jar of Spoon. Then, it will process java class in resources/java/ and compile the project.

In order to try, run (about 3 minutes to execute) :

```
java -cp bin sort.Main
```

You can run all experiment with (more than 15 minutes) :

```
./src/script/run.sh
```


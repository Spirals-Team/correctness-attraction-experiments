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
   * Add N, an incresing magnitude at each call of each perturbation points.
   * Add 1 with a increasing random probability.

## Install & Running

Before running experimentation, you need to retrieve jPerturb and Spoon. A shell script is available.


```
./src/script/install.sh
```

It will clone jPerturb from github, install it and retrieve the last jar of Spoon.

In order to run experiments, you can use the script :

```
./src/script/run.sh
```

It will process classes in results/java/ with jPerturb, compile and run all campaigns describe above, on all subjects.
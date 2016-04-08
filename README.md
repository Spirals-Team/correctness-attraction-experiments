[![Travis Build Status](https://api.travis-ci.org/Spirals-Team/jPerturb-experiments.svg?branch=master)](https://travis-ci.org/Spirals-Team/jPerturb-experiments)

# jPerturb-experiments

This project contains experiments for jPerturb

* sort : Perturbation on Quicksort.
* zip : Perturbation on LZW compression.
* md5 : Perturbation on MD5 hash function.

## Experiments

The experiments are the exploration of the perturbation envelop of each project.

In order to explore it, we use 3 campaigns :

   * Add 1 at each call of each perturbation Point.
   * Add N, an incresing magnitude at each call of each perturbation points.
   * Add 1 with a increasing random probability.
#!/usr/bin/env bash

java -cp target/classes quicksort.Main
java -cp target/classes zip.Main
java -cp target/classes md5.Main
java -cp target/classes sudoku.Main -nb 1 -time 45
java -cp target/classes optimizer.Main -nb 1 -time 45
java -cp target/classes mersenne.Main -nb 10 -v
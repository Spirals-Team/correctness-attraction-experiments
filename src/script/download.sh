#!/usr/bin/env bash

while read LINE
do
    wget $LINE
done <$1

for file in *.txt ; do
    mv -- "$file" "${file//-/_}" 2>/dev/null
done

for file in *.txt ; do
    mv -- "$file" "${file//addnexplorer/AddNExplorer}" 2>/dev/null
done

for file in *.txt ; do
    mv -- "$file" "${file//addoneexplorer/AddOneExplorer}" 2>/dev/null
done

for file in *.txt ; do
    mv -- "$file" "${file//integeradd1rndenactorexplorer/IntegerAdd1RndEnactorExplorer}" 2>/dev/null
done
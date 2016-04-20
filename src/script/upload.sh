#!/usr/bin/env bash
cd $1
for file in *.txt ; do
    curl --upload-file ./"$file" https://transfer.sh/"$file"
done
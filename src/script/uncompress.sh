#!/usr/bin/env bash

for file in resources/optimizer/* ; do
   ./emps $file > "$file"_uc
done
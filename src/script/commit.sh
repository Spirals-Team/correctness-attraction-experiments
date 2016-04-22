#!/usr/bin/env bash

git config --global user.email "bdanglot@gmail.com"
git config --global user.name "bdanglot"

git checkout -b auto-push

git add results/
git commit -m "Commit new results"
git push origin auto-push
#!/usr/bin/env bash

git config --global user.email "bdanglot@gmail.com"
git config --global user.name "bdanglot"

git add results/
git commit -m "Commit new results"
git push --force --quiet "https://${GH_TOKEN}@${GH_REF}" master:test_auto_push > /dev/null 2>&1
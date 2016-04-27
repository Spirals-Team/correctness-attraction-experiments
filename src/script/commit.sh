#!/usr/bin/env bash

CURRENT_BRANCH=($(git rev-parse --abbrev-ref HEAD))

git config --global user.email "bdanglot@gmail.com"
git config --global user.name "bdanglot"

mv .travis.yml save_travis.yml
git add save_travis.yml
git rm .travis.yml
git commit -m "rename travis fil"

git add results/
git commit -m "Commit new results"

git push "https://${GH_TOKEN}@${GH_REF}" $CURRENT_BRANCH:$BRANCH
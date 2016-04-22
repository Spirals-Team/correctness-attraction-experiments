#!/usr/bin/env bash

CURRENT_BRANCH=($(git rev-parse --abbrev-ref HEAD))

git config --global user.email "bdanglot@gmail.com"
git config --global user.name "bdanglot"

echo "branches:" >> .travis.yml
echo "except:" >> .travis.yml
echo "-" $CURRENT_BRANCH >> .travis.yml

git add .travis.yml

git add results/
git commit -m "Commit new results"
git push "https://${GH_TOKEN}@${GH_REF}" $CURRENT_BRANCH:$CURRENT_BRANCH

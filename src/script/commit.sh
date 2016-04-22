#!/usr/bin/env bash

CURRENT_BRANCH=($(git rev-parse --abbrev-ref HEAD))

git config --global user.email "bdanglot@gmail.com"
git config --global user.name "bdanglot"

git add results/
git commit -m "Commit new results"
git push "https://${GH_TOKEN}@${GH_REF}" auto-push-torrent:auto-push-torrent
git push "https://${GH_TOKEN}@${GH_REF}" $CURRENT_BRANCH:branch-results

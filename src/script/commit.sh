#!/usr/bin/env bash

git config --global user.email "bdanglot@gmail.com"
git config --global user.name "bdanglot"

git checkout -b auto-push-torrent

git add results/
git commit -m "Commit new results"
git push "https://${GH_TOKEN}@${GH_REF}" auto-push-torrent:auto-push-torrent
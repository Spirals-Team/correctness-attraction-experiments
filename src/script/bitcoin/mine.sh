#!/usr/bin/env bash

echo mining.....

bitcoinpath=./bitcoin-0.12.1/bin

#ls $bitcoinpath

$bitcoinpath/bitcoin-cli -regtest generate 1 2>/dev/null

sleep 1
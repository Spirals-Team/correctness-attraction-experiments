#!/usr/bin/env bash

bitcoinpath=./bitcoin-0.12.1/bin

$bitcoinpath/bitcoin-cli -regtest sendtoaddress $1 2000.0

echo "$bitcoinpath/bitcoin-cli -regtest sendtoaddress $1 2000.0"
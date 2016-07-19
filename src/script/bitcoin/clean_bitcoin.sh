#!/usr/bin/env bash

bitcoinpath=./bitcoin-0.12.1/bin

$bitcoinpath/bitcoin-cli -regtest stop

sleep 1

killall bitcoind
pid_shutoff=$(ps -e | grep bitcoin-shutoff | cut -c1-5)
kill -9 $pid_shutoff

sleep 1

echo "Cleaning directories..."

rm -Rf $HOME/.bitcoin/
rm -Rf resources/bitcoin/wallets/
mkdir resources/bitcoin/wallets/

sleep 1

echo "$bitcoinpath/bitcoind -regtest -daemon"
$bitcoinpath/bitcoind -regtest -daemon

sleep 8

echo "$bitcoinpath/bitcoin-cli -regtest generate 500"
$bitcoinpath/bitcoin-cli -regtest generate 500


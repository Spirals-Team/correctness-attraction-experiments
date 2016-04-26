#!/usr/bin/env bash
path_bin_bitcoin=./bitcoin-0.12.1/bin/

$path_bin_bitcoin/bitcoin-cli -regtest stop 2>/dev/null

sleep 1

echo "Cleaning directories..."

rm -Rf $HOME/.bitcoin/
rm -f  resources/bitcoin/adr_bitcoin
touch resources/bitcoin/adr_bitcoin
rm -Rf resources/bitcoin/wallets/
mkdir resources/bitcoin/wallets/

sleep 1

$path_bin_bitcoin/bitcoind -regtest -daemon


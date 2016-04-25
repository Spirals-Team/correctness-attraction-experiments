#!/usr/bin/env bash

bitcoin-cli -regtest stop 2>/dev/null

sleep 1

echo "Cleaning directories..."

rm -Rf $HOME/.bitcoin/
rm -f  resources/bitcoin/adr_bitcoin
touch resources/bitcoin/adr_bitcoin
rm -Rf resources/bitcoin/wallets/
mkdir resources/bitcoin/wallets/

sleep 1

bitcoind -regtest -daemon


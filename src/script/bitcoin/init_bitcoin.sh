#!/usr/bin/env bash

bitcoinpath=./bitcoin-0.12.1/bin

#ls $bitcoinpath

$bitcoinpath/bitcoin-cli -regtest generate 131 2>/dev/null

sleep 1

adr_bitcoin="./resources/bitcoin/adr_bitcoin"

while read adr; do
  	echo $adr
  	$bitcoinpath/bitcoin-cli -regtest sendtoaddress $adr 500.0
	sleep 1
done <$adr_bitcoin

echo "sent 500 btc at each nodes in adr_bitcoin..."

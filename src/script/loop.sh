#!/bin/bash

i="0"
while [ $i -lt 11 ]
do
	echo $i
	code=$( ./src/script/run.sh )
	echo $code
	if [ $code -eq "1" ]
	then
		cp results/torrent/BooleanNegation_Bandit_data_graph_analysis.txt results/torrent/BooleanNegation_Bandit_data_graph_analysis_$i.txt
		cp results/torrent/BooleanNegation_Bandit_policy.txt results/torrent/BooleanNegation_Bandit_policy_$i.txt
		cp results/torrent/BooleanNegation_Bandit_state.txt results/torrent/BooleanNegation_Bandit_state_$i.txt
		i=$[$i+1]
    	fi
done

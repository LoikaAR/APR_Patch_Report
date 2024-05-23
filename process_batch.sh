#!/bin/bash

dir=$(pwd)

instanceDir=$dir/APRS_Practise/ProcessedInstances/$1
testsDir=$dir/APRS_Practise/IntroClassTests/$1

for f in "$instanceDir"/*; do
	# copy file to correct disl folder
	cp "$f" $dir/DiSL_Practice/src-app/aprs_introclass
	
	# create reference to the copied file
	filename="${f##*/}"
	copied=$dir/DiSL_Practice/src-app/aprs_introclass/"$filename"
	
	for t in "$testsDir"/*; do
		# copy file to correct disl folder
		cp "$t" $dir/DiSL_Practice/src-app/aprs_introclass
		
		# create reference to the copied file
		testname="${t##*/}"
		testcpy=$dir/DiSL_Practice/src-app/aprs_introclass/"$testname"	
		
		sleep 1 # invoke disl stuff here
		echo "instrumenting"
		
		# remove it
		rm -f "$testcpy"
		sleep 1
	done

	# remove it
	rm -f "$copied"
	sleep 1	
done

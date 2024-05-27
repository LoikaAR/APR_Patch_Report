#!/bin/bash

dir=$(pwd)
instanceDir=$dir/APRS_Practise/ProcessedInstances/$1
testsDir=$dir/APRS_Practise/IntroClassTests/$1

for f in "$instanceDir"/*; do
	# copy file to correct disl folder
	#mkdir $dir/DiSL_Practice/src-app/aprs_introclass/$1
	cp "$f" $dir/DiSL_Practice/src-app/aprs_introclass/ClassDef.java
	
	# create reference to the copied file
	filename="${f##*/}"
	copied=$dir/DiSL_Practice/src-app/aprs_introclass/ClassDef.java
	
	for t in "$testsDir"/*; do
		# copy file to correct disl folder
		cp "$t" $dir/DiSL_Practice/src-app/aprs_introclass/MainInstance.java
		
		# create reference to the copied file
		#testname="${t##*/}"
		testcpy=$dir/DiSL_Practice/src-app/aprs_introclass/MainInstance.java	
		
		sleep 1
		# invoking disl
		# instrument whole program:
		echo "instrumenting"
		cd DiSL_Practice
		ant clean
		ant -Ddislclass=aprs_introclass.Instrumentation
		bash ./startDiSLServer.sh
		sleep 1
		bash ./runInstrumented.sh aprs_introclass.MainInstance
		
		# instrument each basic block:
		ant clean
		ant -Ddislclass=aprs_introclass.Instrumentation_BB
		bash ./startDiSLServer.sh
		sleep 1
		bash ./runInstrumented.sh aprs_introclass.MainInstance
		
		cd ..
		
		# remove copied file
		rm -f "$testcpy"
		sleep 1
	done
	# remove it
	rm -f "$copied"
done

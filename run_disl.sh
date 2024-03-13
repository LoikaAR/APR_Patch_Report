#!/bin/bash

cd DiSL_Practice

ant clean

ant -Ddislclass=aprs_prog1.Instrumentation

bash ./startDiSLServer.sh

sleep 1

bash ./runInstrumented.sh aprs_prog1.Main

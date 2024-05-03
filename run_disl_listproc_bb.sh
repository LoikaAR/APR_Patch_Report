#!/bin/bash

cd DiSL_Practice

ant clean

ant -Ddislclass=aprs_listproc.Instrumentation_BB

bash ./startDiSLServer.sh

sleep 1

bash ./runInstrumented.sh aprs_listproc.Main

#!/bin/bash

cd DiSL_Practice

ant clean

ant -Ddislclass=aprs_introclass.Instrumentation

bash ./startDiSLServer.sh

sleep 1

bash ./runInstrumented.sh aprs_introclass.MainInstance

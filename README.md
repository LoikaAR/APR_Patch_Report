# APR_Patch_Report

In APRS_Practise folder, run

`mvn clean verify`

Then start the OtelServerHandler main method, then run

`java -javaagent:from/root/to/APR_Patch_Reports/APRS_Practise/opentelemetry-javaagent.jar -jar from/root/to/APRS_Practise/out/artifacts/APRS_Practise_jar/APRS_Practise.jar`

To get the metrics and any available info

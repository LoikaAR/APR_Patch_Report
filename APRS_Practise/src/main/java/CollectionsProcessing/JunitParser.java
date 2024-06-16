package CollectionsProcessing;

import java.io.*;
import java.util.HashMap;

/*
* Class to get JUnit test results from the results txt file
* */

public class JunitParser {
    public static HashMap<String, HashMap<String, String>> testResults = new HashMap<String, HashMap<String, String>>();

    public static void main(String[] args) {
        try {
            parseTestResults();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(testResults);
    }

    public static void parseTestResults() throws IOException {
        String filePath = "../junit_res.txt";

        File targetFile = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(targetFile));

        try {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Running introclassJava.")) {
                    int delimFirst = line.indexOf(".");
                    int delimSecond = line.indexOf("_");
                    String testType = line.contains("B") ? "B" : "W";
                    int delimThird = line.indexOf(testType);

                    String project = line.substring(delimFirst+1, delimSecond);
                    String version = line.substring(delimFirst+1, delimThird);
                    String results = br.readLine();

                    int res = getRes(results);
                    HashMap<String, String> testResult = new HashMap<String, String>();
                    testResult.put(version + "_" + testType, String.valueOf(res));

                    if (!testResults.containsKey(project)) {
                        testResults.put(project, testResult);
                    } else {
                        if (project.contains("Checksum")) {
                            System.out.println(version + "_" + testType);
                        }
                        testResults.get(project).put(version + "_" + testType, String.valueOf(res));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private static int getRes(String results) {
        int totalIdxStart = results.indexOf(":")+1;
        int totalIdxEnd = results.indexOf(",");
        float nTests = Integer.parseInt(results.substring(totalIdxStart, totalIdxEnd).strip());

        int failsIdxStart = results.indexOf(":", totalIdxStart+1)+1;
        int failsIdxEnd = results.indexOf(",", totalIdxEnd+1);
        float nFails = Integer.parseInt(results.substring(failsIdxStart, failsIdxEnd).strip());

        float res = (1-(nFails/nTests))*100;
        return (int)res;
    }
}

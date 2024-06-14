package CollectionsProcessing;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

import com.opencsv.CSVWriter;
import SimilarityMeasures.LCS;
import SimilarityMeasures.LevenshteinDistance;
import SimilarityMeasures.SmithWaterman;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import static CollectionsProcessing.JunitParser.parseTestResults;
import static CollectionsProcessing.JunitParser.testResults;

/*
* Class to process the program traces produced by disl */

public class JsonHandler {
    public static String[] projects = {"digits", "checksum", "grade", "median", "smallest", "syllables"};
    public static int nTests = 9; // +1
    public static int CODE_MAX = 9999;
    public static int curCode = 0;
    public static HashMap<String, String> outJson = new HashMap<String, String>();
    public static HashMap<String, String> encodingMap = new HashMap<String, String>();
//    public static HashMap<String, HashMap<String, String>> junitTestResults = new HashMap<String, HashMap<String, String>>();


    public static void main(String[] args) throws IOException {
        File resFile = new File("./distance_results.csv");
        for (String project : projects) {
            HandleJsonTraces(project);
        }

        // print the results
        HashMap<String, HashMap<String, Integer>> scores = new HashMap<String, HashMap<String, Integer>>(); // project_version : test_num : score (LD, LCS, SW)
        int testIdx = 0;

        SortedSet<String> keys = new TreeSet<>(outJson.keySet());
        while (testIdx <= nTests) {
            for (String entry : keys) {
                if (entry.contains("test_" + testIdx)) {
                    int LD_score_BB, LCS_score_BB;
                    double SW_score_BB;

                    if (entry.contains("BB")) {
                        int iend = entry.indexOf("_test");
                        String versionName = entry.substring(0, iend);
                        scores.computeIfAbsent(versionName, k -> new HashMap<>());

                        String projectName = entry.substring(0, entry.indexOf("_"));
                        if (versionName.contains("REF")) {
                            projectName = versionName.substring(versionName.indexOf("_")+1);
                        }

                        LD_score_BB = LevenshteinDistance.levenshteinTwoMatrixRows(outJson.get(entry),
                                outJson.get("REF_" + projectName + "_test_" + testIdx +"_BB"));
                        LCS_score_BB = LCS.longestCommonSubsequence(outJson.get(entry),
                                outJson.get("REF_" + projectName + "_test_" + testIdx + "_BB"));
                        SmithWaterman sw = new SmithWaterman(outJson.get(entry),
                                outJson.get("REF_" + projectName + "_test_" + testIdx +"_BB"));
                        SW_score_BB = sw.getAlignmentScore();

                        scores.get(versionName).put("test_" + testIdx + "_LD", LD_score_BB);
                        scores.get(versionName).put("test_" + testIdx + "_LCS", LCS_score_BB);
                        scores.get(versionName).put("test_" + testIdx + "_SW", (int)SW_score_BB);
                    }
                }
            }
            testIdx++;
        }

        SortedSet<String> scores_sorted = new TreeSet<>(scores.keySet());

        try {
            FileWriter writer = new FileWriter(resFile);
            CSVWriter csvWriter = new CSVWriter(writer);
            String[] header = {"Project", "Version", "LD Score", "LCS Score", "SW Score",
                    "Black Box Pass %", "White Box Pass %"};

            csvWriter.writeNext(header);
            parseTestResults(); // Junit test results (all)


            for (String fullFileName : scores_sorted) {
                HashMap<String, Integer> score = scores.get(fullFileName);
                int avgLD = 0, avgLCS = 0, avgSW = 0;
                for (String testName : score.keySet()) {
                    if (testName.contains("LD")) {
                        avgLD += score.get(testName);
                    } else if (testName.contains("LCS")) {
                        avgLCS += score.get(testName);
                    } else if (testName.contains("SW")) {
                        avgSW += score.get(testName);
                    }
                }
                avgLD = avgLD/(nTests+1);
                avgLCS = avgLCS/(nTests+1);
                avgSW = avgSW/(nTests+1);

                String projectName, version, junit_res_W, junit_res_B;
                String name_w = fullFileName + "_W";
                String name_b = fullFileName + "_B";


                if (!fullFileName.contains("REF")) {
                    projectName = fullFileName.substring(0, fullFileName.indexOf("_"));
                    version = fullFileName.substring(fullFileName.indexOf("_")+1);
                    junit_res_W = testResults.get(projectName).get(name_w);
                    junit_res_B = testResults.get(projectName).get(name_b);
                } else {
                    projectName = fullFileName.substring(fullFileName.indexOf("_")+1);
                    version = "REF";
                    junit_res_W = "100";
                    junit_res_B = "100";
                }

                System.out.println(junit_res_W);
                System.out.println(junit_res_B);
                System.out.println(fullFileName + " <----------------------> " + "REF_" + projectName);
                System.out.println("Average Levenshtein Distance: " + avgLD);
                System.out.println("Average Longest Common Subsequence: " + avgLCS);
                System.out.println("Average Smith-Waterman Score: " + avgSW);
                System.out.println("==================================================");

                String[] nextLine = {projectName, version, String.valueOf(avgLD), String.valueOf(avgLCS),
                        String.valueOf(avgSW), junit_res_W, junit_res_B,};
                csvWriter.writeNext(nextLine);
            }
            csvWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Handles the JSON output from DiSL
     */
    public static void HandleJsonTraces(String project) throws IOException {
        String pathToTests = "./ProcessedInstances/" + project;
        String pathToJson = "../DiSL_Practice/json_out/";                // + project, separate in folders
        ArrayList<String> fileNames = new ArrayList<>();

        getFileNames(pathToTests, fileNames);                            // get names of all project versions
        for (String testName : fileNames) {
            String path = pathToJson + testName;
            processTests(path, testName);
        }
    }

    public static void processTests(String path, String name) throws IOException {
        int curTestIdx = 0;
        String mapKeyRefBB;
        while (curTestIdx <= nTests) {
            String curTestBB = path.replace(".java", "") + "_test_" + curTestIdx + "_BB.json";
            mapKeyRefBB = name.replace(".java", "") + "_test_" + curTestIdx + "_BB";

            ObjectMapper mapperRefBB = new ObjectMapper();
            JsonNode jsonNodeRefBB = mapperRefBB.readTree(new File(curTestBB));

            outJson.put(mapKeyRefBB, "");
            traverse(jsonNodeRefBB, mapKeyRefBB);
            curTestIdx++;
        }
    }

    public static void getFileNames(String path, ArrayList<String> fileNames) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    fileNames.add(file.getName());
                }
            }
        }
    }

    /*
    * Recursive function to do some operation for each element in a json object
    * Here: append each element to a continuous string mapped to a key
    * */
    public static void traverse(JsonNode root, String mapKey) {
        if (root.isObject()) {
            Iterator<String> fieldNames = root.fieldNames();

            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                JsonNode fieldValue = root.get(fieldName);
                traverse(fieldValue, mapKey);
            }
        } else if (root.isArray()) {
            ArrayNode arrayNode = (ArrayNode) root;
            for (int i = 0; i < arrayNode.size(); i++) {
                JsonNode arrayElement = arrayNode.get(i);
                traverse(arrayElement, mapKey);
            }
        } else {
            // JsonNode root represents a single value field
            String val = String.valueOf(curCode);
            if (encodingMap.containsKey(root.asText())) {
                outJson.put(mapKey, outJson.get(mapKey) + encodingMap.get(root.asText()));
            } else {
                int len = val.length();
                // TODO make it work with any number of chars
                if (len == 1) {
                    val = val + "___ ";
                    encodingMap.put(root.asText(), val);
                } else if (len == 2) {
                    val = val + "__ ";
                    encodingMap.put(root.asText(), val);
                } else if (len == 3) {
                    val = val + "_ ";
                    encodingMap.put(root.asText(), val);
                } else {
                    val = val + " ";
                    encodingMap.put(root.asText(), val);
                }
                outJson.put(mapKey, outJson.get(mapKey) + val);
                curCode++;

                if (curCode >= CODE_MAX) {
                    try {
                        throw new Exception("Exception");
                    } catch (Exception e) {
                        System.out.println("Number of elements exceeds maximum allowed");
                    }
                }
            }
        }
    }
}

package CollectionsProcessing;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import SimilarityMeasures.LCS;
import SimilarityMeasures.LevenshteinDistance;
import SimilarityMeasures.SmithWaterman;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/*
* Class to process the program traces produced by disl*/

public class JsonHandler {
    public static String[] projects = {"digits", "checksum", "grade", "median", "smallest", "syllables"};
    public static int nTests = 9; // +1
    public static int CODE_MAX = 9999;
    public static int curCode = 0;
    public static HashMap<String, String> outJson = new HashMap<String, String>();
    public static HashMap<String, String> encodingMap = new HashMap<String, String>();

    public static void main(String[] args) throws IOException {
        for (String project : projects) {
            HandleJsonTraces(project);
        }
//        System.out.println(outJson);
//        System.out.println(encodingMap);

        File outFile = new File("./outTest.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));

        for (Map.Entry<String, String> entry : outJson.entrySet()) {
            bw.write(entry.getKey());
            bw.write("\n");
            bw.write(entry.getValue());
            bw.write("\n\n\n");
        }
        bw.close();

        // print the results
        HashMap<String, HashMap<String, Integer>> scores = new HashMap<String, HashMap<String, Integer>>(); // project_version : test_num : score (LD, LCS, SW)
        int testIdx = 0;

        SortedSet<String> keys = new TreeSet<>(outJson.keySet());
        while (testIdx <= nTests) {
            for (String entry : keys) {
//            for (Map.Entry<String, String> entry : outJson.entrySet()) {
//                System.out.println(entry);
//                System.out.println(outJson.get(entry));
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

        for (String version : scores.keySet()) {
            HashMap<String, Integer> score = scores.get(version);
            int curTest = 0;
            int avgLD = 0, avgLCS = 0, avgSW = 0;
            for (String testName : score.keySet()) {
                if (testName.contains("LD")) {
                    avgLD += score.get(testName);
                } else if (testName.contains("LCS")) {
                    avgLCS += score.get(testName);
                } else if (testName.contains("SW")) {
                    avgSW += score.get(testName);
                }
                curTest++;
            }
            avgLD = avgLD/(nTests+1);
            avgLCS = avgLCS/(nTests+1);
            avgSW = avgSW/(nTests+1);
            System.out.println(version + " <--------------> " + "ref:");
            System.out.println("Average Levenshtein Distance: " + avgLD);
            System.out.println("Average Longest Common Subsequence: " + avgLCS);
            System.out.println("Average Smith-Waterman Score: " + avgSW);
            System.out.println("==================================================");

        }
//        System.out.println(scores);
    }

    /*
     * Handles the JSON output from DiSL
     */
    public static void HandleJsonTraces(String project) throws IOException {
        String pathToTests = "./ProcessedInstances/" + project;
        String pathToJson = "../DiSL_Practice/json_out/";                // + project, separate in folders
        ArrayList<String> fileNames = new ArrayList<>();

        getFileNames(pathToTests, fileNames);                            // get names of all project versions

        int i = 0;
        for (Iterator<String> it = fileNames.iterator(); it.hasNext(); i++) {
            String testName = it.next();

//            if (testName.contains("REF")) {
//                System.out.println(testName);
//                for (int j = 0; j <= nTests; j++) {
//                    fileNames.set(i, testName + "_test_");
//                }
//            }

            String path = pathToJson + testName;
            processTests(path, testName);
        }
    }

    public static void processTests(String path, String name) throws IOException {
        int curTestIdx = 0;
        String mapKeyRefBB;
        while (curTestIdx <= nTests) {
            String curTestBB = path.replace(".java", "") + "_test_" + curTestIdx + "_BB.json";
//            System.out.println(curTestBB);
            mapKeyRefBB = name.replace(".java", "") + "_test_" + curTestIdx + "_BB";
//            System.out.println(mapKeyRefBB);

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
//                    System.out.println("File " + file.getName());
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
//                encodingMap.put(encodingMap.get(root.asText()), String.valueOf(curCode));
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

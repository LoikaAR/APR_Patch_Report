package CollectionsProcessing;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import SimilarityMeasures.LCS;
import SimilarityMeasures.LevenshteinDistance;
import SimilarityMeasures.SmithWaterman;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

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

        System.out.println(outJson);
        System.out.println(encodingMap);

        HashMap<String, HashMap<String, Integer>> scores = new HashMap<String, HashMap<String, Integer>>(); // project_version : test_num : score (LD, LCS, SW)
        int testIdx = 0;
        while (testIdx <= nTests) {
            for (Map.Entry<String, String> entry : outJson.entrySet()) {
                if (entry.getKey().contains("test_" + testIdx)) {
                    int LD_score_BB = 0, LCS_score_BB = 0, LD_score = 0, LCS_score = 0;
                    double SW_score_BB = 0, SW_score = 0;

                    if (entry.getKey().contains("BB")) {
                        int iend = entry.getKey().indexOf("_output");
                        String versionName = entry.getKey().substring(0, iend);
                        scores.computeIfAbsent(versionName, k -> new HashMap<>());

                        LD_score_BB = LevenshteinDistance.levenshteinTwoMatrixRows(entry.getValue(),
                                outJson.get("ref_output_test_" + testIdx +"_BB"));
                        LCS_score_BB = LCS.longestCommonSubsequence(entry.getValue(),
                                outJson.get("ref_output_test_" + testIdx + "_BB"));
                        SmithWaterman sw = new SmithWaterman(entry.getValue(),
                                outJson.get("ref_output_test_" + testIdx +"_BB"));
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
        System.out.println(scores);
    }
    public static void HandleJsonTraces(String project) throws IOException {
        String path_ref = "../DiSL_Practice/json_out/REF_"
                + project.substring(0, 1).toUpperCase() + project.substring(1);

        processTests(path_ref, "ref");                    // populate outJson with encoded DiSL data

        String pathToTests = "./ProcessedInstances/" + project;
        String pathToJson = "../DiSL_Practice/json_out/";       // + project, separate in folders
        ArrayList<String> fileNames = new ArrayList<>();

        getFileNames(pathToTests, fileNames);                   // get names of all project versions

        for (String testName : fileNames) {
            if (!testName.contains("REF")) {
                String path = pathToJson + testName;
                processTests(path, testName);
            }
        }
    }

    public static void processTests(String path, String name) throws IOException {
        int curTestIdx = 0;
        String mapKeyRef;
        String mapKeyRefBB;
        while (curTestIdx <= nTests) {
            String curTest = path.replace(".java", "") + "_test_" + curTestIdx + ".json";
            String curTestBB = path.replace(".java", "") + "_test_" + curTestIdx + "_BB.json";
            mapKeyRef = name.replace(".java", "") + "_output_test_" + curTestIdx;
            mapKeyRefBB = name.replace(".java", "") + "_output_test_" + curTestIdx + "_BB";

            ObjectMapper mapperRef = new ObjectMapper();
            JsonNode jsonNodeRef = mapperRef.readTree(new File(curTest));

            ObjectMapper mapperRefBB = new ObjectMapper();
            JsonNode jsonNodeRefBB = mapperRefBB.readTree(new File(curTestBB));

            outJson.put(mapKeyRef, "");
            traverse(jsonNodeRef, mapKeyRef);

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
                    System.out.println("File " + file.getName());
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

package CollectionsProcessing;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class JsonHandler {
    public static int CODE_MAX = 9999;
    public static int curCode = 0;
    public static HashMap<String, String> outJson = new HashMap<String, String>();
    public static HashMap<String, String> encodingMap = new HashMap<String, String>();

    public static void main(String[] args) throws IOException {
        HandleJsonTraces(false);
        HandleJsonTraces(true);

        // remove "-" from end of each sequence
        for (Map.Entry<String, String> entry : encodingMap.entrySet()) {
            String newVal = entry.getValue().substring(0, entry.getValue().length()-3);
            entry.setValue(newVal);
        }

        for (Map.Entry<String, String> entry : outJson.entrySet()) {
            String newVal = entry.getValue().substring(0, entry.getValue().length()-3);
            entry.setValue(newVal);
        }


        System.out.println(outJson);
        System.out.println(encodingMap);
    }
    public static void HandleJsonTraces(boolean BB) throws IOException {
        String path_v1;
        path_v1 = BB ? "../DiSL_Practice/json_out/output_v1.json" : "../DiSL_Practice/json_out/BB_output_v1.json";

        String mapKeyV1;
        mapKeyV1 = BB ? "output_v1" : "BB_output_v1";

        ObjectMapper mapper_v1 = new ObjectMapper();
        JsonNode jsonNode_v1 = mapper_v1.readTree(new File(path_v1));

        outJson.put(mapKeyV1, "");
        traverse(jsonNode_v1, mapKeyV1);


        String path_v2;
        path_v2 = BB ? "../DiSL_Practice/json_out/output_v2.json" : "../DiSL_Practice/json_out/BB_output_v2.json";

        ObjectMapper mapper_v2 = new ObjectMapper();
        JsonNode jsonNode_v2 = mapper_v2.readTree(new File(path_v2));

        String mapKeyV2;
        mapKeyV2 = BB ? "output_v2" : "BB_output_v2";

        outJson.put(mapKeyV2, "");
        traverse(jsonNode_v2, mapKeyV2);


        // beautifying the json - not obligatory
        String json = mapper_v1.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode_v1);

        try {
            File output_file = new File("../DiSL_Practice/json_out/formatted_output_v1.json");
            BufferedWriter bf = new BufferedWriter(new FileWriter(output_file));
            bf.write(json);
            bf.close();

            System.out.println("Successfully wrote to file " + output_file.getName());
        } catch (IOException e) {
            System.out.println("Error writing to file");
            System.out.println(e.getMessage());
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

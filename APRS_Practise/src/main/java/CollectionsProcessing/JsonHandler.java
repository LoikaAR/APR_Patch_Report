package CollectionsProcessing;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class JsonHandler {

    public static HashMap<String, String> procJson = new HashMap<String, String>();

    public static void main(String[] args) throws IOException {
        HandleJsonTraces();
        HandleJsonTracesBB();
        System.out.println(procJson);
    }
    public static void HandleJsonTraces() throws IOException {
        String path_v1 = "../DiSL_Practice/json_out/output_v1.json";
        ObjectMapper mapper_v1 = new ObjectMapper();
        JsonNode jsonNode_v1 = mapper_v1.readTree(new File(path_v1));

        procJson.put("output_v1", "");
        traverse(jsonNode_v1, "output_v1");

        String path_v2 = "../DiSL_Practice/json_out/output_v2.json";
        ObjectMapper mapper_v2 = new ObjectMapper();
        JsonNode jsonNode_v2 = mapper_v2.readTree(new File(path_v2));

        procJson.put("output_v2", "");
        traverse(jsonNode_v2, "output_v2");


        // beautifying the json
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

    public static void HandleJsonTracesBB() throws IOException {
        String path_v1 = "../DiSL_Practice/json_out/BB_output_v1.json";
        ObjectMapper mapper_v1 = new ObjectMapper();
        JsonNode jsonNode_v1 = mapper_v1.readTree(new File(path_v1));

        procJson.put("BB_output_v1", "");
        traverse(jsonNode_v1, "BB_output_v1");

        String path_v2 = "../DiSL_Practice/json_out/BB_output_v2.json";
        ObjectMapper mapper_v2 = new ObjectMapper();
        JsonNode jsonNode_v2 = mapper_v2.readTree(new File(path_v2));

        procJson.put("BB_output_v2", "");
        traverse(jsonNode_v2, "BB_output_v2");

        String json = mapper_v1.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode_v1);

        try {
            File output_file = new File("../DiSL_Practice/json_out/formatted_BB_output_v1.json");

            BufferedWriter bf = new BufferedWriter(new FileWriter(output_file));
            bf.write(json);
            bf.close();

            System.out.println("Successfully wrote to file " + output_file.getName());
        } catch (IOException e) {
            System.out.println("Error writing to file");
            System.out.println(e.getMessage());
        }
    }

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
            procJson.put(mapKey, procJson.get(mapKey) + root.asText());
        }
    }

}

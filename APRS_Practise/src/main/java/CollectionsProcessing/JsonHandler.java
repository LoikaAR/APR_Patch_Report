package CollectionsProcessing;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHandler {

    public static void main(String[] args) throws IOException {
        HandleJsonTracesBB();
    }
    public static void HandleJsonTraces() throws IOException {
        String path = "../DiSL_Practice/output.json";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(new File(path));
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);


        try {
            File output_file = new File("../DiSL_Practice/formatted_output.json");
            BufferedWriter bf = new BufferedWriter(new FileWriter(output_file));
            bf.write(json);
            bf.close();
            System.out.println("Successfully wrote to file");
        } catch (IOException e) {
            System.out.println("Error writing to file");
            System.out.println(e.getMessage());
        }
    }

    public static void HandleJsonTracesBB() throws IOException {
        String path = "../DiSL_Practice/BB_output.json";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(new File(path));
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);


        try {
            File output_file = new File("../DiSL_Practice/formatted_BB_output.json");
            BufferedWriter bf = new BufferedWriter(new FileWriter(output_file));
            bf.write(json);
            bf.close();
            System.out.println("Successfully wrote to file");
        } catch (IOException e) {
            System.out.println("Error writing to file");
            System.out.println(e.getMessage());
        }
    }

}

package CollectionsProcessing;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonHandler {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(new File("../DiSL_Practice/output (copy).json"));
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);

        System.out.println(json);
    }
}

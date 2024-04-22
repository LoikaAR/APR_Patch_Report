package aprs_listproc;

import com.google.gson.Gson;
import java.util.concurrent.ConcurrentHashMap;
public class JsonProcessor {
    static Gson gson = new Gson();

    public static void process(ConcurrentHashMap<String, Object> map) {
        String json = gson.toJson(map);
        System.out.println(map);
    }
}
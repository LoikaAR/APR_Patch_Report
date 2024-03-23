package aprs_listproc;

import java.util.HashMap;

public class Profiler {
    private static HashMap<Integer, String> BasicBlockMap = new HashMap<>();

    public static void record(Integer idx, String alias) {
        BasicBlockMap.put(idx, alias);
    }
}
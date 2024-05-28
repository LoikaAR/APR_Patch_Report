package aprs_introclass;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ConcurrentHashMap;
//import src-app.aprs_introclass.ClassDef;

public class Profiler {
    private static AtomicLong nAllocations;
    public static String outName = "";
    public static AtomicLong nBytecodeExecuted = new AtomicLong(0);
    public static ConcurrentHashMap<String, Object> beforeBodyOutput = new ConcurrentHashMap<String, Object>();
    public static ConcurrentHashMap<String, Object> afterBodyOutput = new ConcurrentHashMap<String, Object>();
    public static ConcurrentHashMap<String, Object> methodsInvoked = new ConcurrentHashMap<String, Object>();

    public static HashMap<String, Object> varsBeforeBody = new HashMap<String, Object>();
    public static HashMap<String, Object> varsAfterBody = new HashMap<String, Object>();

    // this will run at the end of the program
    static {
        nAllocations = new AtomicLong(0);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("OUT NAME IS " + outName);
                afterBodyOutput.put("#Bytecodes_executed", nBytecodeExecuted.get());
                System.out.println("#Bytecodes executed: " + nBytecodeExecuted.get());

                try {
                    File file;
                    file = new File("./json_out/" + outName + "_test_0.json");
                    int idx = 0;
                    if (file.exists()) {
                        while (file.exists()) {
                            idx++;
                            file = new File("./json_out/" + outName + "_test_" + idx + ".json");
                        }
                    }
                    BufferedWriter bf = new BufferedWriter(new FileWriter(file));
                    System.out.println("writing to output");
                    bf.write("{\"Before\": {");
                    bf.write("\"Vars\": {");
                    int counter = 0;
                    for (Map.Entry<String, Object> entry : varsBeforeBody.entrySet()) {
                        counter++;
                        if (counter == varsBeforeBody.size()) {
                            bf.write("\"var_" + entry.getKey() + "\"" + ": " + "\"" + entry.getValue() + "\"");
                        } else {
                            bf.write("\"var_" + entry.getKey() + "\"" + ": " + "\"" + entry.getValue() + "\",");
                        }
                    }
                    bf.write("}},\n");

                    bf.write("\"After\": {");
                    bf.write("\"Vars\": {");
                    counter = 0;
                    for (Map.Entry<String, Object> entry : varsAfterBody.entrySet()) {
                        counter++;
                        if (counter == varsAfterBody.size()) {
                            bf.write("\"var_" + entry.getKey() + "\"" + ": " + "\"" + entry.getValue() + "\"");
                        } else {
                            bf.write("\"var_" + entry.getKey() + "\"" + ": " + "\"" + entry.getValue() + "\",");
                        }
                    }
                    bf.write("},");

                    bf.write("\"Methods_invoked\": {");
                    counter = 0;
                    for (Map.Entry<String, Object> entry : methodsInvoked.entrySet()) {
                        counter++;
                        if (counter == methodsInvoked.size()) {
                            bf.write("\"" + entry.getKey() + "\"" + ": " + entry.getValue());
                        } else {
                            bf.write("\"" + entry.getKey() + "\"" + ": " + entry.getValue() + ",");
                        }
                    }
                    bf.write("},");

                    bf.write("\"#Bytecodes_executed\": " + "\"" + afterBodyOutput.get("#Bytecodes_executed") + "\"" + ", ");
                    bf.write("\"#Objects_allocated\": " + "\"" + afterBodyOutput.get("#Objects_allocated") + "\"" + ", ");
                    bf.write("\"#Methods_invoked\": " + "\"" + afterBodyOutput.get("#Methods_invoked") + "\"" + ", ");
                    bf.write("\"Output\": " + "\"" + afterBodyOutput.get("output") + "\"");
                    bf.write("}}");
                    bf.close();
                } catch (IOException e) {
                    System.out.println("Error creating file");
                    e.printStackTrace();
                }
            }
        });
    }

    public static void incrementBytecodeCounter(int count) {
        nBytecodeExecuted.addAndGet(count);
    }

    public static void newAllocation() {
        nAllocations.incrementAndGet();
    }
}
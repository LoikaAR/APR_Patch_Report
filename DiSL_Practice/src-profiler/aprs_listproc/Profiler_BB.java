package aprs_listproc;

import java.util.Map;
import java.util.Iterator;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;

public class Profiler_BB {
    public static AtomicLong nTotalBytecodeExecuted = new AtomicLong(0);
    public static AtomicLong nGlobalBytecodeExecuted = new AtomicLong(0);
    public static AtomicLong nLocalBytecodeExecuted = new AtomicLong(0);
    private static AtomicLong nAllocations;

    public static LinkedList<BB_Entry> BBTrace = new LinkedList<BB_Entry>();

    public static ConcurrentHashMap<String, Object> beforeBodyOutput = new ConcurrentHashMap<String, Object>();
    public static ConcurrentHashMap<String, Object> afterBodyOutput = new ConcurrentHashMap<String, Object>();
    public static ConcurrentHashMap<String, Object> varsBeforeBody = new ConcurrentHashMap<String, Object>();
    public static ConcurrentHashMap<String, Object> varsAfterBody = new ConcurrentHashMap<String, Object>();
    public static ConcurrentHashMap<String, Object> methodsInvoked = new ConcurrentHashMap<String, Object>();


    // this will run at the end of the program
    static {
        nAllocations = new AtomicLong(0);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("#Bytecodes executed (total): " + nTotalBytecodeExecuted.get());
                System.out.println("#Bytecodes executed (global scope): " + nGlobalBytecodeExecuted.get());
                System.out.println("#Bytecodes executed (basic blocks 4, 6): " + nLocalBytecodeExecuted.get());

                afterBodyOutput.put("#Bytecodes_executed", nTotalBytecodeExecuted.get());

                try {
                    File file = new File("BB_output.json");
                    BufferedWriter bf = new BufferedWriter(new FileWriter(file));
                    System.out.println("writing to output");
                    System.out.println(BBTrace);

                    bf.write("[");
                    int glob_counter = 0;
                    for (int i = 0; i < BBTrace.size(); i++) {
                        glob_counter++;
                        BBTrace.get(i).setOrderIdx(i);
                        BB_Entry bbe = BBTrace.get(i);

                        bf.write("{\"" + bbe.getOrderIdx() + "\": {");
                        bf.write("\"ID\": " + "\"" + bbe.getId() + "\",");
                        bf.write("\"Vars\": {");
                        int counter = 0;
                        for (Map.Entry<String, String> entry : bbe.getLocalVars().entrySet()) {
                            counter++;
                            if (counter == bbe.getLocalVars().size()) {
                                bf.write("\"" + entry.getKey() + "\"" + ": " + "\"" + entry.getValue() + "\"");
                            } else {
                                bf.write("\"" + entry.getKey() + "\"" + ": " + "\"" + entry.getValue() + "\",");
                            }
                        }
                        bf.write("},\n");
                        bf.write("\"#Bytecodes\":" + "\"" + bbe.getnBytecodes() + "\"");

                        if (glob_counter == BBTrace.size()) {
                            bf.write("}}]");
                        } else {
                            bf.write("}},");
                        }
                    }

//
//                    bf.write("\"After\": {");
//                    bf.write("\"Vars\": {");
//                    counter = 0;
//                    for (Map.Entry<String, Object> entry : varsAfterBody.entrySet()) {
//                        counter++;
//                        if (counter == varsAfterBody.size()) {
//                            bf.write("\"" + entry.getKey() + "\"" + ": " + "\"" + entry.getValue() + "\"");
//                        } else {
//                            bf.write("\"" + entry.getKey() + "\"" + ": " + "\"" + entry.getValue() + "\",");
//                        }
//                    }
//                    bf.write("},");
//
//                    bf.write("\"Methods_invoked\": {");
//                    counter = 0;
//                    for (Map.Entry<String, Object> entry : methodsInvoked.entrySet()) {
//                        counter++;
//                        if (counter == methodsInvoked.size()) {
//                            bf.write("\"" + entry.getKey() + "\"" + ": " + entry.getValue());
//                        } else {
//                            bf.write("\"" + entry.getKey() + "\"" + ": " + entry.getValue() + ",");
//                        }
//                    }
//                    bf.write("},");
//
//                    bf.write("\"#Bytecodes_executed\": " + "\"" + afterBodyOutput.get("#Bytecodes_executed") + "\"" + ", ");
//                    bf.write("\"#Objects_allocated\": " + "\"" + afterBodyOutput.get("#Objects_allocated") + "\"" + ", ");
//                    bf.write("\"#Methods_invoked\": " + "\"" + afterBodyOutput.get("#Methods_invoked") + "\"" + ", ");
//                    bf.write("\"Output\": " + "\"" + afterBodyOutput.get("output") + "\"");
//                    bf.write("}}");
                    bf.close();
                } catch (IOException e) {
                    System.out.println("Error creating file");
                    e.printStackTrace();
                }
            }
        });
    }

    public static void incrementBytecodeCounterTotal(int count) {
        nTotalBytecodeExecuted.addAndGet(count);
    }

    public static void incrementBytecodeCounterGlobal(int count) {
        nGlobalBytecodeExecuted.addAndGet(count);
    }

    public static void incrementBytecodeCounterLocal(int count) {
        nLocalBytecodeExecuted.addAndGet(count);
    }

    public static void newAllocation() {
        nAllocations.incrementAndGet();
    }
}
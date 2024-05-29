package aprs_introclass;

import java.util.ArrayList;
import java.util.List;
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
    public static String outName = "";

    public static List<Integer> allocationsTrace = new ArrayList<Integer>();
    public static List<Integer> fieldAccessTrace = new ArrayList<Integer>();
    public static List<Integer> invocationsTrace = new ArrayList<Integer>();

    public static AtomicLong nTotalBytecodeExecuted = new AtomicLong(0);
    public static AtomicLong nGlobalBytecodeExecuted = new AtomicLong(0);
    public static AtomicLong nLocalBytecodeExecuted = new AtomicLong(0);
    private static AtomicLong nAllocations;

    public static LinkedList<BB_Entry> BBTrace = new LinkedList<BB_Entry>();

    // this will run at the end of the program
    static {
        nAllocations = new AtomicLong(0);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("#Bytecodes executed (total): " + nTotalBytecodeExecuted.get());
                System.out.println("#Bytecodes executed (global scope): " + nGlobalBytecodeExecuted.get());
                System.out.println("#Bytecodes executed (basic blocks 4, 6): " + nLocalBytecodeExecuted.get());

                System.out.println("allocations trace per basic block: " + allocationsTrace);
                System.out.println("field access trace per basic block: " + fieldAccessTrace);
                System.out.println("invocations trace per basic block: " + invocationsTrace);

                System.out.println("BB OUT NAME IS " + outName);
                try {
                    File file;
                    file = new File("./json_out/" + outName + "_test_0_BB.json");
                    int idx = 0;
                    if (file.exists()) {
                        while (file.exists()) {
                            idx++;
                            file = new File("./json_out/" + outName + "_test_" + idx + "_BB.json");
                        }
                    }

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
                                bf.write("\"var_" + entry.getKey() + "\"" + ": " + "\"" + entry.getValue() + "\"");
                            } else {
                                bf.write("\"var_" + entry.getKey() + "\"" + ": " + "\"" + entry.getValue() + "\",");
                            }
                        }
                        bf.write("},\n");
                        bf.write("\"#BytecodesExecuted\":" + "\"" + bbe.getnBytecodes() + "\"");

                        if (glob_counter == BBTrace.size()) {
                            bf.write("}}]");
                        } else {
                            bf.write("}},");
                        }
                    }
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
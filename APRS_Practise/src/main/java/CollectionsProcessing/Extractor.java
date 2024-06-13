package CollectionsProcessing;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;


/*
* Class to extract class definitions and test cases from introclassJava
* */
public class Extractor {
    public static String[] projects = {"digits", "checksum", "grade", "median", "smallest", "syllables"};
    private static long curLine = 1;
    private static final int sampleLimit = 5;

    public static void main(String[] args) throws IOException {
        for (String project : projects) {
            ExtractInstance(project);
            ExtractInstanceRef(project);
            ExtractTests(project);
        }
        ExtractInstrumentation();
    }

    public static void ExtractInstance(String project) {
        String initDirPath = "../IntroClassJava/dataset/" + project;
        File dir = new File(initDirPath);
        File outDir = new File("./ProcessedInstances/" + project);

        if (outDir.exists() && outDir.isDirectory()) {
            try {
                FileUtils.deleteDirectory(outDir);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            boolean remade = outDir.mkdir();
        } else {
            boolean made = outDir.mkdir();
        }

        int sampleIdx = 0;
        System.out.println(project + ": ");
        for (File repo : Objects.requireNonNull(dir.listFiles())) {             // dataset/*
            System.out.println(repo);
            for (File version : Objects.requireNonNull(repo.listFiles())) {     // dataset/project_name/*
                String path = initDirPath + "/" + repo.getName() + "/"
                        + version.getName() + "/src/main/java/introclassJava";
                File targetDir = new File(path);
                System.out.println(version.getName());
                System.out.println("td:" +targetDir.toPath());
                for (File f : Objects.requireNonNull(targetDir.listFiles())) {
                    if (f.isFile() && f.getName().endsWith(".java")) {
                        String targetPath = targetDir + "/" + f.getName();
                        File targetFile = new File(targetPath);
                        String outPath;
                        if (!f.getName().contains("_")) {
                            System.out.println("SEEN REF!");
                            outPath = "./ProcessedInstances/" + project + "/" + "REF_" + f.getName();
                        } else {
                            outPath = "./ProcessedInstances/" + project + "/" + f.getName();
                        }
                        File outFile = new File(outPath);
                        System.out.println("Writing to file: " + outFile.getName());
                        try {
                            BufferedWriter bf = writeFile(targetFile, outFile, project); // from target to out
                            bf.close();
                        } catch (IOException e) {
                            System.out.println("Error writing to file");
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
            // check sample number and if ref was read
            System.out.println(sampleIdx);
            if (sampleIdx >= sampleLimit) {
                return;
            }
            sampleIdx++;
        }
    }


    /*
    Function to extract only the reference projects from introclassJava repos
     */
    public static void ExtractInstanceRef(String project) {
        String targetPath = "../IntroClassJava/dataset/"
                + project
                + "/ref/reference/src/main/java/introclassJava/"
                + project.substring(0, 1).toUpperCase() + project.substring(1)
                + ".java";

        File targetFile = new File(targetPath);
        String outPath = "./ProcessedInstances/" + project + "/" + "REF_" + project + ".java";
        File outFile = new File(outPath);
        System.out.println("Writing to file: " + outFile.getName());
        try {
            BufferedWriter bf = writeFileRef(targetFile, outFile, project); // from target to out
            bf.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            System.out.println(e.getMessage());
        }
    }


    private static BufferedWriter writeFileRef(File targetFile, File outFile, String project) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(targetFile));
        StringBuilder fileContent = new StringBuilder();
        String line;
        br.readLine();
        while ((line = br.readLine()) != null) {
            if (line.contains("public class")) {
                fileContent.append("public class ClassDef {");
            } else if (line.contains(project + "_" + " = new") || line.contains("mainClass = new")) {
                fileContent.append("\tClassDef mainClass = new ClassDef();");
            } else if (line.contains("throws Exception") && !line.contains("public static void main")) {
                fileContent.append(line.replace("throws Exception", ""));
                fileContent.append("\n");
                fileContent.append("\tString name = " + "\"").append(outFile.getName().replace(".java", "")).append("\";");
            } else if (line.contains("throws Exception") && line.contains("public static void main")) {
                fileContent.append(line.replace("throws Exception", ""));
            } else if (line.contains("class IntObj {")
                    || line.contains("class FloatObj {")
                    || line.contains("class LongObj {")
                    || line.contains("class DoubleObj {")
                    || line.contains("class CharObj {")) {
                String hold = line;
                String inserted;
                if (hold.contains("Char")) {
                    inserted = """
                                \n\t@Override
                                public String toString() {
                                    if (this.value == 0) {
                                        return "null";
                                    } else {
                                        return Character.toString(this.value);
                                    }
                                }
                            """;
                } else {
                    inserted = """
                                \n\t@Override
                                public String toString() {
                                    return String.valueOf(this.value);
                                }
                            """;
                }
                inserted += "}";
//                System.out.println("\n\n\n" + inserted + "\n\n\n");
                hold = hold.substring(0, hold.length()-1);
                hold += inserted;
                fileContent.append(hold);
            } else {
                fileContent.append(line);
            }
            fileContent.append("\n");
        }

        BufferedWriter bf = new BufferedWriter(new FileWriter(outFile));
        String paketik = "package aprs_introclass;\n";
        bf.write(paketik);
        bf.write(fileContent.toString());
        return bf;
    }

    /*
    Helper function to process the class definition files
    (A brute force solution)
     */
    private static BufferedWriter writeFile(File targetFile, File outFile, String project) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(targetFile));
        String fileContent = "";
        String line;
        br.readLine();
        while ((line = br.readLine()) != null) {
            if (line.contains("public class")) {
                fileContent += "public class ClassDef {";
            } else if (line.contains(project + "_" + " = new") || line.contains("mainClass = new")) {
                fileContent += "\tClassDef mainClass = new ClassDef();";
            } else if (line.contains("throws Exception") && !line.contains("public static void main")) {
                fileContent += line.replace("throws Exception", "");
                fileContent += "\n";
                fileContent += "\tString name = " + "\"" + outFile.getName().replace(".java","") + "\";";
            } else if (line.contains("throws Exception") && line.contains("public static void main")) {
                fileContent += line.replace("throws Exception", "");
            } else if (line.contains("class IntObj {")
                    || line.contains("class FloatObj {")
                    || line.contains("class LongObj {")
                    || line.contains("class DoubleObj {")
                    || line.contains("class CharObj {")) {
                String hold = line;
                fileContent += line + "\n";
                fileContent += br.readLine() + "\n";
                fileContent += br.readLine() + "\n";
                fileContent += br.readLine() + "\n";
                fileContent += br.readLine() + "\n";
                fileContent += br.readLine() + "\n";
                if (hold.contains("Char")) {
                    fileContent += """
                                @Override
                                public String toString() {
                                    if (this.value == 0) {
                                        return "null";
                                    } else {
                                        return Character.toString(this.value);
                                    }
                                }
                            """;
                } else {
                    fileContent += """
                                @Override
                                public String toString() {
                                    return String.valueOf(this.value);
                                }
                            """;
                }
            }
            else {
                fileContent += line;
            }
            fileContent += "\n";
        }

        BufferedWriter bf = new BufferedWriter(new FileWriter(outFile));
        String paketik = "package aprs_introclass;\n";
        bf.write(paketik);
        bf.write(fileContent);
        return bf;
    }


    public static void ExtractTests(String projectName) {
        String initDirPath = "../IntroClassJava/dataset/" + projectName;
        File dir = new File(initDirPath);

        String versionPath = initDirPath + "/" + Arrays.stream(Objects.requireNonNull(dir.listFiles()))
                .toList().get(0).getName();
        File version = new File(versionPath);

        String itrPath = versionPath + "/" + Arrays.stream(Objects.requireNonNull(version.listFiles()))
                .toList().get(0).getName();
        String testPath = itrPath + "/src/test/java/introclassJava";
        File testFile = new File(testPath);

        for (File test : Objects.requireNonNull(testFile.listFiles())) {

            // get file line count
            long lineCount = 0;
            try (Stream<String> stream = Files.lines(test.toPath(), StandardCharsets.UTF_8)) {
                lineCount = stream.count();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            String targetPath = testPath + "/" + test.getName();
            File targetFile = new File(targetPath);

            String outDirPath =  "./IntroClassTests/" + projectName;
            new File(outDirPath).mkdir();

            String outPath;
            if (test.getName().contains("White")) {
                outPath = outDirPath + "/" + projectName + "_white_0.java";
                File outFile = new File(outPath);
                int var = 1;
                try {
                    BufferedWriter bf = splitTests(projectName, targetFile, outFile,
                            outDirPath, var, lineCount, false);
                } catch (IOException e) {
                    System.out.println("Error writing to file");
                    System.out.println(e.getMessage());
                }
            } else {
                outPath = outDirPath + "/" + projectName + "_black_0.java";
                File outFile = new File(outPath);
                int var = 1;
                try {
                    BufferedWriter bf = splitTests(projectName, targetFile, outFile,
                            outDirPath, var, lineCount, true);
                } catch (IOException e) {
                    System.out.println("Error writing to file");
                    System.out.println(e.getMessage());
                }
            }


        }
    }

    private static BufferedWriter splitTests(String projectName, File targetFile, File outFile, String outPath,
                                             int var, long lineCount, boolean black) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(targetFile));
        br.readLine(); // to omit package name

        BufferedWriter bf = new BufferedWriter(new FileWriter(outFile));
        bf.write("package aprs_introclass;\n");
        bf.write("import aprs_introclass.ClassDef;\n");

        // recursive call
        recursiveTestSplit(projectName, outPath, outFile, bf, br, var, lineCount, black);
        return bf;
    }

    private static void recursiveTestSplit(String projectName, String outPath, File outFile, BufferedWriter bf,
                                           BufferedReader br, int var, long lineCount, boolean black) {
        String line;
        try {
            while ((line = br.readLine()) != null) {
                if (line.contains("public class")) {
                    bf.write("public class MainInstance {");
                } else if (line.contains("@Test")) {
                    bf.write("\tpublic static void main(String[] args) {\n");
                } else if (line.contains(projectName+"_")) {
                    bf.write("\tClassDef mainClass = new ClassDef();\n");
                } else if (line.contains("exec")) {
                    bf.write("\tmainClass.exec();\n");
                } else if (line.contains("assertEquals") || line.contains("String out") || line.contains("import")) {
                    curLine++;
                    continue;
                // recursive call - read new file
                } else if (line.contains("}")) {
                    bf.write(line + "\n}");
                    bf.close();

                    br.mark((int)curLine);
                    if (br.readLine().contains("}")) {
                        break;
                    }
                    br.reset();

                    String outFileName =  outPath + "/" + projectName;
                    outFileName += black ? "_black_" : "_white_";
                    outFileName += var + ".java";
                    File outFileInner = new File(outFileName);

                    BufferedWriter bf_inner = new BufferedWriter(new FileWriter(outFileInner));
                    bf_inner.write("package aprs_introclass;\n");
                    bf_inner.write("import aprs_introclass.ClassDef;\n");
                    bf_inner.write("public class MainInstance {\n");
                    recursiveTestSplit(projectName, outPath, outFileInner, bf_inner, br, var+1, lineCount, black);
                } else {
                    bf.write(line + "\n");
                }
                curLine++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
    * Copy instrumentation_BB file content to many files with names of project versions */
    public static void ExtractInstrumentation() {
        File targetDir = new File("./ProcessedInstances");

        for (File project : Objects.requireNonNull(targetDir.listFiles())) {
            if (project.isDirectory()) {
                for (File version : Objects.requireNonNull(project.listFiles())) {
                    String classDefName = version.getName().substring(0, version.getName().indexOf("."));
                    String instrumPath = "../DiSL_Practice/src-profiler/aprs_introclass/";
                    String instrumName = "Instrumentation_BB_" + classDefName+ ".java";
                    File instrumFile = new File(instrumPath + instrumName);
                    if (instrumFile.exists()) {
                        instrumFile.delete();
                    }
                    try {
                        if (instrumFile.createNewFile()) {
                            String pathToDisl = instrumPath + "/Instrumentation_BB.java";
                            BufferedReader br = new BufferedReader(new FileReader(pathToDisl));   // from
                            BufferedWriter bw = new BufferedWriter(new FileWriter(instrumFile));  // to
                            String line;

                            while ((line = br.readLine()) != null) {
                                if (line.contains("Instrumentation_BB")) {
                                    bw.write("public class Instrumentation_BB_"
                                            + version.getName().substring(0, version.getName().indexOf("."))
                                            + " {\n");
                                } else if (line.contains("binarySearch") || line.contains("BinarySearch")) {
                                    bw.write(line.replace("BinarySearch", "Exec"));
                                    bw.write("\n");
                                } else if (line.contains("HashMap<String, String> LocalVars = new HashMap")
                                        && !line.contains("//")) {
                                    bw.write(line+"\n");
                                    String classDefPath = project + "/" + classDefName + ".java";
                                    File matchingDefFile = new File(classDefPath);
                                    BufferedReader bf_inner = new BufferedReader(new FileReader(matchingDefFile));
                                    String line_inner, defs = "";
                                    boolean flag = false;

                                    bw.write("\t\tLocalVars.put(\"0\",dc.getLocalVariableValue" +
                                            "(0, Object.class).toString());\n");
                                    bw.write("\t\tLocalVars.put(\"1\",dc.getLocalVariableValue(" +
                                            "1, String.class).toString());\n");

                                    // move inner reader to definitions (by introclass template)
                                    while ((line_inner = bf_inner.readLine()) != null) {
                                        if (line_inner.contains("output +=")) {
                                            break;
                                        } if (flag) {
                                            defs += line_inner + " "; // append definitions
                                        } if (line_inner.contains("String name = ")) { // reach this mark
                                            flag = true;
                                        }
                                    }
                                    int idx = 2;
                                    int charIdx = 0;
                                    String[] defsArr = defs.split(" ");
                                    for (int i = 0; i < defsArr.length; i++) {
                                        if (defsArr[i].equals("new")) {
                                            if (defsArr[i+1].equals("CharObj")) {
                                                bw.write("\t\tint s"+charIdx+";\n");
                                                bw.write("\t\tchar c"+charIdx+" = " +
                                                        "dc.getLocalVariableValue("+idx+", Character.class);\n");
                                                bw.write("\t\tif (c"+charIdx+" == 0) {\n");
                                                bw.write("\t\t\ts"+charIdx+" = 0;\n\t\t} ");
                                                bw.write("else {\n\t\t\ts"+charIdx+" = c"+charIdx+";\n\t\t}\n");
                                                bw.write("\t\tLocalVars.put(\""+idx+"\",\"char_\"+String.valueOf(s"+charIdx+"));\n");
                                                charIdx++;
                                            } else {
                                                bw.write("\t\tLocalVars.put(\""+idx+"\",dc.getLocalVariableValue(" +
                                                        idx+", Object.class).toString());\n");
                                            }
                                            idx++;
                                        }
                                    }
                                } else {
                                    bw.write(line);
                                    bw.write("\n");
                                }
                            }
                            bw.close();
                            br.close();
                            System.out.println("created new file " + instrumFile.getName());
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }
}
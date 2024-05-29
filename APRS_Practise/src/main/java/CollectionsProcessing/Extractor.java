package CollectionsProcessing;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.util.stream.Stream;

public class Extractor {
    private static long curLine = 1;
    public static void main(String[] args) throws IOException {

        ExtractInstance("digits");
        ExtractTests("digits");
    }

    public static void ExtractInstance(String project) {
        String initDirPath = "../IntroClassJava/dataset/" + project;
        File dir = new File(initDirPath);

        new File("./ProcessedInstances/" + project).mkdir();

        for (File repo : Objects.requireNonNull(dir.listFiles())) {
            for (File version : Objects.requireNonNull(repo.listFiles())) {
                String path = initDirPath + "/"
                        + repo.getName() + "/"
                        + version.getName() + "/src/main/java/introclassJava";
                File targetDir = new File(path);
                for (File f : Objects.requireNonNull(targetDir.listFiles())) {
                    if (f.isFile() && f.getName().endsWith(".java")) {
                        String targetPath = targetDir + "/" + f.getName();
                        File targetFile = new File(targetPath);
                        String outPath;
                        if (!f.getName().contains("_")) {
                            outPath = "./ProcessedInstances/" + project + "/" + "REF_" + f.getName();
                        } else {
                            outPath = "./ProcessedInstances/" + project + "/" + f.getName();
                        }
                        File outFile = new File(outPath);
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
        }
    }

    private static BufferedWriter writeFile(File targetFile, File outFile, String project) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(targetFile));
        String fileContent = "";
        String line;
        br.readLine();
        while ((line = br.readLine()) != null) {
            if (line.contains("public class")) {
                fileContent += "public class ClassDef {";
            } else if (line.contains(project) || line.contains("mainClass = new")) {
                fileContent += "\tClassDef mainClass = new ClassDef();";
            } else if (line.contains("throws Exception") && !line.contains("public static void main")) {
                fileContent += line.replace("throws Exception", "");
                fileContent += "\n";
                fileContent += "\tString name = " + "\"" + outFile.getName().replace(".java","") + "\";";
            } else if (line.contains("throws Exception") && line.contains("public static void main")) {
                fileContent += line.replace("throws Exception", "");
            }
            else {
                fileContent += line;
            }
            fileContent += "\n";
        }

        BufferedWriter bf = new BufferedWriter(new FileWriter(outFile));
        String pk = "package aprs_introclass;\n";
        bf.write(pk);
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

            // get line count
            long lineCount = 0;
            try (Stream<String> stream = Files.lines(test.toPath(), StandardCharsets.UTF_8)) {
                lineCount = stream.count();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            if (test.getName().contains("White")) {
                String targetPath = testPath + "/" + test.getName();
                File targetFile = new File(targetPath);

                String outDirPath =  "./IntroClassTests/" + projectName;
                new File(outDirPath).mkdir();

                String outPath = outDirPath + "/" + projectName + "0.java";
                File outFile = new File(outPath);
                int var = 1;
                try {
                    BufferedWriter bf = splitTests(projectName, targetFile, outFile, outDirPath, var, lineCount);
                } catch (IOException e) {
                    System.out.println("Error writing to file");
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private static BufferedWriter splitTests(String projectName, File targetFile,
                                             File outFile, String outPath, int var, long lineCount) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(targetFile));
        br.readLine(); // to omit package name

        BufferedWriter bf = new BufferedWriter(new FileWriter(outFile));
        bf.write("package aprs_introclass;\n");
        bf.write("import aprs_introclass.ClassDef;\n");

        // recursive call
        recursiveTestSplit(projectName, outPath, outFile, bf, br, var, lineCount);
        return bf;
    }

    private static void recursiveTestSplit(String projectName, String outPath, File outFile,
                                           BufferedWriter bf, BufferedReader br, int var, long lineCount) {
        String line;
        try {
            while ((line = br.readLine()) != null) {
                if (line.contains("public class")) {
                    bf.write("public class MainInstance {");
                } else if (line.contains("@Test")) {
                    bf.write("\tpublic static void main(String[] args) {\n");
                } else if (line.contains(projectName)) {
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

                    File outFileInner = new File(outPath + "/" + projectName + var + ".java");
                    BufferedWriter bf_inner = new BufferedWriter(new FileWriter(outFileInner));
                    bf_inner.write("package aprs_introclass;\n");
                    bf_inner.write("import aprs_introclass.ClassDef;\n");
                    bf_inner.write("public class MainInstance {\n");
                    recursiveTestSplit(projectName, outPath, outFileInner, bf_inner, br, var+1, lineCount);
                } else {
                    bf.write(line + "\n");
                }
                curLine++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
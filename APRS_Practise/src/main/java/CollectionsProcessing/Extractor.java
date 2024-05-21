package CollectionsProcessing;

import java.util.Arrays;
import java.util.Objects;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;

public class Extractor {
    public static void main(String[] args) throws IOException {

//        ExtractInstance("digits");
        ExtractTests("digits");
    }

    public static void ExtractInstance(String target) {
        String initDirPath = "../IntroClassJava/dataset/" + target;
        File dir = new File(initDirPath);

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
                            outPath = "./ProcessedInstances/" + "REF_" + f.getName();
                        } else {
                            outPath = "./ProcessedInstances/" + f.getName();
                        }
                        File outFile = new File(outPath);
                        try {
                            BufferedWriter bf = writeFile(targetFile, outFile); // from target to out
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

    private static BufferedWriter writeFile(File targetFile, File outFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(targetFile));
        String fileContent = "";
        String line;
        br.readLine();
        while ((line = br.readLine()) != null) {
            fileContent += line;
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
            if (test.getName().contains("White")) {
                String targetPath = testPath + "/" + test.getName();
                File targetFile = new File(targetPath);

                String outDirPath =  "./IntroClassTests/" + projectName;
                new File(outDirPath).mkdir();

                String outPath = outDirPath + "/" + test.getName();
                File outFile = new File(outPath);
                try {
//                    BufferedReader br = new BufferedReader(new FileReader(targetFile));
//                    String fileContent = "";
//                    String line;
//                    br.readLine();
//                    while ((line = br.readLine()) != null) {
//                        fileContent += line;
//                        fileContent += "\n";
//                    }

                    BufferedWriter bf = new BufferedWriter(new FileWriter(outFile));
                    String pk = "package aprs_introclass;\n";
                    bf.write(pk);
                    bf.write("fileContent");
                    bf.close();
                } catch (IOException e) {
                    System.out.println("Error writing to file");
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}



// java:
// extract all instances, process them
// fn to extract one at a time by idx, distinguish ref

// bash:
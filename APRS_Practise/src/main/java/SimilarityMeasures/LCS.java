package SimilarityMeasures;

import CollectionsProcessing.JsonHandler;

import java.io.IOException;
import java.util.Arrays;

public class LCS {

    // using a dp table
    public static int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();

        int[] prev = new int[m + 1];
        int[] cur = new int[m + 1];

        for (int idx1 = 1; idx1 < n + 1; idx1++) {
            for (int idx2 = 1; idx2 < m + 1; idx2++) {
                if (text1.charAt(idx1 - 1) == text2.charAt(idx2 - 1))
                    cur[idx2] = 1 + prev[idx2 - 1];
                else
                    cur[idx2] = Math.max(cur[idx2 - 1], prev[idx2]);
            }
            prev = Arrays.copyOf(cur, m + 1);
        }
        return cur[m];
    }

    public static void main(String[] args) {
        try {
            JsonHandler.HandleJsonTraces();
            JsonHandler.HandleJsonTracesBB();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            System.out.println(e.getMessage());
        }

        int score = longestCommonSubsequence(JsonHandler.procJson.get("output_v1"),
                JsonHandler.procJson.get("output_v2"));
//        int score = levenshteinTwoMatrixRows("hello", "helo");

        System.out.println("Longest common subsequence length: " + score);
    }

}

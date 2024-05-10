package SimilarityMeasures;

import java.util.Arrays;

public class LevenshteinDistance {
    public static int levenshteinTwoMatrixRows(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        int[] prevRow = new int[n + 1];
        int[] currRow = new int[n + 1];

        for (int j = 0; j <= n; j++) {
            prevRow[j] = j;
        }
        for (int i = 1; i <= m; i++) {
            currRow[0] = i;
            for (int j = 1; j <= n; j++) {
                // If characters are equal, no operation needed, take the diagonal value
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    currRow[j] = prevRow[j - 1];
                } else {
                    // If characters are not equal, find the minimum value of insert, delete, or replace
                    currRow[j] = 1 + Math.min(currRow[j - 1], Math.min(prevRow[j], prevRow[j - 1]));
                }
            }
            prevRow = Arrays.copyOf(currRow, currRow.length);
        }
        return currRow[n];
    }
}

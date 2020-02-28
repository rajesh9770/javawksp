package com.hackerrank.dynamicprog;

public class LongestCommonSubSequence {

    /**
     * Find the length of longest common subsequence
     * @param s1
     * @param s2
     * @return
     */
    public static int[][] lcs(String s1, String s2) {

        /**
         *        empty 1 2 3 4 5 ..
         *  empty
         *  1
         *  2
         *  3
         *  .
         *  .
         *  .
         *
         */
        int[][] lcs = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0 || j == 0)
                    lcs[i][j] = 0;
                else if (s1.charAt(i - 1) == s2.charAt(j - 1))
                    lcs[i][j] = 1+ lcs[i - 1][j - 1];
                else
                    lcs[i][j] = Math.max(lcs[i - 1][j], lcs[i][j - 1]);
            }
        }
        return lcs;
    }

    /**
     * find the # of chars to be removed so that strings will be same of deleting those chars
     * @param s1
     * @param s2
     * @return
     */
    public static int minDistancewithArray(String s1, String s2) {

        int[][] lcs = lcs(s1, s2);

        return s1.length() + s2.length() - 2 * lcs[s1.length()][s2.length()];
    }

    /**
     * Find a longest common subsequence.
     * @param lcs
     * @param a
     * @param b
     * @param i
     * @param j
     * @return
     */
    public static String backtrack(int [][] lcs, String a, String b, int i , int j) {

        if(i == 0 || j == 0)
            return "";
        if (a.charAt(i-1) == b.charAt(j-1))
            return backtrack(lcs, a, b, i-1, j-1) + a.charAt(i-1);
        if (lcs[i][j-1] > lcs[i-1][j])
            return backtrack(lcs, a, b, i, j-1);
        return backtrack(lcs, a, b, i-1, j);
    }

    public int minDistance(String s1, String s2) {
        int[][] memo = new int[s1.length() + 1][s2.length() + 1];
        return s1.length() + s2.length() - 2 * lcs(s1, s2, s1.length(), s2.length(), memo);
    }

    public int lcs(String s1, String s2, int m, int n, int[][] memo) {
        if (m == 0 || n == 0)
            return 0;
        if (memo[m][n] > 0)
            return memo[m][n];
        if (s1.charAt(m - 1) == s2.charAt(n - 1))
            memo[m][n] = 1 + lcs(s1, s2, m - 1, n - 1, memo);
        else
            memo[m][n] = Math.max(lcs(s1, s2, m, n - 1, memo), lcs(s1, s2, m - 1, n, memo));
        return memo[m][n];
    }

    public static void main(String[] args) {
        String s1 = "sea";
        String s2 = "eat";
        int [][] lcs = lcs(s1, s2);
        minDistancewithArray(s1,s2);
        System.out.println(backtrack(lcs, s1, s2, s1.length(), s2.length()));
    }
}

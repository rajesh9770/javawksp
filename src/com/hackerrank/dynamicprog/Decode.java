package com.hackerrank.dynamicprog;


/**
 * https://leetcode.com/problems/decode-ways/?source=post_page-----bb9403f03a46--------------------------------
 * A message containing letters from A-Z can be encoded into numbers using the following mapping:
 *
 * 'A' -> "1"
 * 'B' -> "2"
 * ...
 * 'Z' -> "26"
 * To decode an encoded message, all the digits must be grouped then mapped back into letters using the reverse of the mapping above
 * (there may be multiple ways). For example, "11106" can be mapped into:
 *
 * "AAJF" with the grouping (1 1 10 6)
 * "KJF" with the grouping (11 10 6)
 * Note that the grouping (1 11 06) is invalid because "06" cannot be mapped into 'F' since "6" is different from "06".
 *
 * Given a string s containing only digits, return the number of ways to decode it.
 *
 * The test cases are generated so that the answer fits in a 32-bit integer.
 */
public class Decode {

    public int numDecodings(String s) {
        int len = s.length();


        if(s==null|| s.isEmpty() || s.charAt(0) == '0') return 0;
        if(s.length() == 1) return 1;

        int[] ret = new int[s.length()]; // ret[i] = number of decoding of s[0...i]
        ret[0] = 1;

        if(s.charAt(1) == '0'){//it has go with prev char
            if(s.charAt(0) == '1' || s.charAt(0) == '2') {
                ret[1] = 1;
            }else{
                return  0;
            }
        }else{
            if(s.charAt(0) == '1' || (s.charAt(0) == '2' && s.charAt(1) <= '6')){
                ret[1] = 2;
            }else{
                ret[1] =1;
            }
        }

        for(int i=2; i<len; ++i){
            if(s.charAt(i) == '0'){//it has go with prev char
                if(s.charAt(i-1) == '1' || s.charAt(i-1) == '2') {
                    ret[i] = ret[i-2];
                }else{
                    return  0;
                }
            }else{
                if(s.charAt(i-1) == '1' || (s.charAt(i-1) == '2' && s.charAt(i) <= '6')){
                    ret[i] = ret[i-2] + ret[i-1];
                }else{
                    ret[i] = ret[i-1];
                }
            }

        }
        return ret[len-1];

    }

    public static void mainForDecode(String[] args) {
        System.out.println(new Decode().numDecodings("12") == 2);
        System.out.println(new Decode().numDecodings("226") == 3);
        System.out.println(new Decode().numDecodings("106") == 1);
        System.out.println(new Decode().numDecodings("06") == 0);
        System.out.println(new Decode().numDecodings("2101") == 1);
        System.out.println(new Decode().numDecodings("10011") == 0);
        System.out.println(new Decode().numDecodings("1201234") == 3);
    }


    /**
     * https://leetcode.com/problems/unique-paths/
     * There is a robot on an m x n grid.
     * The robot is initially located at the top-left corner (i.e., grid[0][0]).
     * The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1]).
     * The robot can only move either down or right at any point in time.
     *
     * Given the two integers m and n, return the number of possible unique paths that the robot
     * can take to reach the bottom-right corner.
     *
     * The test cases are generated so that the answer will be less than or equal to 2 * 109.
     *
     * This is same as going from grid[m - 1][n - 1] to grid[0][0] with only left and up turns are allowed.
     *
     * also combinatorially, you are going down by m -1 and right by n-1. Any path can be written as combination of
     * m-1 D and n-1 R's. e.g DDDRDR for m = 5 and R = 3.
     *
     * Total permutations = (m-1+n-1)! / ((m-1)! * (n-1)!)
     */
    public int uniquePaths(int m, int n) {
        if(m==1 && n==1) return 1;
        int[][] ret = new int[m][n];
        ret[0][0] = 0;
        for(int i=1; i<m; ++i){
            ret[i][0] = 1; //from column only ups are allowed
        }
        for(int j=1; j<n; ++j){
            ret[0][j] = 1; //from row only lefts are allowed
        }

        for(int i=1; i<m; ++i) {
            for (int j = 1; j < n; ++j) {
                ret[i][j] = ret[i-1][j] //go left first
                            + ret[i][j-1]; // go up first
            }
        }
        return ret[m-1][n-1];
    }

    public static void main(String[] args) {
        System.out.println(new Decode().uniquePaths(3, 7) == 28);
        System.out.println(new Decode().uniquePaths(3, 2) == 3);
        System.out.println(new Decode().uniquePaths(1, 1) == 1);
    }

}
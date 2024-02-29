package com.hackerrank.strings;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/maximum-number-of-non-overlapping-palindrome-substrings/
 * You are given a string s and a positive integer k.
 *
 * Select a set of non-overlapping substrings from the string s that satisfy the following conditions:
 *
 * The length of each substring is at least k.
 * Each substring is a palindrome.
 * Return the maximum number of substrings in an optimal selection.
 *
 * A substring is a contiguous sequence of characters within a string.
 */
public class NonOverlappingPalindromes {

    public int maxPalindromes(String s, int k) {
        boolean[][] allPalindromes = findAllPalindromes(s);
        int [] maxPalindromesOfSizeAtleastK = new int[s.length()]; //stores maxPalindromes[start] OfSize Atleast K for a string starting at start.
        for(int i=0; i<s.length(); ++i){
            maxPalindromesOfSizeAtleastK[i] = -1;
        }
        DFS(allPalindromes, 0,s.length(), k, maxPalindromesOfSizeAtleastK);
        return maxPalindromesOfSizeAtleastK[0];
    }

    /**
     * builds table diagonally
     * @param s
     * @return
     */
    public boolean[][] findAllPalindromes(String s){
        int len = s.length();
        boolean [][] dp = new boolean[len][len];
        for(int i=0; i<len; ++i){
            for(int j=0; j<len; ++j){
                dp[i][j] = false;
            }
        }
        for(int i=0; i<len; ++i){
            dp[i][i] = true;
            if(i<len-1) {
                dp[i][i + 1] = s.charAt(i) == s.charAt(i+1);
            }
        }
        for(int d=2; d<len; ++d){
            for(int i=0; i<len-d; ++i){
                int j = i+d;
                if(s.charAt(i) == s.charAt(j) && dp[i+1][j-1]){
                    dp[i][j] = true;
                }
            }
        }
        return dp;
    }

    public boolean[][] findAllPalindromesColumnar(String s){
        int len = s.length();
        boolean[][] dp = new boolean[len][len];

        //fill column wise
        for(int col=0; col<len; ++col){
            for (int row=0; row<=col; ++row){
                if(s.charAt(row) == s.charAt(col) && (row+1 > col-1  || dp[row+1][col-1])){
                    dp[row][col] = true;
                }
            }
        }
        return dp;
    }
    /**
     * Now we find nonoverlapping palindromes of size at least k
     * DFS returns nonoverlapping palindromes in the suffix substring s[start, ..., len]
     * @param allPalindromes
     * @param start
     * @param len
     * @param k
     * @param maxPalindromesOfSizeAtleastK
     * @return
     */
    public int DFS(boolean [][] allPalindromes, int start, int len, int k, int[] maxPalindromesOfSizeAtleastK){
        if(start>=len) return 0;
        if(maxPalindromesOfSizeAtleastK[start] != -1) return maxPalindromesOfSizeAtleastK[start];

        //first do not include character at start
        int max = DFS(allPalindromes, start+1, len, k, maxPalindromesOfSizeAtleastK);
        for(int end =start+k-1; end<len; ++end){
            if(allPalindromes[start][end]){
                max = Math.max(max, 1 + DFS(allPalindromes, end+1, len, k, maxPalindromesOfSizeAtleastK));
            }
        }
        maxPalindromesOfSizeAtleastK[start] = max;
        return max;
    }


    /**
     * Given a string s, partition s such that every substring of the partition is a palindrome.
     *
     * Return the minimum cuts needed for a palindrome partitioning of s.
     */
    public int minCut(String s) {
        int len = s.length();
        boolean[][] allPalindromes = findAllPalindromes(s);
        int [] mem = new int[len];
        for(int i=0; i<len; ++i){
            mem[i] = -1;
        }
        return minCut(0,len, allPalindromes, mem);
    }

    /**
     * returns min cuts required for s[start,....len-1]
     * @param start
     * @param len
     * @param allPalindromes
     * @param mem
     * @return
     */
    public int minCut(int start, int len, boolean[][] allPalindromes, int[] mem){
        if(start>=len) return 0;
        if(mem[start] != -1) return mem[start];

        int minCut = len - start -1;
        for(int end=start; end<len; ++end){//need to start at start, since a cut at the after the current character could give you a min cut as in a|bb
            if(allPalindromes[start][end]){
                if(end == len-1){
                    minCut = 0;// whole string s[start, end] is a string
                }else {//otherwise, make a cut between end and (end+1) and add min cuts required for string starting at end+1 i.e. s[end+1, ....len-1]
                    minCut = Math.min(minCut, 1 + minCut(end + 1, len, allPalindromes, mem));
                }
            }
        }
        mem[start] = minCut;
        return mem[start];
    }

    public int minCut2(String s) {
        char[] c = s.toCharArray();
        int n = c.length;
        int[] cut = new int[n];//cut[i] = stores the min cuts required for string s[0, i]
        boolean[][] pal = new boolean[n][n];
        //fill by column first
        for (int col = 0; col < n; col++) {
            int min = col;
            for (int row = 0; row <= col; row++) {
                if (c[row] == c[col] && (row + 1 > col - 1 || pal[row + 1][col - 1])) {
                    pal[row][col] = true;
                    min = row == 0 ? 0 : Math.min(min, cut[row - 1] + 1);
                }
            }
            cut[col] = min;
        }
        return cut[n - 1];
    }

    public static void main(String[] args) {
        //System.out.println(new NonOverlappingPalindromes().maxPalindromes("abaccdbbd", 3) == 2);

//        List<List<String>> aab = new NonOverlappingPalindromes().partition("aab");
//        System.out.println(aab);
//        List<List<String>> aaabaccdbbd = new NonOverlappingPalindromes().partition("abaccdbbd");
//        System.out.println(aaabaccdbbd);
        System.out.println(new NonOverlappingPalindromes().minCut("aab"));//1
        System.out.println(new NonOverlappingPalindromes().minCut("abaccdbbd"));//2
        System.out.println(new NonOverlappingPalindromes().minCut("cdd")); //1
    }

    /**
     * Given a string s, partition s such that every substring of the partition is a palindrome.
     * Return all possible palindrome partitioning of s.
     */
    public List<List<String>> partition(String s) {
        boolean[][] allPalindromes = findAllPalindromes(s);
        List<List<String>> ans = new ArrayList<>();
        findAllPartionsStart(s, allPalindromes, new ArrayList<>(), ans, 0, s.length());
        return ans;
    }

    public void findAllPartionsStart(String s, boolean [][] allPalindroms, List<String> ret, List<List<String>> ans, int start, int len){
        if(start == len){
            List<String> oneCompletePartition = new ArrayList<>();
            oneCompletePartition.addAll(ret);
            ans.add(oneCompletePartition);
            return;
        }
        for(int j=start; j<len; ++j){
            if(allPalindroms[start][j]){
                ret.add(s.substring(start, j+1));
                int currentId = ret.size();
                findAllPartionsStart(s, allPalindroms, ret, ans, j+1, len);
                ret.remove(currentId-1);
            }
        }
    }

    /**
     * Given a string s, return true if it is possible to split the string s into three non-empty palindromic substrings. Otherwise, return false.
     *
     * A string is said to be palindrome if it the same string when reversed.
     */

    public boolean checkPartitioning(String s) {
        int len = s.length();
        boolean[][] allPalindromesColumnar = findAllPalindromesColumnar(s);
        for(int left =1; left<len-1; ++left){//only traverse middle portion to leave left and right parts to see if they are palindromes
            for(int right=left; right<len-1; ++right){
                if(allPalindromesColumnar[0][left-1]
                        && allPalindromesColumnar[left][right]
                        && allPalindromesColumnar[right+1][len-1]) return true;
            }
        }
        return false;
    }
}

package com.hackerrank.dynamicprog;

import javafx.scene.Camera;

import java.util.Scanner;

/**
 * Created by Rajesh on 11/8/2017.
 */
public class PalindromeSubSequenceSize4 {

    private static int MOD = 1000000007;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String s = scan.nextLine();
        int[] singleCounts = new int[26];
        int[][] pairCounts = new int[26][26];
        int[] tripleCounts = new int[26];
        int ans = 0;

        for (int i = 0; i < s.length();i++) {
            int index = s.charAt(i) - 'a';
            ans = (ans + tripleCounts[index]) % MOD;
            if(i==s.length()-1) {
                System.out.println(ans);
                return;
            }
            for(int j=0; j<26; ++j){
                tripleCounts[j] = (tripleCounts[j] + pairCounts[j][index]) % MOD;
            }
            for(int j=0; j<26; ++j){
                pairCounts[j][index] = (pairCounts[j][index] + singleCounts[j]) % MOD;
            }
            singleCounts[index] = (singleCounts[index] + 1 ) % MOD;
        }
    }
}

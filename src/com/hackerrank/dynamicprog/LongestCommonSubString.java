package com.hackerrank.dynamicprog;

import java.util.ArrayList;
import java.util.List;

public class LongestCommonSubString {

    public static int lcsubstring(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int LCStuff[][] = new int[m + 1][n + 1];

        // To store length of the longest
        // common substring
        int result = 0;

        List<String> candidates = new ArrayList<>();

        // LCSuff[m+1][n+1] in bottom up fashion
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0) {
                    LCStuff[i][j] = 0;
                } else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    LCStuff[i][j] = LCStuff[i - 1][j - 1] + 1;
                    result = Integer.max(result,  LCStuff[i][j]);
                    candidates.add(s1.substring(i-LCStuff[i][j], i));
                } else {
                    LCStuff[i][j] = 0;
                }
            }
        }
        for (String candidate : candidates) {
            if(candidate.length() == result){
                System.out.println(candidate);
            }
        }

        return result;
    }

    //find the longest palindromic substring
    public static String lcp(String a){

        boolean [][] m = new boolean[a.length()][a.length()];
        int s = 0;
        int e = 0;
        for(int i=0; i<a.length(); ++i){
            m[i][i] = true;
            if(i+1<a.length() && a.charAt(i) == a.charAt(i+1)){
                m[i][i+1] = true;
                s=i; e=i+1;
            }
        }

        for(int k=2; k<a.length(); ++k){
            for(int i=0; i<a.length(); ++i){
                int j = i + k;
                if (j<a.length()){
                    if(a.charAt(i) == a.charAt(j)){
                        m[i][j] = m[i+1][j-1];
                    }
                    if(m[i][j]) {
                        if(e-s < j-i){
                            s=i;
                            e=j;
                        }
                    }
                }
            }
        }


//        for(int i=0; i<a.length(); ++i){
//            for(int j=0; j<a.length(); ++j){
//                System.out.print(m[i][j]? "T" : "F");
//            }
//            System.out.println();
//        }
//        System.out.println(a.substring(s,e+1));
        return a.substring(s,e+1);
    }

    public static void main(String[] args) {
        //lcp("abcba"); //ans: abcba
        String X = "OldSite:GeeksforGeeks.orgAAYYYYYYYYYY";
        String Y = "NewSite:GeeksAAYYYYYYYYYYQuiz.com";
        lcsubstring(X, Y);
    }


}

/**


 TFFFF
 FTFTF
 FFTFF
 FFFTF
 FFFFT
 **/
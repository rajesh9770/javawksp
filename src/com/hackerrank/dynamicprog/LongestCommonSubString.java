package com.hackerrank.dynamicprog;

public class LongestCommonSubString {

    public static int[][] lcs(String s1, String s2) {
        return null;
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
        lcp("abcba"); //ans: abcba
    }


}

/**


 TFFFF
 FTFTF
 FFTFF
 FFFTF
 FFFFT
 **/
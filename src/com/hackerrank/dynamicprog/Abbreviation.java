package com.hackerrank.dynamicprog;

import java.util.Scanner;

/**
 * Created by Rajesh on 10/26/2017.
 */

/**
 * You can perform the following operation on some string,
    1. Capitalize zero or more of 's lowercase letters at some index i (i.e., make them uppercase).
    2. Delete all of the remaining lowercase letters in .
    For two strings a and b, determine if it's possible to make a equal to b by performing the above operation on.
    aBbDdB and BBD  Ans: NO
    aBbDd and BBD  Ans: YES
 */
public class Abbreviation {

    static String abbreviation(char[] a, char[] b) {

        if(a.length < b.length) return "NO";
        for(char ch: b){
            if( !Character.isUpperCase(ch)) return "NO";
        }
        boolean [][] vals = new boolean[a.length+1][b.length+1];
        vals[0][0] = true;

        // Fill out the first column, where we are matching a with empty string
        for(int i=1; i<=a.length; ++i){
            if(Character.isUpperCase(a[i-1])){//we have upper case letter
                vals[i][0] = false;
            }else{
                vals[i][0] = vals[i-1][0];
            }
        }
        // first row is all false except the vals[0][0], since null can not be matched with non null upper case string
        for(int i=1; i<=b.length; ++i){
            vals[0][i] = false;
        }

        for(int i=1; i<=a.length; ++i){
            for(int j=1; j<=b.length; ++j){
                if(j>i) {
                    vals[i][j] = false; //a0-i is shorter than b0-j, it will never match
                }
                else if(a[i-1] == b[j-1]){ // we have to match a[i], since a[i] is upper case.
                    vals[i][j] = vals[i-1][j-1];
                }
                else if(Character.toUpperCase(a[i-1]) == b[j-1]){
                    // a[i] matches b[j] after upper casing, so we can either match or throw it
                    vals[i][j] = vals[i-1][j-1] || vals[i-1][j];
                }
                else if(Character.isLowerCase(a[i-1])){
                    // a[i] is lower case and does not match b[j]
                    vals[i][j] = vals[i-1][j]; // throw away a[i]
                }
                else{
                    // a[i] is upper case and does not match b[j]
                    vals[i][j] = false;
                }
            }
        }

        return vals[a.length][b.length] ? "YES" : "NO" ;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for(int a0 = 0; a0 < q; a0++){
            String a = in.next();
            String b = in.next();
            String result = abbreviation(a.toCharArray(), b.toCharArray());
            System.out.println(result);
        }
        in.close();
    }

}

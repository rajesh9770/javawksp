package com.hackerrank.dynamicprog;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * A string is said to be a child of a another string if it can be formed by deleting 0 or more characters
 * from the other string. Given two strings of equal length, what's the longest string that can be constructed
 * such that it is a child of both?
 */
public class CommonString {

    // Complete the commonChild function below.
    static int commonChild(String s1, String s2) {

        int l1 = s1.length();
        int l2 = s1.length();

        int [][] max = new int[l1][l2];

        boolean firstMatch = false;
        char firstChar = s1.charAt(0);
        for(int j=0; j<l2; ++j){
            if(firstMatch){
                max[0][j] = 1;
            }else if(firstChar == s2.charAt(j)){
                firstMatch = true;
                max[0][j] = 1;
            }else{
                max[0][j] = 0;
            }
        }

        firstMatch = false;
        firstChar = s2.charAt(0);
        for(int i=0; i<l1; ++i){
            if(firstMatch){
                max[i][0] = 1;
            }else if(firstChar == s1.charAt(i)){
                firstMatch = true;
                max[i][0] = 1;
            }else{
                max[i][0] = 0;
            }
        }

        for(int i=1; i<l1; ++i){
            for(int j=1; j<l2; ++j){
                max[i][j] = Math.max(max[i][j-1], max[i-1][j]);
                if(s1.charAt(i) == s2.charAt(j)){
                    max[i][j] = Math.max(max[i-1][j-1] + 1, max[i][j]);
                }
            }
        }

        return max[l1-1][l2-1];
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s1 = scanner.nextLine();

        String s2 = scanner.nextLine();

        int result = commonChild(s1, s2);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}

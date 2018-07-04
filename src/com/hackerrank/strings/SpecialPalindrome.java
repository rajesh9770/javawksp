package com.hackerrank.strings;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SpecialPalindrome {


    // Complete the substrCount function below.
    static long substrCount(int n, String s) {
        long counter = 0;

        for(int i=0; i<s.length(); ++i){
            char startChar = s.charAt(i);
            int ct = 1;
            ++counter;
            for(int j=i+1; j<s.length(); ++j){
                if(s.charAt(j) == startChar){
                    ++ct;
                    ++counter;
                }else{
                    //check there are at least ct startChar characters
                    if(s.length()-1-(j+1) +1 >= ct) {
                        for (int k = j + 1; k < s.length(); ++k) {
                            if (s.charAt(k) == startChar) {
                                --ct;
                                if (ct == 0) break;
                            } else break;
                        }
                        if (ct == 0) ++counter;
                    }
                    break;
                }
            }
        }
        return counter;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        String s = scanner.nextLine();

        long result = substrCount(n, s);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}

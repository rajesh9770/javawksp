package com.hackerrank.dynamicprog;

public class RegEx {

    public static boolean patternMatch(char[] pattern, char[] text) {

        boolean [][] matrix = new boolean[text.length+1][pattern.length +1];

        matrix[0][0] = true; //empty pattern does not match any empty text
        //0-th column is false
        for(int i=1; i< text.length +1 ; ++i){
            matrix[i][0] = false;  //empty pattern does not match any non-empty text
        }

        for (int j=1; j<pattern.length+1; ++j) {
            if ( pattern[j-1] == '*') { // a* or .* matches
                matrix[0][j] = matrix[0][j-2];
            } else {
                matrix[0][j] = false;
            }
        }

        for (int i=1; i< text.length+1; ++i) {
            for (int j=1; j< pattern.length+1; ++j) {
                if ( text[i-1] == pattern[j-1] || pattern[j-1] == '.' ) {
                    matrix[i][j] = matrix[i-1][j-1];
                }else if (pattern[j-1] == '*'){
                    /**
                     *                 (j-2)  (j-1)
                     *   pattern  ---   .|a     *
                     *                        (i-1)
                     *   text     --            a
                     *
                     */
                    matrix[i][j] = matrix[i][j-2];  // true or false if text upto i-1 matches pattern upto j-3, i.e. without last .* or a*, because .* is zero or more
                    if (pattern[j-2] == '.' || pattern[j-2] == text[i-1]) {
                        matrix[i][j] = matrix[i][j] || matrix[i-1][j];   // text upto i-2 matches pattern upto  j-1
                    }
                }else {
                    matrix[i][j] = false;
                }
            }
        }
        return matrix[text.length][pattern.length];
    }

    public static void main(String[] args) {
        System.out.println(patternMatch("mis*is*p*.".toCharArray(), "mississippi".toCharArray()));
        System.out.println(patternMatch("mis*is*ip*.".toCharArray(), "mississippi".toCharArray()));
    }
}

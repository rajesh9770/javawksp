package com.twopointer;

/**
 * @Easy
 * Given two strings s and t, return true if s is a subsequence of t, or false otherwise.
 *
 * A subsequence of a string is a new string that is formed from the original string by
 * deleting some (can be none) of the characters without disturbing the relative positions
 * of the remaining characters. (i.e., "ace" is a subsequence of "abcde" while "aec" is not).
 */
public class IsSubSequence {
    public static boolean isSubsequence(String s, String t) {
        int ptr1 = 0, ptr2 =0;
        while(ptr1 <s.length() && ptr2 <t.length()){
            if(s.charAt(ptr1) == t.charAt(ptr2)){
                ++ptr1;
                if(ptr1 == s.length()) return true;
            }
            ++ptr2;
        }
        return false;
    }





}

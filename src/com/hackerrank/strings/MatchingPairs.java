package com.hackerrank.strings;

import java.util.HashSet;
import java.util.Set;

/**
 * Given two strings s and t of length N, find the maximum number of possible matching pairs in strings s and t after swapping exactly two characters within s.
 * A swap is switching s[i] and s[j], where s[i] and s[j] denotes the character that is present at the ith and jth index of s, respectively.
 * The matching pairs of the two strings are defined as the number of indices for which s[i] and t[i] are equal.
 */
public class MatchingPairs {
    int matchingPairs(String s, String t) {
        Set<String> mismatchS = new HashSet<>();

        char[] sToChar = s.toCharArray();
        char[] tToChar = t.toCharArray();
        int matching =0;
        for (int i =0;i<sToChar.length;i++) {
            if(sToChar[i]!=tToChar[i]) {
                mismatchS.add(sToChar[i]+""+tToChar[i]);
            }
            else
                matching++;
        }
        for(String mism : mismatchS) {
            String reverse=mism.charAt(1)+""+mism.charAt(0);
            if(mismatchS.contains(reverse)) {
                return matching+2;
            }
        }

        if(mismatchS.size()==1)    // case where a a and b a,  is does not need to decrement matching.
            matching -=1;
        if(mismatchS.size()==0)
            matching -=2;
        return matching;
    }
}

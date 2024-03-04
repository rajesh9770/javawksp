package com.hackerrank.strings;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given two strings s and t, determine if they are isomorphic.
 *
 * Two strings s and t are isomorphic if the characters in s can be replaced to get t.
 *
 * All occurrences of a character must be replaced with another character while
 * preserving the order of characters.
 * No two characters may map to the same character, but a character may map to itself.
 */
public class IsomorphicStrings {

    public static boolean isIsomorphic(String s, String t) {
        int m12[] = new int [256];
        int m21[] = new int [256];
        Arrays.fill(m12, -1);
        Arrays.fill(m21, -1);

        for(int i=0; i<s.length(); ++i){
            if(m12[s.charAt(i)] != m21[t.charAt(i)]) return false;
            m12[s.charAt(i)] = m21[t.charAt(i)] =i;
        }
        return true;
    }

    /**
     * Given a pattern and a string s, find if s follows the same pattern.
     *
     * Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty word in s.
     *
     */

    public static boolean wordPattern(String pattern, String s) {
        String[] words = s.split("\\s+");
        if(pattern == null || words == null || words.length != pattern.length()) return false;
        Map<Character, String> mapping = new HashMap<>();
        Map<String, Character> revMapping = new HashMap<>();
        for(int i=0; i<pattern.length(); ++i){
            char c = pattern.charAt(i);
            String word = words[i];
            if(mapping.containsKey(c)){
                if(!mapping.get(c).equals(word)) return false;
            }else{
                if(revMapping.containsKey(word)) return false;
                mapping.put(c, word);
                revMapping.put(word, c);
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(wordPattern("abba",  "dog dog dog dog"));//false
    }
}

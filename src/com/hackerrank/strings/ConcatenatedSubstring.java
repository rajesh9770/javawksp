package com.hackerrank.strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Hard but easy solution.
 * https://leetcode.com/problems/substring-with-concatenation-of-all-words
 * You are given a string s and an array of strings words. All the strings of words are of the same length.
 *
 * A concatenated substring in s is a substring that contains all the strings of any permutation of words concatenated.
 *
 * For example, if words = ["ab","cd","ef"],
 * then "abcdef", "abefcd", "cdabef", "cdefab", "efabcd", and "efcdab" are all concatenated strings.
 * "acdbef" is not a concatenated substring because it is not the concatenation of any permutation of words.
 * Return the starting indices of all the concatenated substrings in s. You can return the answer in any order.
 */
public class ConcatenatedSubstring {

    public static List<Integer> findSubstring(String s, String[] words) {
        Map<String, Integer> counts = new HashMap<>();
        List<Integer> matches = new ArrayList<>();
        for (String word : words) {
            counts.put(word, counts.getOrDefault(word, 0)+1);
        }
        int len = words[0].length();
        //Note that all words in words have same length, so after concatenating them we get a word of length words.length * len
        //we can slide through s using window of length words.length * len
        for(int i = 0; i < s.length() - words.length * len +1; ++i){
            //get the current sliding window
            String str = s.substring(i, i + words.length * len);
            if(checkMatch(str, counts, len)){
                matches.add(i);
            }
        }
        return matches;
    }

    private static boolean checkMatch(String str, Map<String, Integer> counts, int len) {
        HashMap<String, Integer> matched = new HashMap<>();
        for(int i=0; i<str.length(); i+=len){
            String wordToMatch = str.substring(i, i+len);
            if(counts.containsKey(wordToMatch)){
                matched.put(wordToMatch, matched.getOrDefault(wordToMatch, 0) + 1);
            }
        }
        return matched.equals(counts);
    }

    public static void main(String[] args) {
        String [] words = new String[] {"foo","bar"};
        String str = "barfoothefoobarman";
        List<Integer> substring = findSubstring(str, words);
        System.out.println(substring);//0 9
    }
}

package com.hackerrank.strings;

/**
 * Created by Rajesh on 3/17/2018.
 */

import java.util.*;

/**
 * Given a sentence and a set of characters. Find the minimum window within which the set of characters can be found in the sentence in any order.
 * http://coding-interview-archives.blogspot.com/2013/09/find-minimum-window-in-string.html
 * See com.hackerrank.strings.ExactBroadBestMatch
 * https://leetcode.com/problems/minimum-window-substring/
 */
public class MinWindowAllChars {


    private static class IntWrapper{
        int ct;
        public IntWrapper(int c){
            ct=c;
        }

    }
    private static int findWindow(char[] str, char[] find){

        Map<Character, Integer> freq = new HashMap<>();
        Map<Character, IntWrapper> currFreq = new HashMap<>();
        for(char ch:find) {
            Integer ct = freq.getOrDefault(ch, new Integer(0));
            freq.put(ch, ++ct);
            currFreq.put(ch, new IntWrapper(0));
        }

        int count = 0;
        int left=0;
        int min = Integer.MAX_VALUE;
        int start=-1, end = -1;
        for(int right=0; right<str.length; ++right){
            char ch = str[right];
            if(!freq.containsKey(ch)) continue;

            int currCt = ++currFreq.get(ch).ct;
            if(freq.get(ch)>=currCt){
                count++; //note count never decreases
            }
            if(count == find.length){
                while(!freq.containsKey(str[left]) || currFreq.get(str[left]).ct > freq.get(str[left])){
                    if(freq.containsKey(str[left]) && currFreq.get(str[left]).ct > freq.get(str[left])){
                        currFreq.get(str[left]).ct--;
                    }
                    ++left;
                }
                if(right-left+1 < min){
                    min = right-left+1;
                    start = left;
                    end = right;
//                    for(int k=start; k<=end; ++k){
//                        System.out.print(str[k]);
//                    }
//                    System.out.println(" " + min);
                }
            }
        }
        return min;
    }


    public static void main1(String[] args) {
//        System.out.println(~5);
//        System.out.println(Integer.toBinaryString(~0));
//        if(true) return;
        String str = "this is a tis test string";
        String tar = "tisat";
        System.out.println("\nAns : " + findWindow(str.toCharArray(), tar.toCharArray()));

        str = "bbbaaaa bc aaaa c";
        tar = "bc";
        System.out.println(findWindow(str.toCharArray(), tar.toCharArray()));
    }


    /**
     * You are given two strings s and t. You can select any substring of string s and rearrange the characters of the selected substring.
     * Determine the minimum length of the substring of s such that string t is a substring of the selected substring.
     * s = "dcbefebce"
     * t = "fd"'
     * output = 5
     * Basically we want to find a smallest substring of s which contains all the characters of t
     */
    public static int minLengthSubstring(String s, String t){
        Map<Character, Integer> desiredFreq = new HashMap<>();
        Map<Character, Integer> currFreq = new HashMap<>();

        for(char c: t.toCharArray()){
            Integer count = desiredFreq.getOrDefault(c, new Integer(0));
            desiredFreq.put(c, ++count);
        }

        int left = 0;
        int matchedCount = 0;
        int min = Integer.MAX_VALUE;
        for(int i=0; i< s.length(); ++i){
            char c = s.charAt(i);
            if(!desiredFreq.containsKey(c)) continue;

            Integer ct = currFreq.getOrDefault(c, new Integer(0));
            currFreq.put(c, ++ct);
            if(currFreq.get(c)<= desiredFreq.get(c)){
                matchedCount++;
            }
            if(t.length() == matchedCount){
                char lc = s.charAt(left);
                while(!desiredFreq.containsKey(lc)
                        || desiredFreq.get(lc)< currFreq.get(lc)){

                    if(desiredFreq.containsKey(lc)
                            && desiredFreq.get(lc)< currFreq.get(lc)){
                        Integer cc = currFreq.get(lc);
                        currFreq.put(lc, --cc);
                    }
                    ++left;
                }
                if(i-left+1 < min) min = i-left+1;
            }
        }
        return min == Integer.MAX_VALUE ?  -1: min;
    }

    /**
     * https://leetcode.com/problems/longest-repeating-character-replacement/
     * You are given a string s and an integer k.
     * You can choose any character of the string and change it to any other uppercase English character.
     * You can perform this operation at most k times.
     *
     * Return the length of the longest substring containing the same letter you can get after performing the above operations.
     * Input: s = "ABAB", k = 2
     * Output: 4
     * Explanation: Replace the two 'A's with two 'B's or vice versa.
     */
    public static int characterReplacement(String s, int k) {
        int[] counts = new int[26]; //stores the frequency of character
        Arrays.fill(counts, 0);
        int start=0;

        int maxFreq = 0;
        int ans = 0;
        for(int end=0; end<s.length(); ++end){
            int newFreq = ++counts[s.charAt(end) - 'A'];
            maxFreq = Math.max(maxFreq, newFreq);
            int charsToChange = (end-start+1) - maxFreq;  //think that the character with the most frequency is within the window repeated maxFreq times
            if( charsToChange > k){
                counts[s.charAt(start) - 'A']--;
                start++;
            }
            ans = Math.max(ans, end - start +1);
            System.out.println(s.substring(start, end+1));
        }
        return ans;
    }


    public static void main(String[] args) {
        System.out.println(characterReplacement("ABBBBBBBBCPQRTSLBBBBBBBBBBBBBBBBBBBBBBAC", 2));//24
    }
}

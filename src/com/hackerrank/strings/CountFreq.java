package com.hackerrank.strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Maximum instance of word in TEXT
 * Given a INPUT and WORD (both lowercase) find as many instances of WORD as possible from
 * characters of INPUT. Each character in INPUT can be used only once.
 * Ex:
 * hello, halloe Ans: 1
 * intuit inatuaibtaaaainatuabiaata  ans 2
 */
public class CountFreq {


    public static int maxWord(String input, String word) {

        Map<Character, Integer> freq = getFreq(word);


        int count = 0;
        int ans = 0;

        Map<Character, Integer> curr = new HashMap();
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            if (!freq.containsKey(c)) continue;


            Integer ct = curr.get(c);
            if (ct == null) {
                ct = 0;
            }
            ++ct;
            curr.put(c, ct);
            if (freq.get(c) >= ct) {
                count++;
            }
            //System.out.println("Before: " + ans + " " + i + " " + c + " " + count );
            if (count == word.length()) {
                count = processFreq(curr, freq);
                ++ans;
            }
            //System.out.println(ans + " " + i + " " + c + " " + count );
        }

        return ans;
    }

        public static int processFreq(Map<Character, Integer> curr, Map<Character, Integer> freq){
            int count = 0;
            for(Character c : freq.keySet()){
                int ct = freq.get(c); //I need that many now
                int currct = curr.get(c); //I currently have this many
                int remaining = currct - ct; //remaining after using the ones that I need now
                curr.put(c, remaining);
                count += Math.min(remaining, ct); //we need to account for character that we have seen so far.
            }
            return count;

        }

        public static Map<Character, Integer> getFreq(String word){

            Map<Character, Integer> freq = new HashMap();
            for(int i=0; i< word.length(); ++i){

                Integer c = freq.get(word.charAt(i));

                if(c == null){
                    c = 0;
                }
                ++c;
                freq.put(word.charAt(i), c);
            }
            return freq;

        }

    public static Map<Character, Integer> getFreq2(String word, Map<Character, Integer> input){

        Map<Character, Integer> freq = new HashMap();
        for(int i=0; i< word.length(); ++i){
            if(input.containsKey(word.charAt(i))) {
                Integer c = freq.get(word.charAt(i));

                if (c == null) {
                    c = 0;
                }
                ++c;
                freq.put(word.charAt(i), c);
            }
        }
        return freq;

    }


    public static int maxWordEasy(String input, String word){
        int [] inputFreq = new int[26];
        int [] wordFreq = new int[26];
        Arrays.fill(inputFreq, 0);
        Arrays.fill(wordFreq, 0);

        for(int i=0; i<input.length(); ++i){
            inputFreq[input.charAt(i)-'a']++;
        }

        for(int i=0; i<word.length(); ++i){
            wordFreq[word.charAt(i)-'a']++;
        }

        int ans = Integer.MAX_VALUE;
        for(int i=0; i<26; ++i){
            if(wordFreq[i] > 0) {
                ans = Math.min(ans, inputFreq[i] / wordFreq[i]);
            }
        }
        return ans;
    }
        public static void main1(String [] args){

            int ans = maxWordEasy("inatuuaitaiaanftfdufigtsd", "intuit");
            System.out.println(ans);
//            ans = maxWordEasy("halloe", "hallo");
//            System.out.println(ans);

        }

    public static void main(String[] args) {
        int ans = maxWord("inatuuaitaiaanftfdufigtsd", "intuit");
        System.out.println(ans);
        ans = maxWord("halloe", "hallo");
        System.out.println(ans);
    }
    }


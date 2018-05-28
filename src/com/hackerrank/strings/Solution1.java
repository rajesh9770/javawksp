package com.hackerrank.strings;

import java.util.*;

/**
 * Created by Rajesh on 3/17/2018.
 */
public class Solution22 {


    private static class WordCount{
            long count;

            public WordCount(long c){
                this.count = c;
            }
        }

        // METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
        public static List<String> retrieveMostFrequentlyUsedWords(String literatureText,
                                                     List<String> wordsToExclude)
        {
            Set<String>  ignoreWords = new HashSet<>();
            for(String str: wordsToExclude){
                ignoreWords.add(str.toLowerCase());
            }
            Map<String, WordCount> wordFreq = new HashMap<>();
            StringBuilder word = new StringBuilder();
            boolean inside = false;
            WordCount maxFreq = new WordCount(Long.MIN_VALUE);
            List<String> freqWords = new ArrayList<>();
            for(int i=0; i<literatureText.length(); ++i){
                char ch = literatureText.charAt(i);
                if(Character.isLetter(ch)){
                    if(inside){
                        word.append(ch);
                    }else{//start a new word
                        word.setLength(0);
                        word.append(ch);
                        inside = true;
                    }
                }else{
                    if(inside){
                        String newWord = word.toString().toLowerCase();
                        if(!ignoreWords.contains(newWord)){
                            addWord(newWord, wordFreq, maxFreq, freqWords);
                        }
                        inside = false;
                    }
                }
            }
            if(inside){
                String newWord = word.toString().toLowerCase();
                if(!ignoreWords.contains(newWord)){
                    addWord(newWord, wordFreq, maxFreq, freqWords);
                }
                inside = false;
            }

            return  freqWords;
        }
        // METHOD SIGNATURE ENDS

    public static void addWord(String word, Map<String, WordCount> countMap, WordCount maxFreq, List<String> freqWords){

            WordCount wordFreq = countMap.get(word);
            if(wordFreq != null){
                wordFreq.count++;
            }else{
                wordFreq = new WordCount(1);
                countMap.put(word, wordFreq);
            }
            if(wordFreq.count == maxFreq.count){
                freqWords.add(word);
            }else if(wordFreq.count > maxFreq.count){
                maxFreq.count = wordFreq.count;
                freqWords.clear(); //start a new list
                freqWords.add(word);
            }
    }
    public static void main(String[] args) {
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add("is");
        tmp.add("and");
        tmp.add("he");
        tmp.add("the");
        tmp.add("to");

        List<String> strings = retrieveMostFrequentlyUsedWords("Rose is a flower red rose are flower", tmp);
        //List<String> strings = retrieveMostFrequentlyUsedWords("Jack and Jill went to the market to buy bread and cheese. Cheese is Jack's and Jill's favr food", tmp);
        for(String s : strings){
            System.out.println(s);
        }


    }

}

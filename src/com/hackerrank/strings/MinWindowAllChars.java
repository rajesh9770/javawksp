package com.hackerrank.strings;

/**
 * Created by Rajesh on 3/17/2018.
 */

import java.util.*;

/**
 * Given a sentence and a set of characters. Find the minimum window within which the set of characters can be found in the sentence in any order.
 * http://coding-interview-archives.blogspot.com/2013/09/find-minimum-window-in-string.html
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


    public static void main(String[] args) {
        String str = "this is a tis test string";
        String tar = "tis";
        findWindow(str.toCharArray(), tar.toCharArray());
    }
}

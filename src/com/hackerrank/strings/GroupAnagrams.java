package com.hackerrank.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Given an array of strings strs, group the anagrams together. You can return the answer in any order.
 *
 * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase,
 * typically using all the original letters exactly once.
 */
public class GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String nStr = new String(chars);
            if(map.containsKey(nStr)){
                map.get(nStr).add(str);
            }else {
                map.putIfAbsent(nStr, new ArrayList<>());
                map.get(nStr).add(str);
            }
        }
        return new ArrayList(map.values());
    }
}

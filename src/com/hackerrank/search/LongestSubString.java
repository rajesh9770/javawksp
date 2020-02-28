package com.hackerrank.search;

import java.util.HashMap;
import java.util.Map;

/**
 * Find the length of the longest substring of a string without duplicate characters.
 */
public class LongestSubString {

        public int lengthOfLongestSubstring(String s) {

            int n = s.length(), ans = 0;
            Map<Character, Integer> map = new HashMap<>(); // current index of character
            // try to extend the range [i, j]
            for (int j = 0, i = 0; j < n; j++) {
                if (map.containsKey(s.charAt(j))) {
                    //left end -i- excludes the current char, as map contains previous index of that char + 1.
                    i = Math.max(map.get(s.charAt(j)), i);
                }
                ans = Math.max(ans, j - i + 1);
                map.put(s.charAt(j), j + 1);
            }
            return ans;


        }

}

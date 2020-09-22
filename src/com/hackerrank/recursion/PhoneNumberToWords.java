package com.hackerrank.recursion;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent.
 *
 * A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
 */
public class PhoneNumberToWords {

    static List<List<Character>> mapping = new ArrayList<>();

    static {
        mapping.add(Arrays.asList('a', 'b', 'c'));
        mapping.add(Arrays.asList('d', 'e', 'f'));
        mapping.add(Arrays.asList('g', 'h', 'i'));
        mapping.add(Arrays.asList('j', 'k', 'l'));
        mapping.add(Arrays.asList('m', 'n', 'o'));
        mapping.add(Arrays.asList('p', 'q', 'r', 's'));
        mapping.add(Arrays.asList('t', 'u', 'v'));
        mapping.add(Arrays.asList('w', 'x', 'y', 'z'));
    }

    public static void main(String[] args) {

        List<String> strings = new PhoneNumberToWords().letterCombinations("23");
        System.out.println(strings);
        strings = new PhoneNumberToWords().letterCombinations("");
        System.out.println(strings);
    }

    public List<String> letterCombinations(String digits) {
        if (digits.equals("")) return Collections.emptyList();
        List<String> results = new ArrayList<>();
        iterate(results, digits.toCharArray(), new char[digits.length()], 0, 0);
        return results;
    }

    public void iterate(List<String> repo, char[] digits, char[] out, int inIdx, int outIdx){

        if(digits.length == inIdx) {
            repo.add(new String(out));
            //System.out.println(new String(out));
            return;
        }

        char c = digits[inIdx];
        List<Character> letters = mapping.get(c-'2');

        for (Character letter : letters) {
            out[outIdx] = letter;
            iterate(repo, digits, out, inIdx+1, outIdx+1);
        }
    }
}

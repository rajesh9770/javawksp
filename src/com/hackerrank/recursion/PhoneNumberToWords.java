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
        mapping.add(Arrays.asList('a', 'b', 'c'));//2
        mapping.add(Arrays.asList('d', 'e', 'f'));//3
        mapping.add(Arrays.asList('g', 'h', 'i'));//4
        mapping.add(Arrays.asList('j', 'k', 'l'));
        mapping.add(Arrays.asList('m', 'n', 'o'));
        mapping.add(Arrays.asList('p', 'q', 'r', 's'));//7
        mapping.add(Arrays.asList('t', 'u', 'v'));//8
        mapping.add(Arrays.asList('w', 'x', 'y', 'z'));//9
    }

    public static void main1(String[] args) {

        List<String> strings = new PhoneNumberToWords().letterCombinations("23");
        System.out.println(strings);
        strings = new PhoneNumberToWords().letterCombinations("");
        System.out.println(strings);
    }

    public static void main(String[] args) {
        List<String> out = new ArrayList<>();
        process(out, "123", new StringBuilder(), 0);
        System.out.println(out);
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



    public static void process(List<String> all, String digits, StringBuilder out, int inIdx){
        if(inIdx == digits.length()){
            all.add(out.toString());
            return;
        }

        char c = digits.charAt(inIdx);
        if(c == '0' || c == '1') {
            int size = out.length();
            out.append(c);
            process(all, digits, out, inIdx + 1);
            out.setLength(size);
            return;
        }
        List<Character> mappedChars = mapping.get(c - '2');
        for(int i=0; i<mappedChars.size(); ++i){
            int size = out.length();
            out.append(mappedChars.get(i));
            process(all, digits, out, inIdx + 1);
            out.setLength(size);
        }
    }
}

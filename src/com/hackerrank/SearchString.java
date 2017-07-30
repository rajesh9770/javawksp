package com.hackerrank;

import com.hackerrank.utils.Trie;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Rajesh on 7/29/2017.
 */

public class SearchString {

    private static Character[] toCharArray(String a){
        Character[] ret = new Character[a.length()];
        int ct = 0 ;
        for(char i : a.toCharArray()){
            ret[ct++] = i;
        }
        return ret;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<Character[]> strings = IntStream.range(0, in.nextInt()).mapToObj(i -> toCharArray(in.next())).collect(Collectors.toList());
        Trie<Character> trie = new Trie<Character>(strings);
        int i = in.nextInt();
        while(i-->0){
            String next = in.next();
            int contains = trie.contains(toCharArray(next), true);
            System.out.println(contains == -1 ? "0" : contains);
        }
    }


}

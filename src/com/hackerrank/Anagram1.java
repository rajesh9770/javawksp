package com.hackerrank;

import java.util.HashMap;
import java.util.Scanner;

public class Anagram1 {

    public Anagram1() {
        // TODO Auto-generated constructor stub
    }

    private static class Pair{
        int first, second;
        public Pair(int first, int second){
            this.first = first;
            this.second = second;
        }
        
    }
    public static int numberNeeded(String first, String second) {
        HashMap<Character, Pair> freq = new HashMap<Character, Anagram1.Pair>();
        for(int i=0; i<first.length(); ++i){
            Pair pair = freq.get(first.charAt(i));
            if(pair == null) {
                freq.put(first.charAt(i), pair = new Pair(0,0));
            }
            pair.first++;
        }
        for(int i=0; i<second.length(); ++i){
            Pair pair = freq.get(second.charAt(i));
            if(pair == null) freq.put(second.charAt(i), pair = new Pair(0,0));
            pair.second++;
        }
        int deletes = 0;
        for(Pair p: freq.values()){
            deletes += Math.abs(p.first-p.second);
        }
        return deletes;
    }
  
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String a = in.next();
        String b = in.next();
        System.out.println(numberNeeded(a, b));
    }

}

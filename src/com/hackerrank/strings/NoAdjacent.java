package com.hackerrank.strings;

/**
 * Created by Rajesh on 3/17/2018.
 */

import java.util.PriorityQueue;

import java.util.HashMap;

/**
 * Rearrange characters in a string such that no two adjacent are same
 */
public class NoAdjacent
{

    private static class  Node implements Comparable<Node> {
        char c;
        int frequency;

        Node(char c, int freq){
            this.c =c;
            this.frequency = freq;
        }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof Node) && ((Node)(obj)).c == this.c;
        }

        @Override
        public int hashCode() {
            return frequency;
        }

        @Override
        public int compareTo(Node o) {
            return o.frequency-frequency;
        }
    }
    public static String reArrange(String str){
        StringBuilder buff = new StringBuilder();
        char c[] = str.toCharArray();
        PriorityQueue<Node> queue = new PriorityQueue<>(str.length());
        int [] characterFreq = new int [26];
        for(int i=0; i<characterFreq.length; ++i) characterFreq[i] = 0;
        for(char ch: c){
            characterFreq[ch-'a']++;
        }
        for(int i=0; i<characterFreq.length; ++i){
            if(characterFreq[i] >0){
                queue.add(new Node( (char) ('a'+ i), characterFreq[i]));
            }
        }
        Node prev = null;
        while(!queue.isEmpty()){
            Node pop = queue.remove();
            if(prev != null)  queue.add(prev);
            buff.append(pop.c);
            pop.frequency--;
            prev = pop.frequency >0 ? pop : null;
        }
        if(prev == null) return buff.toString();
        return null;
    }
    public static void main(String[] args) {
        System.out.println(reArrange("aaaabbbbbbbc"));
    }
}

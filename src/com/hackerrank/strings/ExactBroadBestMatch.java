package com.hackerrank.strings;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Given a string S and P, find the start and end index of a smallest string of S that contains all the words in P.
 *
 * S = "aaa bbb aa matchx aaaa jjakjakjk matchy addd asdssd adasda matchx aa matchy asasas"
 * P = "matchx matchy"
 * Smallest String in S is "matchx aa matchy"
 */
public class ExactBroadBestMatch {

    public static void main(String[] args) {
        String S = new String("aaa bbb aa matchx aaaa jjakjakjk matchy addd asdssd adasda matchx aa matchy asasas");
        String T = new String("matchx matchy");
        int[] locs = find(S, T);
        for(int i=locs[0]; i<=locs[1]; ++i){
            System.out.print(S.charAt(i));
        }
        System.out.println();
    }

    private static int [] find(String S, String P){
        Map<String, Queue<Integer>> invertedIdx = new HashMap<>();
        StringBuilder word = new StringBuilder();
        int startPoint = -1;
        for(int k=0; k<S.length(); ++k){
            char c = S.charAt(k);
            if(c>='a' && c<='z'){
                if(word.length()==0) startPoint = k;
                word.append(c);
            }else{
                if(word.length()>0){
                    if(!invertedIdx.containsKey(word.toString())) invertedIdx.put(word.toString(), new LinkedList<>());
                    invertedIdx.get(word.toString()).add(startPoint);
                }
                word.setLength(0);
            }
        }
        //last word
        if(word.length()>0){
            if(!invertedIdx.containsKey(word.toString())) invertedIdx.put(word.toString(), new LinkedList<>());
            invertedIdx.get(word.toString()).add(startPoint);
        }

        String [] toks = P.split("\\s+");
        boolean done = false;
        int minDist = Integer.MAX_VALUE;
        int start=-1, end=-1;
        int prevPos = -1;
        while(true){
            for(int i=0; i<toks.length; ++i){
                Queue<Integer> locations = invertedIdx.get(toks[i]);
                while(!locations.isEmpty() && locations.peek()<=prevPos) locations.remove();
                if(locations.isEmpty()) {
                    done = true;
                    break;
                }
                prevPos = locations.peek();
            }
            if(!done){
                int startIdx = invertedIdx.get(toks[0]).peek();
                int endIdx = invertedIdx.get(toks[toks.length-1]).peek();
                if(endIdx-startIdx<minDist){
                    minDist = endIdx-startIdx;
                    start = startIdx;
                    end = endIdx;
                }
            }else{
                break;
            }
        }
        return new int[] {start, end+toks[toks.length-1].length()};
    }
}

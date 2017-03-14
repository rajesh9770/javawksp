package com.hackerrank;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * ====================
 * 7 3 4 ccbcccb Ans: 16
 * c        3
 * cc       3
 * ccb      3
 * ccbc     3
 * ccbcccb  4
 * ===============
 * 
 *
 */
public class Solution {

    public int copyCost;
    public int addCost;
    public String buildStr;
    public Map<String, Long> costs;
    public Map<Character, Set<Integer>> location;
    
    public Solution(int copyC, int addC, String str) {
        this.addCost = addC;
        this.copyCost = copyC;
        this.buildStr = str;
        this.costs = new HashMap<String, Long>();
    }

    public Map<Character, Set<Integer>> locate(String str, int len){
        Map <Character, Set<Integer>> ret = new HashMap<Character, Set<Integer>>();
        for(int i=0; i<len; ++i){
            char c = str.charAt(i);
            Set<Integer> set = ret.get(c);
            if(set == null){
                set = new TreeSet<Integer>();
                ret.put(c, set);
            }
            set.add(i);
        }
        location = ret;
        return ret;
    }


    public ArrayList<Integer> contains(String str, int pStart, int pEnd, int sStart, int sEnd){
        int lenSuffix = sEnd -sStart +1;
        ArrayList<Integer> matchedIdxes = new ArrayList<Integer>();

        Set<Integer> firstCharLocations = location.get(str.charAt(sStart));
        for(Integer i: firstCharLocations){
            if(i+lenSuffix-1<=pEnd){
                if(isValidMatch(str, i, sStart, sEnd))
                    matchedIdxes.add(i);
            }else break;
        }
        return matchedIdxes;
    }
    
    private boolean isValidMatch(String str, int matchedFirst, int sStart, int sEnd) {
        for(int i=1; i<=sEnd-sStart; ++i){
            if(str.charAt(matchedFirst+i) != str.charAt(sStart+i)) return false;
        }
        return true;
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int testcases = in.nextInt();
        in.nextLine();
        while(testcases-->0){
            int len = in.nextInt();
            int addCost = in.nextInt();
            int copyCost = in.nextInt();
            in.nextLine();
            String str = in.nextLine();
            Solution driver = new Solution(copyCost, addCost, str);
            Map<Character, Set<Integer>> locate = driver.locate(str, str.length());
            driver.findCosts(str);
        }

    }

    public void findCosts(String str) {
        long costs [] = new long[str.length()];
        costs[0] = addCost;
        costs[1] = costs[0] + (str.charAt(0) != str.charAt(1) ? addCost : Math.min(addCost, copyCost));
        State state = new State();
        if(str.charAt(0) == str.charAt(1)){
            state.matched.add(0);
            state.suffixStart = 1;
            state.suffixEnd = 1;
        }else{
            state.suffixStart = 1;
            state.suffixEnd = 1;
        }
        for(int i=2; i<costs.length; ++i){
            costs[i] = costs[i-1] + addCost;
            int len = i+1; //len of str 0 .... i
            ArrayList<Integer> matched = process(state, str, i);
            if(!matched.isEmpty()){
                    if(isCopyCheaper(state.suffixEnd - state.suffixStart +1)){
                        costs[i] = Math.min(costs[i], costs[state.suffixStart-1] + copyCost);
                    }
             }
        }
//        for(int i=0; i<str.length(); ++i)
//            System.out.print(costs[i] + " ");
        System.out.print(costs[str.length()-1] + " ");
        System.out.print("\n");

    }

    private ArrayList<Integer> process(State state, String str, int i) {
        //System.out.println(str.substring(0, i+1) + " " + state.suffixStart + " " + state.suffixEnd);
        ArrayList<Integer> matchedIdxes = new ArrayList<Integer>();
        char newChar = str.charAt(i);
        Integer stepped = null;
        if(!state.matched.isEmpty()){
            int lenSuffix = state.suffixEnd - state.suffixStart + 2;
            for(Integer matchedIdx : state.matched){
                if(str.charAt(matchedIdx+lenSuffix-1) == newChar){
                    if(matchedIdx+lenSuffix-1<state.suffixStart){
                        matchedIdxes.add(matchedIdx);
                    }else if(matchedIdx+lenSuffix-1==state.suffixStart){
                        stepped = matchedIdx+1;
                    }
                }
            }
        }
        if(!matchedIdxes.isEmpty()){
            state.matched = matchedIdxes;
            state.suffixEnd = i;
            return matchedIdxes;
        }

        for(int k=1; k<=i-state.suffixStart; ++k){
            ArrayList<Integer> contains = contains(str, 0, state.suffixStart+k-1, state.suffixStart+k, i);
            if(!contains.isEmpty()){
                state.suffixStart = state.suffixStart+k;
                state.suffixEnd = i;
                state.matched = contains;
                return contains;
            }
        }

        state.suffixStart = i;
        state.suffixEnd = i;
        state.matched = matchedIdxes;
        return matchedIdxes;

    }

    public static class State {
        public ArrayList<Integer> matched;
        int suffixStart;
        int suffixEnd;
        public State() {
            matched = new ArrayList<Integer>();
        }
    }

    private boolean isCopyCheaper(int lenOfCopyStr) {
        return lenOfCopyStr*addCost > copyCost;
    }
}

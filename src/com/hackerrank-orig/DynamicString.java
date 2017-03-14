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
public class DynamicString {

    public int copyCost;
    public int addCost;
    public String buildStr;
    public Map<String, Long> costs;
    public Map<Character, Set<Integer>> location;
    
    public DynamicString(int copyC, int addC, String str) {
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

    public ArrayList<Integer> contains2(String str, int pStart, int pEnd, int sStart, int sEnd, State state){
        int lenSuffix = sEnd -sStart +1;
        ArrayList<Integer> matchedIdxes = new ArrayList<Integer>();
        if(state.matched != null){
            char newChar = str.charAt(sEnd);
            for(Integer matchedIdx : state.matched){
                if(matchedIdx+lenSuffix-1<=pEnd && str.charAt(matchedIdx+lenSuffix-1) == newChar){
                    matchedIdxes.add(matchedIdx);
                }
            }
            if(!matchedIdxes.isEmpty()) return matchedIdxes;
        }

        Set<Integer> firstCharLocations = location.get(str.charAt(sStart));
        for(Integer i: firstCharLocations){
            if(i+lenSuffix-1<=pEnd){
                if(isValidMatch(str, i, sStart, sEnd))
                    matchedIdxes.add(i.intValue());
            }else break;
        }
        return matchedIdxes;
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

    public long getCost(String str, int indent){
        int len = str.length();
        //printNice(indent, "======" + str);
        if(costs.containsKey(str)) {
            //printNice(indent, " cache-cost:" + costs.get(str));
            return costs.get(str);
        }
        if(len == 0) {
            //printNice(indent, " len-0: 0");
            return 0;
        }
        if(len == 1){
            //printNice(indent, " len-1: " + addCost);
            return addCost;
        }

        long min = Long.MAX_VALUE;
        long costOfAddingLastChar = addCost + getCost(str.substring(0, len-1), indent+1);
        min = Math.min(min, costOfAddingLastChar);
//        for(int i=0; i<len/2; ++i){
//            String suffix = str.substring(len-1-i);
//            String prefix = str.substring(0, len-1-i);
//            //printNice(indent, "S: " + suffix + " P: " + prefix);
//            if(prefix.contains(suffix)){
//                if(isCopyCheaper(i+1)){
//                    long prvCost = min;
//                    min = Math.min(min, getCost(prefix, indent+1) + copyCost);
//                    if(prvCost != costOfAddingLastChar && prvCost < (getCost(prefix, indent+1) + copyCost)){
//                        printNice(indent, "()()()S: " + suffix + " P: " + prefix + " " + prvCost + " " + (getCost(prefix, indent+1) + copyCost) + " " + costOfAddingLastChar );
//                    }
//                }
//            }else{
//                break;
//            }
//        }
        for(int i=len/2-1; i>=0; --i){
            String suffix = str.substring(len-1-i);
            String prefix = str.substring(0, len-1-i);
            //printNice(indent, "S: " + suffix + " P: " + prefix);
            if(prefix.contains(suffix)){
                if(isCopyCheaper(i+1)){
                    long prvCost = min;
                    min = Math.min(min, getCost(prefix, indent+1) + copyCost);
                    if(prvCost != costOfAddingLastChar && prvCost < (getCost(prefix, indent+1) + copyCost)){
                        printNice(indent, "()()()S: " + suffix + " P: " + prefix + " " + prvCost + " " + (getCost(prefix, indent+1) + copyCost) + " " + costOfAddingLastChar );
                    }
                }
                break;
            }
        }
        costs.put(str, min);
        //printNice(indent, "calc cost:" + str + " " + min);
        return min;
    }

    private void printNice(int indent, String string) {
        for(int i =0; i< indent; ++i)
            System.out.print(" ");
        System.out.println("**" + string);
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
            DynamicString driver = new DynamicString(copyCost, addCost, str);
            Map<Character, Set<Integer>> locate = driver.locate(str, str.length());
            //driver.printLocate(locate);
            driver.findCosts(str);
        }

    }

    public void printLocate(Map<Character, Set<Integer>> map){
        Map<Character, Set<Integer>> map2 = map;
        for(Map.Entry<Character, Set<Integer>> tt: map2.entrySet()){
            System.out.print(tt.getKey() + " ");
            for(Integer i : tt.getValue()){
                System.out.print(i + " ");
            }
            System.out.print("\n");
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
            //if(state.matched != null){
                ArrayList<Integer> matched = process(state, str, i);
//                System.out.print(str.charAt(i) + " ");
//                for(Integer kk: matched){
//                  System.out.print(kk + " ");
//                }
//                System.out.println("");
                if(!matched.isEmpty()){
                    if(isCopyCheaper(state.suffixEnd - state.suffixStart +1)){
                        costs[i] = Math.min(costs[i], costs[state.suffixStart-1] + copyCost);
                    }
                    //state.matched = matched;
                        ////state.suffixStart = len-1-k;
                    //state.suffixEnd = i;
                }
                
//                else{
//                    state.matched = null;
//                }
            //}
//            for(int k=len/2-1; k>=0; --k){
//                String suffix = str.substring(len-1-k, i+1);  //i-len+k+2
//                String prefix = str.substring(0, len-1-k); //len-1-k
//                System.out.println("P:" + prefix + " S:"+ suffix);
//                
//                ArrayList<Integer> matched = contains(str, 0, len-2-k, len-1-k, i, state);
//                
//                for(Integer kk: matched){
//                    System.out.print(kk + " ");
//                }
//                System.out.println("\n===========");
//                
//                if(!matched.isEmpty()){
//                    if(isCopyCheaper(i-len+k+2)){
//                        costs[i] = Math.min(costs[i], costs[len-2-k] + copyCost);
//                    }
//                    if(len%2 == 0 && k== (len/2 -1)){  //abab  -> ababa
//                        Integer remove = matched.remove(0);
//                        matched.clear();
//                        matched.add(remove + 1);
//                    }
//                    state.matched = matched;
//                    state.suffixStart = len-1-k;
//                    state.suffixEnd = i;
//                    break;
//                }else{
//                    state.matched = null;
//                }
//            }
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
//        if(stepped != null){
//            matchedIdxes.add(stepped);
//            state.matched = matchedIdxes;
//            state.suffixEnd = i;
//            state.suffixStart++;
//            return matchedIdxes;
//        }
        
        for(int k=1; k<=i-state.suffixStart; ++k){
            ArrayList<Integer> contains = contains(str, 0, state.suffixStart+k-1, state.suffixStart+k, i);
            if(!contains.isEmpty()){
                state.suffixStart = state.suffixStart+k;
                state.suffixEnd = i;
                state.matched = contains;
                return contains;
            }
        }
//        Set<Integer> newCharLocations = location.get(newChar);
//        for(Integer newCharIdx: newCharLocations){
//            if(newCharIdx < i){
//                matchedIdxes.add(newCharIdx);
//                state.suffixStart = i;
//            }
//        }
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

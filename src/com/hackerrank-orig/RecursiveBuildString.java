package com.hackerrank;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
public class RecursiveBuildString {

    public int copyCost;
    public int addCost;
    public String buildStr;
    public Map<String, Long> costs;
    
    public RecursiveBuildString(int copyC, int addC, String str) {
        this.addCost = addC;
        this.copyCost = copyC;
        this.buildStr = str;
        this.costs = new HashMap<String, Long>();
    }

    public long getCost(String str){
        int len = str.length();
        if(costs.containsKey(str)) return costs.get(str);
        if(len == 0) return 0;
        if(len == 1) return addCost;

        
        long min = Long.MAX_VALUE;
        for(int i=len/2-1; i>=0; --i){
            String suffix = str.substring(len-1-i);
            String prefix = str.substring(0, len-1-i);

            if(prefix.contains(suffix)){
                if(isCopyCheaper(i+1)){
                    min = Math.min(min, getCost(prefix) + copyCost);
                }
                break;
            }
        }
        long costOfAddingLastChar = addCost + getCost(str.substring(0, len-1));
        min = Math.min(min, costOfAddingLastChar);
        System.out.println(str + " " + min);
        costs.put(str, min);
        return min;
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
            RecursiveBuildString driver = new RecursiveBuildString(copyCost, addCost, str);
            System.out.println(driver.getCost(str));
        }

    }

    private boolean isCopyCheaper(int lenOfCopyStr) {
        return lenOfCopyStr*addCost > copyCost;
    }
}

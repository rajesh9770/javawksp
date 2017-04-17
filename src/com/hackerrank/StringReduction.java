package com.hackerrank;

import java.util.Scanner;

public class StringReduction {
    static boolean canA[][], visitA[][] ;
    static boolean canB[][], visitB[][] ;
    static boolean canC[][], visitC[][] ;
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int res;

        int _t_cases = Integer.parseInt(in.nextLine());
        for (int _t_i = 0; _t_i<_t_cases; _t_i++) {
            String _a = in.nextLine();
            res = stringReduction(_a);
            canA = new boolean[_a.length()][_a.length()];
            canB = new boolean[_a.length()][_a.length()];
            canC = new boolean[_a.length()][_a.length()];
            visitA = new boolean[_a.length()][_a.length()];
            visitB = new boolean[_a.length()][_a.length()];
            visitC = new boolean[_a.length()][_a.length()];
            fillArray(canA, false);
            fillArray(canB, false);
            fillArray(canC, false);
            fillArray(visitA, false);
            fillArray(visitB, false);
            fillArray(visitC, false);
            System.out.println(res);
        }
        in.close();
    }


    public static void fillArray(boolean[][] arr, boolean val) {
        for(int i=0; i<arr.length; ++i)
            for(int j=0; j<arr[0].length; ++j)
                arr[i][j] = val;
    }
    

    //Can we reduce the substring [st, ed] to a single character ch?
    public static boolean can(String a, int st, int ed, char ch, boolean[][] results, boolean[][] visited) {
        if(visited[st][ed]) return results[st][ed];
        visited[st][ed] = true;
        if(st==ed){
            if(a.charAt(st)==ch){
                results[st][st] = true;
                return results[st][ed];
            }else{
                results[st][st] = false;
                return results[st][ed];
            }
        }
        char otherch1, otherch2;
        if(ch=='a'){
            otherch1 = 'b';
            otherch2 = 'c';
        }else if(ch=='b'){
            otherch1 = 'a';
            otherch2 = 'c';
        }else{
            otherch1 = 'b';
            otherch2 = 'a';
        }
        for(int i=st; i<ed; ++i){
            if( (can(a, st, i, otherch1, results, visited) && can(a, i+1, ed, otherch2, results, visited))  ||
                    (can(a, st, i, otherch2, results, visited) && can(a, i+1, ed, otherch1, results, visited))    ){
                results[st][ed] = true;
                return results[st][ed];
            }
        }
        results[st][ed] = false;
        return results[st][ed];
    }
    
    private static int DP(String a) {
        int ret = Integer.MAX_VALUE;
        char ch = 'a';
        int [] dp = new int [a.length()+1];
        for(int i=0; i<dp.length+1; ++i) dp[i] = 0;

        for(int i=0; i<a.length(); ++i){
            int min = Integer.MAX_VALUE;
            for(int j=0; j<=i; ++j){
                if(can(a, j, i, ch, canA, visitA)) min = Math.min(min, dp[j]+1);
            }
            dp[i+1] = min;
        }
        ret = Math.min(ret, dp[a.length()]);
        
        ch = 'b';
        for(int i=0; i<dp.length+1; ++i) dp[i] = 0;

        for(int i=0; i<a.length(); ++i){
            int min = Integer.MAX_VALUE;
            for(int j=0; j<=i; ++j){
                if(can(a, j, i, ch, canA, visitA)) min = Math.min(min, dp[j]+1);
            }
            dp[i+1] = min;
        }
        ret = Math.min(ret, dp[a.length()]);
        
        ch = 'c';
        for(int i=0; i<dp.length+1; ++i) dp[i] = 0;

        for(int i=0; i<a.length(); ++i){
            int min = Integer.MAX_VALUE;
            for(int j=0; j<=i; ++j){
                if(can(a, j, i, ch, canA, visitA)) min = Math.min(min, dp[j]+1);
            }
            dp[i+1] = min;
        }
        ret = Math.min(ret, dp[a.length()]);
        return ret;
    }
    
    
    
    static int stringReduction(String str) {

        int [] count = new int [3]; count[0] = count[1] = count[2] = 0; 

        for(int i=0; i< str.length(); ++i){
            count[str.charAt(i) - 'a']++;
        }
        if( (count[0] == 0 && count[1] == 0) ||
            (count[0] == 0 && count[2] == 0) ||
            (count[1] == 0 && count[2] == 0) ) return str.length();
        if(count[0]%2 == count[1]%2  && count[1] %2 == count[2] %2 ) return 2;
        return 1;

    }
}

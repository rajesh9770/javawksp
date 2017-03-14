package com.hackerrank;


import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class OnesAndTwos {

    private static long mod = 1000000007l;
    private static HashMap<Integer, Long> powersOfTwo = new HashMap<>();
    
    public static long modpow(long base_value, long exponent, long modulus){
        base_value = base_value % modulus;
        long result = 1;
        while(exponent > 0){
                if (exponent % 2 == 1) result = (result * base_value) % modulus;
                exponent = exponent / 2;
                if(exponent >0 )
                    base_value = (base_value * base_value) % modulus;
        }
        return result;
    }
    
    static {
        
    }
    /**
     * 2^n   remaining bits 1 2 3 ... n-1  (0th bit excluded since only twos are considered)
     * Fill these n-1 bits with twos. i-th bit requires i twos.
     * @param n
     * @param left  (left after using n twos)
     * @return
     */
    public static Long findOnlyTwos(int n, int left) {
         long total = (n-1)*(n-2)/2;
         if(left >= total){// we have enough to cover all n-1 bits
             return powersOfTwo.get(n-1); // all even from 2^n to 2^(n+1) -1
         }
         
         return null;

    }

    /**
     * columns-> #of twos
     * rows -> level
     * 
     * @param n
     * @return
     */
    public static int[][] countPartitions(int n) {
        int [][]dp = new int [n+1][n+1];
        for(int i=1; i<=n; ++i){
            for(int j=i;j<=n; ++j){
                if(i>j) dp[i][j] = 0;
                else if(i==j) dp[i][j] = 1;
                else{
                    int remaining = j-i;
                    dp[i][j] = 1;
                    for(int k=1; k<i; ++k) {
                        dp[i][j] += dp[k][remaining];
                        dp[i][j] %= mod;
                    }
                }
                //System.out.print(dp[i][j] + " ");
            }
            //System.out.println("");
        }
        return dp;
    }

    public static int getCount(int [][] cts, int n, int i, int j) {
        if(j<i) return 0;
        int maxJ= Math.min(i*(i+1)/2, n);
        if(j>maxJ) return cts[i][maxJ];
        return cts[i][j];
    }

    public static int[][] countPartitions2(int n) {
        int [][]dp = new int [n+1][n+1];
        for(int i=1; i<=n; ++i){
            int maxJ= Math.min(i*(i+1)/2, n);
            dp[i][i] = 1;
            //System.out.print(dp[i][i] + " ");
            for(int j=i+1;j<=maxJ; ++j){
                    int remaining = j-i;
                    dp[i][j] = 1;
                    for(int k=1; k<i; ++k) {
                        dp[i][j] += getCount(dp, n, k, remaining);
                        dp[i][j] %= mod;
                        if(k==remaining) break;
                    }
                //System.out.print(dp[i][j] + " ");
            }
            //System.out.println("");
        }
        return dp;
    }

    public OnesAndTwos() {
        // TODO Auto-generated constructor stub
    }

    public static long eval(char [] expr, int len) {
        Stack<Integer> operand = new Stack<Integer>();
        
        for(int i=0; i<len; ++i){
            switch (expr[i]) {
            case '1':
            case '2':
                operand.push(expr[i] - '1' +1);
                break;
            case '*':
                operand.push(operand.pop() * (expr[++i] - '1' +1));
                break;
            default:
                break;
            }
        }
        long ret = 0;
        while(!operand.isEmpty()) ret += operand.pop();
        return ret;
    }

   public static void fill(char [] expr, int id, int ones, int twos, Set<Long> set, long lasteval) {
       if(ones==0 && twos ==0) return;
       
       if(id == 0 || expr[id-1] == '+' || expr[id-1] == '*'){
           if(ones > 0 && (id == 0 || expr[id-1] == '+')){
               expr[id] = '1';
               long eval = eval(expr, id+1);
               set.add(eval);
               fill(expr, id+1, ones-1, twos, set, eval);
           }
           if(twos > 0){
               expr[id] = '2';
               long eval = eval(expr, id+1);
               set.add(eval);
               fill(expr, id+1, ones, twos-1, set, eval);
           }
       }else{
           if(expr[id-1] != '1'){
               expr[id] = '*';
               fill(expr, id+1, ones, twos-1, set, lasteval);
           }
           expr[id] = '+';
           fill(expr, id+1, ones, twos, set, lasteval);
       }
   }
   
   public static void fill(int [] expr, int id, int ones, int twos, Set<Long> set, long lasteval) {
       if(ones >0){
           expr[id] = 1;
           set.add(lasteval +1);
           fill(expr, id+1, ones-1, twos, set,lasteval +1);
       }
       if(twos >0){
           expr[id] = 2;
           set.add(lasteval +2);
           fill(expr, id+1, ones, twos-1, set,lasteval +2);
       }
       
       if(expr[id-1] != 1 && twos >0) {
           long tmp = expr[id-1];
           expr[id-1] *=2 ;
           set.add(lasteval +tmp);
           fill(expr, id, ones, twos-1, set,lasteval +tmp);
       }
       
   }
   
   public static void main(String[] args) {
       Scanner in = new Scanner(System.in);
       int testcases = in.nextInt();
       while(testcases-->0){
           int ones = in.nextInt();
           int twos = in.nextInt();
           int[][] countTwoPartitions = countPartitions2(twos);
           int totalct = 0;
           for(int i=1; i<=twos; ++i){
               //totalct += countTwoPartitions[i][twos];
               totalct += getCount(countTwoPartitions, twos, i, twos);
               totalct %= mod;
           }
           System.out.println(totalct);

       }
   }
   
    public static void mainFill(String[] args) {
        Scanner in = new Scanner(System.in);
        int testcases = in.nextInt();
        while(testcases-->0){
            int ones = in.nextInt();
            int twos = in.nextInt();
            int [] buff = new int[2*(ones+twos)];
            Set<Long> ct = new HashSet<Long>();
            if(ones>0){
                buff[0] = 1;
                ct.add(1l);
                fill(buff, 1, ones-1, twos, ct, 1);
            }
            if(twos>0){
                buff = new int[2*(ones+twos)];
                buff[0] = 2;
                ct.add(2l);
                fill(buff, 1, ones, twos-1, ct, 2);
            }
            System.out.println(ct.size());

        }
    }
}
/***
 * http://math.stackexchange.com/questions/712411/count-expressions-with-1s-and-2s9810778
 * 
 * 
 * #coding=utf8
import sys
sys.setrecursionlimit(10000) # 10000 is an example, try with different values
from collections import Counter
from itertools import cycle, product as prod, permutations as perm, combinations as comb, combinations_with_replacement as combr
from sys import stdin, stdout
read_ints = lambda: map(int, raw_input().split())
read_floats = lambda: map(float, raw_input().split())

MOD = 10 ** 9 + 7

def get_max(level, left):
    ret = 0
    while left and level:
        ret += 2 ** level
        left -= level
        level = min(level - 1, left)
    return ret

memo = {}

def rec(level, left, threshold):
    global memo
    #assume left >= level
    level = min(level, left)
    if level <= 0:
        return 0
    if (level, left) in memo:
        return memo[(level, left)]
    #print level, left, threshold
    ret = 0
    diff = 2 ** level - get_max(level - 1, left) - 1
    if diff > threshold:
        ret += (diff - threshold) % MOD
    ret += rec(level - 1, left - level, threshold) + rec(level - 1, left, threshold)
    memo[(level, left)] = ret
    return ret


def main():
    global memo
    t = read_ints()[0]
    for cas in xrange(t):
        memo = {}
        a, b = read_ints()
        ret = get_max(b, b) + a - rec(b, b, a)
        ret = (ret % MOD + MOD) % MOD
        print ret

if __name__ == '__main__':
    main()
 * 
 */


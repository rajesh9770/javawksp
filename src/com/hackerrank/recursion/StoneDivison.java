package com.hackerrank.recursion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rajesh on 2/25/2018.
 * http://gautamdp.blogspot.com/2016/12/taras-beautiful-permutationsnumber-of.html
 *  Ans_X is denoting maximum number of moves for single pile with height X
 In case no divisors of number  X from set S , Ans_X =0, otherwise  Ans_X = max( X/Y * Ans_Y)
 for all Y such that X mod Y = 0  and Y !=X .
 For doing this recursion you only need to notice that in case when we get  X/Y smaller piles each of size Y,
 we will perform the same set of moves separately on each of them, so it is not important to call recursion for each of that piles, it is enough to calculate answer only for one and multiply by X/Y.
 */
public class StoneDivison {

    static Map<Long, Long> cache = new HashMap<>();

    static long stoneDivision(long n, long[] s) {
        if(cache.containsKey(n)) return cache.get(n);
        long ret = 0;
        for(long divisor: s){
            if(n%divisor == 0 && n != divisor){
                ret = Math.max(ret, 1+ ((n/divisor) * stoneDivision(divisor, s)));
            }
        }
        cache.put(n, ret);
        return ret;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for(int a0 = 0; a0 < q; a0++){
            long n = in.nextLong();
            int m = in.nextInt();
            long[] s = new long[m];
            for(int s_i = 0; s_i < m; s_i++){
                s[s_i] = in.nextLong();
            }
            Arrays.sort(s);
            cache.clear();
            long result = stoneDivision(n, s);
            System.out.println(result);
        }
        in.close();
    }
}

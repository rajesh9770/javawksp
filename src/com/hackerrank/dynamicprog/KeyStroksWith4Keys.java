package com.hackerrank.dynamicprog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.TreeSet;

/**
 * Keyboard has only 4-keys
 * Key-1: prints A
 * Key-2: selects current buffer
 * Key-2: copies current buffer
 * Key-2: pastes current buffer
 *
 * With N keystrokes what is the maximum number of A's.
 * e.g with N=7, AAA(AAA)(AAA)
 *
 * f(n) = n for n<=6
 * f(n) = max {
 *              2*f(n-3),
 *              3*f(n-4),
 *              4*f(n-5),
 *              ...
 *              (n-2)*f(1)
 *              }
 *
 */
public class KeyStroksWith4Keys {


    private static long maxA(int n, long [] f){
        if(n<=6) {
            return n;
        }
        long max = 0;

        long multiplier = 2;

        for( int i=n-3; i>=1; --i){
            if(f[i] == -1) {
                f[i] = maxA(i, f);
            }
            if(f[i]*multiplier > max) {
                max = f[i] *multiplier;
            }
            multiplier +=1;
        }
        return max;
    }

    public static void main(String[] args) {
//        int n = 7;
//        long [] f = new long[n];
//        for(int i=0; i<n; ++i) f[i] = -1;
//        System.out.println(maxA(7, f));

        ArrayList<Query> queries = new ArrayList<>();
        queries.add(new Query(2,3));
        queries.add(new Query(1,2));
        queries.add(new Query(2,1));
        queries.add(new Query(2,3));
        queries.add(new Query(2,2));
        System.out.println(Arrays.toString(answerQueries(queries, 5)));
    }

    public static class Query{
        int type;
        int idx;

        public Query(int t, int v){
            type=t;
            idx=v;
        }
    }
    public static  int[] answerQueries(ArrayList<Query> queries, int N){

        TreeSet<Integer> trueIdx= new TreeSet<>();
        ArrayList<Integer> ret = new ArrayList<>();

        for(int i=0; i<queries.size(); ++i){
            Query q = queries.get(i);
            if(q.type==1){
                trueIdx.add(q.idx);
            }else{
                Integer ceiling = trueIdx.ceiling(q.idx);
                if(ceiling == null) ret.add(-1);
                else ret.add(ceiling);

            }
        }

        int [] ret2 = new int[ret.size()];
        for(int a=0; a< ret.size(); ++a){
            ret2[a] = ret.get(a);
        }
        return ret2;
    }
}

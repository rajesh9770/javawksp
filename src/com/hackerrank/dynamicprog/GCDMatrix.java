package com.hackerrank.dynamicprog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Rajesh on 1/7/2018.
 */
//https://github.com/2997ms/some-problems/blob/master/Hackerrank/HourRank_17/GCD%20Matrix.cpp
public class GCDMatrix {

//    private static Map<Integer, Integer> gcd;
    private static int n, m;
//
//    private class Pair{
//        int a ,b, hash;
//        public Pair(int a, int b){
//            this.a = a<b? a: b;
//            this.b = a<b? b: a;
//            this.hash = (b*(b+1))/2;
//        }
//
//        //https://math.stackexchange.com/questions/882877/produce-unique-number-given-two-integers
//        @Override
//        public int hashCode() {
//            return hash;
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            Pair p = (Pair)obj;
//            return p.a == this.a && p.b == this.b;
//        }
//    }
//
//    static int [] ret = new int[3];
//    private static void getHash(int a, int b){
//        ret[0] = a<b? a:b;
//        ret[1] = a<b? b:a;
//        ret[2] = (ret[1]*(ret[1]+1))/2  + ret[0];
//    }
//
//    /**
//     * assumes divisor < dividend
//     * @param divisor
//     * @param dividend
//     * @return
//     */
//    private static int gcd(int divisor, int dividend){
//        int hash = (dividend * (dividend+1))/2 + divisor;
//        if(gcd.containsKey(hash)) return gcd.get(hash);
//        int remainder = dividend % divisor;
//        int ret = 1;
//        if(remainder == 0) {
//            ret = divisor;
//        }else{
//            ret = gcd(remainder, divisor);
//        }
//        gcd.put(hash, ret);
//        return ret;
//    }
//
//    private static int [][] createGCDMatrix(int [] a, int [] b){
//        int [][] gcdMatrix = new int [n][m];
//        for(int r=0; r<n; ++r){
//            for(int c=0; c<m; ++c){
//                int divisor = a[r] < b[c] ? a[r] : b[c];
//                int dividend = a[r] < b[c] ? b[c]: a[r];
//                gcdMatrix[r][c] = gcd(divisor, dividend);
//                System.out.print(gcdMatrix[r][c] + " ");
//            }
//            System.out.println();
//        }
//        return gcdMatrix;
//    }
//
//    private static int [][] createDistinctGCDMatrix(int [] a, int [] b){
//        int[][] gcdMatrix = createGCDMatrix(a, b);
//        Set<Integer>[][] gcds = new Set[n][m];
//        gcds[0][0] = new HashSet<Integer>();
//        gcds[0][0].add(gcdMatrix[0][0]);
//        {
//            int r = 0;
//            for (int c = 1; c < m; ++c) {//r==0
//                int gcd = gcdMatrix[r][c];
//                if (gcds[r][c - 1].contains(gcd)) gcds[r][c] = gcds[r][c - 1];
//                else {
//                    gcds[r][c] = new HashSet<>(gcds[r][c - 1]);
//                    gcds[r][c].add(gcd);
//                }
//            }
//        }
//        {
//            int c = 0;
//            for (int r = 1; r < n; ++r) {//c==0
//                int gcd = gcdMatrix[r][c];
//                if (gcds[r-1][c].contains(gcd)) gcds[r][c] = gcds[r-1][c];
//                else {
//                    gcds[r][c] = new HashSet<>(gcds[r-1][c]);
//                    gcds[r][c].add(gcd);
//                }
//            }
//        }
//        for (int r = 1; r < n; ++r) {
//            for (int c = 1; c < n; ++c) {//c==0
//                Set<Integer> prevCol = gcds[r][c - 1];
//                Set<Integer> prevRow = gcds[r-1][c];
//                int newGCD = gcdMatrix [r][c];
//                Set<Integer> min, max, newSet = null;
//
//                if(prevCol.size() < prevRow.size()){
//                    min = prevCol; max = prevRow;
//                }else{
//                    min = prevRow; max = prevCol;
//                }
//
//                if(!prevCol.contains(newGCD) || ! prevRow.contains(newGCD)){
//                    newSet = new HashSet<>(max);
//                    newSet.add(newGCD);
//                }
//                for(Integer i: min){
//                    if(!max.contains(i)){
//                        if(newSet == null) newSet = new HashSet<>(max);
//                        newSet.add(i);
//                    }
//                }
//                if(newSet == null) newSet = max;
//                gcds[r][c] = newSet;
//            }
//        }
//
//        int [][] gcdSizes = new int[n][m];
//        for (int r = 0; r < n; ++r) {
//            for (int c = 0; c < n; ++c) {//c==0
//                gcdSizes[r][c] = gcds[r][c].size();
//                System.out.print(gcdSizes[r][c]);
//            }
//            System.out.println();
//        }
//
//        return gcdSizes;
//    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream(new File("C:\\Users\\manjrr\\workspace\\javawksp\\data\\gcd-matrix")));
        n = in.nextInt();
        m = in.nextInt();
        int q = in.nextInt();

        int[] a = new int[n];
        int max = Integer.MIN_VALUE;
        for (int a_i = 0; a_i < n; a_i++) {
            a[a_i] = in.nextInt();
            max = Math.max(max, a[a_i]);
        }
        int[] b = new int[m];
        for (int b_i = 0; b_i < m; b_i++) {
            b[b_i] = in.nextInt();
            max = Math.max(max, b[b_i]);
        }


        for (int a0 = 0; a0 < q; a0++) {

            int r1 = in.nextInt();
            int c1 = in.nextInt();
            int r2 = in.nextInt();
            int c2 = in.nextInt();

            int []row = new int[max+1]; //row[i] contains the count of elements in array a that are divisible by i
            int []col = new int[max+1]; //col[i] contains the count of elements in array b that are divisible by i

            for(int r = r1; r<=r2; ++r) row[a[r]]++; // first set row[i] = #times i appears in a
            for(int c = c1; c<=c2; ++c) col[b[c]]++; // first set col[i] = #times i appears in b

            for(int i=1; i<=max; ++i){
                for(int j=i+i; j<=max; j+=i){
                    row[i] += row[j];  // increment row[i] by # of times x*i, ... appears in a
                    col[i] += col[j];  // increment col[i] by # of times x*i, ... appears in b
                }
            }

            long [] ans = new long [max+1]; // ans[i] contains count of all pairs (x,y) x from a and y from b whose gcd is i
            // ans[i] = row[i]*col[i] - SUM {ans[j]: j >i and i divides j}
            int count = 0;
            for(int i=max; i>=1; --i){//backward loop
                ans[i] = 1l * row[i] * col[i];
                for(int j=i+i; j<=max; j+=i){
                    ans[i] -= ans[j];
                }
                if(ans[i] != 0) ++count;
            }
            System.out.println(count);
        }
    }
}

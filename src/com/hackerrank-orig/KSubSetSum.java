package com.hackerrank;

import java.math.BigInteger;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;


public class KSubSetSum {

    public static class IntWrapper{
        public IntWrapper(int i) {
            this.count = i;
        }

        public int count;
        @Override
        public String toString() {
            return Integer.toString(count);
        }
    };
    public KSubSetSum() {
        // TODO Auto-generated constructor stub
    }

    public static BigInteger choose(int n, int k) {
        BigInteger r = BigInteger.valueOf(1);
        long d;
        if (k > n) return BigInteger.valueOf(0);
        for (d=1; d <= k; d++) {
            r = r.multiply(BigInteger.valueOf(n));
            n--;
            r= r.divide(BigInteger.valueOf(d));
            //r *= n--;
            //r /= d;
        }
        return r;
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int testcases = in.nextInt();
        while(testcases-->0){
            int n = in.nextInt();
            int k = in.nextInt();
            BigInteger noOfSubsetSums = choose(n+k-1, k);
            TreeMap<Long, IntWrapper> subsetSums = new TreeMap<Long, IntWrapper>(); 
            for(long i=0;i<noOfSubsetSums.longValue(); ++i){
                long subsetSum = in.nextLong();
                IntWrapper subSetSumCount = subsetSums.get(subsetSum);
                if(subSetSumCount == null){
                    subSetSumCount = new IntWrapper(0);
                    subsetSums.put(subsetSum, subSetSumCount);
                }
                subSetSumCount.count++;
            }
            long[] arr = process(subsetSums, n, k);
            for(int l=0; l<arr.length; ++l){
                if(l != 0)
                    System.out.print(" ");
                System.out.print(arr[l]);
            }
            System.out.println("");
        }

    }

    private static long[] process(TreeMap<Long, IntWrapper> subsetSums, int n, int k) {
        long [] output = new long[n];
        for(int i=0; i<n; ++i){
            Entry<Long, IntWrapper> smallestSumEntry = subsetSums.firstEntry();

            if(i==0){
                output[0] = smallestSumEntry.getKey()/k;
                if(--smallestSumEntry.getValue().count ==0){
                    subsetSums.remove(output[0]*k);
                }
            }else{
                //smallest sum is sum of (output[0] + output[0] + ... + output[0] + output[i])
                output[i] = smallestSumEntry.getKey() - (output[0]*(k-1));
                //remove all entries that are contains i-th element and only first i elements
                 removeEntries(subsetSums, output, i, k);
            }
        }
        return output;
    }

    /**
     * removes entries from map that contains subsets containing 0...i elements from output and definitely i-the element
     * @param subsetSums
     * @param output has entries from 0 to i
     * @param i
     */
    private static void removeEntries(TreeMap<Long, IntWrapper> subsetSums, long []output, int i, int k) {
        for(int j=1; j<=k; ++j){
            removeEntries(subsetSums, output, i-1, output[i]*j, k-j);
        }
    }

    /**
     * 
     * @param subsetSums
     * @param output array is from 0 to lastElement set, say J 
     * @param predecessorIdx  choose remainingCount elements from [0,  predecessor] 
     * @param currentSum  sum accumulated to so far by taking only elements from predecessorIdx + 1 .... J.
     * @param remainingCount # of elements to be chosen from [0,  predecessor].
     */
    private static void removeEntries(TreeMap<Long, IntWrapper> subsetSums,
            long[] output, int predecessorIdx, long currentSum, int remainingCount) {
        if(predecessorIdx == 0 ){//no choice but to take the first element remainingCount times
            currentSum = currentSum + output[predecessorIdx] * remainingCount;
            remainingCount = 0;
        }
        if(remainingCount ==0){
            IntWrapper ct = subsetSums.get(currentSum);
            if(--ct.count == 0){
                subsetSums.remove(currentSum);
            }
            return;
        }
        for(int i=0; i<=remainingCount; ++i){
            //take output[predessorIdx] i-times
            removeEntries(subsetSums, output, predecessorIdx-1, currentSum + output[predecessorIdx] *i, remainingCount-i);
        }
    }
}

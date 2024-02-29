package com.hackerrank.dollar;

import java.util.Arrays;

/**
 * In a given integer array A, we must move every element of A to either list B or list C.
 * (B and C initially start empty.)
 *
 * Return true if and only if after such a move, it is possible that the average value of B is equal to the average value of C,
 * and B and C are both non-empty.
 */
public class SplitForEqualAvg {

    /**
     * If the array of size n can be splitted into group A and B with same mean, assuming A is the smaller group, then
     *   totalSum/n = Asum/k = Bsum/(n-k), where k = A.size() and 1 <= k <= n/2;
     *   Asum = totalSum*k/n, which is an integer. So we have totalSum*k%n == 0;
     *
     * @param A
     * @return
     */
    public boolean splitArraySameAverage(int[] A) {
        int totalSum = 0;
        for(int num : A){
            totalSum += num;
        }
        boolean[][] dp = new boolean[totalSum+1][A.length/2 + 1];  //dp[i][j] = true if we can get sum i with j elements
        dp[0][0] = true;
        for(int num : A){
            //take num into sum, so i >= num
            for(int i = totalSum; i >= num; i--){
                for(int j = 1; j <= A.length/2; j++){
                    dp[i][j] = dp[i][j] || dp[i-num][j - 1];
                }
            }
        }

        for (int k = 1; k <= A.length/2; ++k) {
            //valid k are those  totalSum*k%n == 0
            //for those valid k, if we find a set with k elements with sum (totalSum*k/n),
            //then the sum of remaining elements would be totalSum - (totalSum *k/n) = totalSum *(n-k)/n
            //and avg would be totalSum/n
            if (totalSum * k % A.length == 0 && dp[totalSum * k / A.length][k]) return true;
        }
        return false;
    }

    public boolean splitArraySameAverageBruteForce(int[] A) {
        long total = 0;
        for(int a: A) total += a;
        boolean isPossible = false;
        for (int i = 1; i <= A.length/2 && !isPossible; ++i)
            if (total*i%A.length == 0) isPossible = true;
        if (!isPossible) return false;
        return combine(A, 0, 0, A.length, 0, total);
    }


    static boolean combine(int [] in , int startIdx, int currentIdx, int length, long currLeftSum, long totalSum)
    {
        for(int i=startIdx; i<length; ++i)
        {
            //out[currentIdx] = in[i];
            currLeftSum += in[i];
            //Process(out, currentIdx); // out is filled from 0 to currentIdx
            if ( length != currentIdx+1 &&  currLeftSum * (length - currentIdx -1)  == (totalSum - currLeftSum) * (currentIdx + 1) ){
//                int[] ints = Arrays.copyOfRange(out, 0, currentIdx + 1);
//                for (int ii: ints) {
//                    System.out.println(ii);
//                }
                return true;
           }

            if(currentIdx <= length/2 && i+1 < length ) {
                if(combine(in, i + 1, currentIdx + 1, length, currLeftSum, totalSum)) return true;
            }
            currLeftSum -= in[i];
        }
        return false;
    }

    public static void main(String[] args) {
//        System.out.println(new SplitForEqualAvg().splitArraySameAverage(new int[] {1,2,3,4,5,6,7,8}));
//        long l = System.currentTimeMillis();
//        System.out.println(new SplitForEqualAvg().splitArraySameAverage(new int[] {60,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30}));
//        System.out.println(System.currentTimeMillis()-l);
        System.out.println(minimumAverageDifference(new int[] {2,5,3,9,5,3} ));

    }

    /**
     * You are given a 0-indexed integer array nums of length n.
     *
     * The average difference of the index i is the absolute difference between the average of the first i + 1 elements of nums
     * and the average of the last n - i - 1 elements. Both averages should be rounded down to the nearest integer.
     *
     * Return the index with the minimum average difference. If there are multiple such indices, return the smallest one.
     * @param nums
     * @return
     */
    public static int minimumAverageDifference(int[] nums) {
        long total = 0;
        for(int i=0; i<nums.length; ++i){
            total += nums[i];
        }
        long firstSum = 0;
        long seconSum;
        long diff = Long.MAX_VALUE;
        int ans = 0;
        for(int i=0; i<nums.length; ++i){
            firstSum += nums[i];
            seconSum = total - firstSum;
            long abs;
            if(i<nums.length-1) {
                abs = Math.abs(firstSum / ((i + 1)) - (seconSum / (nums.length - (i + 1))));
            }else{
                abs = Math.abs(firstSum / (i + 1));
            }
            if(abs < diff)
            {
                ans = i;
                diff = abs;
            }
        }

        return ans;
    }
}

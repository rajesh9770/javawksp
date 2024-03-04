package com.hackerrank.List;


/**
 * https://leetcode.com/problems/happy-number/
 * A happy number is a number defined by the following process:
 *
 * Starting with any positive integer, replace the number by the sum of the squares of its digits.
 * Repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1.
 * Those numbers for which this process ends in 1 are happy.
 * Return true if n is a happy number, and false if not.
 */
public class HappyNumberFloydCycle {
    public boolean isHappy(int n) {
        long slow = n;
        long fast = sumOfSquares(n);
        fast = sumOfSquares(fast);
        while(slow != fast){
            slow = sumOfSquares(slow);
            fast = sumOfSquares(fast);
            fast = sumOfSquares(fast);
        }
        if (slow == 1) return true;
        else return false;
    }

    public static long sumOfSquares(long n){
        long sum =0;
        while(n!=0){
            int tmp = (int) (n %10);
            sum += (tmp *tmp);
            n = n/10;
        }
        return sum;
    }

}

package com.numbers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * We can rotate digits by 180 degrees to form new digits.
 * When 0, 1, 6, 8, 9 are rotated 180 degrees, they become 0, 1, 9, 8, 6 respectively.
 * When 2, 3, 4, 5, and 7 are rotated 180 degrees, they become invalid.
 * A confusing number is a number that when rotated 180 degrees becomes a different number with each digit valid.
 * (Note that the rotated number can be greater than the original number.)
 * Given a positive integer N, return the number of confusing numbers between 1 and N inclusive.
 * Input: 100
 * Output: 19
 * Explanation:
 * The confusing numbers are [6,9,10,16,18,19,60,61,66,68,80,81,86,89,90,91,98,99,100]
 * 10 -> 01
 * 16 -> 91
 *
 * Input: n = 20 Output: 6
 * Explanation: The confusing numbers are [6,9,10,16,18,19].
 * 6 converts to 9. 9 converts to 6. 10 converts to 01 which is just 1. 16 converts to 91. 18 converts to 81. 19 converts to 61.
 */
public class ConfusingNumber {
    public static Map<Integer, Integer> mapping = new HashMap(){{
       put(0, 0);
       put(1, 1);
       put(6, 9);
       put(8, 8);
       put(9, 6);
    }};

    public static void main(String[] args) {
        //System.out.println(mapping);
        //System.out.println(helper(0, 0, 1, 20));
        System.out.println(helper(0, 0, 1, 100));
    }

    public static int  helper(int n, int rotation, int numOfDigitsInN, int MAX){
        int count = 0;
        if(n != rotation) {
            System.out.println(n  + " " + rotation);
            count++;
        }
        int power = n==0 ? 1 : (int) Math.pow(10, numOfDigitsInN);
        for (Integer nextDigit : mapping.keySet()) {
            int next = n*10 + nextDigit;
            int noOfDigitsOfNext = n ==0 ? numOfDigitsInN : (numOfDigitsInN + 1);
            if(next == 0)
            if(next == 0 && n ==0) continue;
            if(next > MAX) break;
            //180 rotation of a number N(nextDigit) = (mapping(nextDigit) * 10 ^ d) + 180 rotation of N.
            //where d = num of digit of next - 1;

            int next180 = mapping.get(nextDigit) * power  + rotation;
            count = count + helper(next, next180, noOfDigitsOfNext, MAX);//next will have one more digit
        }
        return count;
    }
}

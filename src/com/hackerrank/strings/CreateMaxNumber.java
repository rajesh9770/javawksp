package com.hackerrank.strings;

import javafx.util.Pair;

/**
 * Given two arrays of length m and n with digits 0-9 representing two numbers.
 * Create the maximum number of length k <= m + n from digits of the two.
 * The relative order of the digits from the same array must be preserved. Return an array of the k digits.
 */
public class CreateMaxNumber {

    public static void main(String[] args) {

    }

    /**
     * To create max number of length k from two arrays,
     * you need to create max number of length i from array one and max number of length k-i from array two, then combine them together.
     * After trying all possible i, you will get the max number created from two arrays.
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int[] result = new int[k];
        int n1 = nums1.length;
        int n2 = nums2.length;

        // step 1: find the largest number from each array, and merge into one
        for (int i = Math.max(0, k - n2); i <= Math.min(n1, k); i++) {
            int[] list1 = findMax(nums1, i);
            int[] list2 = findMax(nums2, k - i);

            // then merge into one
            int[] curr = merge(list1, list2);

            if (greater(curr, 0, result, 0)) {
                result = curr;
            }
        }

        return result;
    }


    //Given array of digits, and int k, find the greatest integer of digits k that can be formed from the input digits taken in the same order

    /**
     * Suppose we need to create max number with length 2 from num = [4, 5, 3, 2, 1, 6, 0, 8].
     * The simple way is to use a stack, first we push 4 and have stack [4], then comes 5 > 4,
     * we pop 4 and push 5, stack becomes [5], 3 < 5, we push 3, stack becomes [5, 3].
     * Now we have the required length 2, but we need to keep going through the array in case a larger number comes, 2 < 3,
     * we discard it instead of pushing it because the stack already grows to required size 2. 1 < 3, we discard it. 6 > 3, we pop 3,
     * since 6 > 5 and there are still elements left, we can continue to pop 5 and push 6, the stack becomes [6], since 0 < 6, we push 0, the stack becomes [6, 0],
     * the stack grows to required length again. Since 8 > 0, we pop 0, although 8 > 6, we can't continue to pop 6 since there is only one number, which is 8, left,
     *  if we pop 6 and push 8, we can't get to length 2, so we push 8 directly, the stack becomes [6, 8].
     * @param nums
     * @param k
     * @return
     */
    private int[] findMax(int[] nums, int k) {
        int[] result = new int[k];  // use it as stack od size k

        int n = nums.length;
        int len = 0; //current stack length
        for (int i = 0; i < n; i++) {
            //if # of remaining elements counting current one, (n-1-i+1)=n-i is more or equal than space left on stack after removing current element (k-len-1)
            // and the new element is greater than top of stack, then pop the stack
            while (len > 0 && n - i >= k-len+1 && nums[i] > result[len - 1]) {
                len--;
            }

            if (len < k) {
                result[len] = nums[i]; //push onto stack
                len++;
            }
        }

        return result;
    }

    private int[] merge(int[] list1, int[] list2) {
        int n1 = list1.length;
        int n2 = list2.length;

        int[] result = new int[n1 + n2];

        int i = 0;
        int j = 0;
        int k = 0;

        while (k < n1 + n2) {
            if (greater(list1, i, list2, j)) {
                result[k++] = list1[i++];
            } else {
                result[k++] = list2[j++];
            }
        }

        return result;
    }

    private boolean greater(int[] list1, int pos1, int[] list2, int pos2) {
        int n1 = list1.length;
        int n2 = list2.length;

        while (pos1 < n1 && pos2 < n2 && list1[pos1] == list2[pos2]) {
            pos1++;
            pos2++;
        }

        if (pos2 == n2) {
            return true;
        }

        if (pos1 < n1 && list1[pos1] > list2[pos2]) {
            return true;
        }

        return false;
    }
}

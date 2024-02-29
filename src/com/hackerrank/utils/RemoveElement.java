package com.hackerrank.utils;

public class RemoveElement {

    /**
     * Given an array and a value, how to implement a function to remove all instances of that value in place 
     * and return the new length? The order of elements can be changed. It doesn't matter what you leave beyond the new length.
     * @param a
     * @param b
     * @param length
     * @return
     */
    private static int removeElment(int [] a, int b, int length) {
        printArray(a, length, "orig" + b + " ");
        int right = findReplacement(a, b, length-1);

        int left =0;
        while(right >=0 && left<=right){
            if(a[left] == b)
            {
                a[left] = a[right];
                right = findReplacement(a, b, right-1);
            }
            ++left;
        }
        printArray(a, left, "remv" + b + " ");
        return left;
    }
    private static void printArray(int[]a, int len, String msg) {
        System.out.print(msg + " ");
        for(int i=0; i<len; ++i)
            System.out.print(a[i] + " ");
        System.out.println("");
    }

    /**
     * returns the greatest index smaller than or equal to rightEnd whose value is not b.
     * @param a
     * @param b
     * @param rightEnd
     * @return
     */
    private static int findReplacement(int[] a, int b, int rightEnd) {
        while(rightEnd>=0 && a[rightEnd] == b) --rightEnd;
        return rightEnd;
    }
    
    public RemoveElement() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        int [] a = new int[] { 1, 2,2,2,2,2,2,2,2,2,2};
        RemoveElement.removeElment(a, 2, a.length);
        a = new int[] { 2,2,2,2,2,2,2,2,2,2};
        RemoveElement.removeElment(a, 1, a.length);
        a = new int[] { 2,2,2,2,2,1,1,1,2,2};
        RemoveElement.removeElment(a, 2, a.length);
        a = new int[] { 2,2,2,2,2,1,1,1,2,2};
        RemoveElement.removeElment(a, 1, a.length);
        a = new int[] { 2,2,2,2,6,7,2,1,1,1,5,2,2,4};
        RemoveElement.removeElment(a, 1, a.length);
    }

}

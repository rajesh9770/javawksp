package com.hackerrank.strings;

import java.util.Scanner;

/**
 * Created by Rajesh on 3/17/2018.
 */
public class NextPermutation {

    public static <T> T[] reverse(T[] input, int start, int end){
        for(int i=0; i< (end-start+1)/2; ++i){
            T tmp = input[start + i];
            input[start + i] = input[end-i];
            input[end-i] = tmp;
        }
        return input;
    }


    /**
     *
     *
     *
     *               |
     *               |  |
     *               |  |
     *               |  |   |
     *               |  |   |
     *               |  |   |       |
     *           |   |  |   |       |
     *           |   |  |   |       |   |
     *           |   |  |   |       |   |
     *           i-1 i .......     k   k+1
     *  i-1 = pivot.
     *  find the k on right that is the first element greater than pivot, since we are interested in
     *  getting the next higher permutation.
     *  Swapping k and i-1 does not change ordering between [i, n-1]. It still remains decreasing from left to right.
     * @param curr
     * @param <T>
     * @return
     */
    public static <T extends Comparable<? super T>> T[] nextPermutation(T[] curr){
        if(curr == null || curr.length == 0)  return null;

        int i=curr.length -1;
        while(i>0 && curr[i-1].compareTo(curr[i]) >=0) --i;
        if(i==0) return null;
        T pivot = curr[i-1];
        for(int k=curr.length-1; k>=i; --k){
            if(curr[k].compareTo(pivot)>0){
                curr[i-1] = curr[k];
                curr[k] = pivot;
                //reverse
                reverse(curr, i, curr.length -1);
                break;
            }
        }
        return curr;
    }


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int i = scan.nextInt();
        while(i-->0){
            String input = scan.next();
            Character [] inputArray = new Character[input.length()];
            for(int j=0; j<input.length(); ++j)
                inputArray[j] = input.charAt(j);
            Character[] nextPermutation = nextPermutation(inputArray);
            if(nextPermutation !=null) {
                StringBuilder output = new StringBuilder();
                for(int j=0; j<nextPermutation.length; ++j)
                    output.append(nextPermutation[j]);
                System.out.println(output);
            }else System.out.println("no answer");

        }
    }
}

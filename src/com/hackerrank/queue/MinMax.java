package com.hackerrank.queue;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Rajesh on 11/20/2017.
 */
public class MinMax {

    public static class MaxQueue<T extends Comparable> extends LinkedList<T> {

        public boolean add(T e){

            while(!isEmpty() && peekLast().compareTo(e) <0){
                removeLast();
            }
            return super.add(e);
        }

        public boolean remove(T e){
            return super.remove(e);
        }
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int q = in.nextInt();
        int [] a = new int [n];

        for(int i=0; i<n; ++i){
            a[i] = in.nextInt();
        }
        for(int i=0; i<q; ++i){
            int d = in.nextInt();
            MaxQueue<Integer> maxQueue = new MaxQueue<>();
            int j=0;
            for(j=0; j<d; ++j){
                maxQueue.add(a[j]);
            }
            int min = maxQueue.peek();
            for(; j<n; ++j){
                maxQueue.add(a[j]);
                //need casting to Object; otherwise it calls remove(int index)
                maxQueue.remove((Object)(a[j-d]));
                min = Math.min(min, maxQueue.peek());
            }
            System.out.println(min);
        }
    }
}

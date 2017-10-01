package com.hackerrank.utils;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by Rajesh on 9/30/2017.
 */
public class MinHeap {

    private PriorityQueue minHeap;


    public static void main(String[] args) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(10, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });

        minHeap.add(10);
        minHeap.add(8);
        minHeap.add(20);

        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());
    }
}

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
            //this is a default comparator
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

        PriorityQueue<Integer> defaultHeap = new PriorityQueue<>();

        defaultHeap.add(10);
        defaultHeap.add(8);
        defaultHeap.add(20);

        System.out.println(defaultHeap.poll());
        System.out.println(defaultHeap.poll());
        System.out.println(defaultHeap.poll());

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new Comparator<Integer>() {
            //this needs to be specified
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        });

        maxHeap.add(10);
        maxHeap.add(8);
        maxHeap.add(20);

        System.out.println("   ");
        System.out.println(maxHeap.poll());
        System.out.println(maxHeap.poll());
        System.out.println(maxHeap.poll());
    }
}

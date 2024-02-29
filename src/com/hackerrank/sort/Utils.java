package com.hackerrank.sort;

/**
 * Created by Rajesh on 1/3/2018.
 */
public class Utils {

    /**
     * returns an index such that all elements to the left of index are <= arr[index]
     *
     * @param array
     * @param begin
     * @param end
     * @param pivotIdx
     * @param <E>
     * @return
     */
    public static <E extends Comparable<? super E>> int partition(E[] array, int begin, int end, int pivotIdx) {

        E pivot = array[pivotIdx];
        swap(array, pivotIdx, end);//move pivot at end
        pivotIdx = begin;
        for (int i = begin; i < end; ++i) {
            if (array[i].compareTo(pivot) <= 0) {
                if (pivotIdx != i) swap(array, pivotIdx, i);
                ++pivotIdx;
            }
        }
        swap(array, pivotIdx, end);
        return (pivotIdx);
    }

    public static <E> void swap(E[] arr, int a, int b) {
        E temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static <E extends Comparable<? super E>> void qsort(E[] array, int begin, int end) {
        if (end > begin) {
            int index = partition(array, begin, end, begin);
            qsort(array, begin, index - 1);
            qsort(array, index + 1, end);
        }
    }
}

package com.hackerrank.search;

public class SortedRotatedArray {


    public static int search(int[] a, int target, int left, int right) {
        if(right<left) return -1;
        int mid = (right+left)/2;
        if(a[mid] == target) return mid;

        if(a[left] < a[mid]) { //left is sorted
            if( target >=a[left] && target<a[mid]){
                return search(a, target, left, mid-1);
            }else{
                return search(a, target, mid+1, right);
            }
        }else if( a[mid] < a[right]) { //right is sorted
            if( target <=a[right] && target>a[mid]){
                return search(a, target, mid+1, right);
            }else{
                return search(a, target, left, mid-1);
            }
        }else if(a[left] == a[mid]) {
            if(a[mid] != a[right]){//all elements in left side are equal
                return search(a, target, mid+1, right);
            }else{ //either right or left are all equal
                int ret = search(a, target, left, mid-1);
                if(ret ==-1) ret = search(a, target, mid+1, right);
                return ret;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] a = {4, 5, 6, 7, 0, 1, 2};
        System.out.println(search(a, 0, 0, a.length-1) == 4 ) ;
        System.out.println(search(a, 3, 0, a.length-1) == -1 ) ;
    }

    /**
     * A left rotation operation on an array shifts each of the array's elements  unit to the left.
     * For example, if 2 left rotations are performed on array [1 2 3 4 5], then the array would become [3 4 5 1 2] .
     *
     * @param a
     * @param n
     * @param k
     * @return
     */
    public static int[] arrayLeftRotation(int[] a, int n, int k) {
        reverse(a, 0, k-1);
        reverse(a, k, n-1);
        reverse(a, 0, n-1); // lastly rotate entire array
        return a;
    }

    public static void reverse(int [] a, int start, int end){
        int l = end-start+1;
        for(int i=0; i < l/2; ++i){
            swap(a, start+i, end-i);
        }
    }
    public static void swap(int[] a, int i, int j){
        int tmp=a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static int[] arrayRightRotation(int[] a, int n, int k) {
        reverse(a, 0, n-1); // first rotate entire array
        reverse(a, 0, k-1);
        reverse(a, k, n-1);

        return a;
    }
    public static int[] arrayRightRotationBad(int[] a, int n, int k) {
        reverse(a, 0, n-k-1);
        reverse(a, n-k, n-1);
        reverse(a, 0, n-1);
        return a;
    }
}

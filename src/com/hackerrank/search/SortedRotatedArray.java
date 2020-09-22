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
}

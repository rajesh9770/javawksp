package com.functional;

import com.functional.java8lambdas.Album;
import com.functional.java8lambdas.Artist;
import com.functional.java8lambdas.SampleData;
import javafx.collections.transformation.SortedList;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

/**
 * Created by Rajesh on 7/2/2017.
 */
public class SampleExercises {

    //make a string of artists names
    public static void test1(){
        String result = SampleData.getArtists().map(Artist::getName).collect(Collectors.joining(", ", "[", "]"));
        System.out.println(result);
    }

    //count the number of albums for each artists
    public static void test2(){
        Map<Artist, Long> result = SampleData.albums.collect(Collectors.groupingBy(album -> album.getMainMusician(), counting()));
        result.forEach((k,v) -> System.out.printf("%s : %s\n", k,v));
    }

    public static void test3(){
        Map<Artist, List<String>> result = SampleData.albums.collect(Collectors.groupingBy(album -> album.getMainMusician(), mapping(Album::getName, toList())));
        result.forEach((k,v) -> System.out.printf("%s : %s\n", k, v.stream().collect(Collectors.joining(", ", "[", "]"))));
    }

    public static void test4(){
        String result = SampleData.getArtists().map(Artist::getName).collect(new StringCollector(", ", "[", "]"));
        System.out.println(result);
    }
    public static void main(String[] args) {
        test4();

    }


    /**
     * Given a list of N triangles with integer side lengths, determine how many different triangles there are.
     * Two triangles are considered to be the same if they can both be placed on the plane such that their vertices occupy exactly the same three points.
     * Example 1
     * arr = [[2, 2, 3], [3, 2, 2], [2, 5, 6]]
     * output = 2
     * The first two triangles are the same, so there are only 2 distinct triangles.
     * Example 2
     * arr = [[8, 4, 6], [100, 101, 102], [84, 93, 173]]
     * output = 3
     * All of these triangles are distinct.
     */
    class Sides{
        int a;
        int b;
        int c;
        Sides(int a,int b,int c){
            if(a>b){
                a ^=b;
                b ^=a;
                a ^=b;
            }
            if(b>c){
                b ^=c;
                c ^=b;
                b ^=c;
            }
            if(a>b){
                a ^=b;
                b ^=a;
                a ^=b;
            }
            this.a = a;
            this.b = b;
            this.c = c;

        }

        @Override
        public int hashCode() {
            int result = (int) (a ^ (a >>> 32));
            result = 31 * result + (int) (b ^ (b >>> 32));
            result = 31 * result + (int) (c ^ (c >>> 32));
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Sides o = (Sides) obj;
            return o.a == a && o.b==b && o.c == c;

        }
    }

    // Add any helper functions you may need here


    int countDistinctTriangles(ArrayList<Sides> arr) {
        // Write your code here
        Set<String> triangles = new HashSet<>();
        int ct = 0;
        for (Sides s : arr){
            int a = s.a;
            int b = s.b;
            int c = s.c;
            if(a>b){
                a ^=b;
                b ^=a;
                a ^=b;
            }
            if(b>c){
                b ^=c;
                c ^=b;
                b ^=c;
            }
            if(a>b){
                a ^=b;
                b ^=a;
                a ^=b;
            }
            if(triangles.add(a+"-"+b+"-"+c)) ct++;
        }
        return ct;
    }

    /**
     * Given an array of integers (which may include repeated integers),
     * determine if there's a way to split the array into two subarrays A and B such that the sum of the integers in both arrays is the same,
     * and all of the integers in A are strictly smaller than all of the integers in B.
     */
    boolean balancedSplitExists(int[] arr) {
        // Write your code here

        int sum = 0;
        for (int a: arr){
            sum += a;
        }
        if(sum%2 ==0){
            sum /=2;
            Arrays.sort(arr);
            int rightSum =0;
            for(int i= arr.length-1; i>0; --i){
                rightSum += arr[i];
                if(sum == rightSum && arr[i-1] < arr[i]) return true;
                else if(rightSum>sum) return false;
            }
        }

        return false;
    }
}

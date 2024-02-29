package com.hackerrank.dollar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rajesh on 3/10/2018.
 *
 * Permutation (1 3 0 2) maps  (a0, a1, a2, a3) to (a2, a0, a3, a1)   For Permutation (1, 3, 0,2 ) leader is 0.
 * Permutation (1 0 3 2) maps  (a0, a1, a2, a3) to (a1, a0, a3, a2)   For Permutation (1,  0 ) leader is 0. For Permutation ( 3, 2 ) leader is 2.
 */
public class PermuteInPlace {

    private static int [] A = {1,2,3};

    public static void permute(int[] a, int[] out, int len, int currIdx, boolean[] used){
        if(currIdx == len){
            process(out);
            return;
        }
        for(int i=0; i<len; ++i){
            if(!used[i]){
                used[i] = true;
                out[currIdx] = a[i];
                permute(a, out, len, currIdx+1, used);
                used[i] = false;
            }
        }
    }

    private static void process(int[] a) {
        for(int c: a)
            System.out.print(c);

        A[0] = 0; A[1] = 1; A[2] = 2; //reset A
        permute(A, a);

        System.out.print( "-> ");
        for(int c: A)
            System.out.print(c);

        System.out.println();
    }

    /**
     * Following three are main core functions.
     * @param leader
     * @param permutation
     * @return
     */
    private static boolean isLeader(int leader, int[] permutation){
        int next = permutation[leader];
        while(next>leader){ //leader is the min in the cycle
            next = permutation[next];
        }
        return next == leader;
    }

    /**
     * An array, A[0, n − 1], is permuted according to P (or the permutation is applied) if element A[i] is moved to position P(i) for each i.
     * This has the effect of performing the n simultaneous assignments
     * A[P(i)] ← A[i] for i ∈ [n] i.e. A[i] <- A[P^(-1)[i]]
     * @param A
     * @param leader
     * @param permutation
     */
    private static void rotate(int [] A, int leader, int [] permutation){
        int c = permutation[leader];
        while(c != leader){ //leader is the min in the cycle
            A[c] ^= A[leader];
            A[leader] ^= A[c];
            A[c] ^= A[leader];
            c = permutation[c];
        }
    }

    /**
     * An array, A[0, n − 1], is permuted according to P (or the permutation is applied) if element A[P[i]] is moved to position A(i) for each i.
     * This has the effect of performing the n simultaneous assignments
     * A[i] ← A[P(i)] for i ∈ [n]
     * @param A
     * @param leader
     * @param permutation
     */
    private static void rotate2 (int [] A, int leader, int [] permutation){
        int prev = leader;
        int c = permutation[leader];
        while(c != leader){ //leader is the min in the cycle
            System.out.println("swap " + c + " " + prev);
            A[c] ^= A[prev];
            A[prev] ^= A[c];
            A[c] ^= A[prev];
            permutation[prev] = prev;  // optimize incase you want to set optimize isLeader
            prev = c;
            c = permutation[c];
        }
        permutation[prev] = prev; //optimizes
    }

    private static void permute(int [] A, int[] permutation){
        for(int i=0; i<permutation.length; ++i){
//            if(isLeader(i, permutation)){ //optimize
            if (permutation[i] != i) {
                rotate2(A, i, permutation);
            }
        }
    }

//    public static void main(String[] args) {
//        int len  = A.length;
//        int [] orig = new int[len];
//        for(int i=0; i<len; ++i){
//            orig[i] = i;
//        }
//        boolean [] used = new boolean[len];
//        for(int i=0; i<len; ++i){
//            used[i] = false;
//        }
//        permute(orig, new int[len], len, 0, used);
//
//    }

    /**
     * If an additional, initially empty, bit vetor of length n is available, it is easy to determine
     * whether a loation is the smallest element in its yle in onstant time. Speially, when
     * the value in an array loation is moved, the bit orresponding to that loation is set to 1.
     * Sine the loations are onsidered from smallest to largest, a loation under onsideration
     * will have its orresponding bit equal to 0 exatly when it is a yle leader [12, ex. 1.3.3{12(b),
     * pp. 180, 517{518℄. Similarly, if P is given as an array whose elements an be modied, the
     * same effect can be achieved by setting P(i) to i when the value in array loation i is moved.
     * @param args
     */
    public static void main1(String[] args) {
       int [] input = new int[] { 1, 2, 3, 0};
       int [] permutation = new int[] { 1, 3, 0, 2};

       permute(input, permutation);
        System.out.println(Arrays.toString(input));
    }


    /**
     * There are n students, numbered from 1 to n, each with their own yearbook. They would like to pass their yearbooks around
     * and get them signed by other students.
     * You're given a list of n integers arr[1..n], which is guaranteed to be a permutation of 1..n
     * (in other words, it includes the integers from 1 to n exactly once each, in some order). The meaning of this list is described below.
     * Initially, each student is holding their own yearbook.
     * The students will then repeat the following two steps each minute: Each student i will first sign the yearbook that they're currently holding
     * (which may either belong to themselves or to another student), and then they'll pass it to student arr[i-1].
     * It's possible that arr[i-1] = i for any given i,
     * in which case student i will pass their yearbook back to themselves.
     * Once a student has received their own yearbook back, they will hold on to it and no longer participate in the passing process.
     * It's guaranteed that, for any possible valid input, each student will eventually receive their own yearbook back and will never
     * end up holding more than one yearbook at a time.
     * You must compute a list of n integers output, whose element at i-1 is equal to the number of signatures that will be present
     * in student i's yearbook once they receive it back.
     * n = 2
     * arr = [2, 1]
     * output = [2, 2]
     *
     * n = 2
     * arr = [1, 2]
     * output = [1, 1]
     *
     * n=4
     * arr=[3,2,1,4]
     * output=[2, 1, 2, 4]
     */
    static int[] findSignatureCounts(int[] arr) {
        // Write your code here
        int [] counts = new int[arr.length];
        for (int i=0; i< arr.length; ++i){
            int nextStudent = i+1;
            while(i+1 != arr[nextStudent-1]){
                counts[i]++;
                nextStudent = arr[nextStudent-1];
            }
            ++counts[i];
        }
        return counts;
    }


    static int[] findSignatureCounts3(int[] arr) {
        // Write your code here
        int [] counts = new int[arr.length];

        for (int i=0; i< arr.length; ++i){
            if(counts[i] != 0 ) continue;
            Set<Integer> cycle = new HashSet<>();
            int nextStudent = i+1;
            cycle.add(nextStudent);
            while(i+1 != arr[nextStudent-1]) {
                counts[i]++;
                nextStudent = arr[nextStudent-1];
                cycle.add(nextStudent);
            }
            ++counts[i];
            for (int cycleGroup : cycle){
                counts[cycleGroup-1] = counts[i];
            }
        }
        return counts;
    }
    static int[] findSignatureCounts2(int[] arr) {
        // Write your code here
        int [] counts = new int[arr.length];
        for (int i=0; i< arr.length; ++i) {

            int leader = i+1; //students are from 1 to n.
            //is student a leader
            int next = arr[leader-1];
            int cycleLen = 1;
            while (next > leader){
                next = arr[next-1];
                ++cycleLen;
            }
            if (next == leader) {//is a leader
                next = arr[leader-1];
                while (next > leader){
                    counts[next-1]= cycleLen;
                    next = arr[next-1];

                }
                counts[leader-1] = cycleLen;
            }

        }
        return counts;
    }
    public static void main(String[] args) {

        int [] P = new int[] {1,2,3,0};
        int [] A = new int[] {3,1,2,0};
        rotate2(A, 0, P);
        System.out.println(Arrays.toString(A));
        A = new int[] {3,1,2,0};
        rotate(A, 0, P);
        System.out.println(Arrays.toString(A));

        System.out.println(Arrays.toString(findSignatureCounts2(new int[] {1,2})));//1,1
        System.out.println(Arrays.toString(findSignatureCounts2(new int[] {2,1})));//2,2
        System.out.println(Arrays.toString(findSignatureCounts2(new int[] {4,3,2,5,1}))); //[3,2,2,3,3]
        System.out.println(Arrays.toString(findSignatureCounts3(new int[] {4,3,2,5,1}))); //[3,2,2,3,3]
    }
}

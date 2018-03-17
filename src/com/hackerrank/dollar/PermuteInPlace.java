package com.hackerrank.dollar;

/**
 * Created by Rajesh on 3/10/2018.
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

    private static boolean isLeader(int leader, int[] permutation){
        int c = permutation[leader];
        while(c>leader){ //leader is the min in the cycle
            c = permutation[c];
        }
        return c == leader;
    }

    private static void rotate(int [] A, int leader, int [] permutation){
        int c = permutation[leader];
        while(c != leader){ //leader is the min in the cycle
            A[c] ^= A[leader];
            A[leader] ^= A[c];
            A[c] ^= A[leader];
            c = permutation[c];
        }
    }

    private static void permute(int [] A, int[] permutation){
        for(int i=0; i<permutation.length; ++i){
            if(isLeader(i, permutation)){
                rotate(A, i, permutation);
            }
        }
    }

    public static void main(String[] args) {
        int len  = A.length;
        int [] orig = new int[len];
        for(int i=0; i<len; ++i){
            orig[i] = i;
        }
        boolean [] used = new boolean[len];
        for(int i=0; i<len; ++i){
            used[i] = false;
        }
        permute(orig, new int[len], len, 0, used);

    }
}

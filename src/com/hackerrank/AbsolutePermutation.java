package com.hackerrank;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Rajesh on 8/20/2017.
 */
public class AbsolutePermutation {

    public static void findMinAbsPerm(int n, int k){

        Set<Integer> used = new HashSet<>();
        StringBuilder permutation = new StringBuilder();
        for(int i=1; i<=n; ++i){
            int candidate = i-k;
            if(candidate >0 && !used.contains(candidate)){
                permutation.append(candidate).append(" ");
                used.add(candidate);
                continue;
            }
            candidate = i+k;
            if(candidate <=n && !used.contains(candidate)){
                permutation.append(candidate).append(" ");
                used.add(candidate);
                continue;
            }
            System.out.println("-1");
            return;
        }

        System.out.println(permutation.toString().trim());
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int a0 = 0; a0 < t; a0++){
            int n = in.nextInt();
            int k = in.nextInt();
            findMinAbsPerm(n, k);
        }
    }
}

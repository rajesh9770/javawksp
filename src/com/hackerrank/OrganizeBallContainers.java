package com.hackerrank;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rajesh on 10/21/2017.
 */
public class OrganizeBallContainers {

    /**
     * The distribution of ball types per container are described by an  matrix of integers, , where each C_it is the number of balls of type t in container i.
     * In a single operation, David can swap two balls located in different containers (i.e., one ball is moved from container C_it to C_jk and the other ball is moved from C_jk to C_it.
     * Can you arrange balls such that each container contains only balls of the same type and no balls of same type are in two different containers.
     *
     * Solution:
     * Note that operation preserves the number of balls in the container, i.e. if container i has n balls in the beginning, it will contain n balls after any number of operations.
     * If the solution is possible, then each container will contain balls of different type.
     * So if we count the number of balls in each container and balls of same type, then those counts should match.
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for(int a0 = 0; a0 < q; a0++){
            int n = in.nextInt();
            int [] containterCount = new int[n];
            int [] ballTypeCount = new int[n];
            for(int i=0; i < n; i++){
                containterCount[i] = 0;
                for(int j=0; j < n; j++){
                    int  ct = in.nextInt();
                    containterCount[i] += ct;
                    ballTypeCount[j] +=ct;
                }
            }
            Arrays.sort(containterCount);
            Arrays.sort(ballTypeCount);
            if(Arrays.equals(containterCount, ballTypeCount))
                System.out.println("Possible");
            else
                System.out.println("Impossible");
        }
    }
}

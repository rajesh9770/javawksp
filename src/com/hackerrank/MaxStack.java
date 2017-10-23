package com.hackerrank;

import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Rajesh on 10/22/2017.
 */
public class MaxStack {

    /**
     * String containing only p,c,m,b,z are given. Each letter represents a student who is expert at that subject(phy,che, math, botony, zoology)
     * Form a teams each containing five students, one from each subject such that each team member participates in only one team.
     * Find how many such teams can be formed.
     *
     * @param args
     */
    public static void mainForPerfectTeam(String[] args) {

        Scanner in = new Scanner(System.in);
        String str = in.next();
        int p=0,c=0,m=0,b=0,z=0;
        char[] chars = str.toCharArray();
        for(char ch:chars){
            switch(ch){
                case 'p':
                    p++;
                    break;
                case 'c':
                    c++;
                    break;
                case 'm':
                    m++;
                    break;
                case 'b':
                    b++;
                    break;
                case 'z':
                    z++;
                    break;
            }
        }
        int min=Integer.MAX_VALUE;
        min=Math.min(min,p);
        min=Math.min(min,c);
        min=Math.min(min,m);
        min=Math.min(min,b);
        min=Math.min(min,z);

        System.out.println(min);
    }

    public static void mainMaxForStack(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        Stack<Integer> stk = new Stack<>();
        Stack<Integer> max_stk = new Stack<>();
        int currentMax = Integer.MIN_VALUE;
        for(int i = 0; i < q; i++){
            int action = in.nextInt();
            switch (action){
                case 1:
                    int val = in.nextInt();
                    stk.push(val);
                    if(currentMax <= val){
                        currentMax = val;
                        max_stk.push(val);
                    }
                    break;
                case 2:
                    int delVal = stk.pop();
                    if(currentMax == delVal){
                        max_stk.pop();
                        if(max_stk.empty())
                            currentMax = Integer.MIN_VALUE;
                        else
                                currentMax = max_stk.peek();
                    }
                    break;
                case 3:
                    System.out.println(currentMax);
                    break;
            }
        }
    }
}

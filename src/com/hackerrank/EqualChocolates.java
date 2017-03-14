package com.hackerrank;

import java.util.Scanner;

/**
 * Christy is interning at HackerRank. One day she has to distribute some chocolates to her colleagues. 
 * She is biased towards her friends and may have distributed the chocolates unequally. 
 * One of the program managers gets to know this and orders Christy to make sure everyone gets equal number of chocolates.
 * But to make things difficult for the intern, she is ordered to equalize the number of chocolates for every colleague in the following manner,

For every operation, she can choose one of her colleagues and can do one of the three things.

She can give one chocolate to every colleague other than chosen one.
She can give two chocolates to every colleague other than chosen one.
She can give five chocolates to every colleague other than chosen one.
Calculate minimum number of such operations needed to ensure that every colleague has the same number of 
 * @author Rajesh
 *
 */
public class EqualChocolates {

		
	public static long countSteps(int a, int target) {
		int delta = (a-target);
		int fiveSteps = delta /5;
		int twoSteps = (delta %5) /2;
		int oneSteps = (delta %5) %2;
		return fiveSteps + twoSteps + oneSteps;
	}
	
	public static long process(int [] initState, int target) {
		long steps = 0;
		for(int a: initState){
			steps += countSteps(a, target);
		}
		return steps;
	}
	
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        while(n-->0){
        	int m = in.nextInt();
        	int [] a = new int [m];
        	int min = Integer.MAX_VALUE;
        	for(int i=0;i<m; ++i){
        		a[i] = in.nextInt();
        		min = (a[i] < min) ? a[i] : min;
        	}
        	
        	long minSteps = Long.MAX_VALUE;
        	for(int i = min; i>=Math.max(0, min-4); --i){        		
        		long steps = process(a, i);
        		//System.out.println("Target " + i  + " " + steps);
        		if(steps < minSteps) minSteps = steps;
        	}
        	System.out.println(minSteps);
        }
        //test case
        //1) 1 4 2 2 3 7      Ans 2
        //2) 1 3 1 3 1123834  Ans 224769
	}
}

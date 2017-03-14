package com.hackerrank;

import java.util.Scanner;

/**
 * Consider two non-negative long integers,  and , where . The bitwise AND of all long integers in the inclusive range between  and  can be expressed as , where  is the bitwise AND operator.

Given  pairs of long integers,  and , compute and print the bitwise AND of all natural numbers in the inclusive range between  and .

Input Format

The first line contains a single integer, , denoting the number of intervals to calculate results for. 
Each line  of the  subsequent lines contains two space-separated long integers describing the respective values of  and .

Constraints

 *
 */
public class AndSum {

	public static long rangeBitwiseAnd(long m, long n) {
	       long x = m^n;
			x |= x >> 16;
			x |= x >> 8;
			x |= x >> 4;
			x |= x >> 2;
			x |= x >> 1;		
			x = ~x;
			return m & x;
	    }
	    public static void main(String[] args) {
	        Scanner in = new Scanner(System.in);
	        int n = in.nextInt();                
	        while(n-->0){
	            long a = in.nextLong();
	            long b = in.nextLong();
	            System.out.println(rangeBitwiseAnd(a,b));
	        }
	    }

}

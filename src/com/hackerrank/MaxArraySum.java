package com.hackerrank;

import java.util.Scanner;

public class MaxArraySum {

	public static void main(String[] args) {
	        Scanner in = new Scanner(System.in);
	        int testcases = in.nextInt();	        
	        while(testcases-->0){
	        	int arraySize = in.nextInt();
	        	int [] arr = new int[arraySize];
	        	for(int i=0; i<arraySize; ++i){
	        		arr[i] = in.nextInt();
	        		solve(arr);
	        	}
	        }
	        in.close();
	}

	private static void solve(int[] arr) {
		long maxContSum = arr[0];
		long runningContSum = arr[0];
		for(int i=1; i<arr.length; ++i){
			if(runningContSum <0) runningContSum = 0;
			runningContSum += arr[i];
			maxContSum = Math.max(runningContSum, maxContSum);			 
		}
		System.out.println(maxContSum);
		if(maxContSum >0){
			long maxNonContSum =0;
			for(int i=0; i<arr.length; ++i){
				if(arr[i] >0) maxNonContSum += arr[i]; 
			}
			System.out.println(maxNonContSum);
		}else{
			long maxNonContSum =arr[0];
			for(int i=1; i<arr.length; ++i){
				maxNonContSum = Math.max(maxNonContSum, arr[i]); 
			}
			System.out.println(maxNonContSum);
		}
	}

}

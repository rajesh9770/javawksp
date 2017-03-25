package com.hackerrank;

import java.util.Scanner;

public class RichieRich {

	public static String solve(String num, int n, int k) {
		
		int misMatches = 0;
		for(int i=0; i<n/2; ++i){
			if(num.charAt(i) != num.charAt(n-1-i)) ++misMatches;
		}
		if(misMatches > k) return "-1";
		StringBuilder buff = new StringBuilder();		
		for(int i=0; i<n/2; ++i){			 
			if(num.charAt(i) != num.charAt(n-1-i)){ //char mismatch
				if(num.charAt(i) == '9' || num.charAt(n-1-i) == '9'){ //and one of them is 9
					buff.append('9');
					--misMatches;
					--k;
				}else{// none of them is 9
					if(k-2>=misMatches-1){ //we have extras
						buff.append('9');
						--misMatches;
						k -=2;
					}else{// we do not have extras; use the max of two
						char ch = (num.charAt(i) < num.charAt(n-1-i)) ? num.charAt(n-1-i) : num.charAt(i);
						buff.append(ch);
						--misMatches;
						--k;
					}
				}				
			}else{//match
				if(num.charAt(i) != '9' && k-2>=misMatches){// char is not 9 and we have extras
					buff.append('9');					
					k -=2;
				}else{
					buff.append(num.charAt(i));
				} 
			}
		}
		StringBuilder reverse = new StringBuilder(buff).reverse();	
		if(n%2==1){			
			if(k>0 && num.charAt(n/2) != '9'){
				buff.append('9');
				--k;
			}
			else buff.append(num.charAt(n/2));
		}	
		
		buff.append(reverse);
		return buff.toString();
	}
	
	 public static void main(String[] args) {
	        Scanner in = new Scanner(System.in);
	        int n = in.nextInt();
	        int k = in.nextInt();
	        String number = in.next();
	        System.out.println(solve(number, n, k));
	        
	    }

}

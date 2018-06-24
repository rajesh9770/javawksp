package com.hackerrank.strings;

import java.util.Scanner;

public class ZAlgo {

    public ZAlgo() {
        // TODO Auto-generated constructor stub
    }

    private static int getZForSuffix(String str1, int k) {
        int i=0;
        while(i+k < str1.length() && str1.charAt(i) == str1.charAt(i+k)) ++i;
        return i;
    }
    
    private static long ZAlgo(String str) {
        //z[i] = length of longest substring of str1 starting at i and matches a prefix of str.
        if(str.length() == 0) return 0;
        int [] z = new int [str.length()];
        z[0] = str.length();
        if(str.length() == 1) return z[0];
        z[1] = getZForSuffix(str, 1);
        int r = 1 + z[1] -1;
        int l=1;
        long sum = z[0] + z[1];
        for(int k=2; k<str.length(); ++k){
            if(r <k){
                r = k + getZForSuffix(str, k) -1;
                l = k;
                z[k] = r-l+1;
            }else{
                //right end of z-box starting at k-l   l->0, k->k-l, r-> r-l
                int rightEndOfZ = k-l + z[k-l] -1;
                if(rightEndOfZ < r-l){
                    z[k] = z[k-l];
                }else{
                    z[k] = r-k+1;
                    for(int i=r+1; i<str.length(); ++i){
                        if(str.charAt(i) == str.charAt(i-k)) z[k]++;
                        else break;
                    }
                    l=k;
                    r=k+z[k]-1;
                }
            }
            sum += z[k];
        }
        return sum;
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int testcases = in.nextInt();
        in.nextLine();
        while(testcases-->0){
            String str = in.nextLine();
            System.out.println(ZAlgo(str));
        }
        in.close();
    }

}

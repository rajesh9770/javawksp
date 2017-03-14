package com.problem;

import java.util.Scanner;

public class InsertSortShifts {

    public InsertSortShifts() {
        // TODO Auto-generated constructor stub
    }

    public static long power(int a, int b, int x) {
        //5 2 7 Ans: 28
        //5 2 2 Ans: 24
        long pow = (long) Math.pow(a, b);
        pow += (x/2 + x%2);
        long remainder = pow%x;
        long ans = remainder == 0? pow - x : (pow - remainder);
        System.out.println(a + " " + b + " " + x);
        System.out.println(ans + " " + remainder);
        return ans;
    }
    //
    public static void insertIntoSorted(int[] ar) {
        int tmp = ar[ar.length-1];
        int i = ar.length-2;
        for(; i>=0 && ar[i]>tmp ; --i){
            ar[i+1] = ar[i];
            printArray(ar);
        }
        ar[i+1] = tmp;
        printArray(ar);
    }
    
    
/* Tail starts here */
     public static void mainInsert(String[] args) {
        Scanner in = new Scanner(System.in);
        int s = in.nextInt();
        int[] ar = new int[s];
         for(int i=0;i<s;i++){
            ar[i]=in.nextInt(); 
         }
         insertIntoSorted(ar);
    }
    
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        while(n-->0){
            int a = in.nextInt();
            int b = in.nextInt();
            int x = in.nextInt();
            power(a,b,x);
        }
//        power(349, 1, 4);
//        power(395, 1, 7);
//        power(4, -2, 2);
    }
    
    private static void printArray(int[] ar) {
      for(int n: ar){
         System.out.print(n+" ");
      }
        System.out.println("");
   }
}

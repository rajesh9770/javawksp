package com.hackerrank;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * Created by Rajesh on 5/29/2017.
 */
public class Practice {

    public static void mainForLadder(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        for(int i=0; i<n; ++i){
            for(int j=0; j<n; ++j){
                if(j>n-2-i) System.out.print("#");
                else System.out.print(" ");
            }
            System.out.println("");
        }
    }

    public static void mainForConsecutiveOnesInBinary(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int longOnes = 0;
        while(n>0){
            n &= (n>>1);//idea is the longer sequence of 1's will only survive till end
            ++longOnes;
        }
        System.out.println(longOnes);
    }

    public static void main(String[] args) {
        mainForConsecutiveOnesInBinary(null);
    }
    public static void mainForStrangeCounter(String[] args) {
        Scanner in = new Scanner(System.in);
        long n = in.nextLong();
        long counter = 3;
        long groupSize = 3;
        while(n>counter){
            groupSize *= 2;
            counter += groupSize;
        }
        //counter maps to 1, counter-1 maps to 2, ...
        System.out.println(counter-n+1);
    }

    public static void mainForSaveThePrisoner(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for (int a0 = 0; a0 < t; a0++) {
            long n = in.nextInt();
            long m = in.nextInt();
            long s = in.nextInt();
            //System.out.println(m % n);
            long result = (s - 2) + m % n;
            result +=n;
            result %= n;
            //System.out.println(result%n+1);
            System.out.println(result + 1);
        }
    }

    private static void HappyLadyBugs(String b){
        if(b.indexOf('_') == -1 ){
            for(int i=0; i<b.length(); ){
                if(i>0 && (b.charAt(i) == b.charAt(i-1))) ++i;
                else if(i<b.length()-1 && b.charAt(i) == b.charAt(i+1)) i+=2;
                else {
                    System.out.println("NO");
                    return;
                }
            }
            System.out.println("YES");
            return;
        }

        Map<Character, Integer> counts = new HashMap<>();
        for(int i=0; i<b.length(); ++i){
            if(b.charAt(i) == '_') continue;
            Integer count = counts.get(b.charAt(i));
            if(count == null) count = 1;
            else count = count +1;
            counts.put(b.charAt(i), count);
        }
        for(Map.Entry<Character, Integer> items : counts.entrySet()){
            if(items.getValue() == 1){
                System.out.println("NO");
                return;
            }
        }
        System.out.println("YES");
    }

    public static void mainForValley(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        String str = in.next();
        char[] chars = str.toCharArray();
        long valleyCount = 0;
        long upSteps = 0, downSteps = 0;
        for(char c: chars){
            if( (upSteps == downSteps) && c == 'D'){
                ++valleyCount;
            }
            if (c=='D') downSteps++;
            if (c=='U') upSteps++;
        }
        System.out.println(valleyCount);
    }

    public static void main1(String[] args) {
        Scanner in = new Scanner(System.in);
        int Q = in.nextInt();
        for(int a0 = 0; a0 < Q; a0++) {
            int n = in.nextInt();
            String b = in.next();
            HappyLadyBugs(b);
        }
    }
}

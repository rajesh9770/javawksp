package com.hackerrank;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

/**
 */
public class Miscellaneous {

    public Miscellaneous() {
        // TODO Auto-generated constructor stub
    }

    public static BigInteger choose(int n, int k) {
        BigInteger r = BigInteger.valueOf(1);
        long d;
        if (k > n) return BigInteger.valueOf(0);
        for (d=1; d <= k; d++) {
            r = r.multiply(BigInteger.valueOf(n));
            n--;
            r= r.divide(BigInteger.valueOf(d));
            //r *= n--;
            //r /= d;
        }
        return r;
    }

    public static long modpow(long base_value, long exponent, long modulus){
        base_value = base_value % modulus;
        long result = 1;
        while(exponent > 0){
                if (exponent % 2 == 1) result = (result * base_value) % modulus;
                exponent = exponent / 2;
                if(exponent >0 )
                    base_value = (base_value * base_value) % modulus;
        }
        return result;
    }
    
     public static int gcd(int a, int b) {
        int t;
        while (b != 0) {
            t = b;
            b = a % b; // a mod b
            a = t;
        }
        return a;
    }
    
    //Closest Number
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

    public static void mainForModFibonacci(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println(modFibonacci(BigInteger.valueOf(in.nextInt()),
                BigInteger.valueOf(in.nextInt()), in.nextInt()));
    }

    private static BigInteger modFibonacci(BigInteger a, BigInteger b, int n ) {
        if(n==1) return a;
        if(n==2) return b;

        for(int i=3; i<=n; ++i){
            BigInteger tmp = b;
            
            b = a.add(b.multiply(b));
            a = tmp;
        }
        return b;
    }
    
    public static void mainForSteady() {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        String str = in.next();
        steady(str);
    }

    public static void mainForFunny() {
        Scanner in = new Scanner(System.in);
        int testcases = in.nextInt();
        in.nextLine();
        while(testcases-->0){
            String str = in.nextLine();
            funnyString(str);
        }
    }

    public static void mainForEuler(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        while(n-->0){
            int a = in.nextInt();
            int p = in.nextInt();
            if(a==0 || p==2){
                System.out.println("YES");
                continue;
            }
            long pow = modpow(a, (p-1)/2, p);
            if(pow==1)
                System.out.println("YES");
            else
                System.out.println("NO");
        }
    }
    
    public static void mainForSherlockGCD(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        while(n-->0){
            int m = in.nextInt();
            int [] arr = new int [m];
            while(m-->0){
                arr[m] = in.nextInt();
            }
            System.out.println(process(arr));
        }
    }

    public static long minLenCoalesce(String str) {
        int [] count = new int [3]; count[0] = count[1] = count[2] = 0; 

        for(int i=0; i< str.length(); ++i){
            count[str.charAt(i) - 'a']++;
        }
        if( (count[0] == 0 && count[1] == 0) ||
            (count[0] == 0 && count[2] == 0) ||
            (count[1] == 0 && count[2] == 0) ) return str.length();
        if(count[0]%2 == count[1]%2  && count[1] %2 == count[2] %2 ) return 2;
        return 1;
    }

    public static long minLenCoalesce(String str, Map<String, Long> lens) {
        if(lens.containsKey(str)) return lens.get(str);
        long cost = str.length();
        for(int i=1; i<str.length(); ++i){
            if(str.charAt(i) != str.charAt(i-1)){
                cost = Math.min(cost, minLenCoalesce(getNewString(str, i), lens));
            }
        }
        //System.out.println(str + " " + cost);
        lens.put(str, cost);
        return cost;
    }

    
    private static String getNewString(String str, int i) {
        char a1 = str.charAt(i-1);
        char a2 = str.charAt(i);
        char newchar = 'c';

        if('b' != a1 && 'b' != a2 ) newchar = 'b';
        if('a' != a1 && 'a' != a2 ) newchar = 'a';

        //if(i==1) return newchar + str.substring(i+1);
        return str.substring(0,i-1) + newchar + str.substring(i+1);
    }

    public static void mainMinLenCoalesce(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        in.nextLine();
        while(t-->0){
            String next = in.nextLine();
            System.out.println(minLenCoalesce(next));
        }
    }

    public static void mainForStockPrice(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int a0 = 0; a0 < t; a0++){
            int N = in.nextInt();
            int[] prices = new int[N];
            for(int prices_i=0; prices_i < N; prices_i++){
                prices[prices_i] = in.nextInt();
            }
            int nextHigh = prices[N-1];
            long profit = 0;
            for(int i = N-2; i>=0; --i){
                if(prices[i] > nextHigh) nextHigh = prices[i];
                else profit += (nextHigh-prices[i]);
            }
            System.out.println(profit);
        }
    }


    public static void mainForAngry(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int [] arr  = new int[n];
        while(n>0){
            arr[--n] = in.nextInt();
        }
        System.out.println(calABS(arr, k));
    }

    public static long calABS(int [] arr, int k) {
        Arrays.sort(arr);

        long [] sum = new long [arr.length];
        sum[0] = arr[0];
        for(int i=1; i< arr.length; ++i){
            sum[i] = sum[i-1] + arr[i];
        }
        long fairNess = 0;
        for(int i=0; i<k-1; ++i){
            for(int j=i+1; j<k; ++j)
                fairNess += (arr[j] - arr[i]);
        }

        long minfairNess = fairNess;
        long multiplier = k-1;
        for(int i=k ; i<arr.length; ++i){
            //int lowerIndex = i-k+1;
            fairNess = fairNess - 2l*(sum[i-1] - sum[i-k]);
            fairNess = fairNess + multiplier*arr[i-k];
            fairNess = fairNess + multiplier*arr[i];
            minfairNess = Math.min(minfairNess, fairNess);
        }
        return minfairNess;
    }

    public static long calFaireness2(int [] arr, int start, int k ) {
        long ret = 0;
        for(int i=start; i<start+k-1; ++i){
            for(int j=i+1; j<start+k; ++j)
                ret += (arr[j] - arr[i]);
        }
        return ret;
    }

    private static String process(int[] arr) {
        int gcd = arr[0];
        for(int i=1; i<arr.length; ++i){
            gcd = gcd(gcd, arr[i]);
            if(gcd ==1) return "YES";
        }
        return "NO";
    }
    
    
    public static void mainForNextFromTwoStacks(String[] args) {
        Scanner in = new Scanner(System.in);
        
        int testcases = in.nextInt();
        in.nextLine();
        while(testcases-->0){
            String str1 = in.nextLine();
            String str2 = in.nextLine();
            findNextFromTwoStacks(str1, str2);
        }
        in.close();
        
        /**
         *
7
BAB
BAC
DAD
DAD
ABCBA
BCBA
BAC
BAB
DAD
DABC
YZYYZYZYY
ZYYZYZYY
ZZYYZZZA
ZZYYZZZB


BABABC
DADADD
ABBCBACBA
BABABC
DABCDAD
YZYYZYYZYZYYZYZYY
ZZYYZZYYZZZAZZZB
         */
    }
    
    private static void findNextFromTwoStacks(String str1, String str2) {
        StringBuilder buff = new StringBuilder();
        int idx1 = 0;
        int idx2 = 0;
        while(idx1 <str1.length() && idx2 < str2.length()){
            char c1 = str1.charAt(idx1);
            char c2 = str2.charAt(idx2);
            if(c1 == c2){
                int bIdx1 = idx1+1;
                int bIdx2 = idx2+1;
                char pivot = c1;
                for(; bIdx1 <str1.length() && bIdx2 < str2.length(); ++bIdx1, ++bIdx2){
                    char r1 = str1.charAt(bIdx1);
                    char r2 = str2.charAt(bIdx2);
                    if(r1 != r2){
                        break;
                    }else if(r1 > pivot){
                        buff.append(str1.substring(idx1, bIdx1));
                        buff.append(str2.substring(idx2, bIdx2));
                        idx1 = bIdx1; idx2 = bIdx2;
                        pivot = r1;
                    }
                }
                if(bIdx1 == str1.length()){ // so no need to add 'z' at the end of str1, str2
                    buff.append(str2.charAt(idx2));
                    idx2++;
                }else if(bIdx2 == str2.length()){
                    buff.append(str1.charAt(idx1));
                    idx1++;
                }else{
                    if(str1.charAt(bIdx1) < str2.charAt(bIdx2)){
                        buff.append(str1.charAt(idx1));
                        idx1++;
                    }else{
                        buff.append(str2.charAt(idx2));
                        idx2++;
                    }
                }
            }else{
                if(c1 < c2){
                    buff.append(c1);
                    idx1++;
                }else{
                    buff.append(c2);
                    idx2++;
                }
            }
        }
        buff.append(str1.substring(idx1)).append(str2.substring(idx2));
        System.out.println(buff);
    }
    
    public static void funnyString(String str) {
        int len = str.length();
        for(int i=0; i<=len/2-1; ++i){
            int c1 = Math.abs(str.charAt(i) - str.charAt(i+1));
            int c2 = Math.abs(str.charAt(len -1 - i) - str.charAt(len-1 -i-1));
            if(c1 != c2) {
                System.out.println("Not Funny");
                return;
            }
        }
        System.out.println("Funny");
    }
    
    private static void steady(String str) {
        int [] freq = new int [4];
        for(int i=0; i<4; ++i) freq[i] = 0;
        int len = str.length();
        for(int i = 0; i<len; ++i){
            freq[mapGeneToIdx(str.charAt(i))]++;
        }
        int left = 0, right =0;
        int min = Integer.MAX_VALUE;
        while(right < len){
            freq[mapGeneToIdx(str.charAt(right))]--;
            right++;
            if(invariantHolds(freq, len/4)){
                System.out.println("L:" + left + " R: " + right);
                min = Math.min(min, right-left);
                //move left
                freq[mapGeneToIdx(str.charAt(left))]++;
                left++;
                while(freq[mapGeneToIdx(str.charAt(left-1))] <=len/4){
                    min = Math.min(min, right-left);
                    if(left<right){
                        freq[mapGeneToIdx(str.charAt(left))]++;
                        left++;
                    }else break;
                }
            }
        }
        System.out.println(min);
    }

    private static boolean invariantHolds(int [] counts, int N) {
        for(int ct : counts){
            if(ct > N) return false;
        }
        return true;
    }
    private static int mapGeneToIdx(char c) {
        switch (c) {
        case 'A':
            return 0;
        case 'C':
            return 1;
        case 'G':
            return 2;
        case 'T':
            return 3;
        default:
            return 0;
        }
    }
    
    public static void mainForCandy(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int [] ratings = new int [N];
        for(int i=0; i<N; ++i){
            ratings[i] = in.nextInt();
        }
        System.out.println(countCandy(ratings));
        in.close();
    }
    public static long countCandy(int [] ratings) {
        int [] candies = new int [ratings.length];
        candies[0] = 1;
        for(int i=1; i<ratings.length; ++i){
            if(ratings[i] > ratings[i-1]) candies[i] = candies[i-1]  + 1;
            else candies[i] = 1;
        }
        for(int i=ratings.length-2;  i>=0; --i){
            if(ratings[i] > ratings[i+1]) candies[i] = Math.max(candies[i+1]  + 1, candies[i]);
        }
        long count = 0L;
        for(int c: candies){
            count += c;
        }
        return count;
    }
    
    public static void mainForFillJugs(String[] args) {
        Scanner in = new Scanner(System.in);
        int testcases = in.nextInt();
        while(testcases-- >0){
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            int gcd = gcd(a,b);
            if(c <= Math.max(a, b) && c%gcd ==0)
                System.out.println("YES");
            else
                System.out.println("NO");
        }
    }
    public static void mainForCountSumOfSubStrings(String[] args) {
        Scanner in = new Scanner(System.in);
        String numberString = in.nextLine();
        System.out.println(countSumOfSubStrings(numberString));
    }
    
    public static long countSumOfSubStrings(String number) {
        int mod = 1000000007;
        long ret = 0;
        long f = 1;
        for(int i = number.length()-1; i>=0; --i){
            long x = ((number.charAt(i) -'0') * (i+1l) * f) % mod;
            ret = (ret + x) % mod;
            f = (f*10 + 1) % mod;
        }
        return ret;
    }
    
    public static void mainForCountContiguousSubSequence(String[] args) {
        Scanner in = new Scanner(System.in);
        int testcases = in.nextInt();
        while(testcases-- >0){
            int n = in.nextInt();
            int k = in.nextInt();
            int [] arr = new int[n];
            for(int i=0; i<n; ++i)
                arr[i] = in.nextInt();
            System.out.println(countContiguousSubSequence(arr, k));
        }
        in.close();
    }

    public static long countContiguousSubSequence(int [] arr, int k) {

        int [] mods = new int[k];
        for(int i=0; i<k; ++i){
            mods[i] = 0;
        }
        long count = 0;
        int sum = 0;
        for(int a: arr){
            sum += a;
            sum %=k;
            if(sum == 0) count++;
            count += mods[sum];
            mods[sum]++;
        }
        return count;
    }

    public static void mainForUnboundedKnapsack(String[] args){
        Scanner in = new Scanner(System.in);
        int testcases = in.nextInt();
        while(testcases-- >0){
            int n = in.nextInt();
            int w = in.nextInt();
            int [] arr = new int[n];
            for(int i=0; i<n; ++i)
                arr[i] = in.nextInt();
            System.out.println(UnboundedKnapsack(arr, w));
        }
        in.close();
    }

    public static long UnboundedKnapsack(int [] weights, int W) {
        Arrays.sort(weights);
        long [] wg = new long [W+1];
        wg[0] = 0;
        wg[1] = weights[0] ==1 ? 1: 0;
        for(int i = 2; i<=W; ++i){
            wg[i] = 0;
            for(int j=0; j<weights.length; ++j){
                if(weights[j] <= i) wg[i] = Math.max(wg[i], weights[j] + wg[i-weights[j]]);
                else break;
            }
        }
        return wg[W];
    }
    
    public static void mainForMaxDifferenceWithBounds(String[] args){
        Scanner in = new Scanner(System.in);
        int testcases = in.nextInt();
        while(testcases-- >0){
            int n = in.nextInt();
            int [] arr = new int[n];
            for(int i=0; i<n; ++i)
                arr[i] = in.nextInt();
            System.out.println(maxDifferenceWithBounds(arr, n));
        }
        in.close();
    }
    public static int maxDifferenceWithBounds(int [] arr, int len) {
        int diff[][] = new int [2][len];
        diff[0][0] = diff[1][0] = 0;
        for(int i=1; i<len; ++i){
            diff[0][i] = Math.max(diff[0][i-1] + Math.abs(arr[i]-arr[i-1]), diff[1][i-1] + Math.abs(Math.abs(arr[i]-1)));
            diff[1][i] = Math.max(diff[0][i-1] + Math.abs(1-arr[i-1]), diff[1][i-1]);
        }
        return Math.max(diff[0][len-1], diff[1][len-1]);
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int testcases = in.nextInt();
        
        while(testcases-- >0){
            BigInteger n = new BigInteger(in.nextLine());
            System.out.println(n.multiply(n));
        }
    }
}

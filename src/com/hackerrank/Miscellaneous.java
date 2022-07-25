package com.hackerrank;

import java.math.BigInteger;
import java.util.*;

/**
 */
public class Miscellaneous {

    public Miscellaneous() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Find the suqareroot of a number rounded down
     * 3 -> 1
     * 4 -> 2
     * 5 -> 2
     * 3 -> 2
     * @param input
     * @return
     */
    public static int sqrt(int input){
        int low = 1, high = input;
        //set initial low and high wisely. For example if input = 2^15, we know sqrt lies between 2^7 and 2^8.
        while(low<high){
            int mid = (low+high)/2;
            if (mid*mid == input){
                return mid;
            }else if (mid*mid < input){
                if((mid+1)*(mid+1) <input)  low = mid+1;
                else return mid;
            } else{
                if((mid-1)*(mid-1) >input)  high = mid-1;
                else return mid;
            }
        }
        return low;
    }

    public static int maxCoins(int[] piles) {
        Arrays.sort(piles);
        int n = piles.length/3;
        int ret = 0;
        for(int i=n; i< piles.length; i+=2){
            ret += piles[i];
        }
        return ret;
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

    //Euclid's algorithm
     public static int gcd(int dividend, int divisor) {
        int t;
        while (divisor != 0) {
            t = divisor;
            int remainder = dividend % divisor; // a mod b
            dividend = t; //previous divisor becomes dividend
            divisor = remainder; //remainder becomes divisor
        }
        return dividend;
    }

    /**
     *
     * @param dividend
     * @param divisor
     * @return 0: gcd. 1: x 2: y where gcd = x * dividend + y * divisor
     *
     * g=gcd(a, b)= a * x1 + b * y1;
     * g=gcd(b, a%b) = b * x2  + (a%b) * y2;
     * a%b = a - floor(a/b)*b
     * x1 = y2;
     * y2 = x2 - y2 * floor(a/b);
     * In java floor(a/b) = a/b -- integer division
     */
    public static int[] extendedGCD(int dividend, int divisor){
        int [] ret;
        if(divisor == 0){
            ret = new int[3];
            ret[0] = dividend;
            ret[1] = 1;
            ret[2] = 0;
        }else{
            ret = extendedGCD(divisor, dividend % divisor);
            int tmp = ret[1];
            ret[1] = ret[2];
            ret[2] = tmp - ret[2] * (dividend/divisor);
        }
        return ret;
    }

    public static void mainForExtendedGCD(String[] args) {
        for(int i=2; i<100; ++i){
            for(int j=1; j<i; ++j){
                int[] rets = extendedGCD(i, j);
                if(i*rets[1] + j*rets[2] != rets[0])
                    System.out.println(i + " " + j + " " + rets[0] + " " + rets[1] + " " + rets[2]);
            }
        }
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

    /**
     * Stock Profit
     * Stock Profit
     * Your algorithms have become so good at predicting the market that you now know what the share price of Wooden Orange Toothpicks Inc.
     * (WOT) will be for the next number of days.
     *
     * Each day, you can either buy one share of WOT, sell any number of shares of WOT that you own, or not make any transaction at all.
     * What is the maximum profit you can obtain with an optimum trading strategy?
     * @param args
     */
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


    /**
     *  Each of the packets contains a number of candies. Each kid gets one packet. There are more packets than kids.
     *  Want to minimize the cumulative difference in the number of candies in the packets that are handed out.
     *  Determine the minimum unfairness sum achievable.
     * @param args
     */
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
            fairNess = fairNess - 2L*(sum[i-1] - sum[i-k]);
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

    /**
     * count consecutive subsequences whose sum is divisible by k
     * @param arr
     * @param k
     * @return
     */
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

    /**
     * Given an array of integers and a target sum, determine the sum nearest to but not exceeding the target that can be created.
     * To create the sum, use any element of your array zero or more times.
     *
     * For example, if  arr = [2,3,4] and your target sum is 10, you might select [2,2,2,2,2], [2,2,3,3] or [3,3,4]. In this case, you can arrive at exactly the target.
     * @param weights
     * @param W
     * @return
     */
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

    /**
     * Weights are in ascending order. Bag has capacity W.
     * Only one weight of size weights[i] is available.
     * @param weights
     * @param W
     * @return
     */
    public static long BoundedKnapsack(int W, int [] weights){
        long dp[][] = new long[W+1][weights.length];
        for(int j=0;j<weights.length; ++j) dp[0][j] = 0; //if total weight allowed is 0, then only one solution exists, i.e. take no weights and total weight dp[0][w] is 0.
        for(int i=0; i<W+1; ++i ){
            //with just one weight weight[0], we can take only take weights[0] weight if allowed weight is greater or equal to weights[0].
            dp[i][0] =   weights[0] >=i ? weights[0] : 0;
        }

        for(int i=1; i<W+1; ++i){
            for(int j=1; j<weights.length; ++j){
                if(i>=weights[j]){
                    dp[i][j] = Math.max(dp[i][j-1], weights[j] + dp[i-weights[j]][j-1]);
                }else{//can't take weight[j]
                    dp[i][j] = dp[i][j-1];
                }
            }
        }
        return dp[W][weights.length-1];
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
    
    public static void mainForInversions(String[] args) {
        Scanner in = new Scanner(System.in);
        int testcases = in.nextInt();
        
        while(testcases-- >0){
            int n = in.nextInt();
            int [] arr = new int [n];
            for(int i=0; i<n; ++i) arr[i] = in.nextInt();
            long inversions = 0;
            for(int i=0; i<n; ++i){
            	for(int j=i+1; j<n; ++j){
            		if(arr[i] > arr[j]) ++inversions;
            	}            		
            }
            
            System.out.println(inversions %2 == 0 ? "YES" : "NO");
        }
    }
    
    public static void mainTest(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int a0 = 0; a0 < t; a0++){
            long n = in.nextInt();
            long m = in.nextInt();
            long s = in.nextInt();
            System.out.println(m%n);
            long result = (s-1) + m%n ;
            result--;            
            //System.out.println(result%n+1);
            System.out.println((s+m-2)%n + 1);
        }
    }

    public static int clockProblem(int hour, int min){

        hour %= 12;
        min %= 60;

        //min hand moves 360 degree in 60 min i.e. 6 degree in 1 min
        //hour hand moves 30 degree in 60 min i.e. 0.5 degree in 1 min
        // Calculate the angles moved by hour and minute hands
        // with reference to 12:00
        int hour_angle = (int)(0.5 * (hour*60 + min));
        int minute_angle = (int)(6*min);
        // Find the difference between two angles
        int angle = Math.abs(hour_angle - minute_angle);

        // smaller angle of two possible angles
        angle = Math.min(360-angle, angle);

        return angle;
    }

    public static void mainForSquares(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        while(q-->0){
            int a = in.nextInt();
            int b = in.nextInt();
            int g = gcd(a,b);
            System.out.println(a*b/g*g);
        }
    }

    public static void mainForTriangle(String [] args){
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        while(q-->0){
            int n = in.nextInt();
            //f(x) = 2 if x is edd
            //       3 if 4|x
            //       4 if 2|x
            // prove by induction

            System.out.println(n%2==1 ? 2 : n%4==0 ? 3: 4);
        }
    }
//
//    public static void Samplemain(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int testcases = in.nextInt();
//        
//        while(testcases-- >0){
//            BigInteger n = new BigInteger(in.nextLine());
//            System.out.println(n.multiply(n));
//        }
//    }

        private static boolean isFibonacci(String buf){

            for(int i=1; i<buf.length()-2; ++i){//[0, i)
                for(int j=i+1; j<buf.length()-1; ++j){ //[i, j)
                    long num1 = Long.parseLong(buf.substring(0,i));
                    long num2 = Long.parseLong(buf.substring(i,j));
                    long sum = num1 + num2;
                    if(buf.substring(j).startsWith(""+sum))
                        if (checkForFibonacci(buf.substring(i), j-i, (""+sum).length()))
                            return true;

                }
            }
            return false;
        }

    private static boolean checkForFibonacci(String str, int len1, int len2) {
        if(len1 + len2 == str.length()) return  true;
        if(len1 + len2 < str.length()){
            long num1 = Long.parseLong(str.substring(0, len1));
            long num2 = Long.parseLong(str.substring(len1, len1+len2));
            long sum = num1 + num2;
            if(str.substring(len1+len2).startsWith(""+sum))
                return checkForFibonacci(str.substring(len1), len2, (""+sum).length());
            else return false;
        }
        return false;
    }

    public static void main2(String[] args) {
        //System.out.println(sqrt(9));
        //System.out.println(sqrt(5));
        //System.out.println(sqrt(7));
        //System.out.println( maxCoins(new int[] {2,4,1,2,7,8} ));

        if(true) return;
        StringBuilder buff=new StringBuilder();
        long seeds[] = {12, 23};
        buff.append(seeds[0]).append(seeds[1]);
        for(int i=0; i<4; i++){
            long sum = seeds[0] + seeds[1];
            System.out.println(sum);
            buff.append(sum);
            seeds[0] = seeds[1];
            seeds[1] = sum;
        }
        System.out.println(isFibonacci(buff.toString()));
    }

    public int minDistance(String word1, String word2) {
        char [] a1 = word1.toCharArray();
        char [] a2 = word2.toCharArray();
        int [] ct = new int[26];
        for(char c: a1){
            ct[c-'a']++;
        }
        for(char c: a2){
            ct[c-'a']--;
        }
        int del=0;
        for(char c: a1){
            int a = ct[c-'a'];
            del = del + a > 0? a : -a;
        }
        return del;
    }


    public int maxStockProfit(int[] prices) {
        int profit = 0;
        boolean buy = true;
        int pricePaid = 0;
        for (int i=0; i<prices.length; ++i) {
            if (buy) {
                if (i + 1 < prices.length && prices[i] < prices[i + 1]) { //buy it
                    buy = false;
                    pricePaid = prices[i];
                }
            } else {
                if (pricePaid < prices[i]) {
                    if (i + 1 == prices.length || prices[i] > prices[i + 1]) { //sell it
                        buy = true;
                        profit += (prices[i] - pricePaid);
                    }
                }
            }
        }
        return profit;
    }

    /**
     * Given an array A, partition it into two (contiguous) subarrays left and right so that:
     *
     * Every element in left is less than or equal to every element in right.
     * left and right are non-empty.
     * left has the smallest possible size.
     * @param A
     * @return
     */
    public int partitionDisjoint(int[] A) {
        int [] maxR = new int[A.length];

        int [] minL = new int[A.length];
        maxR[0] = A[0];
        for(int i=1;i<A.length; ++i){
            maxR[i] = Math.max(maxR[i-1], A[i]);
        }

        minL[A.length-1] = A[A.length-1];
        for(int i=A.length-2; i>=0; --i){
            minL[i] = Math.min(minL[i+1], A[i]);
        }

        for(int i=1; i<A.length; ++i){
            if(maxR[i-1]<=minL[i]) return i;
        }
        return 0;
    }

    /**
     * Given an array nums with n integers, your task is to check if it could become non-decreasing by modifying at most 1 element.
     *
     * We define an array is non-decreasing if nums[i] <= nums[i + 1] holds for every i (0-based) such that (0 <= i <= n - 2).
     */

    public boolean checkPossibility(int[] nums) {
        int prev = nums[0];
        int dips = 0;
        for (int i=1; i<nums.length; ++i) {
            if ( prev > nums[i]) {
                ++dips;
                if(dips>1) return false;
                if ( i-2>=0 || nums[i-2] > nums[i]) continue; // case where you raise a[i], so a[i] = a[i-1], prev stays the same
                //else you lower a[i-1], a[i-1] = a[i]
            }
            prev = nums[i];
        }
        return true;
    }

    /**
    One simple way to encrypt a string is to "rotate" every alphanumeric character by a certain amount. Rotating a character means replacing it with another character that is a certain number of steps away in normal alphabetic or numerical order.
    For example, if the string "Zebra-493?" is rotated 3 places, the resulting string is "Cheud-726?". Every alphabetic character is replaced with the character 3 letters higher (wrapping around from Z to A), and every numeric character replaced with the character 3 digits higher (wrapping around from 9 to 0). Note that the non-alphanumeric characters remain unchanged.
    **/
    static String rotationalCipher(String input, int rotationFactor) {
        // Write your code here
        StringBuilder buff = new StringBuilder();
        for(char c: input.toCharArray()){
            if (c>='A' && c<='Z'){
                int C = ((c - 'A') + rotationFactor) % 26 + 'A';
                buff.append((char)C);
            }else if (c>='a' && c<='z'){
                int C = ((c - 'a') + rotationFactor) % 26 + 'a';
                buff.append((char)C);
            }else if (c>='0' && c<='9'){
                int C = ((c - '0') + rotationFactor) % 10 + '0';
                buff.append((char)C);
            } else {
                buff.append(c);
            }
        }
        return buff.toString();
    }



    public static void main(String[] args) {
        //System.out.println(rotationalCipher("Zebra-493?", 3));
        double d1 = 10.0;
        Double d2 = 20.0;
        Double d3 = new Double(30.0);
        double d4 = new Double(40.0);

        System.out.println(d1 + d2 + d3.doubleValue() + d4);
    }
}

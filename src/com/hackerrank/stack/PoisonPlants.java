package com.hackerrank.stack;

import javafx.util.Pair;

import java.util.Arrays;

import java.util.List;
import java.util.Stack;

/**
 * Created by Rajesh on 11/17/2017.
 *  Read stock span problem before attempting this.  http://www.geeksforgeeks.org/the-stock-span-problem/
 *  The stock span problem is a financial problem where we have a series of n daily price quotes for a stock
 *  and we need to calculate the span of the stock’s price for all n days.
 *  The span Si of the stock’s price on a given day i is defined as the maximum number of consecutive days just
 *  before the given day, for which the price of the stock on the current day is less than its price on the given day.
 *
 *
 * There are a number of plants in a garden.
 * Each of the plants has been treated with some amount of pesticide.
 * After each day, if any plant has more pesticide than the plant on its left, being weaker than the left one, it dies.
 *
 * You are given the initial values of the pesticide in each of the plants.
 * Determine the number of days after which no plant dies,
 * i.e. the time after which there is no plant with more pesticide content than the plant to its left.
 */
public class PoisonPlants {

    static class Data{
        int value;
        int deathDay;
        Data(int val, int day){
            this.value = val; //plant
            this.deathDay = day; //stores the day on which plant dies
        }
    }

    static int poisonousPlants(int[] p) {
        Stack<Data> smallerElements = new Stack<>(); //ascending order from bottom of stack to top.
        int days = 0;
        for(int i=0;i<p.length; ++i){
            //we adding p[i]-th element on stack. make sure p[i] is >  top of stack; otherwise pop all higher elements
            if(smallerElements.isEmpty()){
                smallerElements.push(new Data(p[i], -1));
            }else{
                int leftNeighbour = smallerElements.peek().value; // this will always give left neighbor, since we always add the current element on stack.
                if(p[i] > leftNeighbour){//i-th plant dies on day 1 - plant dies if it's fertiliser is more than it's left
                    smallerElements.push(new Data(p[i], 1));
                    days = Math.max(days, 1);
                }else{
                    int deathDay = -1;
                    while(!smallerElements.isEmpty() && smallerElements.peek().value >= p[i]){
                        Data pop = smallerElements.pop();
                        deathDay = Math.max(deathDay, pop.deathDay);
                    }
                    if(smallerElements.isEmpty()){ //this plant will survive b/c all plant on left have more fertilizer
                        smallerElements.push(new Data(p[i], -1));
                    }else{// we have found a killer.
                        smallerElements.push(new Data(p[i], deathDay+1)); // this plant will day the next day after all its neighbors dies
                        days = Math.max(days, deathDay + 1);
                    }
                }
            }
        }
        return days;
    }

    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int n = in.nextInt();
//        int[] p = new int[n];
//        for(int p_i = 0; p_i < n; p_i++){
//            p[p_i] = in.nextInt();
//        }
//        int result = poisonousPlants(p);
//        System.out.println(result);
//        in.close();
        System.out.println(Arrays.equals(StockSpanner(new int[] {100, 80, 60, 70, 60, 75, 85}), (new int[]{1, 1, 1, 2, 1, 4, 6})));

    }

    static int poisonousPlants(List<Integer> p ){
        Stack<Data> st = new Stack<>();//keep the stack in ascending as in largest rectangle histogram
        int ans = 0;
        for(int i=0; i<p.size(); ++i){
            int val = p.get(i);
            if(st.isEmpty()){
                st.push(new Data(val, -1));
            }else if(st.peek().value < val){
                st.push(new Data(val, 1));
                ans = Math.max(ans, 1);
            }else{
                int dd = -1;
                while(!st.isEmpty() && st.peek().value >= val){
                    Data left = st.pop();
                    dd = Math.max(dd, left.deathDay);
                }
                if(st.isEmpty()){
                    st.push(new Data(val, -1));
                }else{
                    st.push(new Data(val, dd+1));
                    ans = Math.max(ans, dd+1);
                }
            }
        }
        return ans;
    }

    //#901. Online Stock Span
    /**
     * The span of the stock's price today is defined as the maximum number of consecutive days (starting from today and going backwards)
     * for which the price of the stock was less than or equal to today's price.
     * [100 80 60 70 60 75 85]
     * Output: 1 1 1 2 1 4 6
     * See com.hackerrank.stack.ContiguousMaxSubArray.countSubarrays
     */
    public Stack<Pair<Integer, Integer>> stockSpan = new Stack<>(); //store price and how long past it has lower value.
    public static int[] StockSpanner(int [] stockPrices) {

        int i = 0;
        Stack<Integer> stockSpan = new Stack<>();
        int [] span = new int[stockPrices.length];
        for(int k=0; k<span.length; ++k) span[k] = 0;

        while(i<stockPrices.length) {
            if(stockSpan.isEmpty() || stockPrices[i] < stockPrices[stockSpan.peek()]) {
                stockSpan.push(i);
                ++i;
            }else{
                int top = stockSpan.pop();
                span[i] += (span[top] +1);
            }
        }
        for(int k=0; k<span.length; ++k) span[k] +=1;
        System.out.println(Arrays.toString(span));
        return span;
    }

    //REad this
    class StockSpanner {
        public Stack<Pair<Integer, Integer>> stockSpan = new Stack<>(); //store price and how long past it has lower value.
        //keep the stackSpan in decr order of price
        public StockSpanner() {

        }

        public int next(int price) {
            int days =1;
            while (!stockSpan.isEmpty() && price >= stockSpan.peek().getKey()){
                days += stockSpan.pop().getValue();
            }
            stockSpan.push(new Pair<>(price, days));
            return days;
        }
    }
//    public int next(int price) {
//        int days =1;
//        while (!stockSpan.isEmpty() && price >= stockSpan.peek().getKey()){//keep the stack strict desc order.
//
//            days += stockSpan.pop().getValue();
//
//        }
//        stockSpan.push(new Pair<>(price, days));
//        return days;
//    }
}

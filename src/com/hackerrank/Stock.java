package com.hackerrank;

public class Stock {

    /**
     * EASY
     * Say you have an array, A, for which the ith element is the price of a given stock on day i.
     * If you were only permitted to complete at most one transaction (i.e, buy one and sell one share of the stock),
     * design an algorithm to find the maximum profit. Note that you are going to buy only once and sell only once all throughout.
     *
     * Return the maximum possible profit.
     */
    public int maxProfit(final int[] A) {
        //keep track of min seen so far
        int min = Integer.MAX_VALUE;
        int profit = 0;
        for (int i=0; i< A.length; ++i){
            min = Math.min(min, A[i]);
            profit = Math.max(profit, A[i]-min);
        }
        return profit;
    }

    /**
     * You are given an integer array prices where prices[i] is the price of a given stock on the ith day.
     *
     * On each day, you may decide to buy and/or sell the stock. You can only hold at most one share of the stock at any time.
     * However, you can buy it then immediately sell it on the same day.
     *
     * Find and return the maximum profit you can achieve.
     * @param prices
     * @return
     */
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
}

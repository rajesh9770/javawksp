package com.hackerrank.stack;

import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Rajesh on 11/17/2017.
 *  Read stock span problem before attempting this.  http://www.geeksforgeeks.org/the-stock-span-problem/
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
        Stack<Data> smallerElements = new Stack<>();
        int days = 0;
        for(int i=0;i<p.length; ++i){
            if(smallerElements.isEmpty()){
                smallerElements.push(new Data(p[i], -1));
            }else{
                int leftNeighbour = smallerElements.peek().value;
                if(p[i] > leftNeighbour){//i-th plant dies on day 1
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
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] p = new int[n];
        for(int p_i = 0; p_i < n; p_i++){
            p[p_i] = in.nextInt();
        }
        int result = poisonousPlants(p);
        System.out.println(result);
        in.close();
    }
}

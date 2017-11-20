package com.hackerrank.queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by Rajesh on 11/19/2017.
 */
public class GasStationsOnCircle {

    public static  class GasStation {
        int gas;
        int starIdx;
        public GasStation(int gas, int idx){
            this.gas = gas;
            this.starIdx = idx;
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        int number = 0;
        Queue<GasStation> gasStations = new LinkedList<>();
        GasStation cGasStation = null;
        int gasInTank = 0;
        int startIdx = 0;

        for(int i=number; i<n; ++i){
            int gas = in.nextInt();
            int dist = in.nextInt();

            if(gasInTank + gas < dist){
                gasStations.add(new GasStation(gasInTank + gas -dist , startIdx));
                //reset
                startIdx = i+1;
                gasInTank = 0;
            }else{
                gasInTank = gasInTank + gas - dist;
            }
        }
        //add the last one, this one should provide starting index if gasInTank at this point is enough to cover previous shortage.
        gasStations.add(new GasStation(gasInTank,  startIdx));

        long shortage =0;
        for(int i=0; i< gasStations.size()-1; ++i){
            shortage += gasStations.remove().gas;
        }
        if(shortage + gasInTank >=0){
            System.out.println(startIdx);
        }else{
            System.out.println("-1");
        }
    }
}

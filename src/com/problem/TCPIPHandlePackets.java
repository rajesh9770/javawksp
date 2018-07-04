package com.problem;

import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * Implement read and getPacket of TCP/IP. getPacket will receive byte packet which starts at given offset.
 * Packets may arrive out of order.
 * Read returns the packets that are received so far.
 */

public class TCPIPHandlePackets {

    static NavigableMap<Integer, byte[]> packets = new TreeMap<>();
    static int currOffSet = 0;

    public static void getPacket(int offSet, byte[] packet){
        packets.put(offSet, packet);
    }

    public static byte[] read(){
        ArrayList<Byte> ret = new ArrayList<>();
        while(packets.containsKey(currOffSet)){
            byte[] bytes = packets.remove(currOffSet);
            for(int i=0; i<bytes.length; ++i){
                ret.add(bytes[i]);
            }
            currOffSet += bytes.length;
        }

        byte[] byteArray = new byte[ret.size()];
        IntStream.range(0, ret.size()).forEach(i -> byteArray[i] = ret.get(i));
        return byteArray;
    }

    public static void main(String[] args) {

    }
}

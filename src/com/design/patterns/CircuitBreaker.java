package com.design.patterns;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//https://martinfowler.com/bliki/CircuitBreaker.html
public class CircuitBreaker {
    private int failCount;
    private long lastFailTime; //time of last failure when a last call made to the remote service

    private int failCountThreshhold = 3;
    private long resetTimeout = 5*60*1000; //retry after 5 min

    //if circuit is closed or half-open, 3 consecutive attempts will be made. After third failed attempt,
    //no calls will be made to remote server till resetTimeout has passed. After that, one  attempt will be made and so on.

    private enum STATE {
        OPEN, CLOSED, HALF_OPEN
    }

    int simulator = 0; //for simulation

    public void makeCall() throws TimeoutException {
        STATE state = getState();
        //call in the half-open state results in a trial call,
        //which will either reset the breaker if successful or restart the timeout if not.
        if (state == STATE.HALF_OPEN || state == state.CLOSED) {
            try {
                // make actual network call
                if (simulator % 3 == 0) {
                    throw new TimeoutException();
                } else if (simulator % 2 == 0) {
                    throw new IOException();
                }
            } catch (TimeoutException | IOException e) {
                recordFailure();
            }
            //success
            reset();
            //return sucess;
        } else if (state == STATE.OPEN) { //do not make actual call, just return error
            throw new TimeoutException();
        }
    }

    private void recordFailure() {
        failCount++;
        lastFailTime = System.currentTimeMillis();
    }

    private void reset() {
        failCount = 0;
        lastFailTime = -1;
    }
    private STATE getState(){
        if( failCount >= failCountThreshhold
                && (lastFailTime > 0 && System.currentTimeMillis() -lastFailTime >= resetTimeout))
        {
            return STATE.HALF_OPEN;
        }else if (failCount >= failCountThreshhold) {
            return STATE.OPEN;
        }else {
            return STATE.CLOSED;
        }
    }

    public static void main(String[] args) {
        char c = '0';
        System.out.println((int)c);
    }
}

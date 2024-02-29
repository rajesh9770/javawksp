package com.hackerrank.strings;

import java.util.HashSet;
import java.util.Set;

public class TokenBucket {

    private int MAX_TOKENS;
    private long lastRequestTime = System.currentTimeMillis();
    private long tokensAvailable = 0;
    private long rate; //tokens per second


    public TokenBucket(long rate, int maxTokens) {
        this.rate = rate; // tokens/sec
        MAX_TOKENS = maxTokens;
        tokensAvailable = maxTokens;
    }

    synchronized boolean getToken() {

        // Divide by a 1000 to get granularity at the second level.
        long newTokens = ((System.currentTimeMillis() - lastRequestTime) / 1000) * rate;
        tokensAvailable += newTokens ;

        if (tokensAvailable > MAX_TOKENS) {
            tokensAvailable = MAX_TOKENS;
        }
        if (newTokens > 0) {
            lastRequestTime = System.currentTimeMillis(); //update the lastRequestTime as we have already added the tokens
        }

        if (tokensAvailable == 0) {
            return false;
        } else {
            tokensAvailable--;
            return true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Set<Thread> allThreads = new HashSet<Thread>();
        final TokenBucket tokenBucketFilter = new TokenBucket(10, 100);

        for (int i = 0; i < 10; i++) {

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < 1000; ++i) {
                        boolean token = tokenBucketFilter.getToken();
                        System.out.println("Thread " + Thread.currentThread().getName() + " " + token + " " + System.currentTimeMillis());
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.setName("Thread_" + (i + 1));
            allThreads.add(thread);
        }

        for (Thread t : allThreads) {
            t.start();
        }

        for (Thread t : allThreads) {
            t.join();
        }
    }

}

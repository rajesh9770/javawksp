package com.hackerrank.graphs.Trees;

//https://www.topcoder.com/community/competitive-programming/tutorials/binary-indexed-trees/
//Fenwick tree


/**
 * Statement:
 * There is an array consisting of n cards. Initially, each card is put on the table with its face down. There are two queries:
 *
 * T i j (switch the side of each card from index i to index j, inclusive –- each card with face down becomes with face up; each card with face up becomes with face down)
 *
 * Q i (output 0 if the i-th card is face down, otherwise output 1)
 * BIT can be used to find prefix sums as well as suffix sums. Following discusses prefix sum. update goes up and read goes down.
 */

/**
 * idx in fTree|covers|     steps to get cumulative sum
 * 0            0
 * 1            1           1, 0  -> (node, parent node)
 * 2            1-2         2, 0
 * 3            3           3, 2
 * 4            1-4         4, 0
 * 5            5           5, 4
 * 6            5-6         6, 4
 * 7            7           7, 6
 * 8            1-8         8, 0
 * 9            9           9, 8
 * 10           9-10        10, 8
 * 11           11          11, 10
 * 12           9-12        12, 8
 * 13           13          13, 12
 * 14           13-14       14, 12
 * 15           15          15, 14
 * 16           1-16        16, 0
 */

/**
 * For suffix sum, update goes down and read goes up. See CounteInversion
 *
 * See CountInversion in sort pkg.
 *
 * idx in fTree |    stores  |                  steps to suffix sums
 * 1                1                           1,2,4,8,16
 * 2                2,3                         2,4,8,16
 * 3                3                           3,4,8,16
 * 4                4,5,6,7                     4,8,16
 * 5                5                           5,6,8,16
 * 6                6,7                         6,7,8,16
 * 7                7                           7,8,16
 * 8                8,9,10,11,12,13,14,15       8,16
 * 9                9                           9,10,12,16
 * 10               10,11                       10,12,16
 * 11               11                          11,12,16
 * 12               12,13,12,15                 12,16
 * 13               13                          13,14,16
 * 14               14,15                       14,16
 * 15               15                          15,16
 * 16               16                          16
 *
 */
public class BinaryIndexedTree {
    int MaxIdx = 100; //maximum index that can have non-zero index
    int [] cards = new int[MaxIdx+1];
    int [] fTree = new int[MaxIdx+1];

    static int getHighestBitNumber(int n)
    {
        if (n == 0)
            return 0;

        int msb = 0;
        n = n / 2;

        while (n != 0) {
            n = n / 2;
            msb++;
        }

        return (1 << msb);
    }

    /**
     * Query of type 1, toggle cards from i to j.
     * @param i
     * @param j
     */
    void Tquery(int i, int j) {
        cards[i]++;
        cards[j+1]--;
    }

    void update() {
        for(int i=1; i<cards.length; ++i){
            update(i, cards[i]);
        }
    }

    void update(int idx, int val) {
        while (idx <= MaxIdx) {
            fTree[idx] += val;
            idx += (idx & -idx);
        }
    }

    int read(int idx) {
        int sum = 0;
        while (idx > 0) {
            sum += fTree[idx];
            idx -= (idx & -idx);
        }
        return sum;
    }


    // If in the tree exists more than one index with the same
    // cumulative frequency, this procedure will return
    // some of them

    // bitMask - initialy, it is the greatest bit of MaxIdx
    // bitMask stores the current interval that should be searched
    int find(int cumFre) {
        int idx = 0; // this variable will be the output
        int bitMask = getHighestBitNumber(MaxIdx) << 1;
        while (bitMask != 0) {
            int tIdx = idx + bitMask; // the midpoint of the current interval
            bitMask >>= 1; // halve the current interval
            if (tIdx > MaxIdx) // avoid overflow
                continue;
            if (cumFre == fTree[tIdx]) // if it is equal, simply return tIdx
                return tIdx;
            else if (cumFre > fTree[tIdx]) {
                // if the tree frequency "can fit" into cumFre,
                // then include it
                idx = tIdx; // update index
                cumFre -= fTree[tIdx]; // update the frequency for the next iteration
            }
        }
        if (cumFre != 0) // maybe the given cumulative frequency doesn't exist
            return -1;
        else
            return idx;
    }

    public static void main(String[] args) {
        BinaryIndexedTree tree = new BinaryIndexedTree();
        tree.Tquery(2, 10); //flip cards 2-10
        tree.Tquery(9, 11); //flip cards 9-11
        tree.Tquery(8, 12); //flip cards 8-12
        tree.Tquery(5, 9);  //flip cards 5-9


        tree.update();
        //2 3 4 5 6 7 8 9 10 11 12
        //F F F T T T F T F  T  F
        for(int i=2; i<=12; ++i) {
            System.out.println(i + " " + tree.read(i) % 2);
        }
    }

    public void testFloatingMedian(int [] nums, int windowSize){

    }

    long sumOfMedians(int[] nums, int N, int K) {
        int mid = (K+1)/2;
        long sum = 0;
        int i;

        for(i=0;i <N; ++i) {
            update(nums[i]+1,1); // in fTree fTree-index is the value of ith number and fTree-value is the frequency   
            if(i == K-1) sum += (find(mid)-1);
            if(i >= K) {
                update(nums[i-K]+1, -1);
                sum += (find(mid)-1);
            }
        }
        return sum;
    }
}

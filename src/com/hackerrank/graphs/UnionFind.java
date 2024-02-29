package com.hackerrank.graphs;
/******************************************************************************
 *
 *  union find with path compression.
 *
 ******************************************************************************/

import java.util.Scanner;

/**
 *  This implementation uses weighted quick union (by size) with full path compression.
 *  Initializing a data structure with <em>n</em> sites takes linear time.
 *  Afterwards, <em>union</em>, <em>find</em>, and <em>connected</em> take
 *  logarithmic time (in the worst case) and <em>count</em> takes constant
 *  time. Moreover, the amortized time per <em>union</em>, <em>find</em>,
 *  and <em>connected</em> operation has inverse Ackermann complexity.
 *  <p>
 */
public class UnionFind {
    private int[] parent;  // parent[i] = parent of i
    private int[] size;    // size[i] = number of sites in tree rooted at i; size of the component
    // Note: not necessarily correct if i is not a root node
    private int count;     // number of components

    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own
     * component.
     *
     * @param  n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public UnionFind(int n) {
        count = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * Returns the number of components.
     *
     * @return the number of components (between {@code 1} and {@code n})
     */
    public int count() {
        return count;
    }


    /**
     * Returns the component identifier for the component containing site {@code p}.
     *
     * @param  p the integer representing one site
     * @return the component identifier for the component containing site {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
    public int find(int p) {
        validate(p);
        int root = p;
        while (root != parent[root])
            root = parent[root];
        while (p != root) {//marks the intermediate node's parent to root to save the time next time
            int newp = parent[p];
            parent[p] = root;
            p = newp;
        }
        return root;
    }

    public int find2(int p) { //only set node's parent to node'parent'parent
        validate(p);
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];    // path compression by halving
            p = parent[p];
        }
        return p;
    }

    /**
     * Returns true if the the two sites are in the same component.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @return {@code true} if the two sites {@code p} and {@code q} are in the same component;
     *         {@code false} otherwise
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }

    /**
     * Merges the component containing site {@code p} with the
     * the component containing site {@code q}.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }

    /**
     * Reads in a sequence of pairs of integers (between 0 and n-1) from standard input,
     * where each integer represents some site;
     * if the sites are in different components, merge the two components
     * and print the pair to standard output.
     *
     * @param args the command-line arguments
     */
    public static void main1(String[] args) {
        Scanner StdIn = new Scanner(System.in);
        int n = StdIn.nextInt();
        UnionFind uf = new UnionFind(n);
        while (!StdIn.hasNext()) {
            int p = StdIn.nextInt();
            int q = StdIn.nextInt();
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
            System.out.println(p + " " + q);
        }
        System.out.println(uf.count() + " components");
    }

    /**
     * There are n couples sitting in 2n seats arranged in a row and want to hold hands.
     *
     * The people and seats are represented by an integer array row where row[i] is the ID of the person sitting in the ith seat.
     * The couples are numbered in order, the first couple being (0, 1), the second couple being (2, 3),
     * and so on with the last couple being (2n - 2, 2n - 1).
     *
     * Return the minimum number of swaps so that every couple is sitting side by side.
     * A swap consists of choosing any two people, then they stand up and switch seats.
     *
     * Think of a couple (2i, 2i+1) as couple i (0<=i<=n-1). Couple's form a node in the graph with N vertices N=# of couples.
     * NOw look at the couples seating at 2i and 2i+1, if these couples are different, then add an edge between those couples.
     * If all couples were sited correctly, holding hands, then this graph won;t have any edges and this connected components will be N.
     *
     * Notice that in this question, every vertex only has at most 2 degrees,
     * and there is only one way to decompose our graph into cycles(which is very obvious) so that the problem could be reduced into
     * #swap = #(vertex) - #num(components)
     */

    public static int minSwapsCouples(int[] row) {
        int noOfCouples = row.length/2;
        UF uf = new UF(noOfCouples);
        //identify the couple (2i, 2i+1) as i-th couple
        //we only need to look right
        for(int seat=0; seat<noOfCouples; ++seat){
            int a = row[2*seat];
            int b = row[2*seat+1];

            uf.union(a/2,b/2); //when merging send the representative of couple a and b which a/2 and b/2
        }
        return noOfCouples - uf.count;
    }

    private static class UF{
        int noOfComps;
        int [] parent; //designated parent for i-th component
        int [] size;  //size of the i-th component
        int count; //# of connected components
        public UF(int n){
            this.noOfComps = n;
            this.parent = new int[n];
            this.size = new int[n];
            for(int i=0; i<n; ++i){
               parent[i] = i;
               size[i] = 1;
            }
            count = n;
        }

        int find(int i){
            int tmp = i;
            while(parent[tmp] != tmp){
                tmp = parent[tmp];
            }
            parent[i] = tmp;
            return tmp;
        }

        int union(int a, int b){
            int parenta = find(a);
            int parentb = find(b);
            if(parenta != parentb){//merge the components

                if(size[a] < size[b]){
                    //currently parenta' parent is parenta, we need to point to parentb.
                    parent[parenta] = parentb;
                }else{
                    parent[parentb] = parenta;
                }
                int newsize = size[a] + size[b];
                size[a] = newsize;
                size[b] = newsize;

                count--; //decrement the # of connected components as we have merged two components
            }
            return size[a];
        }
    }

    public static void main(String[] args) {
        System.out.println(minSwapsCouples(new int[]{11,5,10,13,4,1,3,7,8,6,12,9,0,2}));
    }
}

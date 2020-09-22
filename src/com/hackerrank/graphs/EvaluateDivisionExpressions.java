package com.hackerrank.graphs;

import java.util.*;

/**
 * Equations are given in the format A / B = k, where A and B are variables represented as strings, and k is a real number (floating point number). Given some queries, return the answers. If the answer does not exist, return -1.0.
 *
 * Example:
 * Given a / b = 2.0, b / c = 3.0.
 * queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? .
 * return [6.0, 0.5, -1.0, 1.0, -1.0 ].
 */
public class EvaluateDivisionExpressions {

    public static void main(String[] args) {
        EvaluateDivisionExpressions evaluateDivisionExpressions = new EvaluateDivisionExpressions();
        int entries = 2;
        List<List<String>> equations = new ArrayList<>();
        double[] values = new double[entries];

        ArrayList<String> q1 = new ArrayList<>();
        q1.add("a");
        q1.add("b");
        values[0] = 2.0d;
        equations.add(q1);

        ArrayList<String> q2 = new ArrayList<>();
        q2.add("b");
        q2.add("c");
        values[1] = 3.0d;
        equations.add(q2);

        List<List<String>> queries = new ArrayList<>();
        ArrayList<String> q = new ArrayList<>();
        q.add("a");
        q.add("c");
        queries.add(q);
        double[] doubles = evaluateDivisionExpressions.calcEquation(equations, values, queries);
        System.out.println(doubles[0]);
    }


    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Map<String, Double>> graph = new HashMap<>();

        for(int i=0; i< values.length; ++i){
            String numerator = equations.get(i).get(0);
            String denominator = equations.get(i).get(1);
            double quotient = values[i];
            graph.putIfAbsent(numerator, new HashMap<>());
            graph.putIfAbsent(denominator, new HashMap<>());
            graph.get(numerator).put(denominator, quotient);
            graph.get(denominator).put(numerator, 1d/quotient);
        }

        double[] val = new double[queries.size()];
        int i=0;
        for (List<String> q : queries){
            Set<String> visited  = new HashSet<>();
            val[i++] = evaluate(q.get(0), q.get(1), graph, visited, 1);
        }
        return val;
    }

    public double evaluate(String numerator, String denominator, Map<String, Map<String, Double>> graph, Set<String> visited, double val){
        if (graph.containsKey(numerator)) {
            if (numerator == denominator) return  1d;
            Map<String, Double> stringDoubleMap = graph.get(numerator);
            if (stringDoubleMap.containsKey(denominator)) {
                return val*stringDoubleMap.get(denominator);
            }
            visited.add(numerator);
            for (String intermediateDenominator: stringDoubleMap.keySet()) {
                if (!visited.contains(intermediateDenominator)) {
                    double evaluate = evaluate(intermediateDenominator, denominator, graph, visited, val * stringDoubleMap.get(intermediateDenominator));
                    if (evaluate != -1d) return evaluate;
                }
            }
        }
        return -1d;
    }
}

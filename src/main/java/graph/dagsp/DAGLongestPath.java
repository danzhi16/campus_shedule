package graph.dagsp;
import graph.*;
import graph.topo.TopoSort;
import java.util.*;
public class DAGLongestPath {
    public static double[] longest(Graph g, int start) {
        List<Integer> order = TopoSort.sort(g);
        double[] d = new double[g.size];
        Arrays.fill(d, Double.NEGATIVE_INFINITY);
        d[start] = 0;
        for (int u : order)
            if (d[u] != Double.NEGATIVE_INFINITY)
                for (Edge e : g.edges.get(u))
                    d[e.end] = Math.max(d[e.end], d[u] + e.cost);
        return d;
    }
}

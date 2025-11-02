package graph.dagsp;
import graph.*;
import graph.topo.TopoSort;
import java.util.*;
public class DAGShortest {
    public static double[] shortest(Graph g,int start){
        List<Integer> order=TopoSort.sort(g);
        double[] d=new double[g.size];
        Arrays.fill(d,Double.POSITIVE_INFINITY);
        d[start]=0;
        for(int u:order)
            if(d[u]!=Double.POSITIVE_INFINITY)
                for(Edge e:g.edges.get(u))
                    d[e.end]=Math.min(d[e.end],d[u]+e.cost);
        return d;
    }
}

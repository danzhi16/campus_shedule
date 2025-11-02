package graph.topo;
import graph.*;
import java.util.*;
public class TopoSort {
    public static List<Integer> sort(Graph g){
        int[] in=new int[g.size];
        for(var list:g.edges)
            for(Edge e:list)
                in[e.end]++;
        Queue<Integer> q=new ArrayDeque<>();
        for(int i=0;i<g.size;i++)
            if(in[i]==0)q.add(i);
        List<Integer> ord=new ArrayList<>();
        while(!q.isEmpty()){
            int v=q.poll();
            ord.add(v);
            for(Edge e:g.edges.get(v))
                if(--in[e.end]==0)q.add(e.end);
        }
        return ord;
    }
}

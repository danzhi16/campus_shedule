package graph;
import java.util.*;
public class Graph {
    public final int size;
    public final List<List<Edge>> edges;
    public Graph(int size){
        this.size=size;
        edges=new ArrayList<>();
        for(int i=0;i<size;i++)edges.add(new ArrayList<>());
    }
    public void addEdge(int a,int b,int w){
        edges.get(a).add(new Edge(a,b,w));
    }
    public Graph reversed(){
        Graph g=new Graph(size);
        for(int a=0;a<size;a++)
            for(Edge e:edges.get(a))
                g.addEdge(e.end,e.start,e.cost);
        return g;
    }
}

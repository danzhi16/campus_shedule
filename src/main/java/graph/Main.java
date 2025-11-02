package graph;
import graph.scc.scc;
import graph.topo.TopoSort;
import graph.dagsp.*;
import org.json.*;
import java.nio.file.*;
import java.util.*;
public class Main {
    public static void main(String[] args)throws Exception{
        String json=Files.readString(Paths.get("src/main/resources/large1.json"));
        JSONObject obj=new JSONObject(json);
        int n=obj.getInt("n");
        JSONArray arr=obj.getJSONArray("edges");
        int src=obj.getInt("source");
        Graph g=new Graph(n);
        for(int i=0;i<arr.length();i++){
            JSONObject e=arr.getJSONObject(i);
            g.addEdge(e.getInt("u"),e.getInt("v"),e.getInt("w"));
        }
        System.out.println("SCC:");
        var scc=new scc(g).run();
        for(int i=0;i<scc.size();i++)
            System.out.println("Component "+i+": "+scc.get(i));
        System.out.println("\nTopological order:");
        System.out.println(TopoSort.sort(g));
        System.out.println("\nShortest paths:");
        System.out.println(Arrays.toString(DAGShortest.shortest(g,src)));
        System.out.println("\nLongest paths:");
        System.out.println(Arrays.toString(DAGLongest.longest(g,src)));
    }
}

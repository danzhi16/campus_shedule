package graph.scc;
import graph.*;
import java.util.*;
public class scc {
    private final Graph g;
    private boolean[] vis;
    private Deque<Integer> st=new ArrayDeque<>();
    public List<List<Integer>> comps=new ArrayList<>();
    public scc(Graph g){this.g=g;}
    public List<List<Integer>> run(){
        vis=new boolean[g.size];
        for(int i=0;i<g.size;i++)if(!vis[i])dfs1(i);
        Graph r=g.reversed();
        Arrays.fill(vis,false);
        while(!st.isEmpty()){
            int v=st.pop();
            if(!vis[v]){
                List<Integer> c=new ArrayList<>();
                dfs2(r,v,c);
                comps.add(c);
            }
        }
        return comps;
    }
    private void dfs1(int v){
        vis[v]=true;
        for(Edge e:g.edges.get(v))
            if(!vis[e.end])dfs1(e.end);
        st.push(v);
    }
    private void dfs2(Graph r,int v,List<Integer> c){
        vis[v]=true;
        c.add(v);
        for(Edge e:r.edges.get(v))
            if(!vis[e.end])dfs2(r,e.end,c);
    }
}

import graph.*;
import graph.topo.TopoSort;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class TopoTests {
    @Test
    void testTopoOrder(){
        Graph g=new Graph(4);
        g.addEdge(0,1,1);
        g.addEdge(1,2,1);
        g.addEdge(0,3,1);
        var o=TopoSort.sort(g);
        assertTrue(o.indexOf(0)<o.indexOf(1));
        assertTrue(o.indexOf(1)<o.indexOf(2));
    }
}

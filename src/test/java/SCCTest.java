import graph.*;
import graph.scc.scc;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class SCCTest {
    @Test
    void testSimpleCycle(){
        Graph g=new Graph(3);
        g.addEdge(0,1,1);
        g.addEdge(1,2,1);
        g.addEdge(2,0,1);
        scc s=new scc(g);
        var c=s.run();
        assertEquals(1,c.size());
    }
}

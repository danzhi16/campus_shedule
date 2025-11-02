package graph;
import graph.scc.scc;
import graph.topo.TopoSort;
import graph.dagsp.*;
import org.json.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.InputStream;
import java.util.*;
public class Main {
    public static void main(String[] args) throws Exception {
        String pathArg = (args.length > 0) ? args[0] : null;
        String json = null;
        if (pathArg != null) {
            Path p = Paths.get(pathArg);
            if (Files.exists(p) && Files.isRegularFile(p)) {
                json = Files.readString(p, StandardCharsets.UTF_8);
            }
        }
        String[] candidates = {
                "src/main/resources/large_1.json",
                "src/main/resources/large1.json",
                "tasks.json"
        };
        if (json == null) {
            for (String c : candidates) {
                Path p = Paths.get(c);
                if (Files.exists(p) && Files.isRegularFile(p)) {
                    json = Files.readString(p, StandardCharsets.UTF_8);
                    break;
                }
            }
        }
        if (json == null) {
            String[] cp = {"/large_1.json", "/large1.json", "/tasks.json"};
            for (String r : cp) {
                try (InputStream is = Main.class.getResourceAsStream(r)) {
                    if (is != null) {
                        json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                        break;
                    }
                }
            }
        }

        if (json == null) {
            System.err.println("No JSON input found. Provide a path or add `src/main/resources/large_1.json` or `tasks.json`.");
            return;
        }
        JSONObject obj = new JSONObject(json);
        int n = obj.getInt("n");
        JSONArray edges = obj.getJSONArray("edges");
        int source = obj.getInt("source");

        Graph g = new Graph(n);
        for (int i = 0; i < edges.length(); i++) {
            JSONObject e = edges.getJSONObject(i);
            g.addEdge(e.getInt("u"), e.getInt("v"), e.getInt("w"));
        }
        System.out.println("SCC");
        var comps = new scc(g).run();
        for (int i = 0; i < comps.size(); i++)
            System.out.println("Component " + i + ": " + comps.get(i));

        System.out.println("\nTopological Sort");
        System.out.println(TopoSort.sort(g));

        System.out.println("\nShortest Paths");
        System.out.println(Arrays.toString(DAGShortestPath.shortest(g, source)));

        System.out.println("\nLongest Paths");
        System.out.println(Arrays.toString(DAGLongestPath.longest(g, source)));
    }
}

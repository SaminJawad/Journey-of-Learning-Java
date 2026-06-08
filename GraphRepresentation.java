import java.util.ArrayList;
import java.util.List;

public class GraphRepresentation {
    static class Graph {
        int vertices;
        List<List<Integer>> adjList;

        Graph(int vertices) {
            this.vertices = vertices;
            adjList = new ArrayList<>();
            for (int i = 0; i < vertices; i++)
                adjList.add(new ArrayList<>());
        }

        void addEdge(int src, int dest) {
            adjList.get(src).add(dest);
            adjList.get(dest).add(src);
        }

        void print() {
            for (int i = 0; i < vertices; i++)
                System.out.println("Vertex " + i + " -> " + adjList.get(i));
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 4);
        g.addEdge(3, 4);
        g.print();
    }
}
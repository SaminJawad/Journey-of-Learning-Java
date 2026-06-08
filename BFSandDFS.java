import java.util.*;

public class BFSandDFS {
    static Map<Integer, List<Integer>> graph = new HashMap<>();

    static void addEdge(int u, int v) {
        graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
        graph.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
    }

    static void bfs(int start) {
        Set<Integer> visited = new LinkedHashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        System.out.println("BFS: " + visited);
    }

    static void dfs(int node, Set<Integer> visited) {
        visited.add(node);
        for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            if (!visited.contains(neighbor))
                dfs(neighbor, visited);
        }
    }

    public static void main(String[] args) {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(1, 3);
        addEdge(2, 4);
        addEdge(3, 4);

        bfs(0);

        Set<Integer> visited = new LinkedHashSet<>();
        dfs(0, visited);
        System.out.println("DFS: " + visited);
    }
}
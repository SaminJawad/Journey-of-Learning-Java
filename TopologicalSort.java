import java.util.*;

public class TopologicalSort {
    static Map<Integer, List<Integer>> graph = new HashMap<>();
    static int vertices = 6;

    static void addEdge(int u, int v) {
        graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
    }

    static void dfs(int node, boolean[] visited, Stack<Integer> stack) {
        visited[node] = true;
        for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            if (!visited[neighbor])
                dfs(neighbor, visited, stack);
        }
        stack.push(node);
    }

    static void topologicalSort() {
        boolean[] visited = new boolean[vertices];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < vertices; i++)
            if (!visited[i])
                dfs(i, visited, stack);
        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty())
            result.add(stack.pop());
        System.out.println("Topological Order: " + result);
    }

    public static void main(String[] args) {
        addEdge(5, 2);
        addEdge(5, 0);
        addEdge(4, 0);
        addEdge(4, 1);
        addEdge(2, 3);
        addEdge(3, 1);
        topologicalSort();
    }
}
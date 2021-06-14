package Graph;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class GraphBfs {
    static class Edge {
        int src;
        int nbr;

        Edge(int src, int nbr) {
            this.src = src;
            this.nbr = nbr;
        }
    }
    static class Pair {
        int v;
        String psf;
        public Pair(int v, String psf) {
            this.v = v;
            this.psf = psf;
        }
    }
    public static void bfs(ArrayList<Edge>[] graph, int src) {
        boolean[] visited = new boolean[graph.length];
        Queue<Pair> queue = new ArrayDeque<>();
        queue.add(new Pair(src, "" + src));
        while(!queue.isEmpty()) {
            Pair node = queue.remove();
            if(visited[node.v]) continue;
            visited[node.v] = true;
            System.out.println(node.v + "@" + node.psf);
            for(Edge edge : graph[node.v]) {
                if(!visited[edge.nbr]) {
                    queue.add(new Pair(edge.nbr, node.psf + edge.nbr));
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int vtces = Integer.parseInt(br.readLine());
        ArrayList<Edge>[] graph = new ArrayList[vtces];
        for (int i = 0; i < vtces; i++) {
            graph[i] = new ArrayList<>();
        }

        int edges = Integer.parseInt(br.readLine());
        for (int i = 0; i < edges; i++) {
            String[] parts = br.readLine().split(" ");
            int v1 = Integer.parseInt(parts[0]);
            int v2 = Integer.parseInt(parts[1]);
            graph[v1].add(new Edge(v1, v2));
            graph[v2].add(new Edge(v2, v1));
        }

        int src = Integer.parseInt(br.readLine());
        bfs(graph, src);

    }
}
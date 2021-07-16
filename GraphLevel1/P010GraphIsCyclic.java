package GraphLevel1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class P010GraphIsCyclic {
    static class Edge {
        int src;
        int nbr;
        int wt;

        Edge(int src, int nbr, int wt) {
            this.src = src;
            this.nbr = nbr;
            this.wt = wt;
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
            int wt = Integer.parseInt(parts[2]);
            graph[v1].add(new Edge(v1, v2, wt));
            graph[v2].add(new Edge(v2, v1, wt));
        }

        boolean[] visited = new boolean[graph.length];
        boolean check = false;
        for(int i=0; i<vtces; i++) {
            if(!visited[i]) {
                check = isCyclic(graph, i, visited);
                if(check) break;
            }

        }
        System.out.println(check);
    }
    public static boolean isCyclic(ArrayList<Edge>[] graph, int src, boolean[] visited) {

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(src);
        while(!queue.isEmpty()) {
            Integer node = queue.remove();
            if(visited[node]) return true;
            visited[node] = true;
            for(Edge edge : graph[src]) {
                if(!visited[edge.nbr]) {
                    queue.add(edge.nbr);
                }
            }
        }
        return false;
    }

}
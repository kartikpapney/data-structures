package GraphLevel1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GraphPrintAllPath {
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

        int src = Integer.parseInt(br.readLine());
        int dest = Integer.parseInt(br.readLine());

        // write all your codes here
        printAllPaths(graph, src, dest, new boolean[vtces + 1], "");
    }
    public static void printAllPaths(ArrayList<Edge>[] graph, int src, int dest, boolean[] visited, String asf) {
        if(src == dest) {
            System.out.println(asf+dest);
            return;
        }
        visited[src] = true;
        for(Edge edge : graph[src]) {
            if(!visited[edge.nbr]) {
                printAllPaths(graph, edge.nbr, dest, visited, asf+src);
            }
        }
        visited[src] = false;
    }


}
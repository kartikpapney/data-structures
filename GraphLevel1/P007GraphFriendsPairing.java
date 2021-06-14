package GraphLevel1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class P007GraphFriendsPairing {

    static class Edge {
        int src;
        int nbr;

        Edge(int src, int nbr) {
            this.src = src;
            this.nbr = nbr;
        }
    }

    public static int getConnectedComponents(ArrayList<Edge>[] graph, int src, boolean[] visited) {
        visited[src] = true;
        int count = 1;
        for(Edge edge : graph[src]) {
            if(!visited[edge.nbr]) {
                count += getConnectedComponents(graph, edge.nbr, visited);
            }
        }
        return count;
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

        ArrayList<Integer> comps = new ArrayList<>();
        boolean[] visited = new boolean[vtces];
        for(int i=0; i<vtces; i++) {
            if(!visited[i]) {
                comps.add(getConnectedComponents(graph, i, visited));
            }
        }
        int cnt = 0;
        for(int i=0; i<comps.size()-1; i++) {
            for(int j=i+1; j<comps.size(); j++) {
                cnt+=comps.get(i)*comps.get(j);
            }
        }
        System.out.println(cnt);
    }

}
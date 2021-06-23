package LeetcodeGraph;

import java.lang.reflect.Array;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Medium {

    /************************  959. Regions Cut By Slashes (Union Find Remaining)  ************************************/
    public void dfs(boolean[][] graph, int i, int j) {
        if(i<0 || j <0 || i>=graph.length || j>=graph[0].length || graph[i][j]) return;
        graph[i][j] = true;
        dfs(graph, i+1, j);
        dfs(graph, i, j+1);
        dfs(graph, i-1, j);
        dfs(graph, i, j-1);
    }
    public int regionsBySlashes(String[] grid) {
        int n = grid.length;
        int m = grid[0].length();
        boolean[][] graph = new boolean[3*n][3*m];
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(grid[i].charAt(j) == '/') {
                    graph[3*i][3*j+2] = graph[3*i+1][3*j+1] = graph[3*i+2][3*j] = true;
                } else if (grid[i].charAt(j) == '\\') {
                    graph[3*i][3*j] = graph[3*i+1][3*j+1] = graph[3*i+2][3*j+2] = true;
                }
            }
        }

        int count = 0;
        for(int i=0; i<3*n; i++) {
            for(int j=0; j<3*m; j++) {
                if(!graph[i][j]) {
                    count++;
                    dfs(graph, i, j);
                }
            }
        }
        return count;
    }


    /************************  841. Keys and Rooms  ************************************/

//    @4ms Creating Separate Graph for understanding
    static class Edge {
        int src;
        int nbr;

        Edge(int src, int nbr) {
            this.src = src;
            this.nbr = nbr;
        }
    }

    public int dfs(ArrayList<Edge>[] graph, int i, boolean[] visited) {
        int ans = 1;
        visited[i] = true;
        for(int j=0; j<graph[i].size(); j++) {
            if(!visited[graph[i].get(j).nbr]) {
                ans+=dfs(graph, graph[i].get(j).nbr, visited);
            }
        }
        return ans;
    }

    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        ArrayList<Edge>[] graph = new ArrayList[rooms.size()];
        for(int i=0; i<rooms.size(); i++) {
            graph[i]=new ArrayList<>();
        }
        for(int i=0; i<rooms.size(); i++) {
            for(int j=0; j<rooms.get(i).size(); j++) {
                graph[i].add(new Edge(i, rooms.get(i).get(j)));
            }
        }
        int ans = dfs(graph, 0, new boolean[rooms.size()]);
        return ans==rooms.size();
    }


//    Straight Forward Approach

//    public int dfs(List<List<Integer>> graph, int i, boolean[] visited) {
//        int ans = 1;
//        visited[i] = true;
//        for(int j=0; j<graph.get(i).size(); j++) {
//            if(!visited[graph.get(i).get(j)]) {
//                ans+=dfs(graph, graph.get(i).get(j), visited);
//            }
//        }
//        return ans;
//    }
//
//    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
//        int ans = dfs(rooms, 0, new boolean[rooms.size()]);
//        return ans==rooms.size();
//    }


    /************************  778. Swim in Rising Water  ************************************/

    public int swimInWater(int[][] grid) {
        int n = grid.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        pq.add(new int[]{0, 0, grid[0][0]});
        while(!pq.isEmpty()) {
            int[] reached = pq.poll();
            for(int i=0; i<dirs.length; i++) {
                int newi = reached[0]+dirs[i][0];
                int newj = reached[1]+dirs[i][1];
                if(newi<0 || newj<0 || newi>=grid.length || newj>=grid[0].length) continue;
                if(!visited[newi][newj]) {
                    visited[newi][newj] = true;
                    int newMax = Math.max(grid[newi][newj], reached[2]);
                    if(newi == n-1 && newj == n-1) return newMax;
                    pq.add(new int[]{newi, newj, grid[newi][newj]});
                }
            }
        }
        return 0;
    }
}

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

    /************************  130. Surrounded Regions ************************************/
    public void convertOToZ(char[][] board, int i, int j) {
        if(i == board.length || i == -1 || j == board[0].length || j == -1 || board[i][j] != 'O') return;
        board[i][j] = 'Z';
        convertOToZ(board, i-1, j);
        convertOToZ(board, i, j-1);
        convertOToZ(board, i+1, j);
        convertOToZ(board, i, j+1);
    }

    public void solve(char[][] board) {
        for(int i=0; i<board.length; i++) {
            convertOToZ(board, i, 0);
            convertOToZ(board, i, board[0].length-1);
        }
        for(int j=0; j<board[0].length; j++) {
            convertOToZ(board, 0, j);
            convertOToZ(board, board.length - 1, j);
        }
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[0].length; j++) {
                if(board[i][j] == 'O') board[i][j] = 'X';
                if(board[i][j] == 'Z') board[i][j] = 'O';
            }
        }
    }

    /************************  860 · Number of Distinct Islands (Lintcode) ************************************/
    public void find(int[][] grid, int i, int j, StringBuilder strb) {
        if(i<0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == 0) return;
        grid[i][j] = 0;
        strb.append('R');
        find(grid, i, j+1, strb);
        strb.append('D');
        find(grid, i+1, j, strb);
        strb.append('U');
        find(grid, i-1, j, strb);
        strb.append('L');
        find(grid, i, j-1, strb);
        strb.append('B');
    }
    public int numberofDistinctIslands(int[][] grid) {
        HashSet<String> set = new HashSet<>();
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[0].length; j++) {
                if(grid[i][j] == 1) {
                    StringBuilder res = new StringBuilder();
                    find(grid, i, j, res);
                    set.add(res.toString());
                }
            }
        }
        return set.size();
    }
    /************************  994. Rotting Oranges ************************************/
    public int orangesRotting(int[][] arr) {
        int fresh = 0;
        int[][] dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Queue<int[]> q = new ArrayDeque<>();
        for(int i=0; i<arr.length; i++) {
            for(int j=0; j<arr[0].length; j++) {
                if(arr[i][j] == 2) q.add(new int[]{i, j});
                if(arr[i][j] == 1) fresh++;
            }
        }
        if(q.isEmpty()) {
            if(fresh == 0) return 0;
            return -1;
        }
        int len = -1;
        while(!q.isEmpty()) {
            int size = q.size();
            while(size-- != 0) {
                int[] v = q.remove();
                for(int i=0; i<dir.length; i++) {
                    int x = v[0]+dir[i][0];
                    int y = v[1]+dir[i][1];
                    if(x >= 0 && y >= 0 && x < arr.length && y < arr[0].length && arr[x][y] == 1) {
                        arr[x][y] = 2;
                        q.add(new int[]{x, y});
                    }
                }
            }
            len++;
        }
        for(int i=0; i<arr.length; i++) {
            for(int j=0; j<arr[i].length; j++) {
                if(arr[i][j] == 1) return -1;
            }
        }
        return len;
    }

    /************************ 477. Total Hamming Distance ************************************/
    public int totalHammingDistance(int[] nums) {
        int res = 0, cnt0=0, cnt1=0;
        for(int i=0; i<32; i++) {
            cnt0=0;
            cnt1=0;
            int n = (1<<i);
            for(int j=0; j<nums.length; j++) {
                if((nums[j]&n) == 0) cnt0++;
                else cnt1++;
            }
            res+=(cnt0*cnt1);
        }
        return res;
    }
    /************************ 304. Range Sum Query 2D - Immutable ************************************/
    class NumMatrix {
        private int[][] grid;
        public NumMatrix(int[][] matrix) {
            int m = matrix.length, n = matrix[0].length;
            this.grid = new int[m+1][n+1];
            for(int i=1; i<grid.length; i++) {
                for(int j=1; j<grid[0].length; j++) {
                    grid[i][j]=matrix[i-1][j-1]+grid[i-1][j]+grid[i][j-1]-grid[i-1][j-1];
                }
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            return grid[row2+1][col2+1]-grid[row2+1][col1]-grid[row1][col2+1]+grid[row1][col1];
        }
    }
    /************************ 207. Course Schedule ************************************/
    public int countPalindromicSubsequence(String s) {
        HashSet<String> res = new HashSet<>();
        for(char c='a'; c<='z'; c++) {
            int start = -1;
            int end = -1;
            for(int i=0; i<s.length(); i++) {
                if(s.charAt(i) == c) {
                    start = i;
                    break;
                }
            }
            for(int i=s.length()-1; i>=0; i--) {
                if(s.charAt(i) == c) {
                    end = i;
                    break;
                }
            }
            for(int i=start+1; i<end-1; i++) {
                res.add(String.valueOf(c)+s.charAt(i)+c);
            }
        }
        return res.size();
    }

    /************************ 207. Course Schedule(DFS) ************************************/
    public boolean canFinish(int numCourses, int[][] pre) {
        ArrayList[] arr = new ArrayList[numCourses];
        for(int i=0; i<arr.length; i++) {
            arr[i] = new ArrayList();
        }
        for(int i=0; i<pre.length; i++) {
            arr[pre[i][0]].add(pre[i][1]);
        }
        boolean[] visited = new boolean[numCourses], revisited = new boolean[numCourses];
        for(int i=0; i<numCourses; i++) {
            if(!visited[i]) {
                if(isCyclic(arr, i, visited, revisited)) return false;
            }
        }
        return true;
    }
    public boolean isCyclic(ArrayList[] arr, int src, boolean[] visited, boolean[] revisited) {
        if(revisited[src]) return true;
        if(visited[src]) return false;
        visited[src] = true;
        revisited[src] = true;
        for(int i=0; i<arr[src].size(); i++) {
            if(isCyclic(arr, (Integer)arr[src].get(i), visited, revisited)) {
                return true;
            }
        }
        revisited[src] = false;
        return false;
    }
    /************************ 785. Is Graph Bipartite? ************************************/
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] visited = new int[n];
        Arrays.fill(visited, -1);
        for(int i=0; i<n; i++) {
            if(visited[i] == -1) {
                if(!isBiartite(graph, i, visited)) return false;
            }
        }
        return true;
    }
    public boolean isBiartite(int[][] graph, int src, int[] visited) {
        Queue<Integer> q = new ArrayDeque<>();
        q.add(src);
        int color = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            while(size-- != 0) {
                int rem = q.remove();
                if(visited[rem] != -1) {
                    continue;
                }
                visited[rem] = color;
                for(int i=0; i<graph[rem].length; i++) {
                    int nbr = (int)graph[rem][i];
                    if(visited[nbr] == -1) {
                        q.add(nbr);
                    } else {
                        if(visited[nbr] == color) return false;
                    }
                }
            }
            color = color == 0?1:0;
        }
        return true;
    }

    private boolean isCycleThere(ArrayList[] graph, int src, boolean[] visited, boolean[] revisited) {
        if(revisited[src]) return true;
        if(visited[src]) return false;
        visited[src] = true;
        revisited[src] = true;
        for(int i=0; i<graph[src].size(); i++) {
            if(isCycleThere(graph, (int)graph[src].get(i), visited, revisited)) {
                return true;
            }
        }
        revisited[src] = false;
        return false;
    }

    /************************ 210. Course Schedule II ************************************/
    private void topologicalSort(ArrayList[] graph, int src, boolean[] visited, Stack<Integer> st) {
        visited[src] = true;
        for(int i=0; i<graph[src].size(); i++) {
            int nbr = (int)graph[src].get(i);
            if(!visited[nbr]) topologicalSort(graph, nbr, visited, st);
        }
        st.add(src);
    }
    public int[] findOrder(int numCourses, int[][] pre) {
        ArrayList[] graph = new ArrayList[numCourses];
        for(int i=0; i<graph.length; i++) graph[i] = new ArrayList();
        for(int i=0; i<pre.length; i++) {
            graph[pre[i][0]].add(pre[i][1]);
        }
        boolean[] visited = new boolean[numCourses], revisited = new boolean[numCourses];
        for(int i=0; i<numCourses; i++) {
            if(!visited[i]) {
                if(isCycleThere(graph, i, visited, revisited)) {
                    return new int[0];
                }
            }
        }
        Stack<Integer> st = new Stack<>();
        visited = new boolean[numCourses];
        for(int i=0; i<numCourses; i++) {
            if(!visited[i]){
                topologicalSort(graph, i, visited, st);
            }
        }
        int[] res = new int[st.size()];
        int idx = st.size()-1;
        while(idx >= 0) {
            res[idx--] = st.pop();
        }
        return res;
    }


    public int dfs(List<List<Integer>> graph, boolean[] visited, int src) {
        int count = 0;
        visited[src] = true;
        for(int i=0; i<graph.get(src).size(); i++) {
            int to = graph.get(src).get(i);
            if(!visited[Math.abs(to)]) {
                count += dfs(graph, visited, Math.abs(to)) + (to>0?1:0);
            }
        }
        return count;
    }
    public int minReorder(int n, int[][] arr) {
        List<List<Integer>> graph = new ArrayList<>();
        boolean[] visited = new boolean[n];
        for(int i=0; i<n; i++) {
            graph.add(new ArrayList<>());
        }
        for(int i=0; i<arr.length; i++) {
            graph.get(arr[i][0]).add(arr[i][1]);
            graph.get(arr[i][1]).add(-arr[i][0]);
        }
        return dfs(graph, visited, 0);
    }

}

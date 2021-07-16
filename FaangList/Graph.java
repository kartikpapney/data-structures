package FaangList;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Graph {
    /************************ Chef and Reversing  ************************************/
    // https://www.codechef.com/problems/REVERSE

//    static class Edge {
//        int src, nbr, wt;
//        public Edge(int src, int nbr, int wt) {
//            this.src = src;
//            this.nbr = nbr;
//            this.wt = wt;
//        }
//    }
//    static class Pair {
//        int src;
//        int sum;
//        public Pair(int src, int sum) {
//            this.src = src;
//            this.sum = sum;
//        }
//    }
//    public int zeroOneBFS(ArrayList<Edge>[] graph, int n, int src) {
//        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> a.sum - b.sum);
//        pq.add(new Pair(1, 0));
//        boolean[] visited = new boolean[n+1];
//        while(!pq.isEmpty()) {
//            int size = pq.size();
//            while(size-- != 0) {
//                Pair node = pq.remove();
//                if(node.src == n) {
//                    return node.sum;
//                }
//                if(visited[node.src]) continue;
//                visited[node.src] = true;
//                for(Edge e : (graph[node.src])) {
//                    if(!visited[e.nbr]) {
//                        pq.add(new Pair(e.nbr, node.sum+e.wt));
//                    }
//                }
//            }
//        }
//        return -1;
//    }


    /************************ 399. Evaluate Division (BFS)  ************************************/
    // https://leetcode.com/problems/evaluate-division/

    static class Edge {
        String src, nbr;
        double wt;
        public Edge(String src, String nbr, double wt) {
            this.src = src;
            this.nbr = nbr;
            this.wt = wt;
        }
    }
    static class Pair {
        String src;
        double cost;
        public Pair(String src, double cost) {
            this.src = src;
            this.cost = cost;
        }
    }
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, List<Edge>> graph = new HashMap<>();
        for(int i=0; i<equations.size(); i++) {
            List<String> equation = equations.get(i);
            if(!graph.containsKey(equation.get(0))) graph.put(equation.get(0), new ArrayList<Edge>());
            if(!graph.containsKey(equation.get(1))) graph.put(equation.get(1), new ArrayList<Edge>());
            graph.get(equation.get(0)).add(new Edge(equation.get(0), equation.get(1), values[i]));
            graph.get(equation.get(1)).add(new Edge(equation.get(1), equation.get(0), 1/values[i]));
        }
        double[] res = new double[queries.size()];
        int idx = 0;
        for(int i=0; i<queries.size(); i++) {
            HashSet<String> visited = new HashSet<>();
            List<String> query = queries.get(i);
            String src = query.get(0);
            String des = query.get(1);
            if(!graph.containsKey(src) || !graph.containsKey(des)) {
                res[idx++] = -1;
                continue;
            }
            Queue<Pair> q = new ArrayDeque<>();
            q.add(new Pair(src, 1));
            boolean found = false;
            loop:
            while(!q.isEmpty()) {
                int size = q.size();
                while(size-- != 0) {
                    Pair node = q.remove();
                    if(node.src.equals(des)) {
                        found = true;
                        res[idx++] = node.cost;
                        break loop;
                    }
                    if(visited.contains(node.src)) continue;
                    visited.add(node.src);
                    List<Edge> edge = graph.get(node.src);
                    for(Edge e : edge) {
                        if(!visited.contains(e.nbr)){
                            q.add(new Pair(e.nbr, node.cost*e.wt));
                        }
                    }
                }
            }
            if(!found) res[idx++] = -1;
        }
        return res;
    }

    /************************ 542. 01 Matrix  ************************************/
    // https://leetcode.com/problems/01-matrix/

    public int[][] updateMatrix(int[][] matrix) {
        Queue<int[]> q = new ArrayDeque<>();
        int[][] dirs = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        int cnt = 0;
        for(int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[i].length; j++) {
                if(matrix[i][j] == 0) {
                    q.add(new int[]{i, j});
                } else {
                    matrix[i][j] = -1;
                    cnt++;
                }
            }
        }
        int len = 1;
        while(!q.isEmpty()) {
            int size = q.size();
            while(size-- != 0) {
                int[] pos = q.remove();
                for(int i=0; i<dirs.length; i++) {
                    int x = pos[0]+dirs[i][0];
                    int y = pos[1]+dirs[i][1];
                    if(x >= 0 && y >= 0 && x < matrix.length && y < matrix[0].length && matrix[x][y] == -1) {
                        matrix[x][y] = len;
                        q.add(new int[]{x, y});
                        cnt--;
                        if(cnt == 0) return matrix;
                    }
                }
            }
            len++;
        }
        return matrix;
    }

    /************************ 1020. Number of Enclaves  ************************************/
    // https://leetcode.com/problems/number-of-enclaves/

    public int numEnclaves(int[][] grid) {
        Queue<int[]> queue = new ArrayDeque<>();
        int cnt = 0;
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[0].length; j++) {
                if(grid[i][j] == 0) continue;
                cnt++;
                if(i*j == 0 || i==grid.length - 1 || j == grid[0].length - 1) queue.add(new int[]{i, j});
            }
        }
        while (!queue.isEmpty()) {
            int[] p = queue.remove();
            int i = p[0], j = p[1];
            if(i < 0 || j < 0 || i>= grid.length || j >= grid[0].length || grid[i][j] == 0) continue;
            grid[i][j] = 0;
            --cnt;
            queue.add(new int[]{i-1, j});
            queue.add(new int[]{i+1, j});
            queue.add(new int[]{i, j-1});
            queue.add(new int[]{i, j+1});
        }
        return cnt;
    }

    /************************ Strongly Connected Components (Kosaraju's Algo)  ************************************/
    // https://practice.geeksforgeeks.org/problems/strongly-connected-components-kosarajus-algo/1
    public void topSort(ArrayList<ArrayList<Integer>> graph, boolean[] visited, int src, Stack<Integer> st) {
        if(visited[src]) return;
        visited[src] = true;
        for(Integer nbr : graph.get(src)) {
            if(!visited[nbr]) {
                topSort(graph, visited, nbr, st);
            }
        }
        st.add(src);
    }
    public void dfs(ArrayList<ArrayList<Integer>> graph, boolean[] visited, int src) {
        if(visited[src]) return;
        visited[src] = true;
        for(Integer nbr : graph.get(src)) {
            if(!visited[nbr]) {
                dfs(graph, visited, nbr);
            }
        }
    }
    public ArrayList<ArrayList<Integer>> reverseDirections(int V, ArrayList<ArrayList<Integer>> graph) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for(int i=0; i<V; i++) res.add(new ArrayList<>());
        for(int i=0; i<graph.size(); i++) {
            for(int j=0; j<graph.get(i).size(); j++) {
                res.get(graph.get(i).get(j)).add(i);
            }
        }
        return res;
    }
    public int kosaraju(int V, ArrayList<ArrayList<Integer>> adj)
    {
        boolean[] visited = new boolean[V];
        Stack<Integer> stack = new Stack<>();
        for(int i=0; i<V; i++) {
            if(!visited[i]) {
                topSort(adj, visited, i, stack);
            }
        }
        ArrayList<ArrayList<Integer>> graph = reverseDirections(V, adj);
        visited = new boolean[V];
        int count = 0;
        while (!stack.isEmpty()) {
            int peek = stack.pop();
            if(!visited[peek]) {
                count++;
                dfs(graph, visited, peek);
            }
        }
        return count;

    }

    /************************ Topological sort (DFS Algorithm) ************************************/
    // https://practice.geeksforgeeks.org/problems/topological-sort/1

    void topologicalSorting(int src, ArrayList<ArrayList<Integer>> graph, boolean[] visited, Stack<Integer> stack) {
        if(visited[src]) return;
        visited[src] = true;
        for(int nbr : graph.get(src)) {
            if(!visited[nbr]) {
                topologicalSorting(nbr, graph, visited, stack);
            }
        }
        stack.add(src);
    }

    int[] topoSort(int V, ArrayList<ArrayList<Integer>> adj)
    {
        // add your code here
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[V];
        for(int i=0; i<V; i++) {
            if(!visited[i]) {
                topologicalSorting(i, adj, visited, stack);
            }
        }
        int[] res = new int[stack.size()];
        int idx = 0;
        while(idx!=res.length) {
            res[idx++] = stack.pop();
        }
        return res;
    }

    /************************ Topological sort (BFS Algorithm -> Kahn's Algorithm) ************************************/

    int[] topoSortBFS(int V, ArrayList<ArrayList<Integer>> adj)
    {
        int[] indegre = new int[V];
        for(int i=0; i<adj.size(); i++) {
            for(Integer v : adj.get(i)) {
                indegre[v]++;
            }
        }
        int[] topSort = new int[V];
        int idx = 0;
        Queue<Integer> q = new ArrayDeque<>();
        for(int i=0; i<indegre.length; i++) {
            if(indegre[i] == 0) q.add(i);
        }
        while (!q.isEmpty()) {
            int node = q.remove();
            topSort[idx++] = node;
            for(int i=0; i<adj.get(node).size(); i++) {
                int nbr = adj.get(node).get(i);
                indegre[nbr]--;
                if(indegre[nbr] == 0) q.add(nbr);
            }
        }
        return topSort;
    }

    /************************ 200. Number of Islands  ************************************/
    // https://leetcode.com/problems/number-of-islands/

    public int numIslands(char[][] grid) {
        int count = 0;
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[i].length; j++) {
                if(grid[i][j] == '1') {
                    count+=1;
                    callBFS(grid, i, j);
                }
            }
        }
        return count;
    }
    public void callBFS(char[][] grid, int i, int j) {
        if(i<0 || j<0 || i>=grid.length || j>= grid[0].length || grid[i][j] == '0') return;
        grid[i][j] = '0';
        callBFS(grid, i + 1, j);
        callBFS(grid, i - 1, j);
        callBFS(grid, i, j + 1);
        callBFS(grid, i, j - 1);
    }

    /************************ Mother Vertex ************************************/
    // https://practice.geeksforgeeks.org/problems/mother-vertex/1

    public void topSortForMotherVerted(ArrayList<ArrayList<Integer>> graph, int src, boolean[] visited, Stack<Integer> stack) {
        visited[src] = true;
        for(int nbr : graph.get(src)) {
            if(!visited[nbr]) topSortForMotherVerted(graph, nbr, visited, stack);
        }
        stack.add(src);
    }
    public int findMotherVertex(int V, ArrayList<ArrayList<Integer>>adj)
    {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[V];
        topSortForMotherVerted(adj, 0, visited, stack);
        if(stack.size() != V) {
            return -1;
        }
        return stack.peek();
    }

    /************************ 773. Sliding Puzzle ************************************/
    // https://leetcode.com/problems/sliding-puzzle/

    public int slidingPuzzle(int[][] board) {
        int[][] dirs = new int[][]{{1, 3}, {0, 2, 4}, {1, 5}, {0, 4}, {1, 3, 5}, {2, 4}};
        StringBuilder strb = new StringBuilder();
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[i].length; j++) {
                strb.append(board[i][j]);
            }
        }
        String wantedState = "123450";
        HashSet<String> visited = new HashSet<>();
        Queue<String> q = new ArrayDeque<>();
        q.add(strb.toString());
        int swap = 0;


        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- != 0) {
                String curr = q.remove();
                if(visited.contains(curr)) continue;
                visited.add(curr);
                if(curr.equals(wantedState)) {
                    return swap;
                }
                int zeroIndex = curr.indexOf("0");
                for(int swapIndex : dirs[zeroIndex]) {
                    StringBuilder next = new StringBuilder(curr);
                    next.setCharAt(zeroIndex, curr.charAt(swapIndex));
                    next.setCharAt(swapIndex, '0');
                    if(visited.contains(next.toString())) continue;
                    q.add(next.toString());
                }
            }
            swap++;
        }
        return -1;
    }


    /************************ 990. Satisfiability of Equality Equations ************************************/
    // https://leetcode.com/problems/satisfiability-of-equality-equations/

    public char findParent(char[] set, char c) {
        if(set[c-'a'] == 0) return c;
        return findParent(set, set[c-'a']);
    }
    public boolean equationsPossible(String[] equations) {
        char[] equal = new char[26];
        for(String equation : equations) {
            char a = equation.charAt(0);
            char b = equation.charAt(3);
            char equality = equation.charAt(1);
            if(equality == '=') {
                char pa = findParent(equal, a);
                char pb = findParent(equal, b);
                if(pa == pb) continue;
                equal[pa - 'a'] = pb;
            }
        }

        for(String equation : equations) {
            char a = equation.charAt(0);
            char b = equation.charAt(3);
            char equality = equation.charAt(1);
            if(equality == '!') {
                char pa = findParent(equal, a);
                char pb = findParent(equal, b);
                if(pa == pb) return false;
            }
        }
        return true;
    }




    /************************ 684. Redundant Connection ************************************/
    // https://leetcode.com/problems/redundant-connection/
    public int findParent(int[] set, int v) {
        if(set[v] == 0) return v;
        return findParent(set, set[v]);
    }
    public int[] findRedundantConnection(int[][] edges) {
        int[] set = new int[edges.length+1];
        for(int i=0; i<edges.length; i++) {
            int from = edges[i][0];
            int to = edges[i][1];
            int parentFrom = findParent(set, from);
            int parentTo = findParent(set, to);
            if(parentFrom == parentTo) return edges[i];
            set[parentFrom] = parentTo;
        }
        return null;
    }

}

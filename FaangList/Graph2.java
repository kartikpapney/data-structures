package FaangList;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Graph2 {
    /************************ 815. Bus Routes ************************************/
    // https://leetcode.com/problems/bus-routes/

    public int numBusesToDestination(int[][] routes, int source, int target) {
        Set<Integer> busVisited = new HashSet<>();
        Set<Integer> stopVisited = new HashSet<>();
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for(int i=0; i<routes.length; i++) {
            for(int j=0; j<routes[i].length; j++) {
                map.putIfAbsent(routes[i][j], new ArrayList<>());
                map.get(routes[i][j]).add(i);
            }
        }
        Queue<Integer> q = new ArrayDeque<>();
        if(source == target) return 0;
        q.add(source);
        stopVisited.add(source);
        int len = 1;
        while(!q.isEmpty()) {
            int size = q.size();
            while(size-- != 0) {
                int stop = q.remove();
                for(int i=0; i<map.get(stop).size(); i++) {
                    int bus = map.get(stop).get(i);
                    if(busVisited.contains(bus)) continue;
                    busVisited.add(bus);
                    for(int s : routes[bus]) {
                        if(!stopVisited.contains(s)) {
                            stopVisited.add(s);
                            if(s == target) return len;
                            q.add(s);
                        }
                    }
                }
            }
            len++;
        }
        return -1;
    }

    /************************ 1162. As Far from Land as Possible ************************************/
    // https://leetcode.com/problems/as-far-from-land-as-possible/

    public int maxDistance(int[][] grid) {
        int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Queue<int[]> q = new ArrayDeque<>();
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[i].length; j++) {
                if(grid[i][j] == 1) q.add(new int[]{i, j});
            }
        }
        if(q.size() == 0 || q.size() == grid.length*grid[0].length) return -1;
        int max = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            for(int i=0; i<size; i++) {
                int[] pos = q.remove();
                for(int[] dir : dirs) {
                    int x = pos[0]+dir[0];
                    int y = pos[1]+dir[1];
                    if(x>=0 && y>=0 && x<grid.length && y<grid[0].length && grid[x][y] == 0) {
                        grid[x][y] = 1;
                        q.add(new int[]{x, y});
                    }
                }
            }
            max++;
        }
        return max-1;
    }

    /************************ 934. Shortest Bridge ************************************/
    // https://leetcode.com/problems/shortest-bridge/
    public void dfs(int[][] grid, int i, int j, Queue<int[]> q) {
        if(i >= 0 && j >= 0 && i < grid.length && j < grid[0].length && grid[i][j] == 1) {
            grid[i][j] = 2;
            q.add(new int[]{i, j});
            dfs(grid, i+1, j, q);
            dfs(grid, i, j+1, q);
            dfs(grid, i-1, j, q);
            dfs(grid, i, j-1, q);
        }
    }
    public int shortestBridge(int[][] grid) {
        Queue<int[]> q = new ArrayDeque<>();
        int[][] dirs = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        loop:
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[0].length; j++) {
                if(grid[i][j] == 1) {
                    dfs(grid, i, j, q);
                    break loop;
                }
            }
        }
        int len = 0;
        while(!q.isEmpty()) {
            int size = q.size();
            while(size-- != 0) {
                int[] pos = q.remove();
                for(int[] dir : dirs) {
                    int x = pos[0]+dir[0];
                    int y = pos[1]+dir[1];
                    if(x>=0 && y >=0 && x < grid.length && y < grid[0].length && grid[x][y] != 2) {
                        if(grid[x][y] == 1) return len;
                        grid[x][y] = 2;
                        q.add(new int[]{x, y});
                    }
                }
            }
            len++;
        }
        return -1;
    }

    /************************ 127. Word Ladder ************************************/
    // https://leetcode.com/problems/word-ladder/

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> list = new HashSet<>(wordList);
        Queue<String> q = new ArrayDeque<>();
        q.add(beginWord);
        if(!list.contains(endWord)) return 0;
        int operation = 0;
        while (!q.isEmpty()) {
            operation++;
            int size = q.size();
            while (size-- != 0) {
                StringBuilder rem = new StringBuilder(q.remove());
                if(rem.toString().equals(endWord)) return operation;
                list.remove(rem.toString());
                for (int i = 0; i < rem.length(); i++) {
                    char pc = rem.charAt(i);
                    for(char c='a'; c<='z'; c++) {
                        rem.setCharAt(i, c);
                        if (list.contains(rem.toString())) {
                            q.add(rem.toString());
                        }
                    }
                    rem.setCharAt(i, pc);
                }
            }
        }
        return 0;
    }

    /************************ 854. K-Similar Strings ************************************/
    // https://leetcode.com/problems/k-similar-strings/

    public int kSimilarity(String s1, String s2) {
        if(s1.equals(s2)) return 0;
        HashSet<String> visited = new HashSet<>();
        Queue<String> q = new ArrayDeque<>();
        q.add(s1);
        int len = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- != 0) {
                String rem = q.remove();
                visited.add(rem);
                if(rem.equals(s2)) return len;
                int i=0;
                while (rem.charAt(i) == s2.charAt(i))i++;
                for(int j=i+1; j<s2.length(); j++) {
                    if(s2.charAt(j) == rem.charAt(j)) continue;
                    if(rem.charAt(i) != s2.charAt(j) && rem.charAt(j) != s2.charAt(i)) continue;
                    String swap = swap(rem, i, j);
                    if(!visited.contains(swap)) {
                        q.add(swap);
                    }
                }
            }
            len++;
        }
        return -1;

    }

    public static String swap(String a, int i, int j) {
        StringBuilder strb = new StringBuilder();
        strb.setCharAt(i, a.charAt(j));
        strb.setCharAt(j, a.charAt(i));
        return strb.toString();
    }

    /************************  1466. Reorder Routes to Make All Paths Lead to the City Zero  ************************************/
    // https://leetcode.com/problems/reorder-routes-to-make-all-paths-lead-to-the-city-zero/

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


    /************************  1319. Number of Operations to Make Network Connected ************************************/
    // https://leetcode.com/problems/number-of-operations-to-make-network-connected/
    public void dfs(List<Integer>[] graph, int src, boolean[] visited) {
        visited[src] = true;
        for(Integer nbr : graph[src]) {
            if(!visited[nbr]) dfs(graph, nbr, visited);
        }
    }
    public int makeConnected(int n, int[][] connections) {
        if(n > connections.length + 1) return -1;
        List<Integer>[] graph = new ArrayList[n];
        for(int i=0; i<n; i++) graph[i] = new ArrayList<>();
        for(int i=0; i<connections.length; i++) {
            graph[connections[i][0]].add(connections[i][1]);
            graph[connections[i][1]].add(connections[i][0]);
        }
        boolean[] visited = new boolean[n];
        int cnt = 0;
        for(int i=0; i<graph.length; i++) {
            if(!visited[i]) {
                dfs(graph, i, visited);
                cnt++;
            }
        }
        return cnt-1;
    }


    /************************ 133. Clone Graph ************************************/
    // https://leetcode.com/problems/clone-graph/

    static class Node {
        public int val;
        public List<Node> neighbors;
        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

//    Node[] map = new Node[101];
//    public Node cloneGraph(Node node) {
//        if(node == null) return null;
//        if(map[node.val] != null) return map[node.val];
//        Node cnode = new Node(node.val);
//        map[cnode.val] = cnode;
//        for(int i=0; i<node.neighbors.size(); i++) {
//            cnode.neighbors.add(cloneGraph(node.neighbors.get(i)));
//        }
//        return cnode;
//    }

//    USING BFS

    public Node cloneGraph(Node node) {
        if(node == null) return null;
        Node[] map = new Node[101];
        Queue<Node> oq = new ArrayDeque<>();
        Queue<Node> aq = new ArrayDeque<>();
        Node ans = new Node(node.val);
        oq.add(node);
        aq.add(ans);
        map[node.val] = ans;
        while (!oq.isEmpty()) {
            int size = oq.size();
            while (size-- != 0) {
                Node rem = oq.remove();
                Node rq = aq.remove();
                System.out.println(rem.val + " " + rq.val);
                for(int i=0; i<rem.neighbors.size(); i++) {
                    Node nbr = rem.neighbors.get(i);
                    if(map[nbr.val] == null) {
                        Node anbr = new Node(nbr.val);
                        oq.add(nbr);
                        aq.add(anbr);
                        rq.neighbors.add(anbr);
                        map[anbr.val]=anbr;
                    } else {
                        rq.neighbors.add(map[nbr.val]);
                    }
                }
            }
        }
        return ans;
    }


    public int ladderLength(String beginWord, String endWord, Set<String> list) {
        Queue<String> q = new ArrayDeque<>();
        q.add(beginWord);
        if(!list.contains(endWord)) return 0;
        int operation = 0;
        while (!q.isEmpty()) {
            operation++;
            int size = q.size();
            while (size-- != 0) {
                StringBuilder rem = new StringBuilder(q.remove());
                if(rem.toString().equals(endWord)) return operation;
                list.remove(rem.toString());
                for (int i = 0; i < rem.length(); i++) {
                    char pc = rem.charAt(i);
                    for(char c='a'; c<='z'; c++) {
                        rem.setCharAt(i, c);
                        if (list.contains(rem.toString())) {
                            q.add(rem.toString());
                        }
                    }
                    rem.setCharAt(i, pc);
                }
            }
        }
        return 0;
    }
    public List<List<String>> findLadders(String beginWord, String endWord, Set<String> list, int len) {
        if(len == 1) {
            List<String> base = new ArrayList<>();
            if(beginWord.equals(endWord)) {
                base.add(endWord);
            }
            return new ArrayList(base);
        }
        StringBuilder rem = new StringBuilder(beginWord);
        list.remove(rem.toString());
        List<List<String>> mans = new ArrayList<>();
        for (int i = 0; i < rem.length(); i++) {
            char pc = rem.charAt(i);
            for(char c='a'; c<='z'; c++) {
                rem.setCharAt(i, c);
                List<List<String>> rr = findLadders(rem.toString(), endWord, list, len-1);
                for(int k=0; k<rr.size(); k++) {
                    List<String> pans = new ArrayList<>();
                    pans.add(beginWord);
                    pans.addAll(rr.get(i));
                    mans.add(pans);
                }
            }
            rem.setCharAt(i, pc);
        }
        list.add(rem.toString());
        return mans;
    }
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Set<String> list = new HashSet<>(wordList);
        int len = ladderLength(beginWord, endWord, wordList);
        List<List<String>> res = new ArrayList<>();
        return res;
    }

    /************************ 787. Cheapest Flights Within K Stops ************************************/
    // https://leetcode.com/problems/cheapest-flights-within-k-stops/

//    Solution not done yet


    /************************ 1254. Number of Closed Islands ************************************/
    // https://leetcode.com/problems/number-of-closed-islands/
    public boolean dfs(int[][] grid, int i, int j) {
        if(i == 0 || j == 0 || i == grid.length - 1 || j == grid[0].length - 1) {
            if(grid[i][j] == 1) return true;
            return false;
        }
        if(grid[i][j] == 1 || grid[i][j] == -1) return true;
        grid[i][j] = -1;
        boolean ans = true;
        ans=dfs(grid, i-1, j)&&ans;
        ans=dfs(grid, i, j-1)&&ans;
        ans=dfs(grid, i+1, j)&&ans;
        ans=dfs(grid, i, j+1)&&ans;
        return ans;
    }
    public int closedIsland(int[][] grid) {
        int cnt = 0;
        for(int i=1; i<grid.length - 1; i++) {
            for(int j=1; j<grid[0].length - 1; j++) {
                if(grid[i][j] == 0) {
                    if(dfs(grid, i, j)) cnt++;
                }
            }
        }
        return cnt;
    }

    /************************ 886. Possible Bipartition ************************************/
    // https://leetcode.com/problems/possible-bipartition/
    public boolean bipartiteCheck(ArrayList<Integer>[] graph, int[] visited, int src) {
        Queue<Integer> q = new ArrayDeque<>();
        q.add(src);
        int color = 1;
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- != 0) {
                int rem = q.remove();
                if(visited[rem] == 0) visited[rem] = color;
                else if(visited[rem] != color) return false;
                for(int i=0; i<graph[rem].size(); i++) {
                    int nbr = graph[rem].get(i);
                    if(visited[nbr] == 0) {
                        q.add(nbr);
                    }
                }
            }
            color*=-1;
        }
        return true;
    }
    public boolean possibleBipartition(int n, int[][] dislikes) {
        ArrayList<Integer>[] graph = new ArrayList[n+1];
        for(int i=1; i<graph.length; i++) graph[i] = new ArrayList();
        for(int i=0; i<dislikes.length; i++) {
            graph[dislikes[i][0]].add(dislikes[i][1]);
            graph[dislikes[i][1]].add(dislikes[i][0]);
        }
        int[] visited = new int[n+1];
        for(int i=1; i<=n; i++) {
            if(visited[i] == 0) {
                if(!bipartiteCheck(graph, visited, i)) return false;
            }
        }
        return true;
    }

    /************************ 743. Network Delay Time ************************************/
    // https://leetcode.com/problems/network-delay-time/
    public int networkDelayTime(int[][] arr, int n, int k) {
        ArrayList<int[]>[] graph = new ArrayList[n+1];
        for(int i=1; i<graph.length; i++) graph[i] = new ArrayList();
        for(int i=0; i<arr.length; i++) {
            graph[arr[i][0]].add(new int[]{arr[i][1], arr[i][2]});
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1]-b[1]);
        pq.add(new int[]{k, 0});
        boolean[] visited = new boolean[n+1];
        visited[0] = true;
        int maxv = 0;
        while (!pq.isEmpty()) {
            int[] rem = pq.remove();
            if(visited[rem[0]]) continue;
            visited[rem[0]] = true;
            maxv = Math.max(maxv, rem[1]);
            for(int[] nbr : graph[rem[0]]) {
                if(!visited[nbr[0]]) {
                    pq.add(new int[]{nbr[0], nbr[1] + rem[1]});
                }
            }
        }
        for(boolean b : visited) if(!b) return -1;
        return maxv;
    }


    public List<String> findItinerary(List<List<String>> arr) {
        HashMap<String,PriorityQueue<String>> map = new HashMap<>();
        for(int i=0; i<arr.size(); i++) {
            String from = arr.get(i).get(0);
            String to = arr.get(i).get(1);
            map.putIfAbsent(from, new PriorityQueue<>());
            map.putIfAbsent(to, new PriorityQueue<>());
            map.get(from).add(to);
        }
        Stack<String> pq = new Stack<>();
        pq.add("JFK");
        List<String> res = new ArrayList<>();
        while (!pq.isEmpty()) {
            String rem = pq.pop();
            res.add(rem);
            List<String> brr = new ArrayList<>(map.get(rem));
            for(String s : brr) {
                map.get(rem).remove(s);
                pq.add(s);
            }
        }
        return res;
    }
}

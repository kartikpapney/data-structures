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


    public String largestNumber(int[] nums) {
        String[] arr = new String[nums.length];
        for(int i=0; i<nums.length; i++) {
            arr[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(arr, (a, b) -> {
            String x = a+b;
            String y = b+a;
            return x.compareTo(y);
        });
        StringBuilder strb = new StringBuilder();
        for(String a : arr) strb.append(a);
        return strb.toString();
    }

}

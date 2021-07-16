package Arrays;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Set2 {
    /************************  1750. Minimum Length of String After Deleting Similar Ends ************************************/
    class Solution {
        public int minimumLength(String s) {
            int start = 0;
            int end = s.length()-1;
            int i=start;
            int j=end;
            while(start < end && s.charAt(start) == s.charAt(end)) {
                i=start+1;
                j=end-1;
                while(i <= j && s.charAt(i) == s.charAt(start)) i++;
                while(i <= j && s.charAt(j) == s.charAt(end)) j--;
                start = i;
                end = j;
            }
            return end-start+1;
        }
    }

    /************************  792. Number of Matching Subsequences (Brute Force approach, Still accepting) ************************************/
    public boolean isSubsequence(char[] str, char[] word, int i, int j) {
        if(j == word.length) return true;
        if(i == str.length) return false;
        if(str[i] == word[j]) {
            return isSubsequence(str, word, i+1, j+1);
        }
        return isSubsequence(str, word, i+1, j);
    }
    public int numMatchingSubseq(String s, String[] words) {
        HashMap<String, Integer> map = new HashMap<>();
        for(var ss : words) map.put(ss, map.getOrDefault(ss, 0) + 1);
        int cnt = 0;
        for (Map.Entry<String, Integer> ss : map.entrySet()) {
            if (isSubsequence(s.toCharArray(), ss.getKey().toCharArray(), 0, 0)) {
                cnt+=ss.getValue();
            }
        }
        return cnt;
    }

    /************************  475. Heaters (Brute Force - MLE) ************************************/

    /*
    public int findRadius(int[] houses, int[] heaters) {
        int start = 0;
        int end = 1;
        for(int val : houses) end = Math.max(end, val);
        for(int val : heaters) end = Math.max(end, val);
        boolean[] isHouse = new boolean[end+1];
        for(int val : houses) isHouse[val]=true;
        int[] noOFHeaterAvailable = new int[end+1];

        int radius = 0;
        while(start <= end) {
            Arrays.fill(noOFHeaterAvailable, 0);
            int r = start + (end-start)/2;
            for(int val : heaters) {
                noOFHeaterAvailable[Math.max(val-r, 0)]+=1;
                if(val+r+1<noOFHeaterAvailable.length) noOFHeaterAvailable[val+r+1]-=1;
            }
            for(int i=1; i<noOFHeaterAvailable.length; i++) noOFHeaterAvailable[i]+=noOFHeaterAvailable[i-1];
            boolean check = true;
            for(int i=0; i<noOFHeaterAvailable.length; i++) {
                if(isHouse[i] && noOFHeaterAvailable[i] <= 0) {
                    check = false;
                    break;
                }
            }
            if(check) {
                radius=r;
                end = r-1;
            } else {
                start=r+1;
            }
        }
        return radius;
    } */

    public int[] findCeilAndFloor(int[] houses, int key) {
        int[] indices = new int[2];
        Arrays.fill(indices, Integer.MAX_VALUE);
        int start = 0;
        int end = houses.length - 1;
        while(start <= end) {
            int mid = start+(end-start)/2;
            if(houses[mid] == key) {
                indices[0] = mid;
                indices[1] = mid;
            } else if(houses[mid] < key) {
                indices[0] = houses[mid];
                start = mid+1;
            } else {
                indices[1] = houses[mid];
                end = mid-1;
            }
        }
        return indices;
    }
    public int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(heaters);
        int radius = Integer.MIN_VALUE;
        for(int i=0; i<houses.length; i++) {
            int[] indices = findCeilAndFloor(heaters, houses[i]);
            int ceil = indices[1], floor=indices[0];
            if(ceil == Integer.MAX_VALUE) radius = Math.max(houses[i] - floor, radius);
            else if(floor == Integer.MAX_VALUE) radius = Math.max(ceil - houses[i], radius);
            else radius = Math.max(Math.min(houses[i] - floor, ceil - houses[i]), radius);
        }
        return radius;
    }

    /************************  92. Reverse Linked List II ************************************/

     public class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }

     public ListNode reverse(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode prev=null, curr=head, forw = null;
        while(curr != null) {
            forw = curr.next;
            curr.next = prev;
            prev = curr;
            curr = forw;
        }
        return prev;
     }


    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummyStart = new ListNode(), dummyEnd = new ListNode(), temp = head;

//      Adding one node at the start one at the end
        dummyStart.next = head;
        temp = head = dummyStart;
        while(temp.next != null) temp = temp.next;
        temp.next = dummyEnd;

        ListNode start=head, end=head;

//        Get the part need to reverse
        while (left != 1) {
            start = start.next;
            left--;
        }
        while(right != 1) {
            end = end.next;
            right--;
        }

        ListNode node = start.next;
        temp = end.next;
        end.next = null;
        end = temp;
        temp = reverse(node);
        start.next = temp;
        node.next = end;

        head = dummyStart.next;
        temp = head;

        while(temp.next.next != null) temp=temp.next;
        temp.next = null;
        return head;
    }

    /************************  452. Minimum Number of Arrows to Burst Balloons ************************************/

    public int findMinArrowShots(int[][] points) {
        Arrays.sort(points, (a, b) -> a[1] < b[1]?-1:1);
        int end = points[0][1];
        int cnt = 1;
        for(int i=1; i<points.length; i++) {
            if(points[i][0] <= end) {
                continue;
            }
            end = points[i][1];
            cnt++;
        }
        return cnt;
    }

    /************************ 435. Non-overlapping Intervals ************************************/
    public int eraseOverlapIntervals(int[][] arr) {
        Arrays.sort(arr, (a, b) -> a[1]<b[1]?-1:1 );
        int cnt = 0;
        int end = arr[0][1];
        for(int i=1; i<arr.length; i++) {
            if(arr[i][0]<=end) {
                cnt++;
                continue;
            }
            end = arr[i][1];
        }
        return cnt;
    }

    /************************ 56. Merge Intervals ************************************/
    public int[][] merge(int[][] arr) {

        List<int[]> res = new ArrayList<>();
//        Arrays.sort(arr, (a, b) -> a[1]<b[1]?-1:1 );  (Not working Reason??)
        Arrays.sort(arr, (a, b) -> a[0]<b[0]?-1:a[0]>b[0]?1:0);
        int start = arr[0][0];
        int end = arr[0][1];
        for(int i=1; i<arr.length; i++) {
            if(arr[i][0] <= end) {
                end = Math.max(end, arr[i][1]);
            } else {
                res.add(new int[]{start, end});
                start = arr[i][0];
                end = arr[i][1];
            }
        }
        res.add(new int[]{start, end});
        int[][] ans = new int[res.size()][2];
        for(int i=0; i<ans.length; i++) ans[i] = res.get(i);
        return ans;

    }

    /************************  57. Insert Interval ************************************/

//    O(nlogn)
//    public int[][] insert(int[][] intervals, int[] newInterval) {
//        List<int[]> res = new ArrayList<>();
//        int[][] arr = new int[intervals.length + 1][2];
//        for(int i=0; i<arr.length-1; i++) {
//            arr[i] = intervals[i];
//        }
//        arr[intervals.length] = newInterval;
//        Arrays.sort(arr, (a, b) -> a[0]<b[0]?-1:a[0]>b[0]?1:0);
//        int start = arr[0][0];
//        int end = arr[0][1];
//        for(int i=1; i<arr.length; i++) {
//            if(end >= arr[i][0]) {
//                end = Math.max(end, arr[i][1]);
//            } else {
//                res.add(new int[]{start, end});
//                start = arr[i][0];
//                end = arr[i][1];
//            }
//        }
//        res.add(new int[]{start, end});
//        int[][] ans = new int[res.size()][2];
//        for(int i=0; i<ans.length; i++) ans[i] = res.get(i);
//        return ans;
//    }

//      O(N) solution..
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> sorted = new ArrayList<>();
        int i=0;
        while(i<intervals.length && intervals[i][1] < newInterval[0]) sorted.add(intervals[i++]);
        while (i<intervals.length && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(intervals[i][1], newInterval[1]);
            i++;
        }
        sorted.add(newInterval);
        while(i<intervals.length) sorted.add(intervals[i++]);
        i=0;
        int[][] res = new int[sorted.size()][2];
        while(i<res.length) res[i] = sorted.get(i++);
        return res;
    }

    /************************ 140. Word Break II ************************************/
    /*
    HashSet<String> words;
    List<String> res;
    public List<String> wordBreak(String s, List<String> wordDict) {
        res = new ArrayList<>();
        words = new HashSet<>();
        words.addAll(wordDict);
        helperWordBreak(s, 0, 1, new StringBuilder());
        return res;
    }
    public void helperWordBreak(String s, int i, int j, StringBuilder asf) {
        if(j == s.length()) {
            if(words.contains(s.substring(i, j))) {
                int len = asf.length();
                asf.append(s, i, j);
                res.add(asf.toString());
                asf.delete(len, asf.length());
            }
            return;
        }
        if(words.contains(s.substring(i, j))) {
            int len = asf.length();
            asf.append(s, i, j).append(" ");
            helperWordBreak(s, j, j+1, asf);
            asf.delete(len, asf.length());
        }
        helperWordBreak(s, i, j+1, asf);
    }

    */


    // Storing some precalculated result;
    HashMap<String, List<String>> map = new HashMap<>();
    public List<String> helperWordBreak(String s, Set<String> set) {
        List<String> res = new ArrayList<>();
        if(s.equals("")) return new ArrayList<>();
        else if(map.containsKey(s)) return map.get(s);
        if(set.contains(s)) res.add(s);
        for(int i=1; i<s.length(); i++) {
            String str = s.substring(i);
            if(set.contains(str)) {
                List<String> rr = helperWordBreak(s.substring(0, i), set);
                if(rr.size() != 0) {
                    for(int j=0; j<rr.size(); j++) {
                        res.add(rr.get(j) + " " + s.substring(i));
                    }
                }
            }
        }
        map.put(s, res);
        return res;
    }
    public List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>();
        set.addAll(wordDict);
        return helperWordBreak(s, set);
    }


    /************************ 1020. Number of Enclaves ************************************/

    /* public void dfsNumEnclaves(int[][] grid, int i, int j) {
        if(i<0 || j<0 || i>= grid.length || j >= grid[0].length || grid[i][j] == 0) {
            return;
        }
        grid[i][j] = 0;
        dfsNumEnclaves(grid, i+1, j);
        dfsNumEnclaves(grid, i-1, j);
        dfsNumEnclaves(grid, i, j+1);
        dfsNumEnclaves(grid, i, j-1);
    }
    public int numEnclaves(int[][] grid) {
        for(int i=0; i<grid.length; i++) {
            if(grid[i][grid[0].length - 1] == 1) {
                dfsNumEnclaves(grid, i, grid[0].length - 1);
            }
            if(grid[i][0] == 1) {
                dfsNumEnclaves(grid, i, 0);
            }
        }
        for(int j=0; j<grid[0].length; j++) {
            if(grid[0][j] == 1) {
                dfsNumEnclaves(grid, 0, j);
            }
            if(grid[grid.length - 1][j] == 1) {
                dfsNumEnclaves(grid, grid.length - 1, j);
            }
        }
        int cnt = 0;
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[i].length; j++) {
                if(grid[i][j] == 1) {
                    cnt++;
                }
            }
        }
        return cnt;
    } */

    // BFS Solution

    public int numEnclaves(int[][] grid) {
        Queue<int[]> queue = new ArrayDeque<>();
        int cnt = 0;
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[0].length; j++) {
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

    public int findParent(int[] set, int v) {
        if(set[v] == -1) return v;
        return findParent(set, set[v]);
    }
    public int[] findRedundantConnection(int[][] edges) {
        int[] set = new int[edges.length + 1];
        Arrays.fill(set, -1);
        for(int i=0; i<edges.length; i++) {
            int from = edges[i][0];
            int to = edges[i][1];
            int parentFrom = findParent(set, from);
            int parentTo = findParent(set, to);
            if(parentFrom == parentTo) return edges[i];
            set[from] = to;
        }
        return null;
    }

    /************************ 1915. Number of Wonderful Substrings ************************************/
    public long wonderfulSubstrings(String word) {
        long[] map = new long[(1<<10)];
        map[0] = 1;
        long cnt = 0;
        int mask = 0;
        for(char c : word.toCharArray()) {
            int pos = (int)(c-'a');
            mask^=(1<<pos);
            cnt+=map[mask];
            for(int i=0; i<10; i++) {
                cnt+=map[mask^(1<<i)];
            }
            map[mask]++;
        }
        return cnt;
    }



    /************************ 1542. Find Longest Awesome Substring ************************************/
//    public int longestAwesome(String s) {
//        Integer[] dp = new Integer[1<<10];
//        int len=0,mask=0;
//        dp[0] = -1;
//        for(int i=0; i<s.length(); i++) {
//            char c = s.charAt(i);
//            mask^=1<<(c-'0');
//            if(dp[mask] != null) len = Math.max(len, i-dp[mask]);
//            for(int j=0; j<10; j++) {
//                if(dp[mask^(1<<j)] != null) len = Math.max(len, i-dp[mask^(1<<j)]);
//            }
//            if(dp[mask] == null) dp[mask] = i;
//        }
//        return len;
//    }

    public int longestAwesome(String s) {
        int[] dp = new int[1<<10];
        Arrays.fill(dp, s.length());
        int len=0,mask=0;
        dp[0] = -1;
        for(int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            mask^=1<<(c-'0');
            len = Math.max(len, i-dp[mask]);
            for(int j=0; j<10; j++) {
                len = Math.max(len, i-dp[mask^(1<<j)]);
            }
            dp[mask] = Math.min(i, dp[mask]);
        }
        return len;
    }

    /************************ 135. Candy ************************************/

    public int candy(int[] ratings) {
        int len = ratings.length;
        int[] count = new int[ratings.length];
        Arrays.fill(count, 1);
        for(int i=1; i<ratings.length; i++) {
            if(ratings[i]>ratings[i-1]) count[i] = count[i-1]+1;
        }
        for(int i=ratings.length-2; i>=0; i--) {
            if(ratings[i] > ratings[i+1]) count[i]=Math.max(count[i], count[i+1]+1);
        }
        int cnt = 0;
        for(int i=0; i<count.length; i++) {
            cnt+=count[i];
        }
        return cnt;
    }

    /************************ 1905. Count Sub Islands ************************************/
    public void update(int[][] grid1, int[][] grid2, int i, int j) {
        if(i<0 || j<0 || i>=grid1.length || j>=grid1[0].length || grid1[i][j] == 0) return;
        grid2[i][j]*=2;
        update(grid1, grid2, i-1, j);
        update(grid1, grid2, i, j-1);
        update(grid1, grid2, i+1, j);
        update(grid1, grid2, i, j+2);
    }

    /************************ 1905. Count Sub Islands ************************************/
    public int dfsCountSubIslands(int[][] A, int[][] B, int i, int j) {
        int res = 1;
        if(i<0 || j < 0 || i>=A.length || j>=B.length || B[i][j] == 0) return 1;
        B[i][j] = 0;
        res&=dfsCountSubIslands(A, B, i-1, j);
        res&=dfsCountSubIslands(A, B, i, j-1);
        res&=dfsCountSubIslands(A, B, i+1, j);
        res&=dfsCountSubIslands(A, B, i, j+1);
        return A[i][j]&res;
    }
    public int countSubIslands(int[][] A, int[][] B) {
        int cnt = 0;
        for(int i=0; i<B.length; i++) {
            for(int j=0; j<B[0].length; j++) {
                if(B[i][j] == 1) {
                    cnt+=dfsCountSubIslands(A, B, i, j);
                }
            }
        }
        return cnt;
    }

    /************************ 729. My Calendar I ************************************/

//    Usual approach Basic
    class MyCalendar {

        List<int[]> list;
        public MyCalendar() {
            list = new ArrayList<>();
        }

        public boolean book(int start, int end) {
            for(int i=0; i<list.size(); i++) {

                /** Let say points are (a, b) and (c, d). Intersection will occur when
                 * Case 1: a lies between c and d or
                 * Case 2: b lies between c and d or
                 * Case 3: a and b both lies between c and d
                 */

                if(start >= list.get(i)[0] && start < list.get(i)[1]
                        || end > list.get(i)[0] && end <= list.get(i)[1]
                        || start <= list.get(i)[0] && end >= list.get(i)[1]) return false;
            }
            list.add(new int[]{start, end});
            System.out.println(start + " " + end);
            return true;
        }
    }

    public int optimalScore(int[] stones, int start, int end, int sum) {
        if(start > end) return 0;

        int x = optimalScore(stones, start+2, end, sum-stones[start]-stones[start+1]);
        int y = optimalScore(stones, start+1, end-1, sum-stones[start]-stones[end]);
        int z = optimalScore(stones, start, end-2, sum-stones[start]-stones[end]);
        int a = sum - stones[start] + Math.min(x, y);
        int b = sum - stones[end] + Math.min(y, z);
        return Math.max(a, b);
    }
    public int stoneGameVII(int[] stones) {
        int sum = 0;
        for(int val : stones) sum+=val;
        return sum - optimalScore(stones, 0, stones.length - 1, sum);

    }


}

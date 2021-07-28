package Contest;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class LeetcodeBiweekly57 {
    public boolean areOccurrencesEqual(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        for(char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        ArrayList<Integer> v = new ArrayList<>(map.values());
        for(int i=1; i<v.size(); i++) {
            if(v.get(i) != v.get(i-1)) return false;
        }
        return true;
    }

    public int smallestChair(int[][] times, int targetFriend) {
        int[][] chair = new int[10001][2];
        int t = times[targetFriend][0];
        Arrays.sort(times, (a, b) -> a[0]-b[0]);
        for(int i=0; i<times.length; i++) {
            for(int j=0; j<chair.length; j++) {
                if(chair[j] == null || chair[j][0] >= times[i][0]) {
                    if(times[i][0] == t) return j;
                    chair[j] = times[i];
                    break;
                }
            }
        }
        return -1;
    }

    public List<List<Long>> splitPainting(int[][] segments) {
        int minv = Integer.MAX_VALUE;
        int maxv = Integer.MIN_VALUE;
        for(int i=0; i<segments.length; i++) {
            minv = Math.min(segments[i][0], minv);
            maxv = Math.max(segments[i][1], maxv);
        }
        long[] arr = new long[maxv + 1];
        for(int i=0; i<segments.length; i++) {
            arr[segments[i][0]]+=segments[i][2];
            arr[segments[i][1]+1]-=segments[i][2];
        }
        for(int i=minv+1; i<arr.length; i++) {
            arr[i]+=arr[i-1];
        }
        List<List<Long>> res = new ArrayList<>();
        for(int i=minv; i<maxv;) {
            List<Long> cr = new ArrayList<>();
            cr.add(arr[i]);
            while(arr[i] == arr[i+1]) i++;
            cr.add(arr[i]);
            i++;
            res.add(cr);
        }
        return res;
    }

    public int[] canSeePersonsCount(int[] heights) {
        int n = heights.length;
        int[] res = new int[heights.length];
        Stack<Integer> st = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            while (!st.isEmpty() && heights[i] > heights[st.peek()]) st.pop();
            if (st.isEmpty()) res[i] = 0;
            else res[i] = st.peek() - i;
        }
        return res;
    }
}



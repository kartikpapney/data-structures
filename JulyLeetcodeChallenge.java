import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class JulyLeetcodeChallenge {
    /*
    public List<Integer> grayCode(int m) {
        int n = (1<<(m-1));
        if(n == 1) {
            List<Integer> bc = new ArrayList<>();
            bc.add(0);
            bc.add(1);
            return bc;
        }
        List<Integer> rr = grayCode(m-1);
        List<Integer> mr = new ArrayList<>();
        for(int i=0; i<rr.size(); i++) {
            mr.add(rr.get(i) << 1);
        }
        for(int i=rr.size()-1; i>=0; i--) {
            mr.add((rr.get(i) << 1) | 1);
        }
        return mr;
    }
    */

    // Iterative
    public List<Integer> grayCode(int m) {
        List<Integer> ans = new ArrayList<>();
        ans.add(0);
        ans.add(1);
        for(int i=2; i<=m; i++) {
            int size = ans.size();
            for(int j=size-1; j>=0; j--) {
                ans.add((1<<(i-1))|ans.get(j));
            }
        }
        return ans;
    }
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> Math.abs(a-x)==Math.abs(b-x)?a<b?1:0:Math.abs(a-x)-Math.abs(b-x));
        for(int v : arr) pq.add(v);
        List<Integer> res = new ArrayList<>();
        while(k-- != 0) {
            res.add(pq.poll());
        }
        return res;
    }
}

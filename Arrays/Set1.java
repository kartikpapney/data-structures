package Arrays;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Set1 {
    /************************  1476. Subrectangle Queries  ************************************/

    class SubrectangleQueries {
        private int[][] arr;
        List<int[]> res = new ArrayList<>();
        public SubrectangleQueries(int[][] rectangle) {
            arr = new int[rectangle.length][rectangle[0].length];
            int i=0;
            for(int[] a : rectangle) {
                arr[i++] = a.clone();
            }
        }

        public void updateSubrectangle(int row1, int col1, int row2, int col2, int newValue) {
            res.add(new int[]{row1, col1, row2, col2, newValue});
        }

        public int getValue(int row, int col) {
            for(int i=res.size() - 1; i>=0; i--) {
                int[] update = res.get(i);
                if(update[0] <= row && update[1] <= col && update[2] >= row && update[3] >= col) return update[4];
            }
            return arr[row][col];
        }
    }

    /************************  1769. Minimum Number of Operations to Move All Balls to Each Box  ************************************/
    public int[] minOperations(String boxes) {
        char[] str = boxes.toCharArray();
        int[] ans = new int[str.length];
        int prev = str[0] == '1'?1:0;
        for(int i=1, ops=0; i<ans.length; i++) {
            ops+=prev;
            ans[i] += ops;
            prev+=(str[i] == '1'?1:0);
        }
        prev = str[ans.length - 1] == '1'?1:0;
        for(int i=ans.length - 2, ops=0; i>=0; i--) {
            ops+=prev;
            ans[i] += ops;
            prev+=(str[i] == '1'?1:0);
        }
        return ans;
    }

    /************************  925. Long Pressed Name(Important) ************************************/

    public boolean isLongPressedName(String name, String typed) {
        char[] a = name.toCharArray();
        char[] b = typed.toCharArray();
        if(a.length > b.length) return false;
        int i = 0, j=0;
        while(i < a.length && j < b.length) {
            if(a[i] != b[j]) return false;
            if((i == a.length - 1 || a[i] != a[i+1])) {
                while(j < b.length - 1 && b[j] == b[j+1]) j++;
            }
            j++;
            i++;
        }
        return i == a.length && j == b.length;
    }

    /************************  118. Pascal's Triangle ************************************/

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        for(int i=0; i<numRows; i++) res.add(new ArrayList<>());
        res.get(0).add(1);
        for(int i=1; i<numRows; i++) {
            for(int j=0; j<i+1; j++) {
                if(j == 0) {
                    res.get(i).add(res.get(i-1).get(j));
                } else if(j == i) {
                    res.get(i).add(res.get(i-1).get(j-1));
                } else {
                    res.get(i).add(res.get(i-1).get(j-1) + res.get(i-1).get(j));
                }
            }
        }
        return res;
    }

}

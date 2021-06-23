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

    /************************  475. Heaters ************************************/

    public int findRadius(int[] houses, int[] heaters) {
        return -1;
    }
}

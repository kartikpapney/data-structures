package AdvanceDynamicProgramming;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class LeetcodeHard {

    /************************  629. K Inverse Pairs Array  ************************************/

    public int kInversePairs(int n, int k) {

        int m = (int)(1e9+7);
        if(k > (n*(n-1))/2) return 0;
        if(k == 0 || k == (n*(n-1))/2) return 1;
        long[][] dp = new long[n+1][k+1];
        dp[2][0] = 1;
        dp[2][1] = 1;
        for(int i=3; i<dp.length; i++) {
            dp[i][0] = 1;
            for(int j=1; j<dp[0].length; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
                if(j >= i) dp[i][j] -= dp[i-1][j-i];
                dp[i][j] = (dp[i][j] + m)%m;
            }
        }

        return (int)dp[n][k];
    }

    /************************  403. Frog Jump ************************************/

//    42ms
//    Boolean[][] dp;
//    public boolean findCanCross(int[] stones, int i, int lastJump) {
//        if(i == stones.length - 1) return true;
//        if(dp[i][lastJump] != null) return dp[i][lastJump];
//        boolean ans = false;
//        for(int j=i+1; j<stones.length; j++) {
//            int jump = stones[j] - stones[i];
//            if(jump == lastJump || jump == lastJump-1 || jump == lastJump+1) {
//                ans |= findCanCross(stones, j, jump);
//                if(ans) break;
//            }
//        }
//        dp[i][lastJump] = ans;
//        return ans;
//    }
//    public boolean canCross(int[] stones) {
//        if(stones[1] - stones[0] != 1) return false;
//        dp = new Boolean[stones.length][stones.length];
//        return findCanCross(stones, 1, 1);
//    }

//    Set Implementation

//    public boolean canCross(int[] stones) {
//        HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
//        for(int i=0; i<stones.length; i++) map.put(stones[i], new HashSet<>());
//        map.get(stones[0]).add(1);
//        for(int i=0; i<stones.length - 1; i++) {
//            int currentStone = stones[i];
//            HashSet<Integer> options = map.get(currentStone);
//            for(int jump : options) {
//                int position = currentStone + jump;
//                if(position == stones[stones.length - 1]) return true;
//                if(map.containsKey(position)) {
//                    if(jump != 1) map.get(position).add(jump - 1);
//                    map.get(position).add(jump + 1);
//                    map.get(position).add(jump);
//                }
//            }
//        }
//        return false;
//    }

    public boolean canCross(int[] stones) {
        int n = stones.length;
        boolean[][] dp = new boolean[stones.length][stones.length];
        dp[0][1] = true;
        for(int i=1; i<dp.length; i++) {
            for(int j=0; j<i; j++) {
                int jump = stones[i] - stones[j];
                if(!dp[j][jump]) continue;
                dp[i][jump] = true;
                if(jump - 1 >= 0) dp[i][jump - 1] = true;
                if(jump + 1 < n) dp[i][jump + 1] = true;
                dp[i][jump] = true;
                if(i == n-1) return true;
            }
        }
        return false;
    }

    /************************  87. Scramble String  ************************************/

//    Boolean[][][] dp;
//    public boolean isScramble(String s1, String s2, int i, int j, int length) {
//        if(s1.substring(i, i + length).equals(s2.substring(j, j + length))) return true;
//        if(dp[i][j][length] != null) return dp[i][j][length];
//        dp[i][j][length] = false;
//        for(int len = 1; len<length; len++) {
//            if(isScramble(s1, s2, i, j, len) && isScramble(s1, s2, i+len, j+len, length - len)
//                    || isScramble(s1, s2, i, j + length - len, len) && isScramble(s1, s2, i+len, j, length - len)) dp[i][j][length] = true;
//        }
//        return dp[i][j][length];
//    }

    public boolean isScramble(String s1, String s2) {
//        dp = new Boolean[s1.length()][s2.length()][s1.length() + 1];
//        return isScramble(s1, s2, 0, 0, s1.length());
        int n = s1.length();
        boolean[][][] dp = new boolean[n+1][n][n];
        for(int len = 1; len<=n; len++) {
            for(int i=0; i<=n-len; i++) {
                for(int j=0; j<=n-len; j++) {
                    if(len == 1) {
                        dp[len][i][j] = (s1.charAt(i) == s2.charAt(j));
                    } else {
                        for(int k=1; k<len && !dp[len][i][j]; k++) {
                            dp[len][i][j] = (dp[k][i][j] && dp[len-k][i+k][j+k])
                                    || (dp[len - k][i + k][j] && dp[k][i][j+len-k]);
                        }
                    }
                }
            }
        }
        return dp[n][0][0];
    }

    /************************  263. Ugly Number  ************************************/
    public boolean isUgly(int n) {
        while(n != 1 && n%2 != 0) n/=2;
        while(n != 1 && n%3 != 0) n/=3;
        while(n != 1 &&n%5 != 0) n/=5;
        return n==1;
    }


    /************************  Ugly Numbers GeeksForGeeks  ************************************/
    long getNthUglyNo(int n) {
        // code here
        long nextTwo = 2, nextThree = 3, nextFive = 5;
        int two = 0, three=0, five=0;
        long[] arr = new long[n];
        arr[0] = 1;
        for(int i=1; i<arr.length; i++) {
            long next = Math.min(nextTwo, Math.min(nextThree, nextFive));
            if(next == nextTwo) {
                two++;
                nextTwo = arr[two]*2;
            }
            if(next == nextThree) {
                three++;
                nextThree = arr[three]*3;
            }
            if(next == nextFive) {
                five++;
                nextFive = arr[five]*5;
            }
        }
        return arr[n-1];
    }

    /************************  313. Super Ugly Number  ************************************/

//    public int nthSuperUglyNumber(int n, int[] primes) {
//        int[] dp = new int[n];
//        int[] pointer = new int[n];
//        int[] arr = primes.clone();
//        dp[0] = 1;
//        for(int i=1; i<n; i++) {
//            dp[i] = Integer.MAX_VALUE;
//            for(int j=0; j<primes.length; j++) {
//                dp[i] = Math.min(dp[i], arr[j]);
//            }
//            for(int j=0; j<primes.length; j++) {
//                if(arr[j] == dp[i]) {
//                    pointer[j]++;
//                    arr[j] = dp[pointer[j]]*primes[j];
//                }
//            }
//        }
//        return dp[n-1];
//    }

    public int nthSuperUglyNumber(int n, int[] primes) {
        int[] dp = new int[n];
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        for(int val : primes) pq.add(new int[]{val, 1, val});
        dp[0] = 1;
        for(int i=1; i<n; i++) {
            dp[i] = pq.poll()[2];
            while(dp[i] == pq.poll()[2]) {
                int[] usedUgly = pq.poll();
                pq.add(new int[]{usedUgly[0], usedUgly[1] + 1, usedUgly[0]*(usedUgly[1] + 1)});
            }
        }
        return dp[n-1];
    }

//     Approach Using Heap

//    public int nthSuperUglyNumber(int n, int[] primes) {
//        int[] dp = new int[n];
//        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
//        for(int val : primes) pq.add(new int[]{val, 1, val});
//        dp[0] = 1;
//        for(int i=1; i<n; i++) {
//            dp[i] = pq.peek()[2];
//            while(dp[i] == pq.peek()[2]) {
//                int[] usedUgly = pq.poll();
//                pq.add(new int[]{usedUgly[0], usedUgly[1] + 1, usedUgly[0]*dp[usedUgly[1]]});
//            }
//        }
//        return dp[n-1];
//    }

    /************************  132. Palindrome Partitioning II (1 approach remaining O(n) space)  ************************************/
    public int minCut(String s) {
        boolean[][] check = new boolean[s.length()][s.length()];
        for(int gap=0; gap<check.length; gap++) {
            for(int i=0,j=gap; j<check.length; j++, i++) {
                if(gap == 0) check[i][j] = true;
                else if(gap == 1) check[i][j] = s.charAt(i) == s.charAt(j);
                else {
                    check[i][j] = (s.charAt(i) == s.charAt(j)) && check[i+1][j-1];
                }
            }
        }
        int[] dp = new int[s.length()];
        dp[0] = 0;
        for(int j=1; j<dp.length; j++) {
            if(!check[0][j]) {
                int minv = Integer.MAX_VALUE;
                for(int i=j; i>=1; i--) {
                    if(check[i][j]) minv = Math.min(minv, dp[i-1]);
                }
                dp[j] = minv+1;
            }
        }
        return dp[s.length() - 1];
    }

    /************************  312. Burst Balloons (Important)  ************************************/
    public int maxCoins(int[] nums) {
        int[][] dp = new int[nums.length][nums.length];
        for(int gap=0; gap<dp.length; gap++) {
            for(int i=0, j=gap; j<dp[0].length; i++,j++) {
                dp[i][j] = Integer.MIN_VALUE;
                for(int k=i; k<=j; k++) {
                    int left = (k == i)?0:dp[i][k-1];
                    int right = (k == j)?0:dp[k+1][j];
                    int curr = (i==0?1:nums[i-1])*nums[k]*(j==nums.length-1?1:nums[j+1]);
                    dp[i][j] = Math.max(left + curr + right, dp[i][j]);
                }
            }
        }
        return dp[0][dp[0].length - 1];
    }

    /************************  1031. Maximum Sum of Two Non-Overlapping Subarrays  ************************************/
    public int findMaxSumTwoNoOverlap(int[] nums, int firstLen, int secondLen) {
        int[] first = new int[nums.length];
        int[] second = new int[nums.length];
        int sum = 0;
        for(int i=0; i<nums.length; i++) {
            sum+=nums[i];
            if(i+1<firstLen)continue;
            if(i+1==firstLen) first[i]=sum;
            else {
                sum-=nums[i-firstLen];
                first[i] = Math.max(sum, first[i-1]);
            }
        }
        sum=0;
        for(int i=nums.length - 1; i>=0; i--) {
            sum+=nums[i];
            if(nums.length - i < secondLen) continue;
            if(nums.length - i == secondLen) second[i] = sum;
            else {
                sum-=nums[i+secondLen];
                second[i] = Math.max(second[i+1], sum);
            }
        }
        int max = Integer.MIN_VALUE;
        for(int i=firstLen-1; i+secondLen<nums.length; i++) {
            max = Math.max(first[i] + second[i+1], max);
        }
        return max;
    }
    public int maxSumTwoNoOverlap(int[] nums, int firstLen, int secondLen) {
        return Math.max(findMaxSumTwoNoOverlap(nums, firstLen, secondLen),
                findMaxSumTwoNoOverlap(nums, secondLen, firstLen));
    }

    /************************  689. Maximum Sum of 3 Non-Overlapping Subarrays  ************************************/
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int[] first = new int[nums.length];
        int[] second = new int[nums.length];
        int sum = 0;
        for(int i=0; i<nums.length; i++) {
            sum+=nums[i];
            if(i+1<k)continue;
            if(i+1==k) {
                first[i]=sum;
            }
            else {
                sum-=nums[i-k];
                first[i] = Math.max(sum, first[i-1]);
            }
        }
        sum=0;
        for(int i=nums.length - 1; i>=0; i--) {
            sum+=nums[i];
            if(nums.length - i < k) continue;
            if(nums.length - i == k) {
                second[i] = sum;
            }
            else {
                sum-=nums[i+k];
                second[i] = Math.max(sum, second[i+1]);
            }
        }
        int[] pSum = new int[nums.length];
        pSum[0] = nums[0];
        for(int i=1; i<pSum.length; i++) {
            pSum[i] = nums[i]+pSum[i-1];
        }
        int maxv = Integer.MIN_VALUE;
        int start=-1, mid=-1, end=-1;
        int lsum = -1;
        int rsum = -1;
        int psum = 0;
        for(int idx = k; idx+2*k<=nums.length; idx++) {
            psum = first[idx-1]+second[idx+k]+pSum[idx+k-1]-pSum[idx-1];
            if(maxv < psum) {
                maxv = psum;
                lsum = first[idx-1];
                mid = idx;
                rsum = second[idx+k];
            }
        }
        for(int idx=k-1; idx<mid; idx++) {
            if(lsum == (idx-k<0?pSum[idx]:pSum[idx]-pSum[idx-k])) {
                start=idx-k+1;
                break;
            }
        }
        for(int idx=mid+2*k-1; idx<nums.length; idx++) {
            if(rsum == (pSum[idx] - pSum[idx-k])) {
                end=idx-k+1;
                break;
            }
        }
        return new int[]{start, mid, end};
    }

}

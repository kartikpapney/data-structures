package AdvanceDynamicProgramming;

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

    /************************  689. Maximum Sum of 3 Non-Overlapping Subarrays  ************************************/
    Integer[][][] dp = new Integer[51][51][51];
    int mod = (int)1e9+7;
    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
        if(startRow==m || startColumn==n || startRow==-1 || startColumn ==-1) return 1;
        if(maxMove==0) return 0;
        if(dp[startRow][startColumn][maxMove] != null) return dp[startRow][startColumn][maxMove];
        int a = findPaths(m, n, maxMove-1, startRow+1, startColumn);
        int b = findPaths(m, n, maxMove-1, startRow-1, startColumn);
        int c = findPaths(m, n, maxMove-1, startRow, startColumn+1);
        int d = findPaths(m, n, maxMove-1, startRow, startColumn-1);

        dp[startRow][startColumn][maxMove] = ((a+b)%mod+(c+d)%mod)%mod;
        return dp[startRow][startColumn][maxMove];
    }

    public int findLongestDecomposition(String text, int start, int len) {
        int s1 = start, s2 = text.length() - start - len, e1 = start+len, e2 = text.length() - start;
        if(s2 > e1) {
            return len == 1?0:Integer.MIN_VALUE;
        }
        int a=0, b=0;
        if(text.substring(s1, e1).equals(text.substring(s2, e2))) a = 2 + findLongestDecomposition(text, start+1, 1);
        b = findLongestDecomposition(text, start, len+1);
        return Math.max(a, b);
    }
    public int longestDecomposition(String text) {
        return findLongestDecomposition(text, 0, 1);
    }

    /************************  1048. Longest String Chain  ************************************/

//    Accepted 168ms
    /*

    public boolean checkIfPredecessor(String a, String b) {
        if(a.length() != b.length() + 1) return false;
        boolean check = true;
        int i=0, j=0;
        while(j < b.length()) {
            if(a.charAt(i) == b.charAt(j)) {
                i++;
                j++;
            } else {
                if(check) {
                    i++;
                    check = false;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
    public int findLongestStrChain(String[] words, int i, int lastIdx, Integer[][] dp) {
        if(i == words.length) return 0;
        if(dp[i+1][lastIdx+1] != null) return dp[i+1][lastIdx+1];
        int a = 0, b = 0;
        if(lastIdx == -1 || checkIfPredecessor(words[i], words[lastIdx])) a = 1 + findLongestStrChain(words, i+1, i, dp);
        b = findLongestStrChain(words, i+1, lastIdx, dp);
        return dp[i+1][lastIdx+1] = Math.max(a, b);
    }
    public int longestStrChain(String[] words) {
        Arrays.sort(words, (a, b)->a.length()-b.length());
        Integer[][] dp = new Integer[words.length + 1][words.length + 1];
        return findLongestStrChain(words, 0, -1, dp);
    }

    */

    // Using HashMap + StringBuilder 29 ms

    public int longestStrChain(String[] words) {
        Arrays.sort(words, (a, b)->(a.length() - b.length()));
        Map<String, Integer> dp = new LinkedHashMap<>();
        int ans = 0;
        for(var word : words) {
            int best = 1;
            StringBuilder strb = new StringBuilder(word);
            for(int i=0; i<word.length(); i++) {
                strb.deleteCharAt(i);
                best = Math.max(best, dp.getOrDefault(strb.toString(), 0) + 1);
                strb.insert(i, word.charAt(i));
            }
            dp.put(word, best);
            ans = Math.max(ans, best);
        }
        return ans;
    }

    /************************  1749. Maximum Absolute Sum of Any Subarray  ************************************/
    /*
    public int positiveKadanes(int[] nums) {
        int omax = Integer.MIN_VALUE;
        int curr = 0;
        for(int i=0; i<nums.length; i++) {
            curr+=nums[i];
            if(curr<nums[i]) curr = nums[i];

            omax = Math.max(omax, curr);
        }
        return omax;
    }
    public int negativeKadanes(int[] nums) {
        int omax = Integer.MAX_VALUE;
        int curr = 0;
        for(int i=0; i<nums.length; i++) {
            curr+=nums[i];
            if(curr>nums[i]) curr = nums[i];

            omax = Math.min(omax, curr);
        }
        return omax;
    }

    public int maxAbsoluteSum(int[] nums) {
        int a = positiveKadanes(nums);
        int b = negativeKadanes(nums);
        return Math.max(a, Math.abs(b));
    }
    */

//    Important solution
    public int maxAbsoluteSum(int[] nums) {
        int sum = 0;
        int max=0, min=0;
        for(int v : nums) {
            sum+=v;
            max = Math.max(max, sum);
            min = Math.min(min, sum);
        }
        return max-min;
    }

    /************************  1749. Maximum Absolute Sum of Any Subarray  ************************************/

    public int minimumDeletions(String s) {
        int[] b = new int[s.length()];
        int[] a = new int[s.length()];
        int c = s.charAt(0) == 'b'?1:0;
        for(int i=1; i<b.length; i++) {
            b[i] = c;
            if(s.charAt(i) == 'b') c++;
        }
        c = s.charAt(s.length() - 1) == 'a'?1:0;
        for(int i=s.length()-2; i>=0; i--) {
            a[i] = c;
            if(s.charAt(i) == 'a') c++;
        }
        int ans = Integer.MAX_VALUE;
        for(int i=0; i<a.length; i++) {
            ans = Math.min(ans, a[i] + b[i]);
        }
        return ans;
    }

    /************************  1162. As Far from Land as Possible  ************************************/
    /*
    public int maxDistance(int[][] grid) {
        int[][] dp = new int[grid.length][grid[0].length];
        for(int i=0; i<dp.length; i++) {
            for(int j=0; j<dp[i].length; j++) {
                if(grid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = Integer.MAX_VALUE - 1;
                }
            }
        }

        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[i].length; j++) {
                if(grid[i][j] == 0) {
                    if(i == 0 && j == 0) {}
                    else if(i == 0) {
                        dp[i][j] = Math.min(dp[i][j-1] + 1, dp[i][j]);
                    } else if(j == 0) {
                        dp[i][j] = Math.min(dp[i-1][j] + 1, dp[i][j]);
                    } else {
                        dp[i][j] = Math.min(Math.min(1+dp[i-1][j], 1+dp[i][j-1]), dp[i][j]);
                    }
                }
                if(dp[i][j] == Integer.MAX_VALUE) dp[i][j]--;
            }
        }

        for(int i=0; i<grid.length; i++) {
            for(int j=grid[i].length-1; j>=0; j--) {
                if(grid[i][j] == 0) {
                    if(i == 0 && j == grid[i].length-1) {}
                    else if(i == 0) {
                        dp[i][j] = Math.min(dp[i][j+1] + 1, dp[i][j]);
                    } else if(j == grid[i].length-1) {
                        dp[i][j] = Math.min(dp[i-1][j] + 1, dp[i][j]);
                    } else {
                        dp[i][j] = Math.min(dp[i][j], Math.min(1+dp[i-1][j], 1+dp[i][j+1]));
                    }
                }
                if(dp[i][j] == Integer.MAX_VALUE) dp[i][j]--;
            }
        }

        for(int i=grid.length-1; i>=0; i--) {
            for(int j=grid[i].length-1; j>=0; j--) {
                if(grid[i][j] == 0) {
                    if(i == grid.length - 1 && j == grid[i].length-1) {}
                    else if(i == grid.length-1) {
                        dp[i][j] = Math.min(dp[i][j+1] + 1, dp[i][j]);
                    } else if(j == grid[i].length-1) {
                        dp[i][j] = Math.min(dp[i+1][j] + 1, dp[i][j]);
                    } else {
                        dp[i][j] = Math.min(dp[i][j], Math.min(1+dp[i+1][j], 1+dp[i][j+1]));
                    }
                }
                if(dp[i][j] == Integer.MAX_VALUE) dp[i][j]--;
            }
        }

        for(int i=grid.length-1; i>=0; i--) {
            for(int j=0; j<grid[i].length; j++) {
                if(grid[i][j] == 0) {
                    if(i == grid.length - 1 && j == 0) {}
                    else if(i == grid.length-1) {
                        dp[i][j] = Math.min(dp[i][j-1] + 1, dp[i][j]);
                    } else if(j == 0) {
                        dp[i][j] = Math.min(dp[i+1][j] + 1, dp[i][j]);
                    } else {
                        dp[i][j] = Math.min(dp[i][j], Math.min(1+dp[i+1][j], 1+dp[i][j-1]));
                    }
                }
                if(dp[i][j] == Integer.MAX_VALUE) dp[i][j]--;
            }
        }
        int res = -1;
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[i].length; j++) {
                if(grid[i][j] == 0) res = Math.max(res, dp[i][j]);
            }
        }
        return res==Integer.MAX_VALUE-1?-1:res;
    }
    */

    /************************  1162. As Far from Land as Possible  (Using Queue, Less code length) ************************************/
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

    public int minSwap(int[] nums1, int[] nums2) {
        int[] dp = new int[nums1.length];
        for(int i=1; i<dp.length; i++) {
            if(nums1[i] > nums1[i-1] && nums2[i] > nums2[i-1]) dp[i] = dp[i-1];
            else {
                int temp = nums1[i];
                nums1[i] = nums2[i];
                nums2[i] = temp;
                dp[i] = dp[i-1] + 1;
            }
        }
        return dp[dp.length - 1];
    }
    public int findMinimumDistance(char[] word, int idx, int x, int y, int p, int q) {
        if(idx == word.length) return 0;
        int a = Integer.MAX_VALUE, b = Integer.MAX_VALUE;
        int posx = (word[idx] - 'A')/7;
        int posy = (word[idx] - 'A')%7;
        a = Math.abs(posx - x) + Math.abs(posy - y) + findMinimumDistance(word, idx+1, posx, posy, p, q);
        if(p != Integer.MAX_VALUE) b = Math.abs(posx - p) + Math.abs(posy - q) + findMinimumDistance(word, idx+1, x, y, posx, posy);
        else b = findMinimumDistance(word, idx+1, x, y, posx, posy);
        return Math.min(a, b);
    }
    public int minimumDistance(String w) {
        char[] word = w.toCharArray();
        int x = (word[0] - 'A')/7;
        int y = (word[0] - 'A')%7;
        return findMinimumDistance(word, 1, x, y, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public List<Integer> find(StringBuilder expression, int start, int end, List<Integer>[][] dp) {
        if(start == end) return new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        if(dp[start][end] != null) return dp[start][end];
        for(int i=start; i<end; i++) {
            char c = expression.charAt(i);
            if(c=='+' || c == '-' || c == '*') {
                List<Integer> a = find(expression, 0, i, dp);
                List<Integer> b = find(expression, i+1, end, dp);
                for(int v1 : a) {
                    for(int v2 : b) {
                        switch (c) {
                            case '+':
                                res.add(v1+v2);
                                break;
                            case '-':
                                res.add(v1-v2);
                                break;
                            default:
                                res.add(v1*v2);
                        }
                    }
                }
            }
        }
        if(res.isEmpty()){
            res.add(Integer.parseInt(expression.toString()));
        }
        dp[start][end] = res;
        return res;
    }
    public List<Integer> diffWaysToCompute(String expression) {
        List<Integer>[][] dp = new List[expression.length()][expression.length()];
        return find(new StringBuilder(expression), 0, expression.length(), dp);
    }

    /************************  1578. Minimum Deletion Cost to Avoid Repeating Letters ************************************/

    public int findCost(char[] s, int[] cost, int idx, char c, Integer[][] dp) {
        if(idx == s.length) return 0;
        if(dp[idx][c-'a'] != null) return dp[idx][c-'a'];
        int a = 0, b=Integer.MAX_VALUE;
        a = cost[idx] + findCost(s, cost, idx+1, c, dp);
        if(s[idx] != c) b = findCost(s, cost, idx+1, s[idx], dp);
        return dp[idx][c-'a'] = Math.min(a, b);
    }
    public int minCost(String s, int[] cost) {
        Integer[][] dp = new Integer[s.length()][27];
        return findCost(s.toCharArray(), cost, 0, (char)123, dp);
    }

//    Greedy Solution O(N)

//    public int minCost(String str, int[] cost) {
//        char[] s = str.toCharArray();
//        int maxCost = 0, ans = 0;
//        for(int i=0; i<s.length; i++) {
//            if(i>0 && s[i] != s[i-1]) maxCost = 0;
//            ans+=Math.min(cost[i], maxCost);
//            maxCost = Math.max(maxCost, cost[i]);
//        }
//        return ans;
//    }

    public int countToMakePalindrome(StringBuilder s, int start, int end) {
        int cnt = 0;
        while(start < end) {
            if(s.charAt(start) != s.charAt(end)) cnt++;
            start++;
            end--;
        }
        return cnt;
    }


    public int findPalindromePartition(StringBuilder s, int start, int k, Integer[][] dp) {
        if(dp[start][k] != null) return dp[start][k];
        if(k == 1) {
            return dp[start][k] = countToMakePalindrome(s, start, s.length()-1);
        }
        int minv = Integer.MAX_VALUE;
        for(int i=start; i<=s.length()-k; i++) {
            int a = findPalindromePartition(s, i+1, k-1, dp) + countToMakePalindrome(s, start, i);
            minv=Math.min(a, minv);
        }
        return dp[start][k] = minv;
    }

    public int palindromePartition(String s, int k) {
        Integer[][] dp = new Integer[s.length()+1][k+1];
        int v = findPalindromePartition(new StringBuilder(s), 0, k, dp);
        return v;
    }

    /************************  198. House Robber ************************************/
    public int robi(int[] nums, int start, int end) {
//         arr[0] = inc current element
//         arr[1] = don't inc current element
        int[] arr = new int[2];
        for(int i=start; i<end; i++) {
            int includeCurrentElement = arr[1] + nums[i];
            int dontIncludeCurrentElement = Math.max(arr[0], arr[1]);
            arr[0] = includeCurrentElement;
            arr[1] = dontIncludeCurrentElement;
        }
        return Math.max(arr[0], arr[1]);
    }

    /************************  213. House Robber II ************************************/
    public int robii(int[] nums) {
        if(nums.length == 1) return nums[0];
        return Math.max(robi(nums, 0, nums.length - 1), robi(nums, 1, nums.length));
    }

    /************************  376. Wiggle Subsequence ************************************/
    public int wiggleMaxLength(int[] nums) {
        int[] up = new int[nums.length], down = new int[nums.length];
        up[0] = down[0] = 1;
        for(int i=1; i<nums.length; i++) {
            if(nums[i] > nums[i-1]) {
                up[i] = down[i-1] + 1;
                down[i] = down[i-1];
            } else if(nums[i] < nums[i-1]) {
                down[i] = up[i-1] + 1;
                up[i] = up[i-1];
            } else {
                up[i] = up[i-1];
                down[i] = down[i-1];
            }
        }
        return Math.max(up[up.length - 1], down[down.length - 1]);
    }
    /************************ 396. Rotate Function ************************************/
    public int maxRotateFunction(int[] nums) {
        int n = nums.length;
        int sum = 0, prev = 0;
        for(int i=0; i<nums.length; i++) {
            sum+=nums[i];
            prev+=i*nums[i];
        }
        int max = prev;
        for(int i=1; i<nums.length; i++) {
            prev+=sum;
            prev-=nums[n-i]*n;
            max = Math.max(prev, max);
        }

        return max;
    }
    /************************ Integer Replacement ************************************/
    HashMap<Integer, Integer> map = new HashMap<>();
    public int find(int n) {
        if(n == 1) return 0;
        if(map.containsKey(n)) return map.get(n);
        if(n%2 == 0) {
            int v = 1 + find(n/2);
            map.put(n, v);
            return v;
        }
        int v = 2 + Math.min(find(n/2+1), find(n/2));
        map.put(n, v);
        return v;
    }
    public int integerReplacement(int n) {
        return find(n);
    }

    /************************ 93. Restore IP Addresses ************************************/
    List<String> res;
    public void restoreIp(String s, int idx, int state, StringBuilder cans) {
        if(idx == s.length()) {
            if(state == 5) {
                res.add(cans.substring(1));
            }
            return;
        }
        if(state == 5) return;
        for(int i=1; i+idx <= s.length() && i <= 3; i++) {
            String currStr = s.substring(idx, idx+i);
            if(currStr.length() >= 2 && currStr.charAt(0) == '0') return;
            int value = Integer.parseInt(currStr);
            if(value >= 0 && value <= 255) {
                cans.append('.').append(currStr);
                restoreIp(s, i+idx, state+1, cans);
                cans.delete(cans.length() - currStr.length(), cans.length());
            }
        }
    }
    public List<String> restoreIpAddresses(String s) {
        res = new ArrayList<>();
        if(s.length() > 12) return res;
        restoreIp(s, 0, 1, new StringBuilder());
        return res;
    }

    /************************ 491. Increasing Subsequences  (Important) ************************************/
    List<List<Integer>> result;
    public void findSubsequences(int[] nums, int idx, List<Integer> clist) {
        if(nums.length == idx) {
            if(clist.size() > 1) result.add(new ArrayList<>(clist));
            return;
        }
        if(clist.size() == 0 || nums[idx] >= clist.get(clist.size() - 1)) {
            clist.add(nums[idx]);
            findSubsequences(nums, idx+1, clist);
            clist.remove(clist.size() - 1);
        }
        if(clist.size()>0 && nums[idx] == clist.get(clist.size() - 1)) return;
        findSubsequences(nums, idx+1, clist);
    }
    public List<List<Integer>> findSubsequences(int[] nums) {
        result = new ArrayList<>();
        List<Integer> clist = new ArrayList<>();
        findSubsequences(nums, 0, clist);
        return result;
    }
    /************************ 842. Split Array into Fibonacci Sequence ************************************/
    public boolean findSplit(String str, int idx, List<Integer> resInt) {
        if(idx == str.length()) return true;
        int n = resInt.size();
        boolean check = false;
        for(int i=idx+1; i<=str.length(); i++) {
            String curr = str.substring(idx, i);
            if(curr.length() == 2 && curr.charAt(0) == 0) return false;
            if(Long.parseLong(curr) > Integer.MAX_VALUE) break;
            int c = Integer.parseInt(curr);
            if(resInt.get(n-1) + resInt.get(n-2) == c) {
                resInt.add(c);
                check = findSplit(str, i, resInt);
                if(check) return true;
            }
        }
        return check;
    }
    public List<Integer> splitIntoFibonacci(String num) {
        for(int j=1; j<num.length()-1; j++) {
            String first = num.substring(0, j);
            if(first.length() > 1 && first.charAt(0) == '0') break;
            if(Long.parseLong(first) > Integer.MAX_VALUE) break;
            for(int k=j+1; k<num.length(); k++) {
                String second = num.substring(j, k);
                if(second.length() > 1 && second.charAt(0) == '0') break;
                if(Long.parseLong(second) > Integer.MAX_VALUE) break;
                List<Integer> resInt = new ArrayList<>();
                int a = Integer.parseInt(first);
                int b = Integer.parseInt(second);
                resInt.add(a);
                resInt.add(b);
                if(findSplit(num, k, resInt)) return resInt;
            }
        }
        return new ArrayList<>();
    }
    /************************ 300. Longest Increasing Subsequence ************************************/
//    public int lengthOfLIS(int[] nums) {
//        int[] dp = new int[nums.length];
//        dp[0] = 1;
//        int omax = 1;
//        for(int i=1; i<dp.length; i++) {
//            dp[i] = 1;
//            for(int j=i-1; j>=0; j--) {
//                if(nums[i] > nums[j]) dp[i] = Math.max(dp[i], dp[j]+1);
//            }
//            omax = Math.max(omax, dp[i]);
//        }
//        return omax;
//    }

//    O(NlogN)

    public int bsLIS(List<Integer> nums, int val) {
        int start = 0;
        int end = nums.size() - 1;
        int ceil = nums.size();
        while(start <= end) {
            int mid = start + (end-start)/2;
            if(nums.get(mid) == val) {
                ceil = mid;
                break;
            } else if(nums.get(mid) < val) {
                start = mid+1;
            } else {
                ceil = mid;
                end = mid-1;
            }
        }
        return ceil;
    }
    public int lengthOfLIS(int[] nums) {
        List<Integer> list = new ArrayList<>();
        list.add(nums[0]);
        for(int i=1; i<nums.length; i++) {
            int idx = bsLIS(list, nums[i]);
            if(idx == list.size()) list.add(nums[i]);
            else list.set(idx, nums[i]);
        }
        return list.size();
    }
    /************************ 1201. Ugly Number III ************************************/
    public int nthUglyNumber(int n, int a, int b, int c) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((i, j) -> i[0]*i[1] - j[0]*j[1]);
        pq.add(new int[]{a, 1});
        pq.add(new int[]{b, 1});
        pq.add(new int[]{b, 1});
        while(n-- != 1) {
            int[] curr = pq.poll();
            int value = curr[0]*curr[1];
            pq.add(new int[]{curr[0], curr[1]+1});
            while(true) {
                int[] peek = pq.peek();
                if(peek[0]*peek[1] == value) {
                    pq.add(new int[]{peek[0], peek[1]+1});
                    pq.poll();
                } else {
                    break;
                }
            }
        }
        int[] ans = pq.poll();
        return ans[0]*ans[1];
    }
    /************************ 845. Longest Mountain in Array ************************************/
    public int longestMountain(int[] arr) {
        int max = 0, up = 0, down = 0;
        for(int i=1; i<arr.length; i++) {
            if(down > 0 && arr[i-1] < arr[i] || arr[i-1] == arr[i]) up = down = 0;
            if(arr[i] > arr[i-1]) up++;
            if(arr[i] < arr[i-1]) down++;
            if(up > 0 && down > 0) max = Math.max(up + down + 1, max);
        }
        return max;
    }

    /************************ 1671. Minimum Number of Removals to Make Mountain Array ************************************/
    public int binarySearch(List<Integer> nums, int val) {
        int start = 0;
        int end = nums.size() - 1;
        int mid = 0;
        while(start <= end) {
            mid  = start + (end-start)/2;
            if(nums.get(mid) == val) {
                return mid;
            } else if(nums.get(mid) > val) {
                end = mid-1;
            } else {
                start = mid+1;
            }
        }
        return start;
    }
    public int minimumMountainRemovals(int[] nums) {
        int[] left = new int[nums.length];
        int[] right = new int[nums.length];
        List<Integer> arr = new ArrayList<>();
        arr.add(nums[0]);
        for(int i=1; i<nums.length; i++) {
            int pos = binarySearch(arr, nums[i]);
            if(pos == arr.size()) {
                arr.add(nums[i]);
            } else {
                arr.set(pos, nums[i]);
            }
            left[i] = pos;
        }
        arr = new ArrayList<>();
        arr.add(nums[nums.length-1]);
        for(int i=nums.length-2; i>=0; i--) {
            int pos = binarySearch(arr, nums[i]);
            if(pos == arr.size()) {
                arr.add(nums[i]);
            } else {
                arr.set(pos, nums[i]);
            }
            right[i] = pos;
        }
        int len = 0;
        for(int i=1; i<nums.length-1; i++) {
            if(left[i] != 0 && right[i] != 0) {
                len = Math.max(len, left[i] + right[i] + 1);
            }
        }
        return nums.length-len;
    }

    public boolean canFinish(int numCourses, int[][] pre) {
        boolean[] visited = new boolean[numCourses];
        ArrayList[] arr = new ArrayList[numCourses];
        for(int i=0; i<arr.length; i++) {
            arr[i] = new ArrayList();
        }
        for(int i=0; i<pre.length; i++) {
            arr[pre[i][0]].add(pre[i][1]);
        }
        Queue<Integer> q = new ArrayDeque<>();
        q.add(pre[0][0]);
        while(!q.isEmpty()) {
            int rem = q.remove();
            if(visited[rem]) return false;
            visited[rem] = true;
            for(int i=0; i<arr[rem].size(); i++) {
                Integer nbr = (Integer) arr[rem].get(i);
                if(!visited[nbr]) {
                    q.add(nbr);
                }
            }
        }
        return true;
    }
}
package Arrays;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Set3 {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            return a[0] + a[1] - b[0] - b[1];
        });
        List<List<Integer>> res = new ArrayList<>();
        if(nums1.length == 0 || nums2.length == 0 || k == 0) return res;
        for(int i=0; i<nums1.length && i < k; i++) pq.add(new int[]{nums1[i], nums2[0], 0});
        while(k-- != 0) {
            int[] curr = pq.remove();
            List<Integer> sans = new ArrayList<>();
            sans.add(curr[0]);
            sans.add(curr[1]);
            res.add(sans);
            if(curr[2] == nums2.length - 1) continue;
            pq.add(new int[]{curr[0], curr[1], curr[2]+1});
        }
        return res;
    }
    /************************  1376. Time Needed to Inform All Employees ************************************/
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> matrix[a[0]][a[1]] - matrix[b[0]][b[1]]);
        for(int j=0; j<matrix.length; j++) pq.add(new int[]{0, j});
        while(k-- != 1) {
            int[] element = pq.remove();
            if(element[0] == matrix.length-1) continue;
            pq.add(new int[]{element[0]+1, element[1]});
        }
        int[] res = pq.remove();
        return matrix[res[0]][res[1]];
    }
    /************************  33. Search in Rotated Sorted Array ************************************/
    public int search(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while(start <= end) {
            int mid = start + (end - start)/2;
            if(nums[mid] == target) return mid;
            else if(nums[start] <= nums[mid]) {
                // left side is sorted
                if(nums[mid] > target && nums[start] <= target) {
                    end = mid-1;
                } else {
                    start = mid+1;
                }
            } else {
                // right side is sorted
                if(target > nums[mid] && target <= nums[end]) {
                    start = mid+1;
                } else {
                    end = mid-1;
                }
            }
        }
        return -1;
    }
    /************************  81. Search in Rotated Sorted Array II ************************************/
    public boolean searchii(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while(start <= end) {
            int mid = start + (end - start)/2;
            if(nums[mid] == target) return true;

            // Only need to handle this condition when we're unable to decide that where to go left or right side of the array
            else if(nums[start] == nums[mid] && nums[mid] == nums[end]) {
                start++;
                end--;
            } else if(nums[start] <= nums[mid]) {
                // left side is sorted
                if(nums[mid] > target && nums[start] <= target) {
                    end = mid-1;
                } else {
                    start = mid+1;
                }
            } else {
                // right side is sorted
                if(target > nums[mid] && target <= nums[end]) {
                    start = mid+1;
                } else {
                    end = mid-1;
                }
            }
        }
        return false;
    }

    /************************  Divide the array into three parts such that all the parts have equal sum ************************************/
    public int solve(int A, ArrayList<Integer> B) {
        int[] arr = new int[B.size()];
        arr[0] = B.get(0);
        for(int i=1; i<arr.length; i++) {
            arr[i] = arr[i-1] + B.get(i);
        }
        int sum = arr[B.size() - 1];
        if(sum%3 != 0) return 0;
        int ans = 0;
        int sumby3 = 0;
        for(int i=0; i<arr.length-1; i++) {
            if(arr[i] == 2*(sum/3)) {
                ans+=sumby3;
            }
            if(arr[i] == sum/3) sumby3++;
        }
        return ans;
    }

    /************************   153. Find Minimum in Rotated Sorted Array ************************************/
    public int findMin(int[] nums) {
        int start = 0, end = nums.length - 1;
        int mid = 0;
        while(start < end) {
            mid = start + (end - start)/2;
            if(nums[mid] > nums[end]) start = mid+1;
            else end = mid;
        }
        return nums[start];
    }

    /************************  154. Find Minimum in Rotated Sorted Array II  ************************************/
    public int findMinII(int[] nums) {
        int start = 0, end = nums.length - 1;
        int mid = 0;
        while(start < end) {
            mid = start + (end - start)/2;
            if(nums[mid] > nums[end]) start = mid+1;
            else if(nums[mid] < nums[end]) end = mid;
            // ONLY HANDLE THIS CASE
            else {
                end--;
            }
        }
        return nums[start];
    }

    /************************  179. Largest Number  ************************************/
    // https://leetcode.com/problems/largest-number/
    public String largestNumber(int[] nums) {
        String[] arr = new String[nums.length];
        for(int i=0; i<nums.length; i++) {
            arr[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(arr, (a, b) -> {
            String x = a+b;
            String y = b+a;
            return y.compareTo(x);
        });
        StringBuilder strb = new StringBuilder();
        for(String a : arr) strb.append(a);
        if(strb.charAt(0) == '0') return "0";
        return strb.toString();
    }




}

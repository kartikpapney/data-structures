import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {


    public static long maxProfit(int[] arr, long order) {
        ArrayList<Integer> nums = new ArrayList<>();
        for(int val : arr) nums.add(val);
        Collections.sort(nums, Collections.reverseOrder());
        int profit = 0;
        for(int i=1; i<nums.size() && order != 0; i++) {
            int diff = nums.get(i-1)-nums.get(i);
            long take = Math.min(order, diff);
            order-=take;
            long csum = ((long)nums.get(i-1)*(nums.get(i-1)+1))/2;
            long psum = ((long)nums.get(i)*(nums.get(i)+1))/2;
            profit+=i*(csum-psum);
        }
        return profit;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(maxProfit(new int[]{3, 5}, 6));
    }



}

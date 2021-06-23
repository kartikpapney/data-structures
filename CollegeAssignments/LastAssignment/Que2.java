package CollegeAssignments.LastAssignment;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * DAA LAB Assignment
 * Submitted By
 * Name = Kartik Papney
 * Roll No = CSB19047
 * */



public class Que2{

    /**
     * input -> n = 10, k=4
     * output -> 4 no of steps will be needed
     *
     * input -> n=10, k=2
     * output -> 4 no of steps will be needed
     *
     * input -> n=5, k=3
     * output -> 3 no of steps will be needed
     */

    /***
     *
     * @param n = number of floors in building
     * @param k = number of dish plates we have
     * @return min no of attempts needed to find the critical floor with n size building using k plates
     */
    public static int DropBreak(int n, int k) {

        /**
         * dp[i][j] will store the minimum number of attempts needed to find critical floor if we have j floor building
         * and i dish plates
         */
        int[][] dp = new int[k + 1][n + 1];

        for (int dish = 0; dish < dp.length; dish++) {

            for (int floor = 0; floor < dp[0].length; floor++) {
                // If we have 0 floor building then no attempt is needed to find critical floor
                if (floor == 0) dp[dish][floor] = 0;
                    // if dish == 0 and floor isn't = 0 then it means we've 0 dish plates then we can't find critical floor
                    // So I'm storing Infinite means finding critical floor isn't possible
                else if (dish == 0) dp[dish][floor] = Integer.MAX_VALUE;
                    // If we have 1 floor building then at max 1 attempt is needed to find critical point
                    // either the dish plate will break from 1st floor or there will be no critical point
                else if (floor == 1) dp[dish][floor] = 1;
                    // if we have only 1 plate then we'll start from the bottom most floor and will increase the floor one by one
                    // the moment our dish plate breaks that we'll be our critical floor so atmax j(no of floors) will be needed
                    // to find critical floor
                else if (dish == 1) dp[dish][floor] = floor;

                else {
                    int minimumAttempts = Integer.MAX_VALUE;
                    // We want min no of attempts needed to find critical floor for "floor" sized building
                    // for this we'll get the min no of attempts needed to find critical floor if our building size is [0, floor)
                    // and then we'll take the minimum no of attempts needed from all these options let say minAttempts

                    // No min no of attempts needed to find critical floor of building with floor no of
                    // floors is minAttempts(to find critical point for building with size [0,floor)) + 1
                    for (int f = 1; f < floor; f++) {

                        // dropping the dish plate from floor = f either the plate will break or it'll not break;

                        // If plate doesn't breaks
                        // -> then our critical floor can't be in range [1,f] it will be above f so floor-f size building
                        int doesNotBreak = dp[dish][floor - f];

                        // If plate breaks
                        // then we have dish-1 no of plates
                        // -> our critical floor will be in range [1,f]
                        int doesBreak = dp[dish - 1][f - 1];
                        int val = Math.max(doesNotBreak, doesBreak);
                        minimumAttempts = Math.min(minimumAttempts, val);
                    }
                    dp[dish][floor] = minimumAttempts + 1;
                }
            }

        }
        /**
         * dp[k][n] will store min no of attempts needed to find critical floor of n floor sized building
         * using k dish plates
         *
         * Time complexity = O(K*N^2)
         * Space Complexity = O(K*N)
         */
        return dp[k][n];
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        /**
         * n = no of floors
         * k = no of plates
         * */

        int n = sc.nextInt();
        int k = sc.nextInt();
        if(n < 0 || k < 0) {
            System.out.println("Please enter a valid case");
        } else {
            int atMaxSteps = DropBreak(n, k);
            System.out.println(atMaxSteps + " no of steps will be needed");
        }
    }
}

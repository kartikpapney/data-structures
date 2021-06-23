package CollegeAssignments.LastAssignment;

import java.util.*;

/**
 * DAA LAB Assignment
 * Submitted By
 * Name = Kartik Papney
 * Roll No = CSB19047
 * */


public class Que1 {

    /** I think from the given program a player can see himself
     *
     * for input
     * [[1, 1, 1],
     * [1, 1, 1]
     * [1, 1, 1]]
     *
     * output is
     * [[9, 6, 3],
     * [6, 4, 2]
     * [3, 2, 1]]
     *
     * considering this case arr[2][2] can look himself, So I've solved it accordingly
     * */
    /**
     * n = number of rows
     * m = number of columns
     * arr[i][j] will store the players
     * view[i][j] will store the number of players a player at position (i, j) can see
       such that player can only see */


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();


        int[][] arr = new int[n][m];

        /** At position (i, j)
         * arr[i][j] = 0 -> Player is not present
         * arr[i][j] = 1 -> Player is present
         */

        for(int i=0; i<arr.length; i++) {
            for(int j=0; j<arr[0].length; j++) {
                arr[i][j] = sc.nextInt();
            }
        }
        int[][] view = new int[n][m];
        for(int i=arr.length - 1; i>=0; i--) {
            for(int j=arr[0].length - 1; j>=0; j--) {
                if(i == arr.length - 1 && j == arr[0].length - 1) {
                    view[i][j] = arr[i][j];
                } else if(i == arr.length - 1) {
                    view[i][j] = arr[i][j] + view[i][j+1];
                } else if(j == arr[0].length - 1) {
                    view[i][j] = arr[i][j] + view[i+1][j];
                } else {
                    view[i][j] = arr[i][j] + view[i+1][j] + view[i][j+1] -view[i+1][j+1];
                }
            }
        }

        // Print View
        for(int i=0; i<view.length; i++) {
            for(int j=0; j<view[0].length; j++) {
                System.out.print(view[i][j] + " ");
            }
            System.out.println();
        }

        /**
         * Time complexity = O(n*m)
         * Space complexity = O(n*m)
         */
    }
}

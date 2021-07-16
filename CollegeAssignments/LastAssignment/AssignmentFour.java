package CollegeAssignments.LastAssignment;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/***
 * Name: Kartik Papney
 * Roll No: CSB19047
 *
 * Condition: Points should be given in the clockwise order...
 *
 * I implemented the algorithm using a simple O(N) approach in which we'll check if the point is in the right of
 * the line segment. If the point is in the right of all the line segment then the point is inside the polygon
 */

public class AssignmentFour {

    public static boolean isInsidePolygon(int[][] points, int[] point)
    {
        int[] p1 = points[0], p2;
        int i;
        for (i = 1; i <= points.length; i++)
        {
            p2 = points[i % points.length];

            /***
             * We're traversing in clockwise order.
             *
             * p1 = (x1, y1)
             * p2 = (x2, y2)
             * p = (x, y)
             *
             * line a -->  slope of line p -- p1 is (y1 - y)/(x-x1)
             * line b --> slope of line p1 -- p2 is (y2 - y1)/(x2-x1)
             *
             * if slope of line a > slope of line b then point p lies in the left side of line p1-p2
             *
             * rearranging the above formulae
             * (y1 - y)/(x-x1) > (y2 - y1)/(x2-x1)
             * (y1 - y)*(x2-x1) > (y2 - y1)*(x - x1); (We can use this formulae to check if point is in left side of line p1---p2)
             *
             * we're traversing in the clockwise order of convex polygon then point p will always be in right side of line p1 --- p2
             * here p1 comes before p2 while traversing the polygon in clockwise direction
             */
            if (((point[1] - p1[1]) * (p2[0] - p1[0])) > ((point[0] - p1[0]) * (p2[1] - p1[1]))) return false;
            p1 = p2;
        }
        /**
         * It means p is in right side of all the line p1 ---- p2 here p1 comes before p2 while traversing in clockwise direction
         */
        return true;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of points: (Clockwise direction) --> ");
        int n = sc.nextInt();
        int[][] points = new int[n][2];
        for(int i=0; i<points.length; i++) {
            System.out.print("Enter X and Y coordinate of point (Separated by spaces) " + (i+1) + " -> ");
            points[i][0] = sc.nextInt();
            points[i][1] =  sc.nextInt();
        }
        do {
            int[] point = new int[2];
            System.out.print("Enter X and Y coordinate of point to be checked (Separated by spaces) --> ");
            point[0] = sc.nextInt();
            point[1] = sc.nextInt();
            boolean check = isInsidePolygon(points, point);
            if(check) System.out.println("Yes");
            else System.out.println("No");
            System.out.print("Print 'true' to check for next point else 'false' ---> ");

        } while (sc.nextBoolean());

    }
}

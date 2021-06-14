package GraphLevel1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class P006GraphCountIsland {
    public static void countNumberOfIsland(int[][] arr, int i, int j) {
        if(i < 0 || j < 0 || i >= arr.length || j >= arr[0].length || arr[i][j] == 1) return;
        arr[i][j] = 1;
        countNumberOfIsland(arr, i+1, j);
        countNumberOfIsland(arr, i, j-1);
        countNumberOfIsland(arr, i-1, j);
        countNumberOfIsland(arr, i, j+1);
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int m = Integer.parseInt(br.readLine());
        int n = Integer.parseInt(br.readLine());
        int[][] arr = new int[m][n];

        for (int i = 0; i < arr.length; i++) {
            String parts = br.readLine();
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = Integer.parseInt(parts.split(" ")[j]);
            }
        }
        int cnt = 0;
        for(int i=0; i<arr.length; i++) {
            for(int j=0; j<arr[0].length; j++) {
                if(arr[i][j] == 0) {
                    cnt++;
                    countNumberOfIsland(arr, i, j);
                }
            }
        }
        System.out.println(cnt);

    }

}
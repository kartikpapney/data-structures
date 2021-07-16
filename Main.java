import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void print(int[] arr, int start, int end) {
        if(start + 1 == end){
            System.out.println(arr[start]);
            return;
        }
        int mid = start + (end-start)/2;
        print(arr, start, mid);
        print(arr, mid, end);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7,8};
        print(arr, 0, arr.length);
    }



}

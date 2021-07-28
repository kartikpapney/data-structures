package SPOJ;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Alphacode {
    static HashMap<Integer, Long> map;
    public static long ans(String str, int idx) {
        if(idx == str.length()) return 1;
        if(str.charAt(idx) == '0') return 0;
        if(idx == str.length() - 1) return 1;
        if(map.containsKey(idx)) return map.get(idx);
        long value = Long.parseLong(str.substring(idx, idx+2));
        long ans = ans(str, idx+1);
        if(value<=26) ans += ans(str, idx+2);
        map.put(idx, ans);
        return ans;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str;
        while(!(str = sc.nextLine()).equals("0")) {
            map = new HashMap<>();
            System.out.println(ans(str, 0));
        }
    }
}

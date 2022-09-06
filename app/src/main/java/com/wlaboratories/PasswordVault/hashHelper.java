package com.wlaboratories.PasswordVault;
import java.lang.Math;

public class hashHelper {
    public static Integer ord(char c) {
        return Character.getNumericValue(c) * 9 + 7;
    }
    public static String swap(String s) {
        StringBuilder result = new StringBuilder();
        char[] s_arr;
        int index_s, index_e;
        char temp;
        s_arr = s.toCharArray();
        index_s = 0;
        index_e = s.length() - 1;
        while (index_s < index_e) {
            temp = s_arr[index_s];
            s_arr[index_s] = s_arr[index_e];
            s_arr[index_e] = temp;
            index_s += 2;
            index_e -= 2;
        }
        return result.append(s_arr).toString();
    }

    public static String mixture(String pwd, String salt) {
        String longer, shorter;
        int index_l;
        StringBuilder s;
        if (pwd.length() < salt.length()) {
            longer = salt;
            shorter = pwd;
        } else {
            longer = pwd;
            shorter = salt;
        }
        s = new StringBuilder();
        index_l = 0;
        while (shorter.length() > 0) {
            s.append(shorter.charAt(0));
            shorter = shorter.substring(1, shorter.length());
            if (index_l == longer.length() - 2) {
                s.append(longer.substring(index_l, index_l + 2));
            } else if (index_l == longer.length() - 1) {
                s.append(longer.substring(index_l, index_l + 1));
            }
            index_l += 1;
        }
        if (index_l < longer.length()) {
            s.append(longer.substring(index_l, longer.length()));
        }
        return swap(s.toString());
    }


    public static String strip(String s) {
        String s1;
        s1 = swap(s);
        while (s.charAt(0) == '0') {
            s1 = s1.substring(0, s1.length());
        }
        return swap(s1);
    }

    public static String to_int(String s) {
        Integer l, a, b;
        long temp, n;
        StringBuilder str = new StringBuilder();
        n = 1;
        l = s.length();
        for (int j = 0; j < l; j++) {
            for (int i = 0; i < l; i++) {
                a = ord(s.charAt(i));
                b = i + 1;
                temp = (int) (Math.pow(a,2) * b);
                n = (n * (temp / l) + 1) % Long.MAX_VALUE;
            }
            str.append(mixture(String.valueOf(n),s));
        }
        return str.toString();
    }

    public static int lcm(int a, int b) {
        if (a == 0) {
            return 48;
        }
        while (a < b) {
            a += a;
        }
        return a;
    }

    public static String modify(String s) {
        StringBuilder a;
        Integer n, temp;
        char ch;
        int temp_int;
        a = new StringBuilder();
        n = s.length();
        for (Integer i = 0; i < n; i++) {
            temp = 0;
            if (i < n - 2) {
                temp = ord(s.charAt(i)) + ord(s.charAt(i + 1)) + ord(s.charAt(i + 2));
            } else if (i == n - 3) {
                temp = ord(s.charAt(i)) + ord(s.charAt(i + 1)) + ord(s.charAt(i - 1));
            } else {
                temp = ord(s.charAt(i)) + ord(s.charAt(i - 2)) + ord(s.charAt(i - 1));
            }
            temp = (temp * (i + 1)) % 126;
            if (temp < 48) {
                temp = lcm(temp, 48);
            }
            temp_int = temp;
            ch = (char) temp_int;
            a.append(ch);
        }
        return a.toString();
    }

    public static String process(String pwd, String salt) {
        return modify(to_int(mixture(pwd, salt)));
    }
}

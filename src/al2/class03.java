// KMP, manacher
package al2;

import java.util.Arrays;

public class class03 {
    public static void main(String[] args){
        // System.out.println(strStr("aabaaabaaac", "aabaaac"));
        System.out.println(longestPalindrome("cbbd"));

    }

    // 1. KMP算法 O(N)
    // 实现strStr() leetcode28
    public static int strStr(String haystack, String needle) {
        int m = haystack.length();
        int n = needle.length();
        if(m < n){
            return -1;
        }
        int[] match = maxMatch(needle);
        int i = 0;
        int j = 0;
        while(i < m && j < n){
            if(haystack.charAt(i) == needle.charAt(j)){
                i++;
                j++;
            }else if(match[j] == -1){
                i++;
            }else{
                j = match[j];
            }
        }
        return j == n ? i - j : -1;
    }
    //最长前后缀数组 O(M)
    // match[i] = "i之前字符串的前后缀相等的最长前后缀长度"
    // aabcaabba -> [-1,0,1,0,0,1,2,3,0]
    public static int[] maxMatch(String str){
        int n = str.length();
        int[] res = new int[n];
        res[0] = -1;
        if(n >= 2){
            res[1] = 0;
            int i = 2;
            int cn = 0;
            while (i < n) {
                if(str.charAt(i-1) == str.charAt(cn)){
                    res[i++] = ++cn;
                } else if(cn > 0){
                    cn = res[cn];
                } else{
                    res[i++] = 0;
                }
            }
        }
        return res;
    }


    // 2. manacher算法 O(N)
    // 最长回文子串：给你一个字符串 s，找到 s 中最长的回文子串。 leetcode5
    public static String longestPalindrome(String s) {
        if(s == null || s.length() == 0){
            return "";
        }
        char[] chs = new char[s.length() * 2 + 1]; //添加辅助字符后的辅助字符串
        Arrays.fill(chs, '#');
        for (int i = 0; i < s.length(); i++) {
            chs[2 * i + 1] = s.charAt(i);
        }
        int n = chs.length;
        int[] radius = new int[n]; //回文半径数组， manacher的核心
        int R = -1; //已判定的回文串中的最远右边界
        int C = -1; //最远右边界对应的中心
        for (int i = 0; i < n; i++) {
            if(i > R){ //i在R之外，暴力外扩
                int j = i + 1;
                while(true){
                    if(j >= n || i - (j - i) < 0 || chs[j] != chs[i - (j - i)]){
                        R = j - 1;
                        C = i;
                        radius[i] = R - C + 1;
                        break;
                    }
                    j++;
                }
            } else if(radius[C - (i - C)] < R - i + 1){ //i'处的回文串在C处回文串内部，不含边界 radius[i] = radius[i']
                radius[i] = radius[C - (i - C)];
            } else if(radius[C - (i - C)] == R - i + 1){ //i'处的回文串与C处回文串边界重合，继续外扩
                int j = R + 1;
                while(true){
                    if(j >= n || i - (j - i) < 0 || chs[j] != chs[i - (j - i)]){
                        R = j - 1;
                        C = i;
                        radius[i] = R - C + 1;
                        break;
                    }
                    j++;
                }
            } else{ //i'处的回文串在C处回文串外部，不含边界 radius[i] = R - i + 1
                radius[i] = R - i + 1;
            }
        }
        int max = 0;
        int maxIndex = -1;
        for (int i = 0; i < n; i++) {
            if(radius[i] > max){
                max = radius[i];
                maxIndex = i;
            }
        }
        return maxIndex == -1 ? "" : s.substring((maxIndex - max + 1) / 2, (maxIndex - max + 1) / 2 + max - 1);
    }
}

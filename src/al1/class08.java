// 暴力递归
// 暴力递归就是尝试
// 1,把问题转化为规模缩小了的同类问题的子问题
// 2,有明确的不需要继续进行递归的条件(base case)
// 3,有当得到了子问题的结果之后的决策过程
// 4,不记录每一个子问题的解
package al1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class class08 {
    public static void main(String[] args){
        // hano(5);
        // printAllSubSequence("null");

        // int[] nums = {1,1,1};

        // System.out.println(numDecodings("1111111111111111"));
        // int i = 1000000007;

        long s = System.currentTimeMillis();
        System.out.println(numRollsToTarget(10, 6, 30));
        long e = System.currentTimeMillis();
        System.out.println((e-s) + "ms");

    }

    // 1. 汉诺塔问题
    public static void hano(int n){
        hanoHelper(n, "left", "right", "mid");
    }
    public static void hanoHelper(int i, String from, String to, String other){
        if(i == 1){
            System.out.println(from + " -> " + to);
        }
        else{
            hanoHelper(i-1, from, other, to);
            System.out.println(from + " -> " + to);
            hanoHelper(i-1, other, to, from);
        }
    }

    // 2. 打印一个字符串的所有子序列（字符顺序不可变）
    public static void printAllSubSequence(String str){
        char[] chrs = str.toCharArray();
        printAllSubSequenceHelper(0, chrs);
    }
    public static void printAllSubSequenceHelper(int i, char[] str){ //来到第i个字符时做决策
        if(i == str.length){
            System.out.println(String.valueOf(str));
        }
        else{
            printAllSubSequenceHelper(i+1, str); //要当前字符
            char temp = str[i];
            str[i] = 0;
            printAllSubSequenceHelper(i + 1, str); //不要当前字符
            str[i] = temp;
        }
    }

    // 3. 全排列 leetcode46,47
    // 3.1 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        fnc1(0, nums, res);
        return res;
    }
    public static void fnc1(int i, int[] nums, List<List<Integer>> res){
        if(i == nums.length){
            List<Integer> list = new ArrayList<>();
            for(int num : nums){
                list.add(num);
            }
            res.add(list);
        }
        else{
            for(int j = i; j < nums.length; j++){ //前i-1位上的数字已经确定，穷举i位置的所有可能
                swap(i, j, nums);
                fnc1(i + 1, nums, res);
                swap(i, j, nums);
            }
        }
    }
    public static void swap(int i, int j, int[] nums){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    // 3.2 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
    public static List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
            fnc2(0, nums, res);
            return res;
        }
    public static void fnc2(int i, int[] nums, List<List<Integer>> res){
        if(i == nums.length){
            List<Integer> list = new ArrayList<>();
            for(int num : nums){
                list.add(num);
            }
            res.add(list);
        }
        else{
            boolean[] tried = new boolean[21]; //记录第i位置上的数是否已经尝试过，避免重复
            for(int j = i; j < nums.length; j++){ //前i-1位上的数字已经确定，穷举i位置的所有可能
                if(!tried[nums[j]+10]){
                    tried[nums[j]+10] = true;
                    swap(i, j, nums);
                    fnc2(i + 1, nums, res);
                    swap(i, j, nums);
                }
            }
        }
    }

    // 4. 抽牌问题（leetcode石子游戏877，1423）
    // A，B两人轮流从牌堆（数组）拿牌，只能拿第一张或最后一张，数字累积分数，返回最终的胜者
    public boolean stoneGame(int[] piles) { //true代表A获胜
        int A = stoneGameF(piles, 0, piles.length - 1);
        int sum = 0;
        for (int i = 0; i < piles.length; i++) {
            sum += piles[i];
        }
        return A > sum - A;
    }
    public static int stoneGameF(int[] piles, int l, int r){ //返回l-r范围内先手获得的最大分数
        if(l == r){
            return piles[l];
        }
        return Math.max(piles[l] + stoneGameS(piles, l + 1, r), piles[r] + stoneGameS(piles, l, r - 1));
    }
    public static int stoneGameS(int[] piles, int l, int r){ //返回l-r范围内后手获得的最大分数
        if(l == r){
            return 0;
        }
        return Math.min(stoneGameF(piles, l + 1, r), stoneGameF(piles, l, r - 1));
    }

    // 5. 利用递归逆序栈
    public static void reverseStack(Stack<Integer> stack){
        if(stack.isEmpty()){
            return ;
        }
        int bottom = reverseFnc(stack);
        reverseStack(stack);
        stack.push(bottom);
    }
    public static Integer reverseFnc(Stack<Integer> stack){ //取出栈底元素
        int res = stack.pop();
        if(stack.isEmpty()){
            return res;
        }
        int last = reverseFnc(stack);
        stack.push(res);
        return last;
    }

    // 6. 规定1和A对应、2和B对应、3和C对应.那么一个数字字符串比如"111"，就可以转化为"AAA"、"KA"和"AK"。
    // 给定一个只有数字字符组成的字符串str,返回有多少种转化结果。
    public static int numDecodings(String s) {
        char[] chrs = s.toCharArray();
        return numToChaFnc(chrs, 0);
    }
    public static int numToChaFnc(char[] chrs, int i){
        if(i == chrs.length){
            return 1;
        }
        if(chrs[i] == '0'){
            return 0;
        }
        if(chrs[i] == '1'){
            int res = numToChaFnc(chrs, i + 1);
            if(i + 1 < chrs.length){
                res += numToChaFnc(chrs, i + 2);
            }
            return res;
        }
        if(chrs[i] == '2'){
            int res = numToChaFnc(chrs, i + 1);
            if(i + 1 < chrs.length && chrs[i + 1] <= '6'){
                res += numToChaFnc(chrs, i + 2);
            }
            return res;
        }
        return numToChaFnc(chrs, i + 1);
    }

    public static int MOD = 1000000007;
    public static int numRollsToTarget(int n, int k, int target) {
        if(n == 1){
            return target <= k ? 1 : 0;
        }
        if(n * k <target){
            return 0;
        }
        long res = 0;
        for(int i = 1; i <= k; i++){
            res += numRollsToTarget(n - 1, k, target - i) % MOD;
            res %= MOD;
        }
        return (int)res;
    }
}

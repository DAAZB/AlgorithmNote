package al2;

import java.util.Arrays;

public class class08 {
    public static void main(String[] args){
        // long st = System.currentTimeMillis();
        // System.out.println(walkWays(50, 10, 30, 30));
        // long et = System.currentTimeMillis();
        // System.out.println(et - st + "ms");
        // st = System.currentTimeMillis();
        // System.out.println(walkWaysDP(50, 10, 30, 30));
        // et = System.currentTimeMillis();
        // System.out.println(et - st + "ms");

        // System.out.println(knightProblem1(3, 3, 3));
        // long a = 1;
        // int i = 30;
        // while(i-- > 0){
        //     a *= 8;
        // }
        // System.out.println(a);
        // char[] c = "sdadad".toCharArray();
        // System.out.println(String.valueOf(c));

        System.out.println(minimumMountainRemovals(new int[]{100,92,89,77,74,66,64,66,64}));
    }


    // 1. 一排N个格子（1-N），机器人每次可往任意方向移动一格，若从起始位置s移动到e必须走满k步，有多少种走法
    public static int walkWays(int N, int s, int e, int k){
        return f(N, s, e, k);
    }
    // N - 格子数
    // cur - 当前位置
    // e - 目标点
    // rest - 剩余步数
    public static int f(int N, int cur, int e, int rest){ //暴力递归方法，数据量大时，有大量重复计算过程
        if(rest == 0){
            return cur == e ? 1: 0;
        }
        if(cur == 1){
            return f(N, 2, e, rest - 1);
        }
        if(cur == N){
            return f(N, N - 1, e, rest - 1);
        }
        return f(N, cur - 1, e, rest - 1) + f(N, cur + 1, e, rest - 1);
    }

    // 1.1 改记忆化搜索
    public static int walkWaysDP(int N, int s, int e, int k){ //在递归过程中，N和e是固定参数，s（cur）和k（rest）是可变参数
        int[][] dp = new int[N + 1][k + 1]; //以s和k的可能范围大小创建dp数组来记忆已计算过的值
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }
        return f1(N, s, e, k, dp);
    }
    public static int f1(int N, int cur, int e, int rest, int[][] dp){
        if(dp[cur][rest] != -1){
            return dp[cur][rest];
        }
        if(rest == 0){
            dp[cur][rest] = cur == e ? 1: 0;
        }else if(cur == 1){
            dp[cur][rest] = f1(N, 2, e, rest - 1, dp);
        }else if(cur == N){
            dp[cur][rest] = f1(N, N - 1, e, rest - 1, dp);
        }else{
            dp[cur][rest] = f1(N, cur - 1, e, rest - 1, dp) + f1(N, cur + 1, e, rest - 1, dp);
        }
        return dp[cur][rest];
    }

    // 1.2 改动态规划
    // 找dp数组的位置依赖关系
    public static int walkWays1(int N, int s, int e, int k){
        int[][] dp = new int[N + 1][k + 1];
        for (int j = 0; j < k + 1; j++) {
            for (int i = 0; i < N + 1; i++) {
                if(j == 0){
                    dp[i][j] = i == e ? 1 : 0;
                }else if(i == 0){
                    continue;
                }
                else if(i == 1){
                    dp[i][j] = dp[2][j - 1];
                }else if(i == N){
                    dp[i][j] = dp[N - 1][j - 1];
                }else{
                    dp[i][j] = dp[i - 1][j - 1] + dp[i + 1][j -1];
                }
            }
        }
        return dp[s][e];
    }


    // 2. 抽牌问题（leetcode石子游戏877，1423） //al1-class8-4
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

    // 2.1 改动态规划
    public boolean stoneGame1(int[] piles) { //true代表A获胜
        int n = piles.length;
        int[][] dp1 = new int[n][n];
        int[][] dp2 = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp1[i][i] = piles[i];
            dp2[i][i] = 0;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                int l = j;
                int r = j + i;
                dp1[l][r] = Math.max(piles[l] + dp2[l + 1][r], piles[r] + dp2[l][r - 1]);
                dp2[l][r] = Math.min(dp1[l + 1][r], dp1[l][r - 1]);
            }
        }
        return dp1[0][n - 1] >= dp2[0][n - 1];
    }


    // 3. 马踏棋盘问题 LC397
    // 象棋棋盘9x10，马起始位置位于（0，0），目的地为（a，b），走k步到达目的地的走法有多少种
    // 递归
    public static int knightProblem1(int a, int b, int k){
        return knightProblemRC(a, b, k);
    }
    //等效于从a，b往0，0位置跳
    public static int knightProblemRC(int x, int y, int k){ //当前在x，y，还剩k步
        if(x < 0 || y < 0 || x > 8 || y > 9){
            return 0;
        }
        if(k == 0){
            return (x == 0 && y == 0) ? 1 : 0;
        }
        return knightProblemRC(x + 2, y + 1, k - 1) + 
                knightProblemRC(x + 2, y - 1, k - 1) + 
                knightProblemRC(x - 2, y + 1, k - 1) + 
                knightProblemRC(x - 2, y - 1, k - 1) + 
                knightProblemRC(x + 1, y + 2, k - 1) + 
                knightProblemRC(x + 1, y - 2, k - 1) + 
                knightProblemRC(x - 1, y + 2, k - 1) + 
                knightProblemRC(x - 1, y - 2, k - 1);
    }


    // 4. 零钱兑换 LC322
    // 返回所需的最小张数
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for(int i = 1; i < amount + 1; i++){
            for (int j = 0; j < coins.length; j++) {
                if(coins[j] <= i){
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    // 5. 零钱兑换Ⅱ LC518
    // 返回兑换方法数
    public int coinChangeII(int[] coins, int amount) {
        return coinChangeIIRC(coins, 0, amount);
    }
    public int coinChangeIIRC(int[] coins, int i, int amount){ //暴力递归：从i位置开始选，凑出amount的方法数
        if(i == coins.length){
            return amount == 0 ? 1 : 0;
        }
        int ways = 0;
        for(int j = 0; j * coins[i] <= amount; j++){
            ways += coinChangeIIRC(coins, i + 1, amount - j * coins[i]);
        }
        return ways;
    }
    public int coinChangeIIDP1(int[] coins, int amount){ //动态规划
        int[][] dp = new int[coins.length + 1][amount + 1];
        dp[coins.length][0] = 1;
        for (int i = coins.length - 1; i >= 0; i--) {
            for(int j = 0; j <= amount; j++){
                int ways = 0;
                for(int zhang = 0; zhang * coins[i] <= j; zhang++){
                    ways += dp[i + 1][j - zhang * coins[i]];
                }
                dp[i][j] = ways;
            }
        }
        return dp[0][amount];
    }
    public int coinChangeIIDP2(int[] coins, int amount){ //动态规划优化
        int[][] dp = new int[coins.length + 1][amount + 1];
        dp[coins.length][0] = 1;
        for (int i = coins.length - 1; i >= 0; i--) {
            for(int j = 0; j <= amount; j++){
                if(j < coins[i]){
                    dp[i][j] = dp[i + 1][j];
                }else{
                    dp[i][j] = dp[i][j - coins[i]] + dp[i + 1][j];
                }
            }
        }
        return dp[0][amount];
    }


    // LC1671. 得到山形数组的最少删除次数
    public static int minimumMountainRemovals(int[] nums) {
        int n = nums.length;
        int[] dp1 = new int[n];
        for(int i = 0; i < n; i++){
            dp1[i] = 1;
            for(int j = 0; j < i; j++){
                if(nums[j] < nums[i]){
                    dp1[i] = Math.max(dp1[i], dp1[j] + 1);
                }
            }
        }
        int[] dp2 = new int[n];
        for(int i = n - 1; i >= 0; i--){
            dp2[i] = 1;
            for(int j = n - 1; j > i; j--){
                if(nums[j] < nums[i]){
                    dp2[i] = Math.max(dp2[i], dp2[j] + 1);
                }
            }
        }
        int res = 0;
        for(int i = 1; i < n - 1; i++){
            res = Math.max(res, dp1[i] + dp2[i] - 1);
        }
        return n - res;
    }


}

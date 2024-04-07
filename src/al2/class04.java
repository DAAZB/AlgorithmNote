// 滑动窗口，单调栈，双端队列
package al2;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class class04 {
    public static void main(String[] args){
        int[] nums = {73,74,75,71,69,72,76,73};
        dailyTemperatures(nums);

    }


    // 1. 滑动窗口 leetcode239
    // 给你一个整数数组山ms，有一个大小为k的滑动窗口从数组的最左侧移动到数组的最右侧。
    // 你只可以看到在滑动窗口内的k个数字。滑动窗口每次只向右移动一位。返回滑动窗口中的最大值。
    public static int[] maxSlidingWindow(int[] nums, int k) { //O(N)
        LinkedList<Integer> qmax = new LinkedList<>();
        int n = nums.length;
        int[] res = new int[n - k + 1];
        for(int i = 0; i < n; i++){
            if(i < k){
                while(!qmax.isEmpty() && nums[qmax.peekLast()] <= nums[i]){
                    qmax.pollLast();
                }
                qmax.offer(i);
            } else{
                res[i - k] = nums[qmax.peek()];
                while(!qmax.isEmpty() && nums[qmax.peekLast()] <= nums[i]){
                    qmax.pollLast();
                }
                qmax.offer(i);
                if(qmax.peek() == i - k){
                    qmax.poll();
                }
            }
        }
        res[n - k] = nums[qmax.peek()];
        return res;
    }


    // 2. 单调栈结构
    // 接雨水问题 leetcode42
    public int trap(int[] height) {
        int n = height.length;
        int res = 0;
        // Stack<Integer> st = new Stack<>(); //单调栈
        Deque<Integer> st = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            while(!st.isEmpty() && height[i] > height[st.peek()]){
                int top = st.pop();
                if(!st.isEmpty()){
                    int left = st.peek();
                    res += (i - left - 1) * (Math.min(height[left], height[i]) - height[top]);
                }
            }
            st.push(i);
        }
        return res;
    }


    // 3. 子数组最小乘积的最大值
    // 一个数组的最小乘积定义为这个数组中最小值乘以数组的和。
    public int maxSumMinProduct(int[] nums) {
        int n = nums.length;
        LinkedList<Integer> st = new LinkedList<>();
        int sum = 0;
        int res = 0;
        for (int i = 0; i < 0; i++) {
            while(!st.isEmpty() && nums[st.peekLast()] > nums[i]){
                int pop = st.pollLast();
                
            }
        }
    }



    // 4. 每日温度 leetcode739
    // 给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer ，其中 answer[i] 是指对于第 i 天，下一个更高温度出现在几天后。如果气温在这之后都不会升高，请在该位置用 0 来代替。
    public static int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        LinkedList<Integer> st = new LinkedList<>(); //单调栈
        int[] res = new int[n];
        Arrays.fill(res, 0);
        for (int i = 0; i < n; i++) {
            while(!st.isEmpty() && temperatures[st.peekLast()] < temperatures[i]){
                int small = st.pollLast();
                res[small] = i - small;
            }
            st.offer(i);
        }
        return res;
    }
}

// 前缀树与贪心算法
package al1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

// 1.前缀树
// 前缀树节点
class TrieNode{
    public int pass; //该节点的通过次数
    public int end; //以该节点作为结尾的字符串数量
    public TrieNode[] nexts; //26个分支，分别表示26个字母（这里只考虑小写字母组成的字符串，
            // 如果要表达复杂的字符串可以采用哈希表来表达分支） //HashMap<Char, TrieNode> nexts;
    public TrieNode(){
        pass = 0;
        end = 0;
        // nexts[0] == null  没有走向'a'的路
        // nexts[0] != null  有走向'a'的路
        // ...
        // nexts[25] != null  有走向'z'的路
        nexts = new TrieNode[26];
    }
}

// 前缀树
class Trie{
    private TrieNode root;
    public Trie(){
        root = new TrieNode();
    }
    //插入字符串到前缀树
    public void insert(String word){
        if(word == null){
            return;
        }
        char[] chs = word.toCharArray();
        var node = this.root;
        node.pass++;
        int index = 0;
        for (char c : chs) { //依次遍历每个字符
            index = c - 'a'; //判断该走哪条路
            if(node.nexts[index] == null){
                node.nexts[index] = new TrieNode();
            }
            node = node.nexts[index]; //走过的节点pass++
            node.pass++;
        }
        node.end++; //字符串结尾节点end++
    }

    //查找字符串，返回匹配的个数
    public int search(String word){
        if(word == null){
            return 0;
        }
        char[] chs = word.toCharArray();
        var node = this.root;
        int index = 0;
        for (char c : chs) {
            index = c - 'a';
            if(node.nexts[index] == null){
                return 0;
            }
            node = node.nexts[index];
        }
        return node.end;
    }

    // 删除字符串
    public void delete(String word){
        if(search(word) == 0){
            return;
        }
        char[] chs = word.toCharArray();
        var node = this.root;
        node.pass--;
        int index = 0;
        for (char c : chs) {
            index = c - 'a';
            if(--node.nexts[index].pass == 0){
                node.nexts[index] =null;
                return;
            }
            node = node.nexts[index];
        }
        node.end--;
    }

    //返回以pre为前缀的字符串数量
    public int prefixNumber(String pre){
        if(pre == null){
            return 0;
        }
        char[] chs = pre.toCharArray();
        var node = this.root;
        int index = 0;
        for (char c : chs) {
            index = c - 'a';
            if(node.nexts[index] == null){
                return 0;
            }
            node = node.nexts[index];
        }
        return node.pass;
    }

    // 返回树中字符串的数量
    public int size(){
        return this.root.pass;
    }

}

public class class07 {
    public static void main(String[] args){
        // Trie trie = new Trie();
        // String[] words = {"abc", "abcd", "word", "hello", "words", "hell", "he"};
        // for (String word : words) {
        //     trie.insert(word);
        // }
        // System.out.println(trie.size());
        // System.out.println(trie.search("abc"));
        // System.out.println(trie.prefixNumber("he"));
        // System.out.println(trie.prefixNumber("hell"));
        // System.out.println(trie.search("word"));

        long t1 = System.currentTimeMillis();
        System.out.println(solveNQueens1(15));
        long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1 + "ms");
        t1 = System.currentTimeMillis();
        System.out.println(solveNQueens3(15));
        t2 = System.currentTimeMillis();
        System.out.println(t2 - t1 + "ms");
    }



    // 贪心算法
    // 1. 会议安排问题，每个会议的起始时刻和终止时刻已知，求一段时间内可安排的最多会议数量
    //贪心策略：按结束时间最早的优先安排，即可得到最优解
    class Program{
        int start;
        int end;
        Program(int start, int end){
            this.start = start;
            this.end = end;
        }
    }
    // timePoint: 一天的开始时间
    public static int bestArrange(Program[] programs, int timePoint){
        Arrays.sort(programs, (o1, o2) -> {return o1.end - o2.end;});
        int res = 0;
        for (Program program : programs) {
            if(timePoint <= program.start){
                res++;
                timePoint = program.end;
            }
        }
        return res;
    }

    // 2. 最小字典序问题：将一个字符串数组中的所有字符串按某种顺序拼接起来，返回字典序最小的结果
    // 贪心策略：定义一种排序比较策略：如下
    public static String smallestString(String[] strs){
        Arrays.sort(strs, (s1, s2) -> {
            return (s1 + s2).compareTo(s2 + s1);
        });
        String res = "";
        for (String string : strs) {
            res += string;
        }
        return res;
    }


    // 3. 最大利润，启动资金为w，返回完成k个项目后的最大资本
    // program = [项目启动成本， 净利润]
    // 贪心策略，每次先做力所能及的项目中利润最大的
    public static int maxProfit(int[][] programs, int w, int k){
        PriorityQueue<int[]> lock = new PriorityQueue<>((a, b) -> a[0] - b[0]); //锁定的项目，按成本升序排
        for (int[] program : programs) {
            lock.add(program);
        }
        PriorityQueue<int[]> unlock = new PriorityQueue<>((a, b) -> b[1] - a[1]); //解锁的项目，按利润降序排
        while(k-->0){
            while(lock.peek() != null && lock.peek()[0] <= w){ //将能做的项目解锁
                unlock.add(lock.poll());
            }
            if(unlock.isEmpty()){
                return w;
            }
            w += unlock.poll()[1]; //从解锁的项目中条利润最大的
        }
        return w;
    }

    // 3.1 数据流的中位数（大小堆配合的拓展） leetcode295
    class MedianFinder {
        PriorityQueue<Integer> smallHalf; //大根堆，堆顶即为中间的数
        PriorityQueue<Integer> largeHalf; //小根堆，堆顶即为中间的数

        public MedianFinder() {
            smallHalf = new PriorityQueue<>((a, b) -> b - a);
            largeHalf = new PriorityQueue<>((a, b) -> a - b);
        }
        
        public void addNum(int num) {
            if(smallHalf.isEmpty() && largeHalf.isEmpty()){
                smallHalf.add(num);
                return;
            }
            if(num <= smallHalf.peek()){
                smallHalf.add(num);
            }else{
                largeHalf.add(num);
            }
            if(smallHalf.size() - largeHalf.size() > 1){
                largeHalf.add(smallHalf.poll());
            }
            if(largeHalf.size() - smallHalf.size() > 1){
                smallHalf.add(largeHalf.poll());
            }
        }
        
        public double findMedian() {
            if(smallHalf.size() == largeHalf.size()){
                return ((double)smallHalf.peek() + (double)largeHalf.peek()) / 2;
            }else{
                return smallHalf.size() > largeHalf.size() ? smallHalf.peek() : largeHalf.peek();
            }
        }
    }




    // n 皇后问题：将n个皇后放置在n×n的棋盘上，并且使皇后彼此之间不能相互攻击(皇后不能再同一行同一列或同一斜线上)。
    public static int solveNQueens1(int n) { //返回解决方案的数量
        int[] record = new int[n];
        return queenHelper1(0, record, n);
    }
    // i行以前的皇后已经摆好，来到第i行时，摆完剩余所有皇后有多少种摆法
    public static int queenHelper1(int i, int[] record, int n){
        if(i == n){ //最后一行已经摆完了
            return 1;
        }
        int res = 0;
        for(int j = 0; j < n; j++){
            if(isValid(i, j, record)){
                record[i] = j;
                res += queenHelper1(i + 1, record, n);
            }
        }
        return res;
    }
    // 判断第i行的queen放第j列是否合理
    public static boolean isValid(int i, int j, int[] record){
        for (int k = 0; k < i; k++) {
            if(record[k] == j || Math.abs(k-i) == Math.abs(record[k] - j)){
                return false;
            }
        }
        return true;
    }

    public static List<List<String>> queenRes;
    public List<List<String>> solveNQueens2(int n) { //返回所有解决方案
        queenRes = new ArrayList<>();
        int[] record = new int[n];
        queenHelper2(0, record, n);
        return queenRes;
    }

    public static void queenHelper2(int i, int[] record, int n){
        if(i == n){ //最后一行已经摆完了
            List<String> res = new ArrayList<>();
            for (int k = 0; k < n; k++) {
                String line = "";
                for (int j = 0; j < n; j++) {
                    line += j == record[k] ? "Q" : ".";
                }
                res.add(line);
            }
            queenRes.add(res);
        }
        for(int j = 0; j < n; j++){
            if(isValid(i, j, record)){
                record[i] = j;
                queenHelper1(i + 1, record, n);
            }
        }
    }

    // 性能优化：使用位运算加速
    public static int solveNQueens3(int n){
        // 
        if(n == 0 || n > 32){
            return 0;
        }
        int limit = (1 << n) - 1;
        return queenHelper3(limit, 0, 0, 0);
    }
    public static int queenHelper3(int limit, int colLim, int leftDiaLim, int rightDiaLim){
        if(limit == colLim){
            return 1;
        }
        int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));
        int mostRightOne = 0;
        int res = 0;
        while(pos != 0){
            mostRightOne = pos & (~pos + 1);
            pos -= mostRightOne;
            res += queenHelper3(limit, 
                    colLim | mostRightOne,
                    (leftDiaLim | mostRightOne) << 1, 
                    (rightDiaLim | mostRightOne) >> 1);
        }
        return res;
    }

















}




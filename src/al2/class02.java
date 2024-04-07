// 有序表，并查集
package al2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(){}
        TreeNode(int val){
            this.val = val;
        }
        TreeNode(int val, TreeNode left, TreeNode right){
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

public class class02 {
    public static void main(String[] args){
        // int m = 10000;
        // int n = 10000;
        // char[][] grid = new char[m][n];
        // for (int i = 0; i < m; i++) {
        //     for (int j = 0; j < n; j++) {
        //         grid[i][j] = Math.random() > 0.7 ? '1' : '0';
        //     }
        // }
        // // for (int i = 0; i < m; i++) {
        // //     System.out.println(String.valueOf(grid[i]));
        // // }
        // long start = System.currentTimeMillis();
        // System.out.println(numIslands(grid));
        // long end = System.currentTimeMillis();
        // System.out.println(end - start + "ms");


        // int res = 0;
        // for (int i = 0; i < 1000000; i++) {
        //     res += Math.random() >= 0.5 ? 1 : 0;
        // }
        // System.out.println(res);

        Skiplist sl = new Skiplist();
        sl.add(1);
        sl.add(2);
        sl.add(3);
        sl.search(0);
        sl.add(4);
        sl.search(1);
        sl.erase(0);
        sl.erase(1);
        sl.search(1);
    }


    // 1. 岛屿数量
    // 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
    // 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
    // 此外，你可以假设该网格的四条边均被水包围。
    // 如何设计一个并行算法? 并查集结构，分块计算再合并
    public static int numIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(grid[i][j] == '1'){
                    res += 1;
                    infect(grid, m, n, i, j);
                }
            }
        }
        return res;
    }
    public static void infect(char[][] grid, int m, int n, int i, int j){
        if(i < 0 || j < 0 || i >= m || j >= n){
            return;
        }
        if(grid[i][j] == '1'){
            grid[i][j] = '2';
            infect(grid, m, n, i - 1, j);
            infect(grid, m, n, i, j - 1);
            infect(grid, m, n, i + 1, j);
            infect(grid, m, n, i, j + 1);
        }
    }


    // 2. 并查集
    // 样本进来会包一层
    public static class Element<V>{
        public V value;
        public Element(V value){
            this.value = value;
        }
    }
    public static class UnionFindSet<V>{
        public HashMap<V, Element<V>> elementMap; //元素与包装体之间的对应
        public HashMap<Element<V>, Element<V>> fatherMap; //父节点表
        public HashMap<Element<V>, Integer> sizeMap; //父节点所在集合的大小，只有父节点在这张表里

        public UnionFindSet(List<V> list){
            elementMap = new HashMap<>();
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V value : list) { //初始化时，所有元素包装一层，自成一个集合
                Element<V> element = new Element<V>(value);
                elementMap.put(value, element);
                fatherMap.put(element, element);
                sizeMap.put(element, 1);
            }
        }

        // 往上找，找到该节点的头
        public Element<V> findHead(Element<V> element){
            Stack<Element<V>> path = new Stack<>();
            while(element != fatherMap.get(element)){ //将沿途路径压栈
                path.push(element);
                element = fatherMap.get(element);
            }
            while(!path.isEmpty()){
                fatherMap.put(path.pop(), element); //将路径上的所有元素都挂到头结点上（扁平化，高度降为1，下次查找更快）
            }
            return element;
        }

        // 各自往上找，看头节点是否同一个
        public boolean isSameSet(V a, V b){
            if(elementMap.containsKey(a) && elementMap.containsKey(b)){
                if(findHead(elementMap.get(a)) == findHead(elementMap.get(b))){
                    return true;
                }
            }
            return false;
        }

        public void union(V a, V b){
            if(elementMap.containsKey(a) && elementMap.containsKey(b)){
                var aF = findHead(elementMap.get(a));
                var bF = findHead(elementMap.get(b));
                if(aF != bF){ //把小集合中的头节点挂在大集合的头节点下
                    var big = sizeMap.get(aF) > sizeMap.get(bF) ? aF : bF;
                    var small = big == aF ? bF : aF;
                    fatherMap.put(small, big);
                    sizeMap.put(big, sizeMap.get(big) + sizeMap.get(small));
                    sizeMap.remove(small);
                }
            }
        }
    }


    // 3. 有序表(红黑树，sb树，avl树；跳表) p19
    // 3.1 二叉搜索树的增删查改
    public void BSTAddVal(TreeNode root, int val){
        if(root == null){
            root = new TreeNode(val);
        }else{
            var cur = root;
            var p = cur;
            while(cur != null){
                p = cur;
                cur = cur.val > val ? cur.left : cur.right;
            }
            TreeNode node = new TreeNode(val);
            if(p.val > val){
                p.left = node;
            }else{
                p.right = node;
            }
        }
    }
    public void BSTRemoveVal(TreeNode root, int val){
        var cur = root;
        var p = cur;
        while(cur != null){ //先找到要移除的节点
            if(cur.val != val){
                p = cur;
                cur = cur.val > val ? cur.left : cur.right;
            }else{
                if(cur.left == null && cur.right == null){ //叶子节点直接删除
                    if(p.left == cur){
                        p.left = null;
                    }else{
                        p.right = null;
                    }
                    cur = null;
                }else if(cur.left == null || cur.right == null){ //只有一个孩子，把孩子挂载到父结点上
                    if(p.left == cur){
                        p.left = cur.left != null ? cur.left : cur.right;
                    }else{
                        p.right = cur.left != null ? cur.left : cur.right;
                    }
                    cur = null;
                }else{ //用右子树的最左节点mostLeft替换当前节点，mostLeft的孩子挂载到mostLeft的父结点上
                    var mostLeft = cur.right; 
                    var p2 = cur;
                    while(mostLeft.left != null){
                        p2 = mostLeft;
                        mostLeft = mostLeft.left;
                    }
                    p2.left = mostLeft.right;
                    mostLeft.left = cur.left;
                    mostLeft.right = cur.right;
                    if(p.left == cur){
                        p.left = mostLeft;
                    }else{
                        p.right = mostLeft;
                    }
                    cur = null;
                }
                break;
            }
        }
    }

    // 3.2 二叉搜索树的左旋和右旋
    // 左旋：头节点往左倒，右孩子变为新的头节点，右孩子的左子树成为旧头节点的右子树
    // 右旋：头节点往右倒，左孩子变为新的头节点，左孩子的右子树成为旧头节点的左子树
    // 红黑树，SB树，AVL树的增删改查与一般二叉搜索树一样，平衡性调整都通过左右旋操作完成
    // 平衡性检查时机也相同：增：从新增元素所在位置一路往上每个节点检查并调整；删：从原删除位置的父节点开始一路往上
    // 平衡性定义不同：
        // AVL树：平衡性定义：严格平衡二叉树
        //        调整细节（四种情况）：
        //        LL：左树的左边过长，整体一次右旋；
        //        RR：右树的右边过长，整体一次左旋；
        //        LR：左树的右边过长，先左树一次左旋，再整体一次右旋（让左树的右节点成为头）
        //        RL：右树的左边过长，先右树一次右旋，再整体一次左旋（让右树的左节点成为头）
        // SB树：平衡性定义：每棵树的大小，不小于其任一侄子树的大小
        //        调整细节（四种情况）：
        //        LL：左树的左树大于它的叔叔
        //        RR：右树的右树大于它的叔叔
        //        LR：。。
        //        RL：。。
        //        初始调整与AVL树相同，调整完后要对数量发生变化的节点递归上述操作
        // 红黑树：


    // 3.3 跳表 LC1206
    // 有优化空间
    static class SkipListNode{
        Integer val;
        Integer count;
        ArrayList<SkipListNode> nexts;
        SkipListNode(int val){
            this.val = val;
            this.count = 1;
            this.nexts = new ArrayList<>();
        }
    }
    static class Skiplist {
        SkipListNode root;

        public Skiplist() {
            root = new SkipListNode(-1);
            root.nexts = new ArrayList<>();
            for (int i = 0; i < 32; i++) {
                root.nexts.add(null);
            }
        }
        
        public boolean search(int target) {
            var node = mySearch(target)[0].nexts.get(0);
            return node != null && node.val == target;
        }

        public SkipListNode[] mySearch(int target){ //搜索返回每一层小于target的最大节点
            SkipListNode[] pre = new SkipListNode[this.root.nexts.size()];
            var cur = this.root;
            for(int i = this.root.nexts.size() - 1; i >= 0; i--){ //从根节点最高层开始找到小于num的
                while(cur.nexts.get(i) != null && cur.nexts.get(i).val < target){
                    cur = cur.nexts.get(i);
                }
                pre[i] = cur;
            }
            return pre;
        }
        
        public void add(int num) {
            var pre = mySearch(num);
            if(pre.length > 0 && pre[0] != null && pre[0].val == num){ //已经存在，数量++
                pre[0].count++;
            }else{ // 不存在，插入新节点
                SkipListNode node = new SkipListNode(num);
                int k = 1;
                while(Math.random() > 0.5){ //新节点的高度
                    k++;
                }
                if(k > root.nexts.size()){ //根节点增高
                    for (int i = root.nexts.size(); i < k; i++) {
                        root.nexts.add(null);
                    }
                }
                for (int i = 0; i < k; i++) {
                    var next = pre[i].nexts.get(i);
                    pre[i].nexts.set(i, node);
                    node.nexts.add(next);
                }
            }
        }
        
        public boolean erase(int num) {
            var pre = mySearch(num);
            var node = pre[0].nexts.get(0);
            if(node == null || node.val != num){
                return false;
            }else{
                node.count--;
                if(node.count == 0){
                    for (int i = 0; i < node.nexts.size(); i++) {
                        var next = node.nexts.get(i);
                        pre[i].nexts.set(i, next);
                    }
                }
                return true;
            }
        }
    }

}

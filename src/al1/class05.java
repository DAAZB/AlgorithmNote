// 二叉树

package al1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

class TreeNode
{
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(){}
    TreeNode(int val)
    {
        this.val = val;
    }
}

class TreeNode1
{
    int val;
    TreeNode1 left;
    TreeNode1 right;
    TreeNode1 parent;
    TreeNode1(){}
    TreeNode1(int val)
    {
        this.val = val;
    }
}

public class class05 {
    public static void main(String[] args)
    {
        // System.out.println((1 << 4) - 1); //->15
        // System.out.println(1 << 4 - 1); //->8
        paperProblem1(5);
        System.out.println();
        paperProblem2(5);
    }


    // 1. 二叉树的遍历，一般有递归和迭代两种实现方式
    // 1.1 前序遍历
    public static List<Integer> preorderTraversal1(TreeNode root) { //递归
        List<Integer> res = new ArrayList<>();
        if(root == null)
        {
            return res;
        }
        res.add(root.val);
        if(root.left != null)
        {
            res.addAll(preorderTraversal1(root.left));
        }
        if(root.right != null)
        {
            res.addAll(preorderTraversal1(root.right));
        }
        return res;
    }
    public static List<Integer> preorderTraversal2(TreeNode root) { //迭代
        List<Integer> res = new ArrayList<>();
        if(root == null)
        {
            return res;
        }
        Stack<TreeNode> s = new Stack<>();
        s.push(root);
        while(!s.isEmpty())
        {
            var node = s.pop();
            res.add(node.val);
            if(node.right != null)
            {
                s.push(node.right);
            }
            if(node.left != null)
            {
                s.push(node.left);
            }
        }
        return res;
    }

    // 1.2 中序遍历
    public static List<Integer> inorderTraversal1(TreeNode root) { //递归
        List<Integer> res = new ArrayList<>();
        if(root == null)
        {
            return res;
        }
        if(root.left != null)
        {
            res.addAll(inorderTraversal1(root.left));
        }
        res.add(root.val);
        if(root.right != null)
        {
            res.addAll(inorderTraversal1(root.right));
        }
        return res;
    }
    public static List<Integer> inorderTraversal2(TreeNode root) { //迭代
        List<Integer> res = new ArrayList<>();
        if(root == null)
        {
            return res;
        }
        Stack<TreeNode> s = new Stack<>();
        while(!s.isEmpty() || root != null) //对每一个root
        {
            if(root != null)
            {
                s.push(root); //将其所有的左边界压栈
                root = root.left;
            }
            else
            {
                root = s.pop(); //左==null时，弹出打印，然后对弹出的节点重复上述操作
                res.add(root.val);
                root = root.right;
            }
        }
        return res;
    }

    // 1.3 后序遍历
    public static List<Integer> postorderTraversal1(TreeNode root) { //递归
        List<Integer> res = new ArrayList<>();
        if(root == null)
        {
            return res;
        }
        if(root.left != null)
        {
            res.addAll(postorderTraversal1(root.left));
        }
        if(root.right != null)
        {
            res.addAll(postorderTraversal1(root.right));
        }
        res.add(root.val);
        return res;
    }
    public static List<Integer> postorderTraversal2(TreeNode root) { //迭代
        List<Integer> res = new ArrayList<>();
        if(root == null)
        {
            return res;
        }
        Stack<TreeNode> s = new Stack<>(); //出栈顺序：中右左，反过来即为后序遍历
        Stack<TreeNode> s1 = new Stack<>(); //记录压栈顺序
        s.push(root);
        while(!s.isEmpty())
        {
            var node = s.pop();
            s1.push(node);
            if(node.left != null)
            {
                s.push(node.left);
            }
            if(node.right != null)
            {
                s.push(node.right);
            }
        }
        while(!s1.isEmpty())
        {
            res.add(s1.pop().val);
        }
        return res;
    }

    // 1.4 层序遍历
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if(root == null)
        {
            return res;
        }
        Queue<TreeNode> que = new LinkedList<>();
        que.add(root);
        while(!que.isEmpty())
        {
            int len = que.size();
            List<Integer> sublist = new ArrayList<>();
            while(len-- > 0)
            {
                var node =que.poll();
                sublist.add(node.val);
                if(node.left != null)
                {
                    que.add(node.left);
                }
                if(node.right != null)
                {
                    que.add(node.right);
                }
            }
            res.add(sublist);
        }
        return res;
    }

    // 1.5 树的最大宽度 leetcode662
    // 层的宽度不是节点的个数，是最左与最右节点之间的距离
    // public static int widthOfBinaryTree(TreeNode root) {
        
    // }

    // 2. 验证二叉搜索树（二叉搜索树：对于每一棵子树有左子树的值全都小于根节点，右子树的值全都大于根节点，
        // 即中序遍历时为严格升序，方法一中序遍历时添加判断是否为升序，方法二：）
    public static boolean checkBST(TreeNode root)
    {
        return BSThelper(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    static boolean BSThelper(TreeNode node, int min, int max)
    {
        if(node == null)
        {
            return true;
        }
        if(node.val <= min || node.val >= max)
        {
            return false;
        }
        return BSThelper(node.left, min, node.val) && BSThelper(node.right, node.val, max);
    }


    // 3. 验证完全二叉树
    public static boolean checkBFT(TreeNode root)
    {
        if(root == null)
        {
            return true;
        }
        Queue<TreeNode> que = new LinkedList<>(); //层序遍历
        boolean flag = false; //标记是否遇到了第一个不完整的节点(没有右孩子或没有孩子)
        que.add(root);
        while(!que.isEmpty())
        {
            var cur = que.poll();
            if(flag && (cur.left != null || cur.right != null)) //遇到第一个不完整的节点后，都应是叶节点
            {
                return false;
            }
            if(cur.left == null && cur.right != null) //有右孩无左孩，false
            {
                return false;
            }
            if(cur.left == null || cur.right == null) //第一个不完整节点
            {
                flag = true;
            }
            if(cur.left != null)
            {
                que.add(cur.left);
            }
            if(cur.right != null)
            {
                que.add(cur.right);
            }
        }
        return true;
    }


    // 4. 验证满二叉树
    public static boolean checkBFT1(TreeNode root)
    {
        // 层序遍历时判断层宽
        if(root == null)
        {
            return true;
        }
        Queue<TreeNode> que = new LinkedList<>();
        que.add(root);
        int level = 0;
        while(!que.isEmpty())
        {
            int len = que.size();
            if(len != (1<<level))
            {
                return false;
            }
            for (int i = 0; i < len; i++) {
                var cur = que.poll();
                if(cur.left != null)
                {
                    que.add(cur.left);
                }
                if(cur.right != null)
                {
                    que.add(cur.right);
                }
            }
            level++;
        }
        return true;
    }

    // 5. 二叉树的中最大深度
    public static int maxDepth1(TreeNode root) { //层序遍历法
        if(root == null)
        {
            return 0;
        }
        Queue<TreeNode> que = new LinkedList<>();
        que.add(root);
        int level = 0;
        while(!que.isEmpty())
        {
            int len = que.size();
            for (int i = 0; i < len; i++) {
                var cur = que.poll();
                if(cur.left != null)
                {
                    que.add(cur.left);
                }
                if(cur.right != null)
                {
                    que.add(cur.right);
                }
            }
            level++;
        }
        return level;
    }
    public static int maxDepth2(TreeNode root){ //迭代法
        if(root == null)
        {
            return 0;
        }
        if(root.left == null && root.right == null)
        {
            return 1;
        }
        return Math.max(maxDepth2(root.left), maxDepth2(root.right)) + 1;
    }

    // 6. 平衡二叉树
    public boolean isBalanced(TreeNode root) {
        if(root == null)
        {
            return true;
        }
        return isBalanced(root.left) && isBalanced(root.right) && (Math.abs(maxDepth2(root.left) - maxDepth2(root.right)) <=1);
    }

    // 7. 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。 leetcode236
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null)
        {
            return null;
        }
        if(root == p || root == q)
        {
            return root;
        }
        var leftAn = lowestCommonAncestor(root.left, p, q);
        var rightAn = lowestCommonAncestor(root.right, p, q);
        if(leftAn != null && rightAn != null)
        {
            return root;
        }
        if(leftAn != null)
        {
            return leftAn;
        }
        return rightAn;
    }


    // 8. 二叉树的后继节点（中序遍历的下一个）
    public static TreeNode inorderSuccessor1(TreeNode root, TreeNode p) {
        if(root == null)
        {
            return null;
        }
        boolean flag = false;
        Stack<TreeNode> st = new Stack<>();
        while(!st.isEmpty() || root != null)
        {
            if(root != null)
            {
                st.push(root);
                root = root.left;
            }
            else
            {
                var cur = st.pop();
                if(flag)
                {
                    return cur;
                }
                if(cur == p)
                {
                    flag = true;
                }
                root = cur.right;
            }
        }
        return null;

    }


    // 9.带父节点指针的二叉树后继节点
    // 该二叉树结点结构多了一个指向其父节点的parent指针
    public static TreeNode1 inorderSuccessor2(TreeNode1 root, TreeNode1 p) {
        if(root == null)
        {
            return null;
        }
        if(p.right != null)
        {
            var cur = p.right;
            while(cur.left != null)
            {
                cur = cur.left;
            }
            return cur;
        }
        else
        {
            var cur = p;
            while(cur != null)
            {
                var dad = cur.parent;
                if(dad.left == cur)
                {
                    return dad;
                }
                cur = dad;
            }
        }
        return null;
    }


    // 10. 二叉树的序列化与反序列化
    // 序列化：二叉树结构转变为字符串
    public static String SerialByPre(TreeNode root){ //前序遍历方式序列化
        if(root == null)
        {
            return "#_";
        }
        String res = root.val + "_";
        res += SerialByPre(root.left);
        res += SerialByPre(root.right);
        return res;
    }
    // 反序列化
    public static TreeNode reconByPreString(String preStr)
    {
        String[] value = preStr.split("_");
        Queue<String> que = new LinkedList<>();
        for (String string : value) {
            que.add(string);
        }
        return reconPreOrder(que);
    }
    public static TreeNode reconPreOrder(Queue<String> que)
    {
        var val = que.poll();
        if(val == "#")
        {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(val));
        root.left = reconPreOrder(que);
        root.right = reconPreOrder(que);
        return root;
    }

    // 11. 折纸条问题
    // 将一条纸条向上对折n次，输出打开后从上到下依次的折痕方向
    public static void paperProblem1(int n){ //笨办法：建树，遍历
        if(n<1)
        {
            return;
        }
        TreeNode root = new TreeNode(0); //0表示折痕朝下， 1表示朝上
        Queue<TreeNode> que = new LinkedList<>();
        que.add(root);
        while(--n>0)
        {
            int len = que.size();
            while(len-- > 0)
            {
                var cur = que.poll();
                cur.left = new TreeNode(0); //对于每一次对折产生的折痕，下一次对折会在他们左边产生朝下的折痕
                cur.right = new TreeNode(1); //右边产生朝上的折痕
                que.add(cur.left);
                que.add(cur.right);
            }
        }
        printFold(root);
    }
    public static void printFold(TreeNode root) //从上往下输出即按生成的树的中序遍历输出
    {
        if(root == null)
        {
            return;
        }
        Stack<TreeNode> st = new Stack<>();
        while(!st.empty() || root != null)
        {
            if(root != null)
            {
                st.push(root);
                root = root.left;
            }
            else
            {
                var cur = st.pop();
                System.out.print(cur.val + ",");
                root = cur.right;
            }
        }
    }

    public static void paperProblem2(int n){ //模拟中序遍历递归
        printFold2(1, n, 0);
    }
    public static void printFold2(int i, int n, int fold){
        if(i>n){ //i表示当前是第几次对折，即到树的第几层了
            return;
        }
        printFold2(i+1, n, 0);
        System.out.print(fold + ",");
        printFold2(i+1, n, 1);
    }


}

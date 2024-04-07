// 树形dp，morris遍历
package al2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

class TreeNode
{
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int val)
    {
        this.val = val;
    }
}

public class class05 {
    public static void main(String[]args){
        // TreeNode root = new TreeNode(4);
        // root.left = new TreeNode(-7);
        // root.right = new TreeNode(-3);
        // root.right.left = new TreeNode(-9);
        // root.right.right = new TreeNode(-3);
        // root.right.left.left = new TreeNode(9);
        // root.right.left.right = new TreeNode(-7);
        // root.right.right.left = new TreeNode(-4);
        // root.right.left.left.left = new TreeNode(6);
        // root.right.left.right.left = new TreeNode(-6);
        // root.right.left.right.right = new TreeNode(-6);
        // root.right.left.left.left.left = new TreeNode(0);
        // root.right.left.left.left.right = new TreeNode(6);
        // root.right.left.right.left.left = new TreeNode(5);
        // root.right.left.right.right.left = new TreeNode(9);
        // root.right.left.left.left.left.right = new TreeNode(-1);
        // root.right.left.left.left.right.left = new TreeNode(-4);
        // root.right.left.right.right.left.left = new TreeNode(-2);
        // System.out.println(diameterOfBinaryTree(root));


        // TreeNode root = new TreeNode(1);
        // root.left = new TreeNode(2);
        // root.left.left = new TreeNode(4);
        // root.left.right = new TreeNode(5);
        // root.right = new TreeNode(3);
        // root.right.left = new TreeNode(6);
        // root.right.right = new TreeNode(7);
        // morris(root);

        System.out.println("\u5B88 \u671B \u5148 \u950B");


    }


    // 1. 二叉树节点间的最大距离 leetcode543
    public static int diameterOfBinaryTree(TreeNode root) {
        return diameterOfBinaryTreeHelper(root).dia;
    }
    public static class Info{
        int height;
        int dia;
        Info(int height, int dia){
            this.height = height;
            this.dia = dia;
        }
    }
    public static Info diameterOfBinaryTreeHelper(TreeNode root){
        if(root == null){
            return new Info(0, 0);
        }
        Info info1 = diameterOfBinaryTreeHelper(root.left);
        Info info2 = diameterOfBinaryTreeHelper(root.right);
        return new Info(Math.max(info1.height, info2.height) + 1, Math.max(Math.max(info1.dia, info2.dia), info1.height + info2.height));
    }


    // 2. 最大快乐值的派对 （leetcode打家劫舍）
    // 多叉树取值，相邻节点不能同时选取，返回最大总和
    public class Node{
        int val;
        ArrayList<Node> nexts;
        Node(int val){
            this.val = val;
            nexts = new ArrayList<>();
        }
    }
    public static class RobInfo{
        int take; //选取时能取得的最大值
        int skip; //跳过时、、、
        RobInfo(int take, int skip){
            this.take = take;
            this.skip = skip;
        }
    }
    public static int rob(Node head){
        RobInfo info = robHelper(head);
        return Math.max(info.take, info.skip);
    }
    public static RobInfo robHelper(Node head){
        if(head.nexts.isEmpty()){
            return new RobInfo(head.val, 0);
        }
        int take = head.val;
        int skip = 0;
        for (Node node : head.nexts) {
            RobInfo info = robHelper(node);
            take += info.skip;
            skip += Math.max(info.take, info.skip);
        }
        return new RobInfo(take, skip);
    }


    // 3. morris遍历
    // 假设来到当前节点cur,开始时cur来到头节点位置
    // )如果cur没有左孩子，cur向右移动(cur=cur.right)
    // 2)如果cur有左孩子，找到左子树上最右的节点mostRight:
    //     a.如果mostRight的右指针指向空，让其指向cur,
    //     然后cur向左移动(cur=cur.left)
    //     b.如果mostRight的右指针指向cur,让其指向null,
    //     然后cur向右移动(cur=cur.right)
    // 3)cur为空时遍历停止
    public static void morris(TreeNode root){
        TreeNode cur = root;
        while(cur != null){
            // System.out.print(cur.val + ",");
            if(cur.left == null){
                // System.out.print(cur.val + ",");
                cur = cur.right;
            }
            else{ //有左孩子的节点
                // System.out.print(cur.val + ",");
                TreeNode mostRight = cur.left;
                while(true){
                    if(mostRight.right == null){ //第一次来到cur
                        // System.out.print(cur.val + ",");
                        mostRight.right = cur;
                        cur = cur.left;
                        break;
                    }
                    if(mostRight.right == cur){ //第二次来到cur
                        // System.out.print(cur.val + ",");
                        mostRight.right = null;
                        cur = cur.right;
                        break;
                    }
                    mostRight = mostRight.right;
                }
            }
        }
    }

    // 3.1 morris
    // 先序遍历：有左子树的cur：第一次来到cur时打印，其余节点：正常打印
    // 中序遍历：有左子树的cur：第二次来到cur时打印，其余节点：正常打印
    // 后序遍历：仅在第二次来到cur时逆序打印cur左子树的右边界，遍历结束后逆序打印整棵树的右边界
    //              逆序打印：逆序链表法

}

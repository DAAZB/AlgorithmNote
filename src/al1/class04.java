// 链表
// 笔试：可以只考虑时间复杂度，以快速通过为准（借用哈希表、站等数据结构）
// 面试：优先考虑时间复杂度，然后找到空间复杂度最优的算法
package al1;

import java.util.HashMap;
import java.util.Stack;

class ListNode
{
    public int val;
    public ListNode next;
    ListNode(){}
    ListNode(int val)
    {
        this.val = val;
        this.next = null;
    }

    public static ListNode createNodeList(int[] nums)
    {
        if(nums == null || nums.length < 1)
            return null;
        int n = nums.length;
        ListNode head = new ListNode(nums[0]);
        var cur = head;
        for (int i = 1; i < n; i++) {
            cur.next = new ListNode(nums[i]);
            cur = cur.next;
        }
        return head;
    }
}

class RListNode
{
    public int val;
    public RListNode next;
    public RListNode random; //可指向任意节点
    RListNode(){}
    RListNode(int val)
    {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}

public class class04 {
    public static void main(String[] args)
    {
        int[] nums = {4,5,6,3,2,5,8,4,1,3,6,8,7,4,5,6,3,2,1,5,6,8,7,4};
        ListNode head = ListNode.createNodeList(nums);
        var cur = nodeListPartition(head, 3);
        while(cur != null)
        {
            System.out.print(cur.val + ", ");
            cur = cur.next;
        }

    }

    // 1. 逆序链表
    public static ListNode reverseNodeList(ListNode head)
    {
        ListNode cur = head;
        ListNode pre = null;
        while(cur != null)
        {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    // 2. 回文链表1
    // 快慢指针法，将链表后半压栈，再与链表前半对比
    // O(N) O(N)
    public static boolean isPalindrome1(ListNode head)
    {
        if(head == null || head.next == null)
        {
            return true;
        }
        ListNode fast = head;
        ListNode slow = head;
        while(fast.next != null && fast.next.next != null) 
        {
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = fast.next!=null ? fast.next : fast; //slow -> mid, fast -> end
        // 后半部分入栈
        Stack<ListNode> st = new Stack<>();
        slow = slow.next;
        while(slow != null)
        {
            st.add(slow);
            slow = slow.next;
        }
        slow = head;
        while(!st.isEmpty())
        {
            if(st.pop().val != slow.val)
            {
                return false;
            }
            slow = slow.next;
        }
        return true;
    }

    // 3. 回文链表2
    // 找到中点后将后半段链表逆序，再分别从两头往中间遍历，返回结果前将链表复原
    // O(N) O(1)
    public static boolean isPalindrome2(ListNode head)
    {
        if(head == null || head.next == null)
        {
            return true;
        }
        ListNode fast = head;
        ListNode slow = head;
        while(fast.next != null && fast.next.next != null) 
        {
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = fast.next!=null ? fast.next : fast; //slow -> mid, fast -> end
        //反转后半链表
        reverseNodeList(slow);
        boolean res = true;
        slow = head; //此时快慢指针分别指向两头
        var head2 = fast;
        while(slow != null && fast != null)
        {
            if(slow.val != fast.val)
            {
                res = false;
                break;
            }
                slow = slow.next;
                fast = fast.next;
        }
        //将链表复原
        reverseNodeList(head2);
        return res;
    }


    // 4. 链表分块
    public static ListNode nodeListPartition(ListNode head, int num)
    {
        if(head == null || head.next == null)
        {
            return head;
        }
        ListNode sH = null; //小于区域的头
        ListNode sT = null; //小于区域的尾
        ListNode eH = null; //等于区域的头
        ListNode eT = null;
        ListNode bH = null;
        ListNode bT = null;
        var cur = head;
        while(cur != null)
        {
            var next = cur.next;
            cur.next = null;
            if(cur.val < num)
            {
                if(sH == null)
                {
                    sH = cur;
                    sT = cur;
                }
                else
                {
                    sT.next = cur;
                    sT = cur;
                }
            }
            else if(cur.val == num)
            {
                if(eH == null)
                {
                    eH = cur;
                    eT = cur;
                }
                else
                {
                    eT.next = cur;
                    eT = cur;
                }
            }
            else
            {
                if(bH == null)
                {
                    bH = cur;
                    bT = cur;
                }
                else
                {
                    bT.next = cur;
                    bT = cur;
                }
            }
            cur = next;
        }
        ListNode resHead = null;
        if(sH != null)
        {
            resHead = sH;
            if(eH != null)
            {
                sT.next = eH;
                eT.next = bH;
            }
            else
            {
                sT.next = bH;
            }
        }
        else if(eH != null)
        {
            resHead = eH;
            eT.next = bH;
        }
        else
        {
            resHead = bH;
        }
        return resHead;
    }


    // 5. 复制含有随机指针节点的链表 leetcode138
        // 【题目】一种特殊的单链表节点类描述如下
        // class Node{
        // int value;
        // Node next;
        // Node rand;
        // Node(int val){
        // value val}
        // rand指针是单链表节点结构中新增的指针，rand可能指向链表中的任意一个节
        // 点，也可能指向ulI。给定一个由Node节点类型组成的无环单链表的头节点
        // head,请实现一个函数完成这个链表的复制，并返回复制的新链表的头节点。
    // 方法一：hashmap O(N) O(N)
    public RListNode copyRandomList1(RListNode head)
    {
        if(head == null)
        {
            return null;
        }
        HashMap<RListNode, RListNode> hm = new HashMap<>();
        var cur = head;
        while(cur != null)
        {
            RListNode copy = new RListNode(cur.val);
            hm.put(cur, copy);
            cur = cur.next;
        }
        cur = head;
        while(cur != null)
        {
            hm.get(cur).next = hm.get(cur.next);
            hm.get(cur).random = hm.get(cur.random);
            cur = cur.next;
        }
        return hm.get(head);
    }

    //方法二：O(N) O(1)
    public RListNode copyRandomList2(RListNode head)
    {
        if(head == null)
        {
            return null;
        }
        var cur = head;
        while(cur != null) //复制每一个节点，插入到原节点的后方，[1,1',2,2',3,3',...]
        {
            var next = cur.next;
            RListNode copy = new RListNode(cur.val);
            cur.next = copy;
            copy.next = next;
            cur = next;
        }
        cur = head;
        while(cur != null) //按对遍历，拷贝random值
        {
            var copy = cur.next;
            copy.random = cur.random == null ? null : cur.random.next; //源节点与拷贝成对
            cur = copy.next;
        }
        cur = head;
        var resHead = cur.next;
        while(cur != null) //分离源与拷贝
        {
            var copy = cur.next;
            cur.next = copy.next;
            cur = cur.next;
            copy.next = cur == null ? null : cur.next;
        }
        return resHead;
    }

    // 6. 相交链表1：两个无环链表，判断是否相交，返回第一个相交节点，否则返回null leetcode160
    // O(N) O(1)
    public ListNode getIntersectionNode1(ListNode head1, ListNode head2)
    {
        var p1 = head1;
        var p2 = head2;
        while(p1 != p2)
        {
            p1 = p1 == null ? head2 : p1.next;
            p2 = p2 == null ? head1 : p2.next;
        }
        return p1;
    }

    // 7. 相交链表2：两个链表可能有环，判断是否相交，返回第一个相交节点，否则返回null
    // O(N) O(1)
    public ListNode getIntersectionNode2(ListNode head1, ListNode head2)
    {
        // 先分别判断是否有环，如果相交，两个链表一定同时有环或无环
        var inLoop1 = hasLoop(head1);
        var inLoop2 = hasLoop(head2);
        if((inLoop1 == null && inLoop2 != null) || (inLoop2 == null && inLoop1 != null)) //有无环状态不同，一定不相交
        {
            return null;
        }
        else if(inLoop1 != null && inLoop2 != null) //同时有环
        {
            if(inLoop1 == inLoop2) //先相交，后入环。与无环情况相同,终点为inloop节点
            {
                var p1 = head1;
                var p2 = head2;
                while (p1 != p2)
                {
                    p1 = p1 == inLoop1 ? head2 : p1.next;
                    p2 = p2 == inLoop1 ? head1 : p2.next;
                }
                return p1;
            }
            else //如果为同一个环，任意一个转一圈，判断是否遇到对方
            {
                var cur = inLoop1.next;
                while(cur != inLoop1)
                {
                    if(cur == inLoop2)
                    {
                        return inLoop1;
                    }
                    cur = cur.next;
                }
                return null;
            }
        }
        else //同时无环，相交链表1
        {
            return getIntersectionNode1(head1, head2);
        }
    }

    // 8. 判断一个链表是否有环 leetcode142
    // 快慢指针法 O(N) O(1)
    public static ListNode hasLoop(ListNode head)
    {
        var slow = head;
        var fast = head;
        while(fast != null && fast.next != null)
        {
            slow = slow.next;
            fast = fast.next.next;
            if(fast == slow) //相遇说明有环， 此时相遇点和头节点到入环的位置距离相同
            {
                fast = head;
                while(fast != slow)
                {
                    fast = fast.next;
                    slow = slow.next;
                }
                return slow;
            }
        }
        return null;
    }

}

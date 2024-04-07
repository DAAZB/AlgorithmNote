
package al1;

import java.util.PriorityQueue;

public class class03 {
    public static void main(String[] args)
    {
        int[] nums = {12,563,1,465,85,34,625,125,41,5,874};
        radixSort(nums);
        for (int i : nums) {
            System.out.println(i);
        }
    }

    // 1.数组与完全二叉树
    // 一个数组可视为一颗完全二叉树,将数按层序填满成为数
    // 节点与数组下标对应关系:i.left = 2*i+1, i.right = 2*i+2, i.father = (i-1)/2


    // 2. 堆：有序的完全二叉树
    // 大根堆：每个节点的值是以该节点为根的子树的最大值
    // 堆结构:数组nums前heapSize个数构成一个大根堆


    // 3. 堆排序
    // O(NlogN), O(1)
    public static void heapSort(int[] nums)
    {
        if(nums == null || nums.length < 2)
        {
            return;
        }

        //建堆1: O(Nlog(N)) ***********
        // int heapSize = 0;
        // for (; heapSize < nums.length; heapSize++) { // O(N)
        //     heapInsert(nums, heapSize); // O(logN)
        // }
        // ***************************

        //建堆2: O(N) *****************
        int heapSize = nums.length;
        for (int i = nums.length-1; i >= 0; i--) {
            heapify(nums, i, heapSize);
        }
        // ***************************


        // 每次将nums[0]与nums[heapSize-1]交换,heapSize--,对0位置作heapify
        while(heapSize > 0)
        {
            class01.swap(nums, 0, --heapSize);
            heapify(nums, 0, heapSize);
        }
    }

    // 3.1 heapInsert操作 O(logN)
    // 某个数处在index位置，往上继续移动,以保持堆结构
    public static void heapInsert(int[] nums, int index)
    {
        // 比父节点大时,交换,继续往上比
        while (nums[index] > nums[(index-1)/2])
        {
            class01.swap(nums, index, (index-1)/2);
            index = (index-1)/2;
        }
    }

    // 3.2 heapify操作 O(lobN)
    // 某个数处在index位置,往下移动,以保持堆结构
    public static void heapify(int[] nums, int index, int heapSize)
    {
        int left = 2 * index + 1;
        while(left < heapSize) //当下方还有孩子时
        {
            // 取出左右孩子中较大的数的下标给largest
            int largest = (left + 1 < heapSize && nums[left+1] > nums[left]) ? left + 1 : left;
            // 取largest与index处较大的数的下标给largest
            largest = nums[largest] > nums[index] ? largest : index;
            if(largest == index) //该数已到达理想位置,
            {
                break;
            }
            class01.swap(nums, largest, index); //否则继续向下交换
            index = largest;
            left = 2 * index + 1;
        }
    }


    // 4. 堆结构总结
    //     1,堆结构就是用数组实现的完全二叉树结构
    //     2,完全二叉树中如果每棵子树的最大值都在顶部就是大根堆
    //     3,完全二叉树中如果每棵子树的最小值都在顶部就是小根堆
    //     4,堆结构的heapInsert与heapify操作
    //     5,堆结构的增大和减少
    //     6,优先级队列结构，就是堆结构

    // 5. 堆排序拓展
    // 已知一个几乎有序的数组，几乎有序是指，如果把数组排好顺序的话，每个元素移动的距离可以不超过k,
    // 并且k相对于数组来说比较小。请选择一个合适的排序算法针对这个数据进行排序。
    // 对前k+1个数建小根堆,循环以下过程:抛出堆顶,将堆右侧的数加入堆.时间复杂度O(Nlogk)
    public static void heapSort(int[] nums, int k)
    {
        PriorityQueue<Integer> heap = new PriorityQueue<>(); //优先级队列底层就是一个小根堆
        for (int i = 0; i <= Math.min(nums.length-1, k); i++)
        {
            heap.add(nums[i++]); //入堆
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = heap.poll(); //抛出堆顶
            if(i+k+1 < nums.length)
            {
                heap.add(nums[i+k+1]);
            }
        }
    }

    // 6. 计数排序：统计词频，数值范围较小时适用

    // 7. 基数排序-桶排序
    public static void radixSort(int[] nums)
    {
        if(nums == null || nums.length < 2)
        {
            return;
        }
        radixSort(nums, 0, nums.length-1, maxBits(nums));
    }
    // 获取数组的最大十进制位有多少位
    public static int maxBits(int[] nums)
    {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            max = Math.max(max, nums[i]);
        }
        int res = 0;
        while(max > 0)
        {
            max /= 10;
            res++;
        }
        return res;
    }
    // 获取nums第d位上的数字
    public static int getDigit(int num, int d)
    {
        return (int)(num / Math.pow(10, d-1)) % 10;
    }
    public static void radixSort(int[] nums, int l, int r, int digit)
    {
        final int radix = 10; //十进制
        int[] help = new int[r-l+1]; //辅助数组，存放入桶出桶后的结果
        int i;
        int j;
        int d;
        for(i = 1; i <= digit; i++) //最大的数有多少位就循环多少次
        {
            // 统计第i位上的数字的个数
            int[] count = new int[radix]; //计数数组，统计某一位上数字==i的个数
            for(j = l; j <= r; j++)
            {
                d = getDigit(nums[j], i);
                count[d]++;
            }
            // count变前缀和
            for(j = 1; j < radix; j++)
            {
                count[j] = count[j] + count[j-1];
            } //此时已完成入桶，前缀和分片
            for(j = r; j >= l; j--) //从右往左遍历，完成出桶操作
            {
                d = getDigit(nums[j], i);
                help[count[d] - 1] = nums[j];
                count[d]--;
            }
            for(j = 0; j <= r - l; j++)
            {
                nums[j+l] = help[j];
            }
        }
    }


    // 8. 排序算法总结
    // 排序算法的稳定性：相等的值排完序后相对位置不发生变化，则称稳定
    // 没有稳定性的算法：选择（跨位置交换），快排（跨位置交换），堆排
    // 有稳定性的算法：冒泡排序（相等时不交换），插入，归并排序（merge时左优先），桶排序思想
    // 快排的常数项耗时最少，优先选用
    // 目前为止不存在时间复杂度O(NlogN)，空间复杂度<O(N)，且具有稳定性的排序算法


    // 9. 常见的坑
    //     1,归并排序的额外空间复杂度可以变成O(1)但是非常难，不需要掌握，有兴必趣可以搜“归并排序内部缓存法”
    //     2,“原地归并排序”的帖子都是垃圾，会让归并排序的时间复杂度变成O(N^2)
    //     3,快速排序可以做到稳定性问题，但是非常难，不需要掌握，可以搜“01 stable sort”
    //     4,所有的改进都不重要，因为目前没有找到时间复杂度O(N*logN),额外空间复杂度O(1)，又稳定的排序。
    //     5,有一道题目，是奇数放在数组左边，偶数放在数组右边，还要求原始的相对次序不变，碰到这个问题，可以怼面试官。
    //      相当于快排的01问题，奇数放左边，偶数放右边，

    // 10. 工程上对排序的改进
    // 1)充分利用O(N*IogN)和O(N^2)排序各自的优势
    // 2)稳定性的考虑
}

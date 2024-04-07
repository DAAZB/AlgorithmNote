//O(NlogN)的排序
package al1;

public class class02 {
    public static void main(String[] args)
    {
        // int[] nums = {5,3,7,2,8,1,9,6,3,4};
        // mergeSort(nums);
        // for (int i : nums) {
        //     System.out.println(i);
        // }

        // int[] nums = {1,1,1,3,3,5,2,6,7,6,2,1,1,5,6};
        // System.out.println(smallSum(nums));
        
        int[] nums = {7,2,3,6,5,4,8,3,3,5,4,8,6,9,4,1,2,5,8,4};
        quickSort1(nums);
        for (int i : nums) {
            System.out.println(i);
        }
    }


    // 1. 归并排序,时间复杂度为O(NlogN)的排序,空间复杂度O(N)
    // 根据master公式,a=2, b=2, d=1, 则Iog(b,a)=d->复杂度为O(N^d*IogN)=O(NlogN)
    // 归并排序优于O(N^2)排序算法的原因,每次比较都是有效比较,比较的信息保留并传递了下去
    // 冒泡排序等算法有大量冗余的的比较操作
    public static void mergeSort(int[] nums)
    {
        if(nums == null || nums.length < 2)
            return;
        process(nums, 0, nums.length-1);

    }
    public static void process(int[] nums, int L, int R)
    {
        if(L == R)
        {
            return;
        }
        int mid = L + ((R-L)>>1);
        process(nums, L, mid);
        process(nums, mid + 1, R);
        merge(nums, L, R, mid);
    }
    public static void merge(int[] nums, int L, int R, int mid)
    {
        int[] help = new int[R - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = mid + 1;
        while(p1<=mid && p2 <= R)
        {
            help[i++] = nums[p1] <= nums[p2] ? nums[p1++] : nums[p2++];
        }
        while(p1 <= mid)
        {
            help[i++] = nums[p1++];
        }
        while(p2 <= R)
        {
            help[i++] = nums[p2++];
        }
        for(int j = 0; j < help.length; j++)
        {
            nums[L + j] = help[j];
        }
    }


    // 2.小和问题:在一个数组中，每一个数左边比当前数小的数累加起来，叫做这个数组的小和。求一个数组的小和。
    // 例子：[1,3,4,2,5]1左边比1小的数，没有；3左边比3小的数，1；4左边比4小的数，1、3；2左边比2小的数，1；5左边
    // 比5小的数，1、3、4、2;所以小和为1+1+3+1+1+3+4+2=16
    // 思路,转化为求每一个数对小和有多少次贡献,即每个数比它右边几个数小
    public static int smallSum(int[] nums)
    {
        if(nums == null || nums.length<2)
        {
            return 0;
        }
        return smallSumProcess(nums, 0, nums.length-1);
    }
    public static int smallSumProcess(int[] nums, int L, int R)
    {
        if(L == R)
        {
            return 0;
        }
        int mid = L + ((R - L) >> 1);
        return smallSumProcess(nums, L, mid) + smallSumProcess(nums, mid + 1, R) + smallMerge(nums, L, R, mid);
    }
    public static int smallMerge(int[] nums, int L, int R, int mid)
    {
        int[] help = new int[R-L+1];
        int i = 0;
        int p1 = L;
        int p2 = mid + 1;
        int res = 0;
        while(p1<=mid && p2<=R)
        {
            res += nums[p1]<nums[p2] ? nums[p1] * (R-p2+1) : 0;
            help[i++] = nums[p1]<nums[p2] ? nums[p1++] : nums[p2++]; //相等时,先拷贝右组的数,否则左组中会存在漏算情况
        }
        while(p1<=mid)
        {
            help[i++] = nums[p1++];
        }
        while(p2<=R)
        {
            help[i++] = nums[p2++];
        }
        for(i = 0; i<help.length; i++)
        {
            nums[L+i] = help[i];
        }
        return res;
    }


    // 3.给定一个数组arr,和一个数num,请把小于等于num的数放在数组的左边，大于num的数放在数组的右边。
    // 要求额外空间复杂度O(1)，时间复杂度O(N)
    public static void hollandFlag1(int[] nums, int num)
    {
        if(nums == null || nums.length < 2)
        {
            return;
        }
        int p1 = 0;
        int p2 = nums.length-1; //首尾双指针
        while(p1<p2)
        {
            if(nums[p1] > num)
            {
                class01.swap(nums, p1, p2--);
            }
            else if(nums[p2] <= num)
            {
                class01.swap(nums, p1++, p2);
            }
            else
            {
                p1++;
                p2--;
            }
        }
    }

    // 4.荷兰国旗问题:给定一个数组arr,和一个数num,请把小于num的数放在数组的左边，等于num的数放
    // 在数组的中间，大于num的数放在数组的右边。要求额外空间复杂度0()，时间复杂度0(N)
    public static void hollandFlag2(int[] nums, int num)
    {
        if(nums == null || nums.length < 2)
        {
            return;
        }
        int p1 = -1; //小于区的右边界
        int p2 = nums.length; //大于区的左边界
        int i = 0; //当前遍历位置
        while(i<p2)
        {
            if(nums[i] < num)
            {
                class01.swap(nums, i++, ++p1);
            }
            else if(nums[i] == num)
            {
                i++;
            }
            else
            {
                class01.swap(nums, i, --p2);;
            }
        }
    }


    // 5.快速排序2.0
    // 递归的荷兰国旗,以最右侧的数作划分值
    // O(N^2),时间复杂度依赖数据状态,最右侧的数偏离中位数越远,情况越坏
    // 空间复杂度O(N), 最差要开辟N层递归调用栈
    public static void quickSort1(int[] nums)
    {
        if(nums == null || nums.length < 2)
        {
            return;
        }
        quickSortProcess(nums, 0, nums.length-1);
    }
    public static void quickSortProcess(int[] nums, int l, int r) //l,r:未排序的区间
    {
        if(l >= r)
        {
            return;
        }
        int[] pt = hollandFlag3(nums, l, r, nums[r]);
        quickSortProcess(nums, l, pt[0]);
        quickSortProcess(nums, pt[1], r);
    }
    public static int[] hollandFlag3(int[] nums, int l, int r, int num) //返回左右区域的边界
    {
        int p1 = l-1; //小于区的右边界
        int p2 = r+1; //大于区的左边界
        int i = l; //当前遍历位置
        while(i<p2)
        {
            if(nums[i] < num)
            {
                class01.swap(nums, i++, ++p1);
            }
            else if(nums[i] == num)
            {
                i++;
            }
            else
            {
                class01.swap(nums, i, --p2);;
            }
        }
        int[] pt = {p1, p2};
        return pt;
    }

    // 6.快速排序3.0
    // 每次在区间内随机选取一个数做划分值,任何数据状态下遇到坏情况都成为概率事件
    // O(NlogN) O(logN)
    public static void quickSort2(int[] nums)
    {
        if(nums == null || nums.length < 2)
        {
            return;
        }
        quickSortProcess(nums, 0, nums.length-1);
    }
    public static void quickSortProcess2(int[] nums, int l, int r) //l,r:未排序的区间
    {
        if(l >= r)
        {
            return;
        }
        int rand = l + (int)(Math.random() * (r-l+1)); //随机取区间内的一个数作划分值
        int[] pt = hollandFlag3(nums, l, r, nums[rand]);
        quickSortProcess(nums, l, pt[0]);
        quickSortProcess(nums, pt[1], r);
    }

}

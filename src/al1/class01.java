// 时间复杂度与简单排序算法
package al1;

public class class01 {
    public static void main(String[] args)
    {
        // int[] a = {1,2,3,4,5,6,7,8,9};
        
        // long startTime = System.currentTimeMillis(); //数据状况不同，插入排序所需的时间差别很大
        // for (int i = 0; i < 10000000; i++) {
        //     int[] a = {1,2,3,4,5,6,7,8,9};
        //     insertSort(a);
        // }
        // long endTime = System.currentTimeMillis();
        // System.out.println(endTime-startTime);
        // startTime = System.currentTimeMillis();
        // for (int i = 0; i < 10000000; i++) {
        //     int[] a = {1,2,3,4,5,6,7,8,9};
        //     bubbleSort(a);
        // }
        // endTime = System.currentTimeMillis();
        // System.out.println(endTime-startTime);
        
        // for (int i = 0; i < a.length; i++) {
        //     System.out.println(a[i]);
        // }
        

        int[] a = {5,4,8,4,2,10,5,9,2,8};
        System.out.println(getSubMax(a, 0, a.length-1));
    }


    // 1.时间复杂度， 额外空间复杂度


    // 2.冒泡排序 O(N^2) O(0)
    public static void bubbleSort(int[] arr)
    {
        if(arr == null || arr.length < 2)
        {
            return;
        }
        for (int i = arr.length-1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if(arr[j]>arr[j+1])
                {
                    swap(arr, j, j+1);
                }
            }
        }
    }

    // 3.位运算交换数组的两个数
    // 交换的两个数可以相等，但不能指向同一内存地址，i!=j
    // 异或(^)的性质：1)a^b=b^a  2)a^b^c=a^(b^c)  3)a^a=0  4)a^0=a
    // public static void swap(int[] arr, int i, int j)
    // {
    //     arr[i] = arr[i] ^ arr[j];
    //     arr[j] = arr[i] ^ arr[j];
    //     arr[i] = arr[i] ^ arr[j];
    // }
    public static void swap(int[] arr, int i, int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 4. 一个数组中只有一种数出现了奇数次，其他数都出现了偶数次，找出这个数
    // O(N) O(0)
    public static int findOddNum(int[] nums)
    {
        int eor=0;
        for (int i = 0; i < nums.length; i++) {
            eor ^= nums[i];
        }
        return eor;
    }

    // 5. 一个数组中只有两种数出现了奇数次，其他数都出现了偶数次，找出这两个数
    // O(N) O(0)
    public static int[] findOddNum2(int[] nums)
    {
        int eor=0;
        for (int i = 0; i < nums.length; i++) {
            eor ^= nums[i];
        }
        // eor = a ^ b
        // eor 中必有一位为1，a，b在这一位上必然不同，根据这一位将数组分为两堆，分别包含a，b
        int rightOne = eor & (~eor +1); //找出eor最右边的1， ~表示位取反
        int res = 0;
        for (int i : nums) {
            if((i&rightOne) != 0)
            {
                res ^= i;
            }
        }
        int[] res1 = {res, res^eor};
        return res1;
    }

    // 6. 给你一个整数数组 nums ，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次 。请你找出并返回那个只出现了一次的元素。
    // O(N) O(0)
    public static int findOddNum3(int[] nums)
    {
        int res = 0;
        for (int i = 0; i < 32; i++) {
            int count = 0; //所有数字同一位上的值累加一定是3N或3N+1，后者即表示答案在这一位为1
            for(int num: nums)
            {
                count += (num>>i) & 1;
            }
            if(count%3 == 1)
            {
                res |= (1<<i);
            }
        }
        return res;
    }

    // 7. 插入排序
    // 数据状况不同，时间复杂度不同，数组基本有序时，所需的操作很少
    public static void insertSort(int[] nums)
    {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int j = i; (j > 0) && (nums[j] < nums[j-1]); j--) {
                swap(nums, j, j-1);
            }
        }
    }

    // 8. 二分查找
    // O(logN)
    public static int searchNum(int[] nums, int target)
    {
        int left = 0;
        int right = nums.length;
        int mid;
        while(left <= right)
        {
            mid = (left + right)/2;
            if(nums[mid] == target)
            {
                return mid;
            }
            else if(nums[mid] > target)
            {
                right = mid + 1;
            }
            else
            {
                left = mid + 1;
            }
        }
        return -1;
    }

    // 9.找大于等于target最左侧的位置
    // 二分到底
    public static int searchBiggestIndex(int[] nums, int target)
    {
        int left = 0;
        int right = nums.length;
        int mid, res = -1;
        while(left <= right)
        {
            mid = (left + right) / 2;
            if(nums[mid] >= target)
            {
                right = mid - 1;
                res = mid;
            }
            else
            {
                left = mid + 1;
            }
        }
        return res;
    }


    // 10. nums无序且相邻位置的数不相等，找出一个局部最小值（小于相邻位置）
    // 二分操作不一定只针对有序序列，根据数据和问题能确定甩掉一半即可二分
    public static int minOfRegion(int[] nums)
    {
        if(nums[0] < nums[1])
        {
            return 0;
        }
        if(nums[nums.length-1] < nums[nums.length-2])
        {
            return nums.length-1;
        }
        // 如果首尾不是局部最小，那中间一定存在局部最小，
        int left = 0;
        int right = nums.length-1;
        int mid;
        while(left <= right)
        {
            mid = (left + right)/2;
            if(nums[mid] < nums[mid-1] && nums[mid] < nums[mid+1])
            {
                return mid;
            }
            if(nums[mid] > nums[mid-1]) //mid值不是局部最小，则将mid变成首或尾，子数组中一定存在局部最小
            {
                right = mid -1;
            }
            else
            {
                left = mid + 1;
            }
        }
        return -1;
    }

    // 11. 对数器的概念和使用
    // 1,有一个你想要测的方法a
    // 2,实现复杂度不好但是容易实现的方法b
    // 3,实现一个随机样本产生器
    // 4,把方法a和方法b跑相同的随机样本，看看得到的结果是否一样。
    // 5,如果有一个随机样本使得比对结果不一致，打印样本进行人工干预，改对方法a或者方法b
    // 6,当样本数量很多时比对测试依然正确，可以确定方法a已经正确。

    // 12. 递归查找arr[L...R]范围内最大值
    public static int getSubMax(int[] nums, int L, int R)
    {
        if(L == R)
        {
            return nums[L];
        }
        int mid = L + ((R - L) >> 1); //取中值mid = (L+R)/2, 可能会溢出
        int leftMax = getSubMax(nums, L, mid);
        int rightMax = getSubMax(nums, mid + 1, R);
        return Math.max(leftMax, rightMax);
    }

    // 13. master公式：T(N) = a*T(N/b) + O(N^d)
    // N-母问题数据规模，a-子问题的调用次数，N/b-子问题数据规模， O(N^d)-除去子问题的时间复杂度
    // 用于求解子问题等规模的递归函数的时间复杂度
    // 12中:T(N) = 2*T(N/2) + O(0)
    // 求法:
    // 1)Iog(b,a)>d->复杂度为O(N^Iog(b,a))
    // 2)Iog(b,a)=d->复杂度为O(N^d*IogN)
    // 3)Iog(b,a)<d->复杂度为O(N^d)


}

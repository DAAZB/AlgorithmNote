package al1;

import java.util.Arrays;

public class methodTester {
    public static void main(String[] args)
    {
        int[] nums = sortTester();
        for (int i : nums) {
            System.out.print(i + ",");
        }
        // int[] a = {1,2,5,3,2,3};
        // int[] b = a.clone();
        // Arrays.sort(a);
        // Arrays.sort(b);
        // System.out.println(isEqual(a, b));
    }

    public static int[] randomArrayGenerator(int maxSiza, int maxVal)
    {
        int size = (int)(maxSiza * Math.random());
        int[] arr = new int[size];
        for (int i = 0; i < size; i++)
        {
            arr[i] = (int)(maxVal * Math.random() - (maxVal-1) * Math.random());
        }
        return arr;
    }
    public static boolean isEqual(int[] arr1, int[] arr2)
    {
        int n = arr1.length;
        int m = arr2.length;
        if(m != n)
        {
            return false;
        }
        for (int i = 0; i < n; i++) {
            if(arr1[i] != arr2[i])
            {
                return false;
            }
        }
        return true;
    }

    public static int[] sortTester()
    {
        for (int i = 0; i < 500000; i++)
        {
            int[] nums = randomArrayGenerator(200, 100);
            int[] nums1 = nums.clone();
            int[] nums2 = nums1.clone();

            // *************************
            // class02.quickSort2(nums1); //测试快速排序算法
            // class02.mergeSort(nums1);
            class03.heapSort(nums1);
            // class03.radixSort(nums1);
            // *************************

            Arrays.sort(nums2);
            if(!isEqual(nums1, nums2))
            {
                System.out.println(i + "Fucked!!");
                return nums;
            }
        }
        System.out.println("Nice Work!");
        return new int[0];
    }
}

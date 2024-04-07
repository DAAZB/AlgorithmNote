// 01 滑动窗口，打表，预处理（前缀和等）
package al3;

import java.util.Random;

public class class01 {
    public static void main(String[] args){
        // System.out.println("hello");
        // int[] arr = {-5,-3,0,1,2,4,8,9,12,17};
        // System.out.println(maxCoverPoints2(arr, 5));

        // 01test
        // Random random = new Random();
        // for(int i = 0; i < 1000; i++){
        //     int[] arr = new int[random.nextInt(100)];
        //     int range = random.nextInt(1, 20);
        //     if(maxCoverPoints1(arr, range) != maxCoverPoints2(arr, range)){
        //         System.out.println("fuck!!");
        //         return;
        //     }
        // }


        // 02test
        // Random random = new Random();
        // for (int i = 0; i < 5000; i++) {
        //     int n = random.nextInt(1, 10000);
        //     if(minBags(n) != minBags1(n)){
        //         System.out.println("fuck!!");
        //         System.out.println(n);
        //         System.out.println(minBags1(n));
        //         System.out.println(minBags2(n));
        //         return;
        //     }
        // }


        // 03test
        // for (int i = 0; i < 200; i++) {
        //     System.out.println(winnerCow2(i));
        // } //21211 21211 21211 打表！


        // 04test
        int[] ct = new int[8];
        for (int i = 0; i < 10000000; i++) {
            ct[r1t7()] ++;
        }
        for (int i = 0; i < ct.length; i++) {
            System.out.println(ct[i]);
        }



    }

    // 01. 给定一个有序数组arr,代表数轴上从左到右有n个点arr[0],arr[1],arr[n一1]，
    //     给定一个正数L,代表一根长度为L的绳子，求绳子最多能覆盖其中的几个点。
    public static int maxCoverPoints1(int[] arr, int L){ //  N(NlogN)
        // 贪心：每次把绳子右侧端点放在arr[i]处，看能覆盖多少个点，取最大值
        int n = arr.length;
        if(n == 0){
            return 0;
        }
        int[] cover = new int[n];
        cover[0] = 1;
        int res = 1;
        for(int i = 1; i < n; i++){
            int target = arr[i] - L;
            int r = i;
            int l = i - cover[i - 1];
            int mid = 0;
            //二分查找i - cover[i - 1]到i范围内第一个大于等于绳子左端点target的数
            while(l <= r){
                mid = l + ((r - l)>>1);
                if(arr[mid] >= target){
                    r = mid - 1;
                }else{
                    l = mid + 1;
                }
            }
            cover[i] = i - l + 1;
            res = Math.max(res, cover[i]);
        }
        return res;
    }
    public static int maxCoverPoints2(int[] arr, int L){ //双指针（滑动窗口）(logN)
        int n = arr.length;
        if(n == 0){
            return 0;
        }
        int l = 0;
        int r = 0;
        int res = 1;
        while(l < n){
            while(r < n && arr[r] - arr[l] <= L){
                r++;
            }
            res = Math.max(res, r - l);
            l++;
        }
        return res;
    }

    // 2. 小虎去附近的商店买苹果，奸诈的商贩使用了捆绑交易，只提供6个每袋和8个
    // 每袋的包装包装不可拆分。可是小虎现在只想购买恰好个苹果，小虎想购买尽
    // 量少的袋数方便携带。如果不能购买恰好个苹果，小虎将不会购买。输入一个
    // 整数，表示小虎想购买的个苹果，返回最小使用多少袋子。如果无论如何都不
    // 能正好装下，返回-1。
    public static int minBags1(int n){
        if(n < 6 || (n&1) == 1){
            return -1;
        }
        for(int i = 0; i <= n / 6; i++){
            if((n - 6 * i) % 8 == 0){
                return i + (n - 6 * i) / 8;
            }
        }
        return -1;
    }
    public static int minBags2(int n){
        if(n < 6 || (n&1) == 1 || n == 10){
            return -1;
        }
        return n % 8 == 0 ? n / 8 : n / 8 + 1;
    }
    public static int minBags(int apple){
        if (apple < 0){
            return -1;
        }
        int bag6 = -1;
        int bag8 = apple /8;
        int rest = apple - 8 * bag8;
        while (bag8 >=0 && rest < 24){
            int restUse6 = minBagBase6(rest);
            if (restUse6 != -1){
                bag6 = restUse6;
                break;
            }
            rest = apple - 8 * (--bag8);
        }
        return bag6 == -1 ? -1 : bag6 + bag8;
    }
    public static int minBagBase6(int rest){
        return rest % 6 == 0 ? (rest / 6) : -1;
    }

    // 03. 两只绝顶聪明的牛比赛吃N份草，每次只能吃4幂份草，吃完最后一份草的获胜，返回胜者1-先手，2-后手
    public static int winnerCow1(int n){
        if(n < 5){
            return (n == 1 || n == 3 || n == 4) ? 1 : 2;
        }
        int base = 1;
        while(base <= n){
            if(winnerCow1(n - base) == 2){
                return 1;
            }
            if(base > n / 4){
                break;
            }
            base *= 4;
        }
        return 2;
    }
    public static int winnerCow2(int n){
        return (n % 5 == 0 || n % 5 == 2) ? 2 : 1;
    }

    // 04 已有一个随机数发生器，生成另一个随机数发生器
    // 04.1 r1t5 -> r1t7
    public static int r1t5(){ //随即返回1-5的整数
        return (int) (Math.random() * 5) + 1;
    }
    public static int r01(){
        int res = 0;
        do {
            res = r1t5();
        } while (res == 3);
        return res < 3 ? 0 : 1;
    }
    public static int r1t7(){
        int res = 0;
        do {
            res = 0;
            for (int i = 0; i < 3; i++) {
                res += r01() << i;
            }
        } while (res == 0);
        return res;
    }


}

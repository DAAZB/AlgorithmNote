// 位运算题目
package al2;

public class class07 {
    public static void main(String[] args){
        // System.out.println(isPow4(32));
        // System.out.println(multiply(10, -8));
        System.out.println(-8 >>> 1);
        System.out.println(-8 >> 1);
        

    }


    // 1. 比较两个有符号32位整数的大小，要求不能使用比较操作，仅使用数学运算和位运算
    public static int getMax(int a, int b){
        int c = a - b; //可能溢出
        int sa = sign(a); //先获取abc的符号
        int sb = sign(b);
        int sc = sign(c);
        int difSab = sa ^ sb; //ab异号
        int samSab = flip(difSab); //ab同号
        int returnA = difSab * sa + samSab * sc; //返回a的条件为1）ab异号且a非负，2）ab同号且c非负
        int returnB = flip(returnA);
        return returnA * a + returnB * b; //互斥参数来表达if-else结构 
    }
    public static int sign(int n){ //返回n的符号，1-非负 0-负
        return flip(n >> 31);
    }
    public static int flip(int n){ //0->1, 1->0
        return 1 ^ n;
    }


    // 2. 判断一个数是否为2或4的幂次
    public static boolean isPow2(int n){
        return (n & (n - 1)) == 0;
    }
    public static boolean isPow4(int n){
        return (n & (n - 1)) == 0 && (n & 0x55555555) != 0;
    }


    // 3. 给定两个有符号32位整数a和b,不能使用算术运算符，分别实现a和b的加、减、乘、除运算
    // 【要求】如果给定a、b执行加减乘除的运算结果就会导致数据的溢出，那么你实现的函数不必对此负责，除此之外请保证计算过程不发生溢出
    public static int add(int a, int b){ //a + b
        int xor = a ^ b; //异或表示无进位相加的结果
        int and = a & b; //与表示产生进位的位置 a + b = xor + (and<<1)
        while(and != 0){
            a = xor;
            b = and << 1;
            xor = a ^ b;
            and = a & b;
        }
        return xor;
    }
    public static int minus(int a, int b){ //a - b
        return add(a, add(~b, 1));
    }
    public static int multiply(int a, int b){ //a * b
        int res = 0;
        while(b != 0){
            if((b & 1) != 0){
                res = add(res, a);
            }
            a <<= 1;
            b >>>= 1;
        }
        return res;
    }
    public static int divide(int a, int b){ //a / b leetcode29
        
    }


}

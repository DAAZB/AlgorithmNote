// 基础提升1：哈希表与哈希函数
package al2;

import java.util.HashMap;

public class class01 {
    public static void main(String[] args){
        
    }


    // 1. 哈希函数f
    // MD5、 SHa1、、、
    // 1)输入域：∞，输出域：有限
    // 2)same in -> same out
    // 3)dif in -> same out   hash碰撞（冲突）
    // 4)离散性，均匀性。若干个输入在输出域上的分布是均匀且随机离散的


    // 2. 哈希表
    // 哈希表的增删改查操作使用时可以认为是O(1)，理论上


    // 3.设计RandomPool结构 leetcode380
    // 设计一种结构，在该结构中有如下三个功能：
    // insert(key):将某个key加入到该结构，做到不重复加入
    // delete(key):将原本在结构中的某个key移除
    // getRandom):等概率随机返回结构中的任何一个key。
    // 【要求】Insert、delete和getRandom方法的时间复杂度都是0(1)
    public class RandomizedSet{
        HashMap<Integer, Integer> valIndex;
        HashMap<Integer, Integer> indexVal;
        int size;

        public RandomizedSet() {
            valIndex = new HashMap<>();
            indexVal = new HashMap<>();
            size = 0;
        }

        public boolean insert(int val) {
            if(!valIndex.containsKey(val)){
                valIndex.put(val, size);
                indexVal.put(size, val);
                size++;
                return true;
            }
            return false;
        }

        public boolean remove(int val) {
            if(valIndex.containsKey(val)){
                size--;
                int index = valIndex.get(val);
                int lastVal = indexVal.get(size);
                indexVal.put(index, lastVal);
                valIndex.put(lastVal, index);
                indexVal.remove(size);
                valIndex.remove(val);
                return true;
            }
            return false;
        }

        public Integer getRandom() {
            if(size == 0){
                return null;
            }
            return indexVal.get((int)(Math.random() * size));
        }
    }


    // 4. 布隆过滤器
    // hash，bitArr，仅添加和查询，无删除行为，允许失误率，p13


    // 5. 一致性哈希
    // 数据端分布式服务器数据分配问题
    // 哈希算法，数据迁移代价，虚拟节点，负载均衡

}

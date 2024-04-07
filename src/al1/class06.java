// 图
package al1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;


class Graph{
    public HashMap<Integer, Node> nodes; //点集
    public HashSet<Edge> edges; //边集

    public Graph(){
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }

    // 从矩阵建立图
    // N*3的矩阵
    // [weight， from.val, to.val]
    public static Graph createGraph(Integer[][] matrix){
        Graph graph = new Graph();
        for (int i = 0; i < matrix.length; i++) {
            Integer weight = matrix[i][0];
            Integer from = matrix[i][1];
            Integer to = matrix[i][2];
            if(!graph.nodes.containsKey(from)){
                graph.nodes.put(from, new Node(from));
            }
            if(!graph.nodes.containsKey(to)){
                graph.nodes.put(to, new Node(to));
            }
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            fromNode.nexts.add(toNode);
            fromNode.out++;
            toNode.in++;
            Edge edge = new Edge(weight, fromNode, toNode);
            fromNode.edges.add(edge);
            graph.edges.add(edge);
        }
        return graph;
    }
}
class Node{
    public int val; //点值
    public int in; // 入度，有多少节点指入
    public int out; // 出度，有多少节点指出
    public ArrayList<Node> nexts; //指出的节点列表
    public ArrayList<Edge> edges; //指出的边列表

    public Node(int val)
    {
        this.val = val;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }

}
class Edge{
    public int weight; //权重
    public Node from; //入节点
    public Node to; //出节点

    public Edge(int weight, Node from, Node to){
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}


public class class06 {
    public static void main(String[] args)
    {
        // Integer[][] matrix = {
        //     {0,1,2}, {0,1,3}, {0,1,4},
        //     {0,2,5}, {0,2,6}, {0,3,7},
        //     {0,4,8}, {0,4,9},
        //     {0,5,10}, {0,7,11}, {0,9,12},
        //     {0,2,1}, {0,3,1}, {0,4,1},
        //     {0,5,2}, {0,6,2}, {0,7,3},
        //     {0,8,4}, {0,9,4},
        //     {0,10,5}, {0,11,7}, {0,12,9}
        // };
        // Graph g = Graph.createGraph(matrix);
        // Node start = g.nodes.get(1);
        // graphBFS(start);
        // graphDFS1(start);
        // graphDFS2(start);


        // Integer[][] matrix = {
        //     {0,5,2}, {0,2,1}, {0,3,2},
        //     {0,4,2}, {0,3,1}, {0,4,1}
        // };
        // Integer[][] matrix = {
        //     {3,1,2}, {15,1,3}, {9,1,4}, {2,2,3}, {7,3,4}, {200,2,5}, {14,3,5}, {16,4,5},
        //     {3,2,1}, {15,3,1}, {9,4,1}, {2,3,2}, {7,4,3}, {200,5,2}, {14,5,3}, {16,5,4},
        // };
        Integer[][] matrix = {
            {100,1,6}, {30,1,5}, {10,1,3}, {5,2,3}, {60,5,6}, {10,4,6}, {50,3,4}, {20,4,5},
            {100,6,1}, {30,5,1}, {10,3,1}, {5,3,2}, {60,6,5}, {10,6,4}, {50,4,3}, {20,5,4}
        };
        Graph g = Graph.createGraph(matrix);
        var res = dijkstra(g.nodes.get(1));
        res.forEach((key, val) ->{
            System.out.println(key.val + ":" + val);
        });


    }

    // 1. 图的广度优先遍历
    public static void graphBFS(Node start){
        if(start == null){
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        queue.add(start);
        set.add(start);
        while(!queue.isEmpty()){
            var cur = queue.poll();
            System.out.print(cur.val + ",");
            for (Node node : cur.nexts) {
                if(!set.contains(node)){
                    queue.add(node);
                    set.add(node);
                }
            }
        }
    }

    // 2. 图的深度优先遍历
    public static void graphDFS1(Node start){ //迭代
        if(start == null){
            return;
        }
        Stack<Node> st = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        st.push(start);
        set.add(start);
        System.out.print(start.val + ",");
        while(!st.isEmpty()){
            var cur = st.pop();
            for (Node node : cur.nexts) {
                if(!set.contains(node)){
                    st.push(cur);
                    st.push(node);
                    set.add(node);
                    System.out.print(node.val + ",");
                    break;
                }
            }
        }
    }
    static HashSet<Node> set = new HashSet<>(); //递归
    public static void graphDFS2(Node start){
        if(start == null){
            return;
        }
        set.add(start);
        System.out.print(start.val + ",");
        for (Node node : start.nexts) {
            if(!set.contains(node)){
                graphDFS2(node);
            }
        }
    }

    // 3. 拓扑排序算法
    public static ArrayList<Node> sortedTopology(Graph graph)
    {
        HashMap<Node, Integer> inDegree = new HashMap<>(); //记录节点的入度
        Queue<Node> zeroInNodes = new LinkedList<>(); //入读为0的节点才能进入队列
        for (Node node : graph.nodes.values()) {
            inDegree.put(node, node.in);
            if(node.in == 0){
                zeroInNodes.add(node);
            }
        }
        ArrayList<Node> res = new ArrayList<>();
        while(!zeroInNodes.isEmpty()){
            var cur = zeroInNodes.poll();
            res.add(cur); //开始排序
            for (Node node : cur.nexts) { //擦除该节点的影响，让该节点的所有下节点入度减一
                inDegree.put(node, inDegree.get(node)-1);
                if(inDegree.get(node) == 0){
                    zeroInNodes.add(node);
                }
            }
        }
        return res;
    }

    // 4. 最小生成树问题：连通的无向图中，去掉一些边，如何保证联通且所有边权值和最小
    // 4.1 Kruskal算法
    // 首先认为所有节点不连通，然后依次从最小权值的边开始考虑，加入边如果不使图生成环，则保留
    // 判断是否产生环的机制：并查集
    // 4.1.1 并查集简单实现
    public static class UnionFind{
        public HashMap<Node, List<Node>> map;
        public UnionFind(List<Node> nodes){
            map = new HashMap<>();
            for (Node node : nodes) {
                List<Node> list = new ArrayList<>();
                list.add(node);
                map.put(node, list);
            }
        }
        public boolean isSameSet(Node from, Node to){ //判断是否在同一个集合中
            List<Node> fromList = map.get(from);
            List<Node> toList = map.get(to);
            return fromList == toList;
        }
        public void union(Node from, Node to){ //合并两个集合
            List<Node> fromList = map.get(from);
            List<Node> toList = map.get(to);
            for (Node node : toList) {
                fromList.add(node);
                map.put(node, fromList);
            }
        }
    }
    // 4.1.2 Kruskal算法
    public static Set<Edge> kruskalMST(Graph graph){
        UnionFind uf = new UnionFind(new ArrayList<>(graph.nodes.values())); //新建并查集，初始每个节点在单独的集合中
        PriorityQueue<Edge> orderedEdge = new PriorityQueue<>((a, b) -> a.weight - b.weight);
        for (Edge edge : graph.edges) { //将所有边按从小到大顺序排列
            orderedEdge.add(edge);
        }
        Set<Edge> res = new HashSet<>();
        while(!orderedEdge.isEmpty()){ //每次取出最小的边
            var edge = orderedEdge.poll();
            if(!uf.isSameSet(edge.from, edge.to)){ //如果from和to不在一个集合，说明加入该条边不会产生环
                uf.union(edge.from, edge.to);
                res.add(edge);
            }
        }
        return res;
    }

    // 4.2 prim算法，适用无向图，
    public static Set<Edge> primMST(Graph graph){
        PriorityQueue<Edge> unlockedEdges = new PriorityQueue<>((a, b) -> a.weight - b.weight); //解锁的边
        Set<Node> unlockedNode = new HashSet<>(); //已连通的点
        Set<Edge> res = new HashSet<>();
        for(Node node: graph.nodes.values()){ //随机挑选一个点，循环是为了解决森林的情况
            if(!unlockedNode.contains(node)){ //新的未连通的点，从他开始p算法
                unlockedNode.add(node);
                for (Edge edge : node.edges) { //解锁他所有的边
                    unlockedEdges.add(edge);
                }
                while(!unlockedEdges.isEmpty()){
                    var edge = unlockedEdges.poll(); //依次取出最小边
                    if(!unlockedNode.contains(edge.to)){ //发现新的未连通的点
                        res.add(edge);
                        unlockedNode.add(edge.to);
                        for (Edge newEdge : edge.to.edges) { //解锁新点的所有边
                            unlockedEdges.add(newEdge);
                        }
                    }
                }
            }
        }
        return res;
    }

    // 5. Dijkstra算法
    // 最短路径算法，规定出发点，返回到每个点的最短距离
    public static HashMap<Node, Integer> dijkstra(Node head){
        //求从head出发到所有点的最小距离
        HashMap<Node, Integer> distanceMap = new HashMap<>(); //初始认为所有未记录的点的距离为无穷大
        distanceMap.put(head, 0); //出发点
        HashSet<Node> selectedNodes = new HashSet<>(); //已求出最短距离的点，存放在这里，以后再也不碰
        Node minNode = head; //当前不在selectedNodes中且距离最小的点
        while(minNode != null){
            int distance = distanceMap.get(minNode);
            for (Edge edge : minNode.edges) {
                Node toNode = edge.to;
                if(!distanceMap.containsKey(toNode)){ //toNode还没记录
                    distanceMap.put(toNode, distance + edge.weight);
                } else{ //toNode已经记录了，则更新其最小距离
                    distanceMap.put(toNode, Math.min(distanceMap.get(toNode), distance + edge.weight));
                }
            }
            selectedNodes.add(minNode); //此时，minNode的最小值即可确定。
            // 选出不在selectedNodes中且距离值最小的点（此处采用遍历找，存在很大优化空间）
            if(selectedNodes.size() == distanceMap.size()){
                minNode = null;
            } else{
                for (Node node : distanceMap.keySet()) { //先随机选出一个不在selectedNodes中的点设为minNode
                    if(!selectedNodes.contains(node)){
                        minNode = node;
                        break;
                    }
                }
                for (Node node : distanceMap.keySet()) { //再选出不在selectedNodes中的距离最小的点设为minNode
                    if(!selectedNodes.contains(node) && distanceMap.get(node) < distanceMap.get(minNode)){
                        minNode = node;
                    }
                }
            }
        }
        return distanceMap;
    }

    //5.1 Dijkstra算法优化（左神课程p11）
    public class NodeHeap{

    }
    // public static HashMap<Node, Integer> dijkstra2(Node head, int size){

    // }





}

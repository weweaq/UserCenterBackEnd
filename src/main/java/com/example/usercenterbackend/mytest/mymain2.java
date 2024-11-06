package com.example.usercenterbackend.mytest;

import java.util.*;
import java.util.stream.Collectors;

public class mymain2 {

    public static  List<List<Integer>> shortestPathLength(int[][] graph) {
        List<List<Integer>> res = new ArrayList<>();
        int n = graph.length;

        // 初始化队列及标记数组，存入起点
        Queue<int[]> queue = new LinkedList<>(); // 四个属性分别为 idx, mask, dist, path
        boolean[][] vis = new boolean[n][1 << n]; // 节点编号及当前状态
        for (int i = 0; i < n; i++) {
            queue.offer(new int[]{i, 1 << i, 0, i}); // 存入起点，起始距离0，初始路径
            vis[i][1 << i] = true;
        }

        // 开始搜索
        while (!queue.isEmpty()) {
            int[] tuple = queue.poll(); // 弹出队头元素
            int idx = tuple[0], mask = tuple[1], dist = tuple[2], path = tuple[3];

            // 找到答案，返回结果
            if (mask == (1 << n) - 1) {
                int[] path1 = reconstructPath(path, n);
//                if (!res.isEmpty() && path1.length >  res.get(0).size() ){
//                   return res;
//                }
                List<Integer> integerList = Arrays.stream(path1).boxed().collect(Collectors.toList());
                res.add(integerList);
            }

            // 扩展
            for (int x : graph[idx]) {
                int next_mask = mask | (1 << x);
                int next_path = (path << 3) | x; // 将当前节点添加到路径中
                if (!vis[x][next_mask]) {
                    queue.offer(new int[]{x, next_mask, dist + 1, next_path});
                    vis[x][next_mask] = true;
                }
            }
        }
        return res; // 如果没有路径，返回空数组
    }

    private static int[] reconstructPath(int path, int n) {
        List<Integer> pathList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            pathList.add(path & 0x07); // 取出路径中的最后一个节点
            path >>= 3; // 移除最后一个节点
        }
        Collections.reverse(pathList); // 反转路径以恢复正确的顺序
        return pathList.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        int[][] graph = {{1}, {0, 2, 4}, {1, 3, 4}, {2}, {1, 2}};
        List<List<Integer>> lists = shortestPathLength(graph);
        System.out.println("最短路径: ");
        lists.forEach(System.out::println);
    }

}

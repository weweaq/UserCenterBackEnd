package com.example.usercenterbackend.mytest;

import java.util.*;
import java.util.stream.Collectors;

public class generateMap {
    public static void main(String[] args) {
        int[][] A = {
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 1, 1, 0},
                {0, 0, 0, 0, 0}
        };

        List<List<Integer>> graph = buildGraph(A);
        for (List<Integer> neighbors : graph) {
            System.out.println(neighbors);
        }

        List<List<Integer>> lists = shortestPathLength(convertToListArray(graph));

        System.out.println("最短路径: ");
        lists.forEach(System.out::println);
    }

    public static int[][] convertToListArray(List<List<Integer>> list) {
        int[][] array = new int[list.size()][];

        for (int i = 0; i < list.size(); i++) {
            List<Integer> innerList = list.get(i);
            array[i] = new int[innerList.size()];
            for (int j = 0; j < innerList.size(); j++) {
                array[i][j] = innerList.get(j);
            }
        }

        return array;
    }

    public static List<List<Integer>> buildGraph(int[][] A) {
        int rows = A.length;
        int cols = A[0].length;
        Map<Integer, Integer> nodeMap = new HashMap<>();
        int nodeId = 1;

        // 构建节点映射
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (A[i][j] == 1) {
                    nodeMap.put(i * cols + j, nodeId++);
                }
            }
        }

        // 初始化图
        List<List<Integer>> graph = new ArrayList<>(nodeId - 1);
        for (int i = 0; i < nodeId - 1; i++) {
            graph.add(new ArrayList<>());
        }

        // 构建邻接表
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (A[i][j] == 1) {
                    int currentNode = nodeMap.get(i * cols + j);
                    // 上下左右四个方向
                    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
                    for (int[] dir : directions) {
                        int ni = i + dir[0];
                        int nj = j + dir[1];
                        if (ni >= 0 && ni < rows && nj >= 0 && nj < cols && A[ni][nj] == 1) {
                            int neighborNode = nodeMap.get(ni * cols + nj);
                            graph.get(currentNode - 1).add(neighborNode);
                        }
                    }
                }
            }
        }

        return graph;
    }

    public static List<List<Integer>> shortestPathLength(int[][] graph) {
        List<List<Integer>> res = new ArrayList<>();
        int n = graph.length;

        // 初始化队列及标记数组，存入起点
        Queue<int[]> queue = new LinkedList<>(); // 四个属性分别为 idx, mask, dist, path
        boolean[][] vis = new boolean[n + 1][1 << n]; // 节点编号及当前状态
        for (int i = 1; i < n + 1; i++) {
            queue.offer(new int[]{i, 1 << (i - 1), 0, i}); // 存入起点，起始距离0，初始路径
            vis[i][1 << (i - 1)] = true;
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
            for (int x : graph[idx - 1]) {
                int next_mask = mask | (1 << (x - 1));
                int next_path = (path << 3) | x; // 将当前节点添加到路径中
                if (!vis[x - 1][next_mask]) {
                    queue.offer(new int[]{x, next_mask, dist + 1, next_path});
                    vis[x - 1][next_mask] = true;
                }
            }
        }
        return res; // 如果没有路径，返回空数组
    }

    private static int[] reconstructPath(int path, int n) {
        List<Integer> pathList = new ArrayList<>();
        while (path != 0) {
            pathList.add(path & 0x07); // 取出路径中的最后一个节点
            path >>= 3; // 移除最后一个节点
        }
        Collections.reverse(pathList); // 反转路径以恢复正确的顺序
        return pathList.stream().mapToInt(i -> i).toArray();
    }
}
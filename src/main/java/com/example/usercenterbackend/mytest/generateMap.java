package com.example.usercenterbackend.mytest;

import com.sun.xml.internal.bind.v2.TODO;

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

    /**
     * 将 List<List<Integer>> 转换为 int[][] 可以通过遍历列表并手动将每个 Integer 转换为 int 来实现
     *
     * @param list
     * @return
     */
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

    /**
     * 为了生成一个二维数组 graph，其中 graph[i] 是一个列表，包含所有与节点 i 直接相连的节点，我们可以按照以下步骤进行：
     * 遍历二维数组 A：找到所有的 1，这些 1 表示节点。
     * 构建节点映射：将每个 1 的位置映射到一个唯一的节点编号。
     * 构建邻接表：根据节点的位置关系，构建每个节点的邻接节点列表。
     * 以下是实现该功能的 Java 代码：
     *
     * @param A
     * @return
     */
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

    /**
     * 在上述代码中，我们使用左移8位的方法来保存路径，这是因为每个节点的编号范围在0到n-1之间，假设n不超过256（即节点数不超过256个），
     * 那么每个节点的编号可以用一个字节（8位）来表示。通过左移8位并将当前节点编号与之进行按位或操作，我们可以将多个节点编号压缩到一个整数中，从而节省内存。
     * 具体步骤
     * 左移8位：
     * path << 8：将当前路径左移8位，为新的节点编号腾出空间。
     * | x：将当前节点编号 x 添加到路径的最低8位。
     * 路径还原：
     * path & 0xFF：取出路径中的最后一个节点编号。
     * path >>= 8：将路径右移8位，移除最后一个节点编号。
     *
     * 注意这里使用从1为起点的图进行探索路径
     * 如果使用从0开始探索的路径，则在搜索路径，路径还原的步骤中，需要将路径右移8位，由于判断条件为path为0则跳出循环，所以导致最后一个可能为0的起点被忽略掉。
     *
     * @param graph
     * @return
     */
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
                // TODO: 2024/11/6 待解决
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
            // TODO: 2024/11/6 待解决
            pathList.add(path & 0x07); // 取出路径中的最后一个节点
            path >>= 3; // 移除最后一个节点
        }
        Collections.reverse(pathList); // 反转路径以恢复正确的顺序
        return pathList.stream().mapToInt(i -> i).toArray();
    }
}
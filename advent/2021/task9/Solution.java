package task9;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner; // Import the Scanner class to read text files


public class Solution {
    static int H, W;
    static boolean[][] visited;
    static List<List<Integer>> data;
    static int[] dxArr = new int[]{1,-1,0,0};
    static int[] dyArr = new int[]{0,0,1,-1};

    public static void main(String[] args) throws FileNotFoundException {
        data = new ArrayList<>();

        File myObj = new File("aoc2021/src/task9/test.in");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            List<Integer> row = new ArrayList<>();
            for (char val : myReader.nextLine().toCharArray()) {
                row.add(Character.getNumericValue(val));
            }
            data.add(row);
        }
        myReader.close();

        H = data.size();
        W = data.get(0).size();

        int part1 = part1();
        int part2 = part2();
        System.out.println(part1);
        System.out.println(part2);
    }

    public static int part1() {
        int ans = 0;
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                boolean top = (i == 0 || data.get(i).get(j) < data.get(i - 1).get(j));
                boolean bot = (i == H - 1 || data.get(i).get(j) < data.get(i + 1).get(j));
                boolean left = (j == 0 || data.get(i).get(j) < data.get(i).get(j - 1));
                boolean right = (j == W - 1 || data.get(i).get(j) < data.get(i).get(j + 1));
                if (top && bot && left && right) {
                    ans += data.get(i).get(j) + 1;
                }
            }
        }
        return ans;
    }

    public static int dfs(int i, int j) {
        int area = 1;
        for (int k=0;k<4; k++) {
            int dx = dxArr[k];
            int dy = dyArr[k];
            if (dx + i >= 0 && dx + i < H && dy + j >= 0 && dy + j < W) {
                int ii = dx + i;
                int jj = dy + j;
                if (!visited[ii][jj] && data.get(ii).get(jj) < 9) {
                    visited[ii][jj] = true;
                    area += dfs(ii, jj);
                }
            }
        }
        return area;
    }

    public static int part2() {
        // all basins are surrounded by 'walls' of 9 or edge
        visited = new boolean[H][W];
        // too lazy to change to maxHeap
        PriorityQueue<Integer> minHeap=new PriorityQueue<>();
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (!visited[i][j]) {
                    visited[i][j] = true;
                    if (data.get(i).get(j) != 9) {
                        int area = dfs(i, j);
                        minHeap.add(-area);
                    }
                }
            }
        }
        return minHeap.poll() * minHeap.poll() * minHeap.poll() * -1;
    }
}
package task15;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner; // Import the Scanner class to read text files


public class Solution {
    public static void main(String[] args) throws FileNotFoundException {
        List<List<Integer>> data = new ArrayList<>();

        File myObj = new File("aoc2021/src/task15/test.in");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            List<Integer> row = new ArrayList<>();
            for (Character val : myReader.nextLine().toCharArray()) {
                row.add(Character.getNumericValue(val));
            }
            data.add(row);
        }
        myReader.close();

        int h = data.size();
        int w = data.get(0).size();
        int[][] smallData = new int[h][w];
        int[][] largeData = new int[5*h][5*w];

        for (int i=0; i<h; i++) {
            for (int j=0; j<w; j++) {
                smallData[i][j] = data.get(i).get(j);
                for (int dx=0; dx < 5; dx++) {
                    for (int dy=0; dy < 5;dy++){
                        int val = data.get(i).get(j)+dx+dy;
                        if (val >= 10) {
                            val -= 9;
                        }
                        largeData[i+h*dx][j+w*dy] = val;
                    }
                }
            }
        }

        int part1 = solve(smallData);
        int part2 = solve(largeData);

        System.out.println(part1);
        System.out.println(part2);
    }

    public static int solve(int[][] data) {
        int H = data.length;
        int W = data[0].length;
        int[] dxArray = {1,-1,0,0};
        int[] dyArray = {0,0,1,-1};
        int[][] visited = new int[H][W];
        for (int i=0; i<H;i++) {
            for (int j=0; j<W; j++) {
                visited[i][j] = Integer.MAX_VALUE;
            }
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(0,0,0));

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            if (node.x == H-1 && node.y == W-1) {
                return node.cost;
            }
            for (int i=0; i<4;i++) {
                int xn = node.x + dxArray[i];
                int yn = node.y + dyArray[i];
                if (xn >= 0 && xn < H && yn >= 0 && yn < W) {
                    int cost = node.cost + data[xn][yn];
                    if (visited[xn][yn] > cost) {
                        visited[xn][yn] = cost;
                        pq.add(new Node(cost, xn, yn));
                    }
                }
            }
        }
        return -1;
    }
}

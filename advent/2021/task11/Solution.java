package task11;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;


public class Solution {
    static int N=10;

    static int[] dxArr = {1,-1,0,0,1,-1,1,-1};
    static int[] dyArr = {0,0,1,-1,1,1,-1,-1};

    public static void main(String[] args) throws FileNotFoundException {
        int[][] data = new int[N][N];

        File myObj = new File("aoc2021/src/task11/test.in");
        Scanner myReader = new Scanner(myObj);
        int row=0;
        while (myReader.hasNextLine()) {
            int col=0;
            for (char ch: myReader.nextLine().toCharArray()) {
                data[row][col] = Character.getNumericValue(ch);
                col++;
            };
            row++;
        }
        myReader.close();

        int part1 = part1(data);
        int part2 = part2(data);
        System.out.println(part1);
        System.out.println(part2);
    }

    public static int[][] sim(int[][] inp) {
        for (int i=0;i<N;i++) {
            for (int j = 0; j < N; j++) {
                inp[i][j]++;
            }
        }
        boolean[][] flash = new boolean[N][N];

        for (int i=0;i<N;i++) {
            for (int j = 0; j < N; j++) {
                if (inp[i][j] >= 10 && !flash[i][j]) {
                    flash[i][j] = true;
                    Deque<Pair> deque = new ArrayDeque<>(){};
                    deque.add(new Pair(i,j));
                    while (!deque.isEmpty()) {
                        Pair node = deque.pop();
                        for (int k=0; k<8;k++) {
                            int nx = node.fi + dxArr[k];
                            int ny = node.se + dyArr[k];
                            if (nx >= 0 && nx < N && ny >=0 && ny < N) {
                                inp[nx][ny]++;
                                if (inp[nx][ny] >= 10 && !flash[nx][ny]) {
                                    flash[nx][ny] = true;
                                    deque.add(new Pair(nx, ny));
                                }
                            }
                        }
                    }
                }
            }
        }
        return inp;

    }

    public static int part1(int[][] inp) {
        int[][] curStep = new int[N][N];
        for (int i=0;i<N;i++) {
            for (int j = 0; j < N; j++) {
                curStep[i][j] = inp[i][j];
            }
        }
        int ans = 0;
        for (int step=0; step<100;step++) {
            curStep = sim(curStep);

            for (int i=0;i<N;i++) {
                for (int j=0;j<N;j++) {
                    if (curStep[i][j] >= 10) {
                        curStep[i][j] = 0;
                        ans++;
                    }
                }
            }
        }
        return ans;
    }

    public static int part2(int[][] inp) {
        int[][] curStep = new int[N][N];
        for (int i=0;i<N;i++) {
            for (int j = 0; j < N; j++) {
                curStep[i][j] = inp[i][j];
            }
        }
        int step = 0;
        while (true) {
            curStep = sim(curStep);

            boolean ok = true;
            for (int i=0;i<N;i++) {
                for (int j=0;j<N;j++) {
                    if (curStep[i][j] >= 10) {
                        curStep[i][j] = 0;
                    }
                    else{
                        ok = false;
                    }
                }
            }
            step++;
            if (ok) {
                break;
            }
        }
        return step;
    }
}

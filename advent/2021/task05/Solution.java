package task05;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files


public class Solution {
    public static void main(String[] args) throws FileNotFoundException {
        List<Line> data = new ArrayList<>();

        File myObj = new File("aoc2021/src/task05/test.in");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            data.add(new Line(myReader.nextLine()));
        }
        myReader.close();

        int part1 = solve(data, false);
        int part2 = solve(data, true);
        System.out.println(part1);
        System.out.println(part2);
    }

    public static int solve(List<Line> inp, boolean withDiag) {
        int ans = 0;
        int size = 1000;
        int[][] map = new int[size][size];
        for (Line line : inp) {
            if (!line.isDiag) {
                for (int x=Math.min(line.x1,line.x2); x <= Math.max(line.x1, line.x2); x++) {
                    for (int y=Math.min(line.y1,line.y2); y <= Math.max(line.y1, line.y2); y++) {
                        map[x][y]++;
                    }
                }
            }
            if (line.isDiag && withDiag) {
                int dx_sign = (line.x2 > line.x1 ? 1 : -1);
                int dy_sign = (line.y2 > line.y1 ? 1 : -1);
                int steps = Math.abs(line.x2 - line.x1);
                for (int i=0; i<=steps; i++) {
                    map[line.x1 + dx_sign * i][line.y1 + dy_sign * i]++;
                }
            }
        }

        for(int i=0; i<size; i++) {
            for (int j=0;j<size; j++) {
                ans += (map[i][j] > 1 ? 1 : 0);
            }
        }
        return ans;
    }
}

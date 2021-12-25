package task25;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files


public class Solution {
    public static void main(String[] args) throws FileNotFoundException {
        List<char[]> data = new ArrayList<>();

        File myObj = new File("aoc2021/src/task25/test.in");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            data.add(myReader.nextLine().toCharArray());
        }
        myReader.close();

        int part1 = part1(data);
        System.out.println(part1);
    }

    public static int part1(List<char[]> map) {
        int H = map.size();
        int W = map.get(0).length;
        int step = 0;

        while (true) {
            List<Integer[]> mvEast = new ArrayList<>();

            for (int i=0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    if (map.get(i)[j] == '>' && map.get(i)[(j + 1) % W] == '.') {
                        mvEast.add(new Integer[]{i, j, i, (j + 1) % W});
                    }
                }
            }
            for (Integer[] fromTo : mvEast) {
                map.get(fromTo[0])[fromTo[1]] = '.';
                map.get(fromTo[2])[fromTo[3]] = '>';
            }

            List<Integer[]> mvSouth = new ArrayList<>();
            for (int i=0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    if (map.get(i)[j] == 'v' && map.get((i+1)%H)[j] == '.') {
                        mvSouth.add(new Integer[]{i, j, (i+1)%H, j});
                    }
                }
            }
            for (Integer[] fromTo : mvSouth) {
                map.get(fromTo[0])[fromTo[1]] = '.';
                map.get(fromTo[2])[fromTo[3]] = 'v';
            }

            step++;
            if (mvEast.isEmpty() && mvSouth.isEmpty()) {
                return step;
            }

        }
    }
}

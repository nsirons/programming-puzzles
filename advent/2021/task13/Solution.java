package task13;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files


public class Solution {
    static int N=2000;
    public static void main(String[] args) throws FileNotFoundException {
        List<Integer> xy = new ArrayList<>();
        List<String> fold = new ArrayList<>();
        boolean map[][] = new boolean[N][N];

        File myObj = new File("aoc2021/src/task13/test.in");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String line = myReader.nextLine();
            if (line.length() > 1) {
                // fold
                if (line.charAt(0) == 'f') {
                    line = line.replace("fold along ", "");
                    String[] cmd = line.split("=");
                    fold.add(cmd[0]);
                    xy.add(Integer.valueOf(cmd[1]));
                }
                else {
                    String[] cmd = line.split(",");
                    map[Integer.valueOf(cmd[1])][Integer.valueOf(cmd[0])] = true;
                }
            }
        }
        myReader.close();

        int part1 = 0;
        for (int i=0; i< xy.size();i++) {
            int val = xy.get(i);
            if (fold.get(i).equals("y")) {
                for (int ii=0;ii<val;ii++) {
                    for (int jj = 0; jj < N; jj++) {
                        map[val - ii - 1][jj] = map[val - ii - 1][jj] || map[val + ii + 1][jj];
                        map[val + ii + 1][jj] = false;
                    }
                }
            }
            else {
                for (int ii=0;ii<val;ii++) {
                    for (int jj = 0; jj < N; jj++) {
                        map[jj][val - ii - 1] = map[jj][val - ii - 1] || map[jj][val + ii + 1];
                        map[jj][val + ii + 1] = false;
                    }
                }
            }

            if (i==0) {
                for (int ii=0;ii<N;ii++) {
                    for (int jj=0;jj<N;jj++) {
                        part1 += map[ii][jj]? 1 : 0;
                    }
                }
                System.out.println(part1);
            }
            else if (i==xy.size()-1) {
                for (int ii=0;ii<6;ii++) {
                    for (int jj = 0; jj < 40; jj++) {
                        System.out.print(map[ii][jj] ? " # " : "   ");
                    }
                    System.out.println();
                }
            }

        }
    }
}

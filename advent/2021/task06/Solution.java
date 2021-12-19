package task06;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.stream.LongStream;


public class Solution {
    public static void main(String[] args) throws FileNotFoundException {
        long[] data = new long[9];

        File myObj = new File("aoc2021/src/task06/test.in");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            for (String val : myReader.nextLine().split(",")) {
                data[Integer.valueOf(val)]++;
            }
        }
        myReader.close();

        long part1 = solve(data, 80);
        long part2 = solve(data, 256);
        System.out.println(part1);
        System.out.println(part2);
    }

    public static long solve(long[] curStep, int day) {
        for (int i=0;i<day; i++) {
            long[] nextStep = new long[9];
            for (int j=0; j<9; j++) {
                if (j == 0) {
                    nextStep[6] += curStep[0];
                    nextStep[8] += curStep[0];
                }
                else {
                    nextStep[j-1] += curStep[j];
                }
            }
            curStep = nextStep;
        }
        return LongStream.of(curStep).sum();
    }
}

package task07;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files


public class Solution {
    public static void main(String[] args) throws FileNotFoundException {
        List<Integer> data = new ArrayList<>();

        File myObj = new File("aoc2021/src/task07/test.in");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            for (String val : myReader.nextLine().split(",")) {
                data.add(Integer.valueOf(val));
            }
        }
        myReader.close();

        data.sort(Comparator.naturalOrder());
        int part1 = part1(data);
        int part2 = part2(data);
        System.out.println(part1);
        System.out.println(part2);
    }

    public static int part1(List<Integer> inp) {
        // Optimal height is median of input
        int ans = 0;
        int median = inp.get(inp.size() / 2);
        for (Integer val : inp) {
            ans += Math.abs(val - median);
        }
        return ans;
    }

    public static int part2(List<Integer> inp) {
        // Optimal height is mean of input
        int ans = 0;
        int mean = inp.stream().mapToInt(Integer::intValue).sum() / inp.size();
        for (Integer val : inp) {
            int n = Math.abs(val - mean);
            ans += Math.abs(n * (n+1) / 2);
        }
        return ans;
    }
}

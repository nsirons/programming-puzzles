package task1;


import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files


public class Solution {
    public static void main(String[] args) throws FileNotFoundException {
        List<Integer> data = new ArrayList<>();

        File myObj = new File("aoc2021/src/task1/1.in");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            data.add(Integer.valueOf(myReader.nextLine()));
        }
        myReader.close();

        int part1 = solve(data, 1);
        int part2 = solve(data, 3);
        System.out.println(part1);
        System.out.println(part2);
    }

    public static int solve(List<Integer> inp, int dx) {
        int ans = 0;
        for (int i=0; i<inp.size()-dx;i++){
            ans = ans + (inp.get(i+dx) > inp.get(i)? 1 : 0);
        }
        return ans;
    }
}

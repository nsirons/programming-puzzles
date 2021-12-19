package task02;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files


public class Solution {
    public static void main(String[] args) throws FileNotFoundException {
        List<Command> data = new ArrayList<>();

        File myObj = new File("aoc2021/src/task02/test.in");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String[] line = myReader.nextLine().split("\\s+");
            data.add(new Command(line[0], line[1]));
        }
        myReader.close();

        int part1 = part1(data);
        int part2 = part2(data);
        System.out.println(part1);
        System.out.println(part2);
    }

    public static int part1(List<Command> inp) {
        int x=0; int y=0;
        for (Command cmd : inp) {
            if (cmd.op == 0) x += cmd.val;
            else y += cmd.val;
        }
        return x*y;
    }

    public static int part2(List<Command> inp) {
        int aim=0; int x=0; int y=0;
        for (Command cmd: inp) {
            if (cmd.op == 0) {
                x += cmd.val;
                y += cmd.val * aim;
            }
            else {
                aim += cmd.val;
            }
        }
        return x * y;
    }
}

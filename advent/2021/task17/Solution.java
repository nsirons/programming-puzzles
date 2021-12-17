package task17;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Solution {
    static int xmax, xmin, ymax, ymin;
    public static void main(String[] args) throws FileNotFoundException {
        List<Integer> data = new ArrayList<>();
        final String regex = "(-?\\d+)";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);


        File myObj = new File("aoc2021/src/task17/test.in");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String line = myReader.nextLine();
            final Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                data.add(Integer.valueOf(matcher.group(0)));
            }
        }
        myReader.close();

        xmin = data.get(0);
        xmax = data.get(1);
        ymin = data.get(2);
        ymax = data.get(3);

        int part1 = 0;
        int part2 = 0;

        // just in case for other inputs giving +5 as an extra buffer
        for (int vx=-5; vx < xmax+5; vx++) {
            for (int vy=ymin; vy < -ymin+5; vy++) {
                int maxH = sim(0,0,vx,vy);
                if (maxH != Integer.MIN_VALUE) {
                    part2++;
                    part1 = Math.max(part1, maxH);
                }
            }
        }
        System.out.println(part1);
        System.out.println(part2);
    }

    public static int sim(int x, int y, int vx, int vy) {
        int maxH = y;
        boolean isTarget = false;

        while (y >= ymin && !isTarget) {
            x += vx;
            y += vy;

            maxH = Math.max(maxH, y);
            if (xmin <= x && xmax >= x && ymin <= y && ymax >= y) {
                isTarget = true;
            }

            if (vx > 0) vx--;
            else if (vx < 0) vx++;
            vy--;
        }
        return (isTarget ? maxH : Integer.MIN_VALUE);
    }
}

package task24;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Solution {
    public static void main(String[] args) throws FileNotFoundException {
        int[] xArr = new int[14];
        int[] yArr = new int[14];
        int[] zArr = new int[14];

        String regexX = "add x ([-]?\\d+)";
        String regexY = "add y w"
                + "add y ([-]?\\d+)";
        String regexZ = "div z ([-]?\\d+)";

        File myObj = new File("aoc2021/src/task24/test.in");
        Scanner myReader = new Scanner(myObj);
        StringBuilder data = new StringBuilder();
        while (myReader.hasNextLine()) {
            data.append(myReader.nextLine());
        }
        myReader.close();
        String dataStr = data.toString();

        Pattern patternX = Pattern.compile(regexX, Pattern.MULTILINE);
        Pattern patternY = Pattern.compile(regexY, Pattern.MULTILINE);
        Pattern patternZ = Pattern.compile(regexZ, Pattern.MULTILINE);
        Matcher matcherX = patternX.matcher(dataStr);
        Matcher matcherY = patternY.matcher(dataStr);
        Matcher matcherZ = patternZ.matcher(dataStr);


        int i = 0;
        while (matcherX.find()) {
            xArr[i] = Integer.parseInt(matcherX.group(1));
            i++;
        }
        i = 0;
        while (matcherY.find()) {
            yArr[i] = Integer.parseInt(matcherY.group(1));
            i++;
        }
        i = 0;
        while (matcherZ.find()) {
            zArr[i] = Integer.parseInt(matcherZ.group(1));
            i++;
        }

        long[] ans = solve(xArr,yArr,zArr);
        System.out.println(ans[0]);
        System.out.println(ans[1]);
    }

    public static boolean runBlock(int w, int z, int X, int Y, int Z, int prev) {
        int x = (z%26) + X;
        z = z / Z;
        x = (x != w ? 1 : 0);
        if (x == 1) {
            int y = 25 * x + 1;
            z *= y;
            y = (w + Y) * x;
            z += y;
        }
        return z == prev;
    }

    public static long[] solve(int[] xArr, int[] yArr, int[] zArr) {
        long part1 = 0;
        long part2 = Long.MAX_VALUE;

        Queue<State> queue = new LinkedList<>();
        queue.add(new State(13, 0, ""));
        while (!queue.isEmpty()) {
            State curState = queue.poll();
            if (curState.level == -1) {
                long val = Long.parseLong(curState.backtrace);
                part1 = Math.max(part1, val);
                part2 = Math.min(part2, val);
                continue;
            }
            int zstart = (zArr[curState.level] == 26 ? curState.znow * 26 : curState.znow / 26);

            for (int w = 1; w <= 9; w++) {
                for (int z=zstart; z<zstart+26; z++) {
                    if (runBlock(w, z, xArr[curState.level], yArr[curState.level], zArr[curState.level], curState.znow)) {
                        queue.add(new State(curState.level-1, z, w+curState.backtrace));
                    }
                }
            }
        }
        return new long[]{part1, part2};
    }
}

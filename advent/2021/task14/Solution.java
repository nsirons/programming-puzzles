package task14;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;


public class Solution {
    public static void main(String[] args) throws FileNotFoundException {
        String start;
        HashMap<String, String> Pair2Char = new HashMap<>();

        File myObj = new File("aoc2021/src/task14/test.in");
        Scanner myReader = new Scanner(myObj);
        start = myReader.nextLine();
        myReader.nextLine();
        while (myReader.hasNextLine()) {
            String[] line = myReader.nextLine().split(" -> ");
            Pair2Char.put(line[0], line[1]);
        }
        myReader.close();

        Long part1 = solve(start, Pair2Char, 10);
        Long part2 = solve(start, Pair2Char, 40);
        System.out.println(part1);
        System.out.println(part2);
    }

    public static Long solve(String start, HashMap<String, String> map, int steps) {
        HashMap<String, Long> dp = new HashMap<>();
        for (int i=0; i<start.length()-1;i++) {
            String key = start.substring(i,i+2);
            if (dp.containsKey(key)) dp.put(key, dp.get(key)+1);
            else dp.put(key, 1L);
        }
        // Original number of chars in string
        HashMap<Character, Long> charCnt = new HashMap<>();
        for (Character ch : start.toCharArray()) {
            charCnt.putIfAbsent(ch, 0L);
            charCnt.put(ch, charCnt.get(ch) + 1);
        }
        for (int curStep=0; curStep < steps; curStep++) {
            HashMap<String, Long> dpNew = new HashMap<>();
            for (String pair : dp.keySet()) {
                if (map.containsKey(pair)) {
                    String b = map.get(pair);
                    String newKeyLeft = pair.charAt(0) + b;
                    String newKeyRight = b + pair.charAt(1);
                    dpNew.putIfAbsent(newKeyLeft, 0L);
                    dpNew.putIfAbsent(newKeyRight,0L);
                    dpNew.put(newKeyLeft, dpNew.get(newKeyLeft) + dp.get(pair));
                    dpNew.put(newKeyRight, dpNew.get(newKeyRight) + dp.get(pair));
                    // cnt of new chars in string
                    charCnt.putIfAbsent(b.charAt(0), 0L);
                    charCnt.put(b.charAt(0), charCnt.get(b.charAt(0))+ dp.get(pair));
                }
            }
            dp = dpNew;
        }
        Long max = Collections.max(charCnt.values());
        Long min = Collections.min(charCnt.values());
        return max-min;
    }
}

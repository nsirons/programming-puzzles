package task10;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;


public class Solution {
    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = new ArrayList<>();

        File myObj = new File("aoc2021/src/task10/test.in");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            data.add(myReader.nextLine());
        }
        myReader.close();

        int part1 = 0;
        List<Long> part2Arr = new ArrayList<>();

        Map<Character, Character> Open2Close = new HashMap<>() {{
            put('(', ')');
            put('[', ']');
            put('{', '}');
            put('<', '>');
        }};
        Map<Character, Integer> Close2Score1 = new HashMap<>() {{
            put(')', 3);
            put(']', 57);
            put('}', 1197);
            put('>', 25137);
        }};
        Map<Character, Integer> Close2Score2 = new HashMap<>() {{
            put(')', 1);
            put(']', 2);
            put('}', 3);
            put('>', 4);
        }};

        for (String line : data) {
            List<Character> bracketStack = new ArrayList<>();
            boolean isComplete = true;
            // part1
            for (char ch : line.toCharArray()) {
                if (Open2Close.containsKey(ch)) {
                    bracketStack.add(Open2Close.get(ch));
                }
                else {
                    if (bracketStack.get(bracketStack.size()-1).equals(ch)) {
                        bracketStack.remove(bracketStack.size()-1);
                    }
                    else {
                        isComplete = false;
                        part1 += Close2Score1.get(ch);
                        break;
                    }
                }
            }

            // part2
            if (isComplete) {
                Collections.reverse(bracketStack);
                Long subAns = 0L;
                for (char ch : bracketStack) {
                    subAns = subAns * 5L + Close2Score2.get(ch);
                }
                part2Arr.add(subAns);
            }
        }
        Collections.sort(part2Arr);
        Long part2 = part2Arr.get(part2Arr.size() / 2);

        System.out.println(part1);
        System.out.println(part2);
    }
}

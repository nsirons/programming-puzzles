package task21;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;


public class Solution {

    static HashMap<Integer, Integer> combo =  new HashMap<>(){
        {
            put(3, 1);
            put(4, 3);
            put(5, 6);
            put(6, 7);
            put(7, 6);
            put(8, 3);
            put(9, 1);
        }
    };

    static HashMap<List<Integer>, long[]> dp = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        File myObj = new File("aoc2021/src/task21/test.in");
        Scanner myReader = new Scanner(myObj);
        int p1 = Integer.valueOf(myReader.nextLine().replace("Player 1 starting position: ", ""));
        int p2 = Integer.valueOf(myReader.nextLine().replace("Player 2 starting position: ", ""));
        p1--; p2--;
        myReader.close();

        int part1Ans = part1(p1,p2);
        long[] part2AnsArr = part2(0, 0, p1,p2);
        long part2Ans = Math.max(part2AnsArr[0], part2AnsArr[1]);
        System.out.println(part1Ans);
        System.out.println(part2Ans);
    }

    public static int part1(int p1, int p2) {
        int s1=0;
        int s2=0;
        int curDice = 0;
        boolean isP1 = true;
        int turn = 0;
        while (s1 < 1000 && s2 < 1000) {
            int diceSum = 0;
            for (int i=0; i<3; i++) {
                diceSum += curDice + 1;
                curDice++;
                curDice %= 100;
            }
            if (isP1) {
                p1 = (p1 + diceSum) % 10;
                s1 += p1 + 1;
            }
            else {
                p2 = (p2 + diceSum) % 10;
                s2 += p2 + 1;
            }
            isP1 = !isP1;
            turn++;
        }
        return Math.min(s1, s2) * turn * 3;
    }

    public static long[] part2(int s1, int s2, int p1, int p2) {
        if (s2 >= 21) {
            return new long[]{0, 1};
        }
        List<Integer> state = Collections.unmodifiableList(Arrays.asList(s1,s2,p1,p2));
        if (dp.containsKey(state)) {
            return dp.get(state);
        }

        long[] ans = new long[2];
        for (int key : combo.keySet()) {
            int p1New = (p1 + key) % 10;
            int s1New = s1 + p1New + 1;
            long[] res = part2(s2, s1New, p2, p1New);
            ans[0] += res[1] * combo.get(key);
            ans[1] += res[0] * combo.get(key);
        }
        dp.put(state, ans);
        return ans;
    }
}

package task08;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;
import java.util.stream.Collectors;


public class Solution {
    public static void main(String[] args) throws FileNotFoundException {
        List<List<String>> dataInp = new ArrayList<>();
        List<List<String>> dataOut = new ArrayList<>();

        File myObj = new File("aoc2021/src/task08/test.in");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String[] inpOut = myReader.nextLine().split(" \\| ");
            List<String> inp = new ArrayList<>();
            List<String> out = new ArrayList<>();
            for (String val : inpOut[0].split("\\s+")) {
                char[] chars = val.toCharArray();
                Arrays.sort(chars);
                inp.add(new String(chars));
            }
            for (String val : inpOut[1].split("\\s+")) {
                char[] chars = val.toCharArray();
                Arrays.sort(chars);
                out.add(new String(chars));
            }
            dataInp.add(inp);
            dataOut.add(out);
        }
        myReader.close();

        int part1 = part1(dataInp, dataOut);
        int part2 = part2(dataInp, dataOut);
        System.out.println(part1);
        System.out.println(part2);
    }

    public static int part1(List<List<String>> inp, List<List<String>> out) {
        int ans = 0;
        for (int i=0; i<inp.size(); i++) {
            Set<String> visitedNum = new HashSet<String>();
            for (String val : inp.get(i)) {
                if (val.length() < 5 || val.length() == 7) {
                    visitedNum.add(val);
                }
            }
            for (String val : out.get(i)) {
                if (visitedNum.contains(val)) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public static int part2(List<List<String>> inp, List<List<String>> out) {
        int ans = 0;
        String zero="", one="",two="",three="",four="",five="",six="",seven="",eight="",nine="";
        for (int i=0; i<inp.size(); i++) {
            HashMap<String, String> code2Num = new HashMap<>();

            // Find 1,4,7,8 based on the length of req. signal
            for (String val : inp.get(i)) {
                    if (val.length() == 2) one = val;
                    else if (val.length() == 3) seven = val;
                    else if (val.length() == 4) four = val;
                    else if (val.length() == 7) eight = val;
            }


            for (String val: inp.get(i)) {
                // for signals with length 6 (0,6,9)
                // signal 4 is inside of signal 9
                // signal 1 is inside of signal 0 and 9, but not fully in 6
                // when 6,9 are found, the third signal with length 6 is 0
                if (val.length() == 6) {
                    Set<Character> fourSet = four.chars().mapToObj(e->(char)e).collect(Collectors.toSet());
                    fourSet.retainAll(val.chars().mapToObj(e->(char)e).collect(Collectors.toSet()));
                    if (fourSet.size() == 4) {
                        nine = val;
                    }
                    Set<Character> oneSet = one.chars().mapToObj(e->(char)e).collect(Collectors.toSet());
                    oneSet.retainAll(val.chars().mapToObj(e->(char)e).collect(Collectors.toSet()));
                    if (oneSet.size() == 1) {
                        six = val;
                    }
                }
                // for signals with length 5 (2,3,5)
                // signal 7 is inside of signal 3
                // signal 5 is inside of signal 6 (but need to find 6 first!)
                // when 3 and 5 are found, the third signal with length 5 is two
                if (val.length() == 5) {
                    Set<Character> sevenSet = seven.chars().mapToObj(e->(char)e).collect(Collectors.toSet());
                    sevenSet.retainAll(val.chars().mapToObj(e->(char)e).collect(Collectors.toSet()));
                    if (sevenSet.size() == 3) {
                        three = val;
                    }
                }
            }

            for (String val : inp.get(i)) {
                if (val.length() == 5) {
                    Set<Character> valSet = val.chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
                    valSet.retainAll(six.chars().mapToObj(e -> (char) e).collect(Collectors.toSet()));
                    if (valSet.size() == 5) {
                        five = val;
                        break;
                    }
                }
            }

            for (String val : inp.get(i)) {
                if (val.length() == 6 && !val.equals(six) && !val.equals(nine)) {
                    zero = val;
                }
                if (val.length() == 5 && !val.equals(three) && !val.equals(five)) {
                    two = val;
                }
            }

            code2Num.put(zero, "0");
            code2Num.put(one, "1");
            code2Num.put(two, "2");
            code2Num.put(three, "3");
            code2Num.put(four, "4");
            code2Num.put(five, "5");
            code2Num.put(six, "6");
            code2Num.put(seven, "7");
            code2Num.put(eight, "8");
            code2Num.put(nine, "9");

            StringBuilder fullNum = new StringBuilder();
            for (String val : out.get(i)) {
                fullNum.append(code2Num.get(val));
            }
            ans += Integer.valueOf(fullNum.toString());
        }
        return ans;
    }
}

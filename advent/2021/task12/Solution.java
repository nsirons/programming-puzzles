package task12;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;


public class Solution {
    static HashMap<String, List<String>> graph = new HashMap<>();
    static HashMap<String, Integer> graphCnt = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        File myObj = new File("aoc2021/src/task12/test.in");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String[] line = myReader.nextLine().split("-");
            String u = line[0];
            String v = line[1];
            if (!graph.containsKey(u)) {
                graph.put(u, new ArrayList<>());
                graphCnt.put(u,0);
            }
            if (!graph.containsKey(v)) {
                graph.put(v, new ArrayList<>());
                graphCnt.put(v,0);
            }
            graph.get(u).add(v);
            graph.get(v).add(u);

        }
        myReader.close();

        graphCnt.put("start", 20); // don't visit start more than once
        int part1 = solve("start", false);
        int part2 = solve("start", true);
        System.out.println(part1);
        System.out.println(part2);
    }

    public static int solve(String node, boolean smallTwice) {
        int ans = 0;
        if (node.equals("end")) {
            return 1;
        }
        for (String nextNode : graph.get(node)) {
            if (graphCnt.get(nextNode)+1 <= (nextNode.toLowerCase(Locale.ROOT).equals(nextNode) ? (smallTwice ? 2 : 1) : Integer.MAX_VALUE)) {
                graphCnt.put(nextNode, graphCnt.get(nextNode) + 1);
                boolean change = false;
                if (nextNode.toLowerCase(Locale.ROOT).equals(nextNode) && graphCnt.get(nextNode) == 2) {
                    change = true;
                }
                if (change) smallTwice = false;
                ans += solve(nextNode, smallTwice);
                graphCnt.put(nextNode, graphCnt.get(nextNode) - 1);
                if (change) smallTwice = true;
            }
        }
        return ans;
    }
}

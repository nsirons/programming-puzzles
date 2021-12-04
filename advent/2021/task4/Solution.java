package task4;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files


public class Solution {
    public static void main(String[] args) throws FileNotFoundException {
        int BOARDSIZE = 5;
        List<Integer> bingo = new ArrayList<>();
        HashMap<Integer, List<Triple>> val2Board = new HashMap<>();
        List<Board> boardList = new ArrayList<>();


        File myObj = new File("aoc2021/src/task4/test.in");
        Scanner myReader = new Scanner(myObj);
        for (String val : myReader.nextLine().split(",")) bingo.add(Integer.valueOf(val));
        int boardCnt = 0;
        while (myReader.hasNextLine()) {
            String line = myReader.nextLine();
            Board board = new Board(BOARDSIZE);
            for (int i = 0; i < BOARDSIZE; i++) {
                int j = 0;
                for (String valStr : myReader.nextLine().split("\\s+")) {
                    int val;
                    try {
                        val = Integer.valueOf(valStr);
                    }
                    catch (NumberFormatException e){
                        continue;

                    }

                    val2Board.computeIfAbsent(val, k -> new ArrayList<>());
                    val2Board.get(val).add(new Triple(boardCnt, i, j));

                    board.board[i][j] = val;
                    j++;
                }
            }
            boardList.add(board);
            boardCnt += 1;

        }
        myReader.close();

        int part1 = part1(bingo, boardList, val2Board);
        int part2 = part2(bingo, boardList, val2Board);
        System.out.println(part1);
        System.out.println(part2);
    }

    public static int part1(List<Integer> bingo, List<Board> inp, HashMap<Integer, List<Triple>> val2Board ) {
        for (int val : bingo) {
            for (Triple triple : val2Board.get(val)) {
                boolean resp = inp.get(triple.fi).update(triple.se, triple.th);
                if (resp) {
                    return inp.get(triple.fi).get_ans() * val;
                }
            }
        }
        return -1;
    }



    public static int part2(List<Integer> bingo, List<Board> inp, HashMap<Integer, List<Triple>> val2Board ) {
        boolean[] visited = new boolean[inp.size()];
        int cnt=0;
        for (int val : bingo) {
            for (Triple triple : val2Board.get(val)) {
                boolean resp = inp.get(triple.fi).update(triple.se, triple.th);
                if (resp) {
                    if ((cnt == inp.size() -1) && !visited[triple.fi]) return inp.get(triple.fi).get_ans() * val;
                    else if (!visited[triple.fi]) {
                        cnt++;
                        visited[triple.fi] = true;
                    }
                }
            }
        }
        return -1;
    }
}

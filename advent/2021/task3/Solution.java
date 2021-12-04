package task3;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files


public class Solution {
    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = new ArrayList<>();

        File myObj = new File("aoc2021/src/task3/test.in");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            data.add(myReader.nextLine());
        }
        myReader.close();

        int part1 = part1(data);
        int part2 = part2(data);
        System.out.println(part1);
        System.out.println(part2);
    }

    public static int part1(List<String> inp) {
        int gamma=0; int eps=0;
        int nbits = inp.get(0).length();
        for (int col=0; col < nbits; col++) {
            int cnt = 0;
            for (int row=0; row < inp.size(); row++) {
                cnt += (inp.get(row).charAt(col)== '1' ? 1: 0);
            }
            if (cnt >= inp.size()-cnt) gamma += (int)Math.pow(2, nbits-col-1);
            else eps += (int)Math.pow(2, nbits-col-1);
        }
        return gamma * eps;
    }

    public static int part2(List<String> inp) {
        String gammaStr, epsStr;
        List<String> gammaGood = inp, epsGood = inp;
        int gammaColIdx = 0, epsColIdx=0;
        while (gammaGood.size() > 1) {
            List<String> zeroLst = new ArrayList<>();
            List<String> oneLst = new ArrayList<>();
            int cnt = 0;
            for (String line : gammaGood) {
                if (line.charAt(gammaColIdx) == '1') {
                    cnt++;
                    oneLst.add(line);
                }
                else zeroLst.add(line);
            }
            if (cnt >=gammaGood.size() - cnt) gammaGood = oneLst;
            else gammaGood = zeroLst;
            gammaColIdx++;
        }

        while (epsGood.size() > 1) {
            List<String> zeroLst = new ArrayList<>();
            List<String> oneLst = new ArrayList<>();
            int cnt = 0;
            for (String line : epsGood) {
                if (line.charAt(epsColIdx) == '1') {
                    cnt++;
                    oneLst.add(line);
                }
                else zeroLst.add(line);
            }
            if (cnt >= epsGood.size() - cnt) epsGood = zeroLst;
            else epsGood = oneLst;
            epsColIdx++;
        }
        return Integer.parseInt(gammaGood.get(0), 2) * Integer.parseInt(epsGood.get(0),2);
    }
}

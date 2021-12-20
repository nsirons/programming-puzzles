package task20;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files


public class Solution {
    public static String decode;
    public static int KERNEL_SIZE = 3;  // must be odd
    public static int BORDER_SIZE = 2 * (KERNEL_SIZE + 1) ;


    public static void main(String[] args) throws FileNotFoundException {

        List<String> data = new ArrayList<>();
        File myObj = new File("aoc2021/src/task20/test.in");
        Scanner myReader = new Scanner(myObj);
        decode = myReader.nextLine();
        myReader.nextLine();
        while (myReader.hasNextLine()) {
            data.add(myReader.nextLine());
        }
        myReader.close();

        int h = data.size();
        int w = data.get(0).length();

        boolean[][] img = new boolean[h][w];
        for (int i=0; i<h;i++) {
            for (int j = 0; j < w; j++) {
                img[i][j] = data.get(i).charAt(j) == '#';
            }
        }
        int part1 = solve(img, 2);
        int part2 = solve(img, 50);
        System.out.println(part1);
        System.out.println(part2);
    }

    public static boolean[][] enhance(boolean[][] img) {
        int h = img.length;
        int w = img[0].length;
        boolean[][] output = new boolean[h-KERNEL_SIZE/2-1][w-KERNEL_SIZE/2-1];

        for (int i=KERNEL_SIZE/2; i<h-KERNEL_SIZE/2;i++) {
            for (int j=KERNEL_SIZE/2; j<w-KERNEL_SIZE/2;j++) {
                StringBuilder idxString = new StringBuilder();
                for (int k=-KERNEL_SIZE/2; k <= KERNEL_SIZE/2; k++) {
                    for (int kk=-KERNEL_SIZE/2; kk <=KERNEL_SIZE/2; kk++) {
                        idxString.append(img[i+k][j+kk] ? '1' : '0');
                    }
                }
                output[i-KERNEL_SIZE/2][j-KERNEL_SIZE/2] = decode.charAt(Integer.parseInt(idxString.toString(), 2)) == '#';
            }
        }
        return output;
    }
    public static int solve(boolean[][] img, int step) {
        int h = img.length;
        int w = img[0].length;

        for (int i=0;i<step;i++) {
            if (i%2==0) {
                boolean[][] imgNew = new boolean[h+BORDER_SIZE][w+BORDER_SIZE];
                for (int j=0; j<h;j++) {
                    for (int k=0; k<w;k++) {
                        imgNew[j+BORDER_SIZE/2][k+BORDER_SIZE/2] = img[j][k];
                    }
                }
                img = imgNew;
                h += BORDER_SIZE;
                w += BORDER_SIZE;
            }
            img = enhance(img);
            h -= 2*(KERNEL_SIZE / 2) ;
            w -= 2*(KERNEL_SIZE / 2);
        }

        int ans = 0;
        for (int i=0; i<h;i++) {
            for (int j = 0; j < w; j++) {
                ans += img[i][j] ? 1 : 0;
            }
        }
        return ans;
    }

}

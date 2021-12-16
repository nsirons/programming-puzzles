package task16;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;

public class Solution {

    static int part1 = 0;

    public static void main(String[] args) throws FileNotFoundException {
        // https://www.geeksforgeeks.org/java-program-to-convert-hexadecimal-to-binary/
        HashMap<Character, String> hashMap = new HashMap<Character, String>();
        // storing the key value pairs
        hashMap.put('0', "0000");
        hashMap.put('1', "0001");
        hashMap.put('2', "0010");
        hashMap.put('3', "0011");
        hashMap.put('4', "0100");
        hashMap.put('5', "0101");
        hashMap.put('6', "0110");
        hashMap.put('7', "0111");
        hashMap.put('8', "1000");
        hashMap.put('9', "1001");
        hashMap.put('A', "1010");
        hashMap.put('B', "1011");
        hashMap.put('C', "1100");
        hashMap.put('D', "1101");
        hashMap.put('E', "1110");
        hashMap.put('F', "1111");

        Deque<Character> dq = new LinkedList<>();

        File myObj = new File("aoc2021/src/task16/test.in");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            for (Character ch : myReader.nextLine().toCharArray()) {
                for (Character chBin : hashMap.get(ch).toCharArray()) {
                    dq.add(chBin);
                }
            }
        }
        myReader.close();

        long part2 = decode(dq);
        System.out.println(part1);
        System.out.println(part2);
    }

    public static String get(Deque<Character> packet, int n) {
        StringBuilder str = new StringBuilder();
        for (int i=0;i<n;i++) {
            str.append(packet.poll());
        }
        return str.toString();
    }

    public static long decode(Deque<Character> packet) {
        String versionStr = get(packet, 3);
        String typeIdStr = get(packet, 3);
        int version = Integer.parseInt(versionStr, 2);
        int typeId = Integer.parseInt(typeIdStr, 2);
        part1 += version;

        List<Long> stack = new ArrayList<>();
        if (typeId == 4) {
            // literal value
            StringBuilder value = new StringBuilder();
            while (packet.peek() == '1') {
                packet.poll();
                String s = get(packet,4);
                value.append(s);
            }
            packet.poll();
            String s= get(packet, 4);
            value.append(s);
            return Long.parseLong(value.toString(), 2);
        }
        else {
            String I = get(packet, 1);
            if (I.charAt(0) == '0') {
                String lengthStr = get(packet, 15);
                Integer length = Integer.parseInt(lengthStr, 2);
                Deque<Character> subPacket = new LinkedList<>();
                for (int i=0; i<length; i++) {
                    subPacket.add(packet.poll());
                }
                while (!subPacket.isEmpty()) {
                    stack.add(decode(subPacket));
                }
            }
            else {
                String nStr = get(packet, 11);
                Integer n = Integer.parseInt(nStr, 2);
                for (int i=0; i<n; i++) {
                    stack.add(decode(packet));
                }
            }
        }

        if (typeId == 0) {
            return stack.stream().mapToLong(a->a).sum();
        }
        else if (typeId == 1) {
            Long f = 1L;
            for (long val : stack) {
                f *= val;
            }
            return f;
        }
        else if (typeId == 2) {
            return Collections.min(stack);
        }
        else if (typeId == 3) {
            return Collections.max(stack);
        }
        else if (typeId == 5){
            return (stack.get(0) > stack.get(1)) ? 1L : 0L;
        }
        else if (typeId == 6){
            return (stack.get(0) > stack.get(1)) ? 1L : 0L;
        }
        else if (typeId == 7){
            return (stack.get(0) > stack.get(1)) ? 1L : 0L;
        }
        return 0L;
    }
}

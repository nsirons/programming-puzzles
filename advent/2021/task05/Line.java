package task05;

public class Line {
    public int x1,x2,y1,y2;
    public boolean isDiag;
    Line(String line) {
        String[] startEnd = line.split(" -> ");
        String[] start = startEnd[0].split(",");
        String[] end = startEnd[1].split(",");
        x1=Integer.valueOf(start[0]);
        y1=Integer.valueOf(start[1]);
        x2=Integer.valueOf(end[0]);
        y2=Integer.valueOf(end[1]);
        isDiag = !((x1 == x2) || (y1 == y2));
    }
}

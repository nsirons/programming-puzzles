package task15;

public class Node implements Comparable<Node> {
    int cost;
    int x;
    int y;
    Node(int cost, int x, int y) {
        this.cost = cost;
        this.x = x;
        this.y = y;
    }

    public int compareTo(Node b) {
        return (this.cost > b.cost ? 1 : -1);
    }
}

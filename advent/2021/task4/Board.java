package task4;

public class Board {
    public int size;
    public int[][] board;
    public boolean[][] state;

    Board(int size) {
        this.size = size;
        this.board = new int[size][size];
        this.state = new boolean[size][size];
    }
    
    boolean update(int i, int j) {
        state[i][j] = true;
        return check_row(i) || check_col(j);

    }
    int get_ans() {
        int ans = 0;
        for (int i=0; i<size; i++) for (int j=0; j<size; j++) ans += (state[i][j] ? 0 : board[i][j]);
        return ans;
    }

    private boolean check_row(int idx) {
        for (int i=0; i<size; i++) {
            if (!state[idx][i]) return false;
        }
        return true;
    }

    private boolean check_col(int idx) {
        for (int i=0; i<size; i++) {
            if (!state[i][idx]) return false;
        }
        return true;
    }
}

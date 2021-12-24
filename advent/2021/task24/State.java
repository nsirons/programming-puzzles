package task24;

public class State {
    public int level;
    public int znow;
    public String backtrace;

    public State(int level, int znow, String backtrace) {
        this.level = level;
        this.znow = znow;
        this.backtrace = backtrace;
    }
}

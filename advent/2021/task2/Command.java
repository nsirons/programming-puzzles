package task2;

public class Command {
    public int op;
    public int val;

    Command(String command, String val) {
        this.val = Integer.parseInt(val);
        switch (command) {
            case "forward":
                op = 0;
                break;
            case "up":
                op = 1;
                this.val = -this.val;
                break;
            case "down":
                op = 2;
        }
    }
}

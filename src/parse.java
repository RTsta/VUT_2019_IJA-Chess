import java.util.Timer;
import java.util.TimerTask;

public class parse {
    public static void main(String[] args) {
        int bb = 0;
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("aaa");
            }
        };
        Timer timer = new Timer("Timer");

        timer.schedule(task, 1000L);

        timer.cancel();
    }
}

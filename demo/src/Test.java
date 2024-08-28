import java.util.Date;
import java.util.Objects;

public class Test {

    public volatile static Integer flag;
    public static void main(String[] args) {
        int ad = 0;
        Thread thread = new Thread(() -> {
            while(Objects.isNull(flag)) {

            }
            System.out.println("thread线程over");
        });
        ad++;
        Thread thread1 = new Thread(() -> {
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = Integer.valueOf(1);
        });
        thread.start();
        thread1.start();
    }
}

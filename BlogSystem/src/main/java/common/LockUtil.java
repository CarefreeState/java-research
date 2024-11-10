package common;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-10
 * Time: 22:27
 */
public class LockUtil {

    public static void lockDoSomething(String lock, Runnable behavior) {
        if(StringUtil.hasText(lock)) {
            synchronized (lock.intern()) {
                behavior.run();
            }
        }
    }

}

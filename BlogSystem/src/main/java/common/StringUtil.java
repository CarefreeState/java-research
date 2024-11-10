package common;

import java.util.Objects;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-10
 * Time: 22:28
 */
public class StringUtil {

    public static boolean hasText(String str) {
        return Objects.nonNull(str) && !str.trim().isEmpty();
    }

}

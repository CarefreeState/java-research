package common;

import model.entity.User;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-10
 * Time: 22:06
 */
public class UsernameUserLocalMap extends ConcurrentHashMap<String, User> {

    private final static AtomicInteger ID = new AtomicInteger(10000);

    public final static UsernameUserLocalMap INSTANCE = new UsernameUserLocalMap();

    @Override
    public User put(String username, User user) {
        if(Objects.nonNull(username) && Objects.nonNull(user)) {
            if(Objects.isNull(user.getUserId())) {
                user.setUserId(ID.getAndAdd(1));
            }
        }
        return super.put(username, user);
    }

}

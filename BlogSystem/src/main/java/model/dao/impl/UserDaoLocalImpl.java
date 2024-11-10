package model.dao.impl;

import common.UsernameUserLocalMap;
import model.dao.UserDao;
import model.entity.User;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-10
 * Time: 22:10
 */
public class UserDaoLocalImpl implements UserDao {

    @Override
    public User selectUserByName(String username) {
        return UsernameUserLocalMap.INSTANCE.get(username);
    }

    @Override
    public void insertUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        UsernameUserLocalMap.INSTANCE.put(username, user);
    }
}

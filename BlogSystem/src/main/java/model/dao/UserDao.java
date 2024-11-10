package model.dao;

import model.entity.User;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2023-07-07
 * Time: 12:06
 */
public interface UserDao {

    User selectUserByName(String username);

    void insertUser(String username, String password);

}


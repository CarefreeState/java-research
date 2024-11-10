package model.dao.impl;

import common.DBUtil;
import model.dao.UserDao;
import model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-10
 * Time: 22:09
 */
public class UserDaoDBImpl implements UserDao {

    @Override
    public User selectUserByName(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        User user = new User();
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from user where username = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            set = statement.executeQuery();
            if(set.next()) {
                user.setUserId(set.getInt("userId"));
                user.setPassword(set.getString("password"));
                user.setUsername(set.getString("username"));
            }else {
                user = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, set);
        }
        return user;
    }

    @Override
    public void insertUser(String username, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "insert into user(userId, username, password) values(null, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, null);
        }
    }

}

package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.LockUtil;
import common.StringUtil;
import common.SystemJsonResponse;
import model.dao.UserDao;
import model.dao.impl.UserDaoDBImpl;
import model.dao.impl.UserDaoLocalImpl;
import model.entity.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-10
 * Time: 19:48
 */
@WebServlet("/api/v1/user/register")
public class RegisterServlet extends HttpServlet {

    private final UserDao userDao = new UserDaoDBImpl();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final static String USER_REGISTER_LOCK = "userRegisterLock:";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf8");
        resp.setContentType("application/json;charset=utf8");
        byte[] reqBody = new byte[1024 * 1024];
        req.getInputStream().read(reqBody);
        User reqUser = objectMapper.readValue(reqBody, User.class);
        String username = reqUser.getUsername();
        String password = reqUser.getPassword();
        if(!StringUtil.hasText(username)) {
            resp.getWriter().write(objectMapper.writeValueAsString(SystemJsonResponse.SYSTEM_FAIL("请输入用户名")));
            return;
        }
        if(!StringUtil.hasText(password)) {
            resp.getWriter().write(objectMapper.writeValueAsString(SystemJsonResponse.SYSTEM_FAIL("请输入密码")));
            return;
        }
        LockUtil.lockDoSomething(USER_REGISTER_LOCK + username, () -> {
            try {
                User user = userDao.selectUserByName(username);
                if(Objects.nonNull(user)) {
                    resp.getWriter().write(objectMapper.writeValueAsString(SystemJsonResponse.SYSTEM_FAIL("用户名已被注册")));
                }else {
                    userDao.insertUser(username, password);
                    resp.getWriter().write(objectMapper.writeValueAsString(SystemJsonResponse.SYSTEM_SUCCESS()));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}

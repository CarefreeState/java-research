package api;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2023-07-08
 * Time: 12:45
 */
@WebServlet("/api/v1/user/login")
public class LoginServlet extends HttpServlet {

    private final UserDao userDao = new UserDaoLocalImpl();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final static String SESSION_KEY = "user";

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
        User user = userDao.selectUserByName(username);
        if(Objects.isNull(user) || !password.equals(user.getPassword())) {
            resp.getWriter().write(objectMapper.writeValueAsString(SystemJsonResponse.SYSTEM_FAIL("用户名或密码错误")));
        }else {
            HttpSession httpSession = req.getSession(Boolean.TRUE);
            httpSession.setAttribute(SESSION_KEY, user);
            resp.getWriter().write(objectMapper.writeValueAsString(SystemJsonResponse.SYSTEM_SUCCESS()));
        }
    }
}

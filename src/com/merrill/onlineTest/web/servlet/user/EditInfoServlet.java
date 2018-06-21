package com.merrill.onlineTest.web.servlet.user;

import com.merrill.onlineTest.dao.IUserDAO;
import com.merrill.onlineTest.dao.impl.UserDAOImpl;
import com.merrill.onlineTest.domain.User;
import com.merrill.onlineTest.utils.MD5Util;
import com.merrill.onlineTest.utils.Req2Obj;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/editInfo")
public class EditInfoServlet extends HttpServlet{
    IUserDAO dao = new UserDAOImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        if ("edit".equals(cmd)){
            edit(req, resp);
        }else {
            editInfo(req, resp);
        }
    }

    protected void editInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("USER_IN_SESSION");
        dao.getUserById(user.getId());
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/user/editInfo.jsp").forward(req, resp);
    }

    protected void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = dao.getUserById(new Long(req.getParameter("id")));
        String Msg = new String();
        if (dao.update(Req2Obj.req2User(req, user)) != 0){
            req.getSession().setAttribute("USER_IN_SESSION", user);
            Msg = "个人信息修改完成";
        } else {
            Msg = "个人信息修改失败";
        }
        req.setAttribute("Msg", Msg);
        req.getRequestDispatcher("/WEB-INF/views/user/editResult.jsp").forward(req, resp);

    }


}

package com.merrill.onlineTest.web.servlet.user;

import com.merrill.onlineTest.dao.IUserDAO;
import com.merrill.onlineTest.dao.impl.UserDAOImpl;
import com.merrill.onlineTest.domain.User;
import com.merrill.onlineTest.utils.MD5Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/editPassword")
public class EditPasswordServlet extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("/user/editPassword");
        String cmd = req.getParameter("cmd");
        if ("edit".equals(cmd)){
            this.edit(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/user/editPassword.jsp").forward(req, resp);
        }
    }

    protected void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MD5Util md5Util = new MD5Util();
        String password = md5Util.getMD5ofStr(req.getParameter("password"));
        User user = (User) req.getSession().getAttribute("USER_IN_SESSION");
        if (password != null){
            user.setPassword(password);
        }
        IUserDAO dao = new UserDAOImpl();
        String Msg;
        if (dao.update(user) == 1){
            Msg = "修改成功！";
        } else {
            Msg = "修改失败,请重新修改！";
        }
        req.setAttribute("Msg", Msg);
        req.getRequestDispatcher("/WEB-INF/views/user/editResult.jsp").forward(req, resp);
    }
}

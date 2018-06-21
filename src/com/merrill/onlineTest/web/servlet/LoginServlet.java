package com.merrill.onlineTest.web.servlet;

import com.merrill.onlineTest.dao.IAdminDAO;
import com.merrill.onlineTest.dao.IUserDAO;
import com.merrill.onlineTest.dao.impl.AdminDAOImpl;
import com.merrill.onlineTest.dao.impl.UserDAOImpl;
import com.merrill.onlineTest.domain.Admin;
import com.merrill.onlineTest.domain.User;
import com.merrill.onlineTest.utils.MD5Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String codeInSession = (String) req.getSession().getAttribute("RANDOMCODE_IN_SESSION");
        if(code.compareToIgnoreCase(codeInSession) != 0){
            req.setAttribute("errorMsg", "验证码不正确或已过期！");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }else{
            String identity = req.getParameter("identity");
            if("admin".equals(identity)){
                this.admin(req, resp);
            } else {
                this.user(req, resp);
            }
        }
        String identity = req.getParameter("identity");
        if("admin".equals(identity)){
                this.admin(req, resp);
            } else {
                this.user(req, resp);
            }
    }

    protected void user(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IUserDAO dao = new UserDAOImpl();
        Long id = new Long(req.getParameter("id"));
        MD5Util m = new MD5Util();
        String password = m.getMD5ofStr(req.getParameter("password"));
        User user = dao.login(id, password);
        if (user == null){
            req.setAttribute("errorMsg", "账号密码输入错误！");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        } else {
            req.getSession().setAttribute("USER_IN_SESSION", user);
            req.getRequestDispatcher("/views/user/user.jsp").forward(req, resp);
        }
    }

    protected void admin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IAdminDAO dao = new AdminDAOImpl();
        Long id = new Long(req.getParameter("id"));
        MD5Util m = new MD5Util();
        String password = m.getMD5ofStr(req.getParameter("password"));
        Admin admin = dao.login(id, password);
        if (admin == null){
            req.setAttribute("errorMsg", "账号密码输入错误！");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        } else {
            req.getSession().setAttribute("ADMIN_IN_SESSION", admin);
            req.getRequestDispatcher("/views/admin/admin.jsp").forward(req, resp);
        }
    }
}

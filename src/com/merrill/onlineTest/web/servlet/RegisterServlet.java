package com.merrill.onlineTest.web.servlet;

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
import java.io.PrintWriter;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = req2User(req);
        IUserDAO dao = new UserDAOImpl();
        if (dao.save(user) != 1){
            req.setAttribute("Msg", "注册失败！");
            System.out.println("失败");
        } else {
            req.setAttribute("Msg", "注册成功！");
            System.out.println("成功");
        }
        req.getRequestDispatcher("/views/user/registerResult.jsp").forward(req, resp);
    }

    private User req2User(HttpServletRequest req){
        Long id = new Long(req.getParameter("id"));
        String name = req.getParameter("name");
        MD5Util md5Util = new MD5Util();
        String password = md5Util.getMD5ofStr(req.getParameter("userpwd1"));
        String sex = req.getParameter("sex");
        String qq = req.getParameter("qq");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String remark = req.getParameter("remark");

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        user.setSex(sex);
        user.setEmail(email);
        user.setQq(qq);
        user.setPhone(phone);
        user.setRemark(remark);
        return user;
    }
}

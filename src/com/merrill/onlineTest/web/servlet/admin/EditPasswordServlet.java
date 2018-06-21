package com.merrill.onlineTest.web.servlet.admin;

import com.merrill.onlineTest.dao.IAdminDAO;
import com.merrill.onlineTest.dao.impl.AdminDAOImpl;
import com.merrill.onlineTest.domain.Admin;
import com.merrill.onlineTest.utils.MD5Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/editPassword")
public class EditPasswordServlet extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("/admin/editPassword");
        String cmd = req.getParameter("cmd");
        if ("edit".equals(cmd)){
            this.edit(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/admin/editPassword.jsp").forward(req, resp);
        }
    }

    protected void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MD5Util md5Util = new MD5Util();
        String password = md5Util.getMD5ofStr(req.getParameter("password"));
        Admin admin = (Admin) req.getSession().getAttribute("ADMIN_IN_SESSION");
        admin.setPassword(password);
        IAdminDAO dao = new AdminDAOImpl();
        String Msg;
        if (dao.update(admin) == 1){
            Msg = "修改成功！";
        } else {
            Msg = "修改失败,请重新修改！";
        }
        req.setAttribute("Msg", Msg);
        req.getRequestDispatcher("/WEB-INF/views/admin/editResult.jsp").forward(req, resp);
    }
}

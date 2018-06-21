package com.merrill.onlineTest.web.servlet.admin;

import com.merrill.onlineTest.dao.IAdminDAO;
import com.merrill.onlineTest.dao.impl.AdminDAOImpl;
import com.merrill.onlineTest.domain.Admin;
import com.merrill.onlineTest.utils.MD5Util;
import com.merrill.onlineTest.utils.Req2Obj;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/editInfo")
public class EditInfoServlet extends HttpServlet{
    IAdminDAO dao = new AdminDAOImpl();

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
        Admin admin = (Admin) req.getSession().getAttribute("ADMIN_IN_SESSION");
        dao.getAdminById(admin.getId());
        req.setAttribute("admin", admin);
        req.getRequestDispatcher("/WEB-INF/views/admin/editInfo.jsp").forward(req, resp);
    }

    protected void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Admin admin = dao.getAdminById(Long.valueOf(req.getParameter("id")));
        String Msg = new String();
        if (dao.update(Req2Obj.req2Admin(req, admin)) == 0){
            req.getSession().setAttribute("ADMIN_IN_SESSION", admin);
            Msg = "个人信息修改完成";
        } else {
            Msg = "个人信息修改失败";
        }
        req.setAttribute("Msg", Msg);
        req.getRequestDispatcher("/WEB-INF/views/admin/editResult.jsp").forward(req, resp);

    }

//    private Admin req2admin(HttpServletRequest req){
//        String id = req.getParameter("id");
//        Admin admin = dao.getAdminById(new Long(id));
//        String name = req.getParameter("name");
//        String sex = req.getParameter("sex");
//        String email = req.getParameter("email");
//        String phone = req.getParameter("phone");
//        String remark = req.getParameter("remark");
//        if (StringUtils.isNotBlank(name)){
//            admin.setName(name);
//        }
//        if (StringUtils.isNotBlank(sex)){
//            admin.setSex(sex);
//        }
//        if (StringUtils.isNotBlank(email)){
//            admin.setEmail(email);
//        }
//        if (StringUtils.isNotBlank(phone)){
//            admin.setPhone(phone);
//        }
//        if (StringUtils.isNotBlank(remark)){
//            admin.setRemark(remark);
//        }
//        String password = req.getParameter("password");
//        if (StringUtils.isNotBlank(password)){
//            MD5Util md5Util = new MD5Util();
//            admin.setPassword(md5Util.getMD5ofStr(password));
//        }
//        return admin;
//    }
}

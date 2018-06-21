package com.merrill.onlineTest.web.servlet.admin;

import com.merrill.onlineTest.dao.IUserDAO;
import com.merrill.onlineTest.dao.impl.UserDAOImpl;
import com.merrill.onlineTest.domain.User;
import com.merrill.onlineTest.query.PageResult;
import com.merrill.onlineTest.query.QueryUser;
import com.merrill.onlineTest.utils.MD5Util;
import com.merrill.onlineTest.utils.Req2Obj;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/userInfo")
public class UserInfoServlet extends HttpServlet {
    IUserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        System.out.println("/admin/userInfo"+cmd);
        if ("editUserInfo".equals(cmd)){
            this.editUserInfo(req, resp);
        } else if ("editPassword".equals(cmd)){
            this.editPassword(req, resp);
        } else if ("delete".equals(cmd)){
            this.delete(req, resp);
        } else if ("updateUserInfo".equals(cmd)){
            this.updateUserInfo(req, resp);
        } else if ("updatePassword".equals(cmd)){
            this.updatePassword(req, resp);
        } else if ("add".equals(cmd)){
            req.getRequestDispatcher("/WEB-INF/views/admin/userInfo/addUser.jsp").forward(req, resp);
        } else if ("addUser".equals(cmd)){
            this.addUser(req, resp);
        } else {
            this.query(req, resp);
        }
    }

    private void editPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = userDAO.getUserById(Long.valueOf(req.getParameter("id")));
        req.getSession().setAttribute("USER_IN_SESSION", user);
        req.getRequestDispatcher("/WEB-INF/views/admin/userInfo/editUserPassword.jsp").forward(req, resp);
    }

    protected void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        QueryUser queryUser = new QueryUser();
        if (req.getSession().getAttribute("queryUser") != null){
            queryUser = (QueryUser) req.getSession().getAttribute("queryUser");
        }
        System.out.println("query1 "+queryUser);
        String id = req.getParameter("id2");
        String name = req.getParameter("name");
        if (StringUtils.isNotBlank(id)){
            queryUser.setId(new Long(id));
        } else {
            queryUser.setId(null);
        }
        System.out.println(id);
//        if (StringUtils.isNotBlank(name)) {
            queryUser.setName(name);
//        }
        queryUser.getParameters();
        String s1 = req.getParameter("currentPage");
        int currentPage = 1;
        if (StringUtils.isNotBlank(s1)){
            currentPage = Integer.parseInt(s1);
        }
        String s2 = req.getParameter("pageSize");
        int pageSize = 5;
        if (StringUtils.isNotBlank(s2)){
            pageSize = Integer.parseInt(s2);
        }
        int totalCount = userDAO.count(queryUser);
        PageResult pageResult = new PageResult(currentPage, pageSize, totalCount);
        queryUser.setStart((pageResult.getCurrentPage()-1)*pageSize);
        queryUser.setEnd(pageSize);
        List<User> users = userDAO.query(queryUser);
        pageResult.setListData(users);
        req.getSession().setAttribute("pageResult", pageResult);
        req.getSession().setAttribute("queryUser", queryUser);
        System.out.println("query2 "+queryUser);
        System.out.println("pagesize  "+pageResult.getPageSize());
        req.getRequestDispatcher("/WEB-INF/views/admin/userInfo/userInfo.jsp").forward(req, resp);
    }

    protected void editUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = userDAO.getUserById(Long.valueOf(req.getParameter("id")));
        req.getSession().setAttribute("USER_IN_SESSION", user);
        req.getRequestDispatcher("/WEB-INF/views/admin/userInfo/editUserInfo.jsp").forward(req, resp);
    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] ids = req.getParameterValues("id");


        List<User> users = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            if (StringUtils.isNotBlank(ids[i])){
                User user = userDAO.getUserById(Long.valueOf(ids[i]));
                users.add(user);
                if (userDAO.delete(Long.valueOf(ids[i])) != 1){
                    for (int j = 0; j < users.size()-1; j++) {
                        userDAO.save(users.get(j));
                    }
                    userDAO.update(users.get(users.size()-1));
                    req.setAttribute("Msg", "删除失败，该用户已存在考试成绩");
                    req.getRequestDispatcher("/WEB-INF/views/admin/editResult.jsp").forward(req, resp);
                }
            }
        }



        for (int i = 0; i < ids.length; i++) {
            if (StringUtils.isNotBlank(ids[i])){
                userDAO.delete(Long.valueOf(ids[i]));
            }
        }
        resp.sendRedirect("/admin/userInfo");
    }

    protected void updateUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = userDAO.getUserById(Long.valueOf(req.getParameter("id")));
        userDAO.update(Req2Obj.req2User(req, user));
        resp.sendRedirect("/admin/userInfo");
    }

    protected void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        Req2Obj.req2User(req, user);
        if(userDAO.save(user) != 1){
            String Msg = "添加用户失败，可能该账号已经存在！";
            req.setAttribute("Msg",Msg);
            req.getRequestDispatcher("/WEB-INF/views/admin/editResult.jsp").forward(req, resp);
        }
        resp.sendRedirect("/admin/userInfo");
    }

    protected void updatePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = userDAO.getUserById(Long.valueOf(req.getParameter("id")));
        userDAO.update(Req2Obj.req2User(req, user));
        resp.sendRedirect("/admin/userInfo");
    }

}

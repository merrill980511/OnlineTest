package com.merrill.onlineTest.web.servlet.admin;

import com.merrill.onlineTest.query.QueryQuestion;
import com.merrill.onlineTest.query.QueryTestPaper;
import com.merrill.onlineTest.query.QueryTestResult;
import com.merrill.onlineTest.query.QueryUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        req.getSession().setAttribute("queryUser", new QueryUser());
        req.getSession().setAttribute("queryQuestion", new QueryQuestion());
        req.getSession().setAttribute("queryTestPaper", new QueryTestPaper());
        req.getSession().setAttribute("queryTestResult", new QueryTestResult());
        if ("userInfo".equals(cmd)){
            req.getRequestDispatcher("/admin/userInfo").forward(req, resp);
        } else if("question".equals(cmd)){
            req.getRequestDispatcher("/admin/question").forward(req, resp);
        } else if("sort".equals(cmd)){
            req.getRequestDispatcher("/admin/sort").forward(req, resp);
        } else if("testPaper".equals(cmd)){
            req.getRequestDispatcher("/admin/testPaper").forward(req, resp);
        } else if("result".equals(cmd)){
            req.getRequestDispatcher("/admin/result").forward(req, resp);
        } else if("editPassword".equals(cmd)){
            req.getRequestDispatcher("/admin/editPassword").forward(req, resp);
        } else if("editInfo".equals(cmd)){
            req.getRequestDispatcher("/admin/editInfo").forward(req, resp);
        } else{
            req.getRequestDispatcher("/views/admin/right.jsp").forward(req, resp);
        }
    }
}

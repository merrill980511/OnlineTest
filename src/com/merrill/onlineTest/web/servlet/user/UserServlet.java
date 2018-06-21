package com.merrill.onlineTest.web.servlet.user;

import com.merrill.onlineTest.query.QueryTestPaper;
import com.merrill.onlineTest.query.QueryTestResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        req.getSession().setAttribute("queryTestResult", new QueryTestResult());
        req.getSession().setAttribute("queryTestPaper", new QueryTestPaper());
        if ("result".equals(cmd)){
            req.getRequestDispatcher("/user/result").forward(req, resp);
        } else if ("editPassword".equals(cmd)){
            req.getRequestDispatcher("/user/editPassword").forward(req, resp);
        } else if ("editInfo".equals(cmd)){
            req.getRequestDispatcher("/user/editInfo").forward(req, resp);
        } else if ("test".equals(cmd)){
            req.getRequestDispatcher("/user/test").forward(req, resp);
        } else {
            req.getRequestDispatcher("/views/user/right.jsp").forward(req, resp);
        }
    }

}

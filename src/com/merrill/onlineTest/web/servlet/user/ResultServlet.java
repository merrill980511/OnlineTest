package com.merrill.onlineTest.web.servlet.user;

import com.merrill.onlineTest.dao.ISortDAO;
import com.merrill.onlineTest.dao.ITestResultDAO;
import com.merrill.onlineTest.dao.impl.SortDAOImpl;
import com.merrill.onlineTest.dao.impl.TestResultDAOImpl;
import com.merrill.onlineTest.domain.Sort;
import com.merrill.onlineTest.domain.TestResult;
import com.merrill.onlineTest.domain.User;
import com.merrill.onlineTest.query.PageResult;
import com.merrill.onlineTest.query.QueryTestResult;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/user/result")
public class ResultServlet extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("/user/result");
        query(req, resp);
    }

    protected void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        QueryTestResult queryTestResult = new QueryTestResult();
        if (req.getSession().getAttribute("queryTestResult") != null){
            queryTestResult = (QueryTestResult) req.getSession().getAttribute("queryTestResult");
        }
        User user = (User) req.getSession().getAttribute("USER_IN_SESSION");
        String userName = req.getParameter("name1");
        String testId = req.getParameter("id2");
        String testName = req.getParameter("name2");
        queryTestResult.setUserId(user.getId());
        if (StringUtils.isNotBlank(testId)){
            queryTestResult.setTestId(new Long(testId));
        } else {
            queryTestResult.setTestId(null);
        }
        queryTestResult.setUserName(userName);
        queryTestResult.setTestName(testName);
        String max = req.getParameter("max");
        String min = req.getParameter("min");
        String sort = req.getParameter("sort");
        if (StringUtils.isNotBlank(max)){
            queryTestResult.setMax(new BigDecimal(max));
        }
        if (StringUtils.isNotBlank(min)){
            queryTestResult.setMin(new BigDecimal(min));
        }
        if (StringUtils.isNotBlank(sort)){
            queryTestResult.setSort(new Long(sort));
        }
        queryTestResult.getParameters();
        String s1 = req.getParameter("currentPage");
        int currentPage = 1;
        if (StringUtils.isNotBlank(s1)){
            currentPage = Integer.parseInt(s1);
        }
        String s2 = req.getParameter("pageSize");
        System.out.println("s2  " + s2);
        int pageSize = 5;
        if (StringUtils.isNotBlank(s2)){
            pageSize = Integer.parseInt(s2);
        }

        ITestResultDAO dao = new TestResultDAOImpl();
        int totalCount = dao.count(queryTestResult);
        PageResult pageResult = new PageResult(currentPage, pageSize, totalCount);
        queryTestResult.setStart((pageResult.getCurrentPage()-1)*pageSize);
        queryTestResult.setEnd(pageSize);
        List<TestResult> testPapers = dao.query(queryTestResult);
        pageResult.setListData(testPapers);
        req.getSession().setAttribute("pageResult", pageResult);
        req.getSession().setAttribute("queryTestResult", queryTestResult);
        ISortDAO sortDAO = new SortDAOImpl();
        List<Sort> sorts = sortDAO.list();
        req.getSession().setAttribute("sorts",sorts);
        req.getRequestDispatcher("/WEB-INF/views/user/result/result.jsp").forward(req, resp);    }
}

package com.merrill.onlineTest.web.servlet.admin;

import com.merrill.onlineTest.dao.ISortDAO;
import com.merrill.onlineTest.dao.ITestResultDAO;
import com.merrill.onlineTest.dao.impl.SortDAOImpl;
import com.merrill.onlineTest.dao.impl.TestResultDAOImpl;
import com.merrill.onlineTest.domain.Sort;
import com.merrill.onlineTest.domain.TestPaper;
import com.merrill.onlineTest.domain.TestResult;
import com.merrill.onlineTest.domain.User;
import com.merrill.onlineTest.query.PageResult;
import com.merrill.onlineTest.query.QueryTestPaper;
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

@WebServlet("/admin/result")
public class ResultServlet extends HttpServlet{
    ITestResultDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new TestResultDAOImpl();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        if ("edit".equals(cmd)){
            edit(req, resp);
        } else if ("editResult".equals(cmd)){
            editResult(req ,resp);
        } else if ("add".equals(cmd)){
            add(req ,resp);
        } else if ("addResult".equals(cmd)){
            addResult(req ,resp);
        } else if ("delete".equals(cmd)){
            delete(req ,resp);
        } else {
            query(req, resp);
        }
    }

    protected void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        QueryTestResult queryTestResult = new QueryTestResult();
        if (req.getSession().getAttribute("queryTestResult") != null){
            queryTestResult = (QueryTestResult) req.getSession().getAttribute("queryTestResult");
        }
        String userId = req.getParameter("id1");
        String userName = req.getParameter("name1");
        String testId = req.getParameter("id2");
        String testName = req.getParameter("name2");
        if (StringUtils.isNotBlank(userId)){
            queryTestResult.setUserId(new Long(userId));
        } else {
            queryTestResult.setUserId(null);
        }
        if (StringUtils.isNotBlank(testId)){
            queryTestResult.setTestId(new Long(testId));
        } else {
            queryTestResult.setTestId(null);
        }
//        if (StringUtils.isNotBlank(userName)) {
            queryTestResult.setUserName(userName);
//        }
//        if (StringUtils.isNotBlank(testName)) {
            queryTestResult.setTestName(testName);
//        }
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

        int totalCount = dao.count(queryTestResult);
        PageResult pageResult = new PageResult(currentPage, pageSize, totalCount);
        queryTestResult.setStart((pageResult.getCurrentPage()-1)*pageSize);
        queryTestResult.setEnd(pageSize);
        List<TestResult> testPapers = dao.query(queryTestResult);
        pageResult.setListData(testPapers);
        req.getSession().setAttribute("pageResult", pageResult);
        req.getSession().setAttribute("queryTestResult", queryTestResult);
        ISortDAO dao = new SortDAOImpl();
        List<Sort> sorts = dao.list();
        req.getSession().setAttribute("sorts",sorts);
        req.getRequestDispatcher("/WEB-INF/views/admin/result/result.jsp").forward(req, resp);
    }

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/admin/result/addResult.jsp").forward(req, resp);
    }

    protected void addResult(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id1 = req.getParameter("userId");
        String id2 = req.getParameter("testId");
        Long userId = new Long(-1);
        Long testId = new Long(-1);
        if (StringUtils.isNotBlank(id1)){
            userId = new Long(id1);
        }
        if (StringUtils.isNotBlank(id2)){
            testId = new Long(id2);
        }
        String mark1 = req.getParameter("mark");
        BigDecimal mark = new BigDecimal(-1);
        if (StringUtils.isNotBlank(mark1)){
            mark = new BigDecimal(mark1);
        }
        TestResult testResult = new TestResult();
        User user = new User();
        user.setId(userId);
        testResult.setUser(user);
        TestPaper testPaper = new TestPaper();
        testPaper.setId(testId);
        testResult.setTestPaper(testPaper);
        testResult.setMark(mark);
        if (mark.doubleValue()>=0 && mark.doubleValue()<=100 && dao.save(testResult)==1){
            resp.sendRedirect("/admin/result");
        } else {
            req.setAttribute("Msg", "保存失败，可能是学号或者试卷编号有误，或者是成绩有问题！");
            req.getRequestDispatcher("/WEB-INF/views/admin/editResult.jsp").forward(req, resp);
        }
    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] ids = req.getParameterValues("id");
        for (int i = 0; i < ids.length; i++) {
            if (StringUtils.isNotBlank(ids[i])){
                dao.delete(Long.valueOf(ids[i]));
            }
        }
        resp.sendRedirect("/admin/result");
    }

    protected void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        TestResult testResult = dao.getResultById(new Long(id));
        System.out.println(testResult);
        req.getSession().setAttribute("testResult", testResult);
        req.getRequestDispatcher("/WEB-INF/views/admin/result/editResult.jsp").forward(req, resp);
    }

    protected void editResult(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mark = req.getParameter("mark");
        BigDecimal num = new BigDecimal(-1);
        if (StringUtils.isNotBlank(mark)){
            num = new BigDecimal(mark);
        }
        if (num.doubleValue()<0 || num.doubleValue()>100){
            req.setAttribute("Msg", "输入成绩有误，请重新修改");
            req.getRequestDispatcher("/WEB-INF/views/admin/editResult.jsp").forward(req, resp);
        } else {
            String id = req.getParameter("id");
            if (StringUtils.isNotBlank(id)){
                dao.update(new Long(id), num);
                resp.sendRedirect("/admin/result");
            }
        }
    }
}

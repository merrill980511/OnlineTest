package com.merrill.onlineTest.web.servlet.user;

import com.merrill.onlineTest.dao.ISortDAO;
import com.merrill.onlineTest.dao.ITestPaperDAO;
import com.merrill.onlineTest.dao.ITestResultDAO;
import com.merrill.onlineTest.dao.impl.SortDAOImpl;
import com.merrill.onlineTest.dao.impl.TestPaperDAOImpl;
import com.merrill.onlineTest.dao.impl.TestResultDAOImpl;
import com.merrill.onlineTest.domain.*;
import com.merrill.onlineTest.query.PageResult;
import com.merrill.onlineTest.query.QueryTestPaper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@WebServlet("/user/test")
public class TestServlet extends HttpServlet{
    ITestPaperDAO testPaperDAO;

    @Override
    public void init() throws ServletException {
        testPaperDAO = new TestPaperDAOImpl();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("/user/test");
        String cmd = req.getParameter("cmd");
        if ("testPaper".equals(cmd)){
            testPaper(req, resp);
        } else if ("check".equals(cmd)){
            check(req, resp);
        } else {
            query(req, resp);
        }
    }

    private void testPaper(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        TestPaper testPaper = new TestPaper();
        if (StringUtils.isNotBlank(id)){
            testPaper = testPaperDAO.getTestPaperById(new Long(id));
        }
        List<String> answers = new ArrayList<>();
        for(Iterator it = testPaper.getQuestions().iterator(); it.hasNext(); ){
            Question question = (Question) it.next();
            answers.add(question.getAnswer());
            question.setAnswer(null);
        }

        System.out.println("date   "+new Date().getTime());
        req.getSession().setAttribute("testTime", new Date().getTime());
        req.getSession().setAttribute("answers", answers);
        req.getSession().setAttribute("testPaper", testPaper);
        System.out.println(testPaper);
        req.getRequestDispatcher("/WEB-INF/views/user/test/testPaper.jsp").forward(req, resp);
    }

    private void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        QueryTestPaper queryTestPaper = new QueryTestPaper();
        if (req.getSession().getAttribute("queryTestPaper") != null){
            queryTestPaper = (QueryTestPaper) req.getSession().getAttribute("queryTestPaper");
        }
        String id = req.getParameter("id2");
        String name = req.getParameter("name");
        if (StringUtils.isNotBlank(id)){
            queryTestPaper.setId(new Long(id));
        } else {
            queryTestPaper.setId(null);
        }
        String sort = req.getParameter("sort");
        if (StringUtils.isNotBlank(sort)){
            queryTestPaper.setSort(new Long(sort));
        }
        queryTestPaper.setName(name);
        queryTestPaper.getParameters();
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
         testPaperDAO = new TestPaperDAOImpl();
        int totalCount = testPaperDAO.count(queryTestPaper);
        System.out.println("total" + totalCount);

        PageResult pageResult = new PageResult(currentPage, pageSize, totalCount);
        queryTestPaper.setStart((pageResult.getCurrentPage()-1)*pageSize);
        queryTestPaper.setEnd(pageSize);
        List<TestPaper> testPapers = testPaperDAO.queryTestPaper(queryTestPaper);
        pageResult.setListData(testPapers);
        req.getSession().setAttribute("pageResult", pageResult);
        req.getSession().setAttribute("queryTestPaper", queryTestPaper);
        ISortDAO dao = new SortDAOImpl();
        List<Sort> sorts = dao.list();
        req.getSession().setAttribute("sorts",sorts);
        req.getRequestDispatcher("/WEB-INF/views/user/test/test.jsp").forward(req, resp);
    }

    private void check(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TestPaper testPaper = (TestPaper) req.getSession().getAttribute("testPaper");
        List<String> userAnswers = new ArrayList<>();
        for (Iterator it = testPaper.getQuestions().iterator(); it.hasNext(); ){
            Question question = (Question) it.next();
            if (question.getSelection().getId() == 1){
                //单选
                String answer = req.getParameter(String.valueOf(question.getId()));
                userAnswers.add(answer);
            } else {
                //多选
                String[] answer = req.getParameterValues(String.valueOf(question.getId()));
                userAnswers.add(StringUtils.join(answer));
            }
        }
        BigDecimal mark = new BigDecimal(0);
        BigDecimal avg = new BigDecimal(0);
        List<String> answers = (List<String>) req.getSession().getAttribute("answers");
        int num = answers.size();
        if (num != 0){
            avg = new BigDecimal(100).divide(new BigDecimal(num));
        }
        List<Question> questions = testPaper.getQuestions();
        for (int i = 0; i < answers.size(); i++) {
            questions.get(i).setAnswer(answers.get(i));
            if (answers.get(i).equals(userAnswers.get(i))){
                mark = mark.add(avg);
            }
        }

        ITestResultDAO dao = new TestResultDAOImpl();
        TestResult testResult = new TestResult();
        User user = (User) req.getSession().getAttribute("USER_IN_SESSION");
        testResult.setUser(user);
        testResult.setTestPaper(testPaper);
        testResult.setMark(mark);

        System.out.println(dao.save(testResult));
        req.getSession().setAttribute("userAnswer", userAnswers);
        req.setAttribute("mark", mark);
        req.getRequestDispatcher("/WEB-INF/views/user/test/testResult.jsp").forward(req, resp);
    }
}

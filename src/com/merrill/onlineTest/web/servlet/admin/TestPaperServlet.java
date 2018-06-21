package com.merrill.onlineTest.web.servlet.admin;

import com.merrill.onlineTest.dao.IQuestionDAO;
import com.merrill.onlineTest.dao.ISelectionDAO;
import com.merrill.onlineTest.dao.ISortDAO;
import com.merrill.onlineTest.dao.ITestPaperDAO;
import com.merrill.onlineTest.dao.impl.QuestionDAOImpl;
import com.merrill.onlineTest.dao.impl.SelectionDAOImpl;
import com.merrill.onlineTest.dao.impl.SortDAOImpl;
import com.merrill.onlineTest.dao.impl.TestPaperDAOImpl;
import com.merrill.onlineTest.domain.Question;
import com.merrill.onlineTest.domain.Selection;
import com.merrill.onlineTest.domain.Sort;
import com.merrill.onlineTest.domain.TestPaper;
import com.merrill.onlineTest.query.PageResult;
import com.merrill.onlineTest.query.QueryQuestion;
import com.merrill.onlineTest.query.QueryTestPaper;
import com.merrill.onlineTest.utils.Req2Obj;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@WebServlet("/admin/testPaper")
public class TestPaperServlet extends HttpServlet{
    ITestPaperDAO testPaperDAO;
    IQuestionDAO dao;
    ISortDAO sortDAO;
    ISelectionDAO selectionDAO;

    @Override
    public void init() throws ServletException {
        testPaperDAO = new TestPaperDAOImpl();
        dao = new QuestionDAOImpl();
        sortDAO = new SortDAOImpl();
        selectionDAO = new SelectionDAOImpl();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        if ("delete".equals(cmd)){
            delete(req, resp);
        } else if ("edit".equals(cmd)){
            edit(req, resp);
        } else if ("editTestPaper".equals(cmd)){
            editTestPaper(req, resp);
        } else if ("deleteQuestion".equals(cmd)){
            deleteQuestion(req, resp);
        } else if ("add".equals(cmd)){
            add(req, resp);
        } else if ("addQuestion".equals(cmd)){
            addQuestion(req, resp);
        } else if ("addTestPaperResult".equals(cmd)){
            addTestPaperResult(req, resp);
        } else if ("addTestPaper".equals(cmd)){
            addTestPaper(req, resp);
        } else if ("autoGenerate".equals(cmd)){
            autoGenerate(req, resp);
        } else if ("auto".equals(cmd)){
            auto(req, resp);
        } else {
            query(req, resp);
        }
    }

    private void autoGenerate(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        List<Selection> selections = selectionDAO.list();
        String s = req.getParameter("sort");
        String name = req.getParameter("name");
        Sort sort = new Sort();
        if (StringUtils.isNotBlank(s)){
            sort = sortDAO.getSortById(new Long(s));
        }
        List<Long> ids = new ArrayList<>();
        for (Iterator it = selections.iterator(); it.hasNext(); ){
            Selection selection = (Selection) it.next();
            String num = req.getParameter(String.valueOf(selection.getId()));
            int number = 0;
            if (StringUtils.isNotBlank(num)){
                number = Integer.parseInt(num);
            }
            List<Long> list = dao.generateById(sort.getId(), selection.getId());
            if (list.size()<number){
                req.setAttribute("Msg",sort.getName()+" 的 "+selection.getName()+"不足，");
                req.getRequestDispatcher("/WEB-INF/views/admin/editResult.jsp").forward(req, resp);
            } else {
                Collections.shuffle(list);
                ids.addAll(list.subList(0, number));
            }
        }
        if (ids.size() != 0){
            Long id = testPaperDAO.save(ids, name,sort.getId());
            resp.sendRedirect("/admin/testPaper?cmd=edit&id=" + id);
        }
    }

    private void auto(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        List<Selection> selections = selectionDAO.list();
        req.setAttribute("selections", selections);
        req.getRequestDispatcher("/WEB-INF/views/admin/testPaper/autoGenerate.jsp").forward(req, resp);
    }

    //手动添加试题生成试卷
    private void addTestPaper(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/admin/testPaper/addTestPaper.jsp").forward(req, resp);
    }

    //手动添加试题生成试卷处理方法
    private void addTestPaperResult(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {

    }

    private void addQuestion(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        QueryQuestion queryQuestion = new QueryQuestion();
        String stem2 = req.getParameter("stem2");
        String id2 = req.getParameter("id2");
        String sort = req.getParameter("sort");
        String selection = req.getParameter("selection");
        if (StringUtils.isNotBlank(id2)){
            queryQuestion.setId(new Long(id2));
        }
        if (StringUtils.isNotBlank(stem2)) {
            queryQuestion.setName(stem2);
        }
        if (StringUtils.isNotBlank(sort)) {
            queryQuestion.setSort(new Long(sort));
        }
        if (StringUtils.isNotBlank(selection)) {
            queryQuestion.setSelection(new Long(selection));
        }
        queryQuestion.getParameters();
        String s1 = req.getParameter("currentPage");
        int currentPage = 1;
        if (s1 != null){
            currentPage = Integer.parseInt(s1);
        }
        String s2 = req.getParameter("pageSize");
        int pageSize = 5;
        if (s2 != null){
            pageSize = Integer.parseInt(s2);
        }
        int totalCount = dao.questionCount(queryQuestion);
        System.out.println("total" + totalCount);
        PageResult pageResult = new PageResult(currentPage, pageSize, totalCount);
        System.out.println("current:"+pageResult.getCurrentPage());

        queryQuestion.setStart((pageResult.getCurrentPage()-1)*pageSize);
        queryQuestion.setEnd(pageSize);
        List<Question> question = dao.queryQuestion(queryQuestion);
        List<Question> list = new ArrayList<>();
        for(Iterator<Question> it = question.iterator();it.hasNext();){
            Question q = it.next();
            list.add(dao.getQuestionById(q.getId()));
        }
        System.out.println(list);
        pageResult.setListData(list);
        req.getSession().setAttribute("pageResult", pageResult);
        req.getSession().setAttribute("queryQuestion", queryQuestion);
        List<Sort> sorts = sortDAO.list();
        List<Selection> selections = selectionDAO.list();
        req.setAttribute("sorts",sorts);
        req.setAttribute("selections", selections);

        req.getRequestDispatcher("/WEB-INF/views/admin/testPaper/addQuestion.jsp").forward(req, resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        String[] ids = req.getParameterValues("id");
        List<Long> idsInSession = (List<Long>) req.getSession().getAttribute("IDS_IN_SESSION");
        List<Long> list = new ArrayList<>();
        System.out.println("ids   " + ids);
        for (int i = 0; i < ids.length; i++) {
            list.add(new Long(ids[i]));
        }
        System.out.println("list " + list);
        System.out.println("ids   " + idsInSession);
        idsInSession.addAll(list);
        System.out.println("ids   " + idsInSession);

        System.out.println("   " +  req.getSession().getAttribute("testPaperId"));
        req.getSession().setAttribute("IDS_IN_SESSION", idsInSession);
        TestPaper testPaper = (TestPaper) req.getSession().getAttribute("TESTPAPER_IN_SESSION");

        System.out.println("         " + testPaper);
        List<Question> questions = testPaper.getQuestions();
        IQuestionDAO dao = new QuestionDAOImpl();
        for(Iterator it = list.iterator(); it.hasNext(); ){
            Long id = (Long) it.next();
            questions.add(dao.getQuestionById(id));
        }

        testPaper.setQuestions(questions);
        System.out.println("szie   " + testPaper.getQuestions().size());

        req.getSession().setAttribute("testPaper2",testPaper);
        req.getRequestDispatcher("/WEB-INF/views/admin/testPaper/editTestPaper.jsp").forward(req, resp);
    }

    private void deleteQuestion(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        String[] ids = req.getParameterValues("id");
        List<Long> idsInSession = (List<Long>) req.getSession().getAttribute("IDS_IN_SESSION");
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            list.add(new Long(ids[i]));
        }
        System.out.println("list " + list);
        System.out.println("ids   " + idsInSession);
        idsInSession.removeAll(list);
        System.out.println("ids   " + idsInSession);


        req.getSession().setAttribute("IDS_IN_SESSION", idsInSession);
        TestPaper testPaper = (TestPaper) req.getSession().getAttribute("TESTPAPER_IN_SESSION");
        List<Question> questions = testPaper.getQuestions();
        questions.clear();
        IQuestionDAO dao = new QuestionDAOImpl();
        for(Iterator it = idsInSession.iterator(); it.hasNext(); ){
            Long id = (Long) it.next();
            questions.add(dao.getQuestionById(id));
        }

        testPaper.setQuestions(questions);

        req.getSession().setAttribute("testPaper",testPaper);
        req.getRequestDispatcher("/WEB-INF/views/admin/testPaper/editTestPaper.jsp").forward(req, resp);
    }

    private void editTestPaper(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        TestPaper testPaper = testPaperDAO.getTestPaperById(Long.valueOf(req.getParameter("testPaperId")));
        String Msg = new String();
        testPaper = Req2Obj.req2TestPaper(req, testPaper);
        if (testPaper.getQuestions().size() == 0){
            Msg = "该试卷不包含题目，修改失败！";
        } else {
            System.out.println(testPaperDAO.update(testPaper));
            testPaperDAO.update(testPaper);
            Msg = "试卷信息修改完成";
        }
        req.setAttribute("Msg", Msg);
        req.getRequestDispatcher("/WEB-INF/views/admin/editResult.jsp").forward(req, resp);
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        String id = req.getParameter("id");
        TestPaper testPaper = testPaperDAO.getTestPaperById(new Long(id));
        req.getSession().setAttribute("TESTPAPER_IN_SESSION",testPaper);
        List<Long> ids = new ArrayList<>();
        System.out.println("id   " + id);
        System.out.println("testPaper" + testPaper);
        for (Iterator<Question> it = testPaper.getQuestions().iterator(); it.hasNext(); ){
            Question q = it.next();
            ids.add(q.getId());
        }
        req.getSession().setAttribute("IDS_IN_SESSION",ids);
        System.out.println(ids);
        req.getSession().setAttribute("testPaper",testPaper);
        ISortDAO dao = new SortDAOImpl();
        List<Sort> sorts = dao.list();
        req.getSession().setAttribute("sorts",sorts);
        req.getRequestDispatcher("/WEB-INF/views/admin/testPaper/editTestPaper.jsp").forward(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        String[] ids = req.getParameterValues("id");
        for (int i = 0; i < ids.length; i++) {
            if (StringUtils.isNotBlank(ids[i])){
                testPaperDAO.delete(Long.valueOf(ids[i]));
            }
        }
        resp.sendRedirect("/admin/testPaper");
    }

    private void query(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
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

//        if (StringUtils.isNotBlank(name)) {
            queryTestPaper.setName(name);
//        }
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
        int totalCount = testPaperDAO.count(queryTestPaper);
        System.out.println("total" + totalCount);


        PageResult pageResult = new PageResult(currentPage, pageSize, totalCount);
        queryTestPaper.setStart((pageResult.getCurrentPage()-1)*pageSize);
        queryTestPaper.setEnd(pageSize);
        List<TestPaper> testPapers = testPaperDAO.queryTestPaper(queryTestPaper);
        pageResult.setListData(testPapers);
        req.getSession().setAttribute("pageResult", pageResult);
        req.getSession().setAttribute("queryTestPaper", queryTestPaper);
//        System.out.println("page  "+pageResult.getListData().size());
        ISortDAO dao = new SortDAOImpl();
        List<Sort> sorts = dao.list();
        req.getSession().setAttribute("sorts",sorts);
        req.getRequestDispatcher("/WEB-INF/views/admin/testPaper/testPaper.jsp").forward(req, resp);
    }
}

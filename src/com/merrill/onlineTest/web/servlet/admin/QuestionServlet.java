package com.merrill.onlineTest.web.servlet.admin;

import com.merrill.onlineTest.dao.IQuestionDAO;
import com.merrill.onlineTest.dao.ISelectionDAO;
import com.merrill.onlineTest.dao.ISortDAO;
import com.merrill.onlineTest.dao.impl.QuestionDAOImpl;
import com.merrill.onlineTest.dao.impl.SelectionDAOImpl;
import com.merrill.onlineTest.dao.impl.SortDAOImpl;
import com.merrill.onlineTest.domain.Option;
import com.merrill.onlineTest.domain.Question;
import com.merrill.onlineTest.domain.Selection;
import com.merrill.onlineTest.domain.Sort;
import com.merrill.onlineTest.query.PageResult;
import com.merrill.onlineTest.query.QueryQuestion;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/admin/question")
public class QuestionServlet extends HttpServlet{
    IQuestionDAO dao;
    ISortDAO sortDAO;
    ISelectionDAO selectionDAO;

    @Override
    public void init() throws ServletException {
        dao = new QuestionDAOImpl();
        sortDAO = new SortDAOImpl();
        selectionDAO = new SelectionDAOImpl();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        if ("add".equals(cmd)){
            add(req, resp);
        } else if ("addQuestion".equals(cmd)){
            addQuestion(req, resp);
        } else if ("edit".equals(cmd)){
            edit(req, resp);
        } else if ("editQuestion".equals(cmd)){
            editQuestion(req, resp);
        } else if ("delete".equals(cmd)){
            delete(req, resp);
        } else if ("addTestPaper".equals(cmd)){
            addTestPaper(req, resp);
        } else {
            this.query(req, resp);
        }
        System.out.println("/admin/question");
    }

    private void addTestPaper(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void editQuestion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Question question = dao.getQuestionById(new Long(id));
        String stem = req.getParameter("stem");
        String answer = req.getParameter("answer");
        if (StringUtils.isNotBlank(stem)){
            question.setStem(stem);
        }
        if (StringUtils.isNotBlank(answer)){
            question.setAnswer(answer);
        }
        String sort = req.getParameter("sort");
        String selection = req.getParameter("selection");
        if (StringUtils.isNotBlank(sort)){
            Sort s = new Sort();
            s.setId(new Long(sort));
            question.setSort(s);
        }
        if (StringUtils.isNotBlank(selection)){
            Selection s = new Selection();
            s.setId(new Long(selection));
            question.setSelection(s);
        }

        List<Option> options = question.getOptions();
        for (Iterator it = options.iterator(); it.hasNext();){
            Option option = (Option) it.next();
            String content = req.getParameter("content"+option.getId());
            String item = req.getParameter("item"+option.getId());
            System.out.println(content + "   " + item);
            if (StringUtils.isNotBlank(item)){
                if (StringUtils.isNotBlank(content)){
                    option.setContent(content);
                    option.setItem(item);
                }
            }
        }
        if (dao.update(question) == 1){
            resp.sendRedirect("/admin/question");
        } else {
            String Msg = "问题保存失败！";
            req.setAttribute("Msg", Msg);
            req.getRequestDispatcher("/WEB-INF/views/admin/editResult.jsp").forward(req, resp);
        }
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (StringUtils.isNotBlank(id)){
            Question question = dao.getQuestionById(new Long(id));
            req.setAttribute("question", question);
            List<Sort> sorts = sortDAO.list();
            List<Selection> selections = selectionDAO.list();
            req.setAttribute("sorts",sorts);
            req.setAttribute("selections", selections);
            req.getRequestDispatcher("/WEB-INF/views/admin/question/editQuestion.jsp").forward(req, resp);
        }
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] ids = req.getParameterValues("id");
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            if (StringUtils.isNotBlank(ids[i])){
                Question question = dao.getQuestionById(Long.valueOf(ids[i]));
                questions.add(question);
                if (dao.delete(Long.valueOf(ids[i])) != 1){
                    for (int j = 0; j < questions.size()-1; j++) {
                        dao.save(questions.get(j));
                    }
                    dao.update(questions.get(questions.size()-1));
                    req.setAttribute("Msg", "删除失败，该题目已存在于试题中");
                    req.getRequestDispatcher("/WEB-INF/views/admin/editResult.jsp").forward(req, resp);
                }
            }
        }
        resp.sendRedirect("/admin/question");
    }

    private void addQuestion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Question question = new Question();
        String stem = req.getParameter("stem");
        String answer = req.getParameter("answer");
        if (StringUtils.isNotBlank(stem)){
            question.setStem(stem);
        }
        if (StringUtils.isNotBlank(answer)){
            question.setAnswer(answer);
        }
        String sort = req.getParameter("sort");
        String selection = req.getParameter("selection");
        if (StringUtils.isNotBlank(sort)){
            Sort s = new Sort();
            s.setId(new Long(sort));
            question.setSort(s);
        }
        if (StringUtils.isNotBlank(selection)){
            Selection s = new Selection();
            s.setId(new Long(selection));
            question.setSelection(s);
        }

        List<Option> options = new ArrayList<>();
        question.setOptions(options);
        for (int i = 1; i < 7; i++){
            String content = req.getParameter("content"+i);
            String item = req.getParameter("item"+i);
            System.out.println(content + "   " + item);
            if (StringUtils.isNotBlank(item)){
                if (StringUtils.isNotBlank(content)){
                    Option option = new Option();
                    options.add(option);
                    option.setContent(content);
                    option.setItem(item);
                }
            }
        }
        if (dao.save(question) == 1){
            resp.sendRedirect("/admin/question");
        } else {
            String Msg = "添加失败！";
            req.setAttribute("Msg", Msg);
            req.getRequestDispatcher("/WEB-INF/views/admin/editResult.jsp").forward(req, resp);
        }
    }

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Sort> sorts = sortDAO.list();
        List<Selection> selections = selectionDAO.list();
        req.setAttribute("sorts",sorts);
        req.setAttribute("selections", selections);
        req.getRequestDispatcher("/WEB-INF/views/admin/question/addQuestion.jsp").forward(req, resp);
    }

    protected void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        QueryQuestion queryQuestion = new QueryQuestion();
        if (req.getSession().getAttribute("queryQuestion") != null){
            queryQuestion = (QueryQuestion) req.getSession().getAttribute("queryQuestion");
        }
        String stem2 = req.getParameter("stem2");

//        if (StringUtils.isNotBlank(stem2)) {
            queryQuestion.setName(stem2);
//        }

        String id2 = req.getParameter("id2");
        String sort = req.getParameter("sort");
        String selection = req.getParameter("selection");
        if (StringUtils.isNotBlank(id2)){
            queryQuestion.setId(new Long(id2));
        } else {
            queryQuestion.setId(null);
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
//        if ( question != null){
        System.out.println("question   " + question);
            for(Iterator<Question> it = question.iterator();it.hasNext();){
                Question q = it.next();
                list.add(dao.getQuestionById(q.getId()));
            }
//        }
        System.out.println(list);
        pageResult.setListData(list);
        req.getSession().setAttribute("pageResult", pageResult);
        req.getSession().setAttribute("queryQuestion", queryQuestion);
        List<Sort> sorts = sortDAO.list();
        List<Selection> selections = selectionDAO.list();
        req.setAttribute("sorts",sorts);
        req.setAttribute("selections", selections);
//        System.out.println("sort:"+sorts);
        System.out.println("q:"+queryQuestion);
        req.getRequestDispatcher("/WEB-INF/views/admin/question/question.jsp").forward(req, resp);
    }

}

package com.merrill.onlineTest.web.servlet.admin;

import com.merrill.onlineTest.dao.ISortDAO;
import com.merrill.onlineTest.dao.impl.SortDAOImpl;
import com.merrill.onlineTest.domain.Sort;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet("/admin/sort")
public class SortServlet extends HttpServlet{
    ISortDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new SortDAOImpl();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("/admin/sort");
        String cmd = req.getParameter("cmd");
        if ("add".equals(cmd)){
            req.getRequestDispatcher("/WEB-INF/views/admin/sort/addSort.jsp").forward(req, resp);
        } else if ("delete".equals(cmd)){
            delete(req, resp);
        } else if ("addSort".equals(cmd)){
            add(req, resp);
        } else {
            query(req, resp);
        }
    }

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        if (StringUtils.isNotBlank(name)){
            Sort sort = new Sort();
            sort.setName(name);
            System.out.println(sort);
            dao.save(sort);
        }
        resp.sendRedirect("/admin/sort");
    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] ids = req.getParameterValues("id");
        List<Sort> sorts = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            if (StringUtils.isNotBlank(ids[i])){
                Sort question = dao.getSortById(Long.valueOf(ids[i]));
                sorts.add(question);
                if (dao.delete(Long.valueOf(ids[i])) != 1){
                    for (int j = 0; j < sorts.size()-1; j++) {
                        dao.save(sorts.get(j));
                    }
                    dao.update(sorts.get(sorts.size()-1));
                    req.setAttribute("Msg", "删除失败，该学科已存在试题");
                    req.getRequestDispatcher("/WEB-INF/views/admin/editResult.jsp").forward(req, resp);
                }
            }
        }
        resp.sendRedirect("/admin/sort");
    }

    protected void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Sort> sorts = dao.list();
        req.setAttribute("sorts", sorts);
        req.getRequestDispatcher("/WEB-INF/views/admin/sort/sort.jsp").forward(req, resp);
    }
}

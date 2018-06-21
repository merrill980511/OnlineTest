package com.merrill.onlineTest.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "AdminFilter")
public class AdminFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        resp.setContentType("text/html; charset=UTF-8");
        HttpSession session = request.getSession();
        String host = request.getContextPath();
        if (session.getAttribute("ADMIN_IN_SESSION") == null) {
            PrintWriter writer = response.getWriter();
            writer.println("<html><head></head><body>你无权访问，或你的登陆已过期，请先<a href='"
                    + host
                    + "/index.jsp'  target='_parent'>登陆</a></body></html>");
            writer.close();
        }
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}

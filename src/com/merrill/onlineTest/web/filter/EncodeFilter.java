package com.merrill.onlineTest.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodeFilter implements Filter {
    String encode;

    public void init(FilterConfig config) throws ServletException {
        if (config.getInitParameter("encode") != null){
            encode = config.getInitParameter("encode");
        } else {
            encode = "utf-8";
        }
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding(encode);
        response.setCharacterEncoding(encode);
        chain.doFilter(req, resp);
    }

    public void destroy() {

    }
}

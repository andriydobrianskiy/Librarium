package com.softserve.academy.controller;

import com.softserve.academy.Entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String servletPath = request.getServletPath();
        User user = (User) request.getSession().getAttribute("user");

        if (user != null && servletPath.equals("/login")) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        if (user == null) {
            if (servletPath.equals("/users") || servletPath.equals("/user")) {
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

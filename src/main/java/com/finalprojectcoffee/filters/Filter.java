package com.finalprojectcoffee.filters;

import com.finalprojectcoffee.utils.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebFilter(urlPatterns = {"/login", "/register"})
public class Filter implements jakarta.servlet.Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        jakarta.servlet.Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String token = req.getHeader("Authorization");

        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            request.setAttribute("claims",claims);
            chain.doFilter(request,response);
        } catch (Exception e) {
            res.setStatus(401);
            res.getWriter().write("Unauthorized: Token validation failed");
        }

    }

    @Override
    public void destroy() {
        jakarta.servlet.Filter.super.destroy();
    }
}

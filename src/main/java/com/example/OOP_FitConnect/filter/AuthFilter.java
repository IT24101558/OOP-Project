package com.example.OOP_FitConnect.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter { //polymophisum,inheritance

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) //abstraction
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute("userId") != null;
        String userRole = session != null ? (String) session.getAttribute("userRole") : null;
        String requestURI = httpRequest.getRequestURI();

        // Handle AJAX requests differently to avoid CORS issues
        boolean isAjax = "XMLHttpRequest".equals(httpRequest.getHeader("X-Requested-With"));

        if (isLoggedIn) {
            // Check admin access
            if (requestURI.startsWith("/admin/") && !"ADMIN".equals(userRole)) {
                if (isAjax) {
                    httpResponse.setContentType("application/json");
                    httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    httpResponse.getWriter().write("{\"error\":\"Access denied\",\"redirect\":\"/verification-result\"}");
                } else {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/verification-result");
                }
                return;
            }
            // User is authenticated and authorized, proceed with the request
            chain.doFilter(request, response);
        } else {
            // User is not authenticated
            if (isAjax) {
                httpResponse.setContentType("application/json");
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("{\"error\":\"Not authenticated\",\"redirect\":\"/register\"}");
            } else {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/member_dashboard");
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization if needed
    }

    @Override
    public void destroy() {
        // Cleanup if needed
    }
}
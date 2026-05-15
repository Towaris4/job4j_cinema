package ru.job4j.cinema.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
@Order(1)
public class AuthorizationFilter extends HttpFilter {

    private static final Set<String> PROTECTED_PATHS = Set.of(
            "/tickets/buying"
    );

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        var uri = request.getRequestURI();
        var contextPath = request.getContextPath();
        if (!isProtectedPath(uri)) {
            chain.doFilter(request, response);
            return;
        }

        var session = request.getSession(false);
        var user = (session != null) ? session.getAttribute("user") : null;

        if (user == null) {
            request.getSession().setAttribute("redirectAfterLogin", uri);
            response.sendRedirect(contextPath + "/users/login");
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isProtectedPath(String uri) {
        for (String protectedPath : PROTECTED_PATHS) {
            if (uri.startsWith(protectedPath)) {
                return true;
            }
        }
        return false;
    }
}
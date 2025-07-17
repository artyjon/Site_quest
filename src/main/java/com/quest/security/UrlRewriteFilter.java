package com.quest.security;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;

@WebFilter("/*")
public class UrlRewriteFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(UrlRewriteFilter.class);

    private static final Set<String> KNOWN_QUESTS = Set.of("plant_worker", "space_marine");

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String contextPath = request.getContextPath();
        String uri = request.getRequestURI();
        String path = uri.substring(contextPath.length());

        LOG.info("[FILTER] URI: {}", uri);
        LOG.info("[FILTER] Path: {}", path);

        if (shouldBypass(path)) {
            chain.doFilter(req, res);
            return;
        }

        if (path.equals("/") || path.isBlank()) {
            response.sendRedirect(contextPath + "/about");
            return;
        }

        HttpSession session = request.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute("user") != null;

        if (!isLoggedIn && !isPublicPage(path)) {
            response.sendRedirect(contextPath + "/login");
            return;
        }

        String pagePath;
        if (path.startsWith("/html/")){
            pagePath = "/WEB-INF/views" + path + ".html";
        } else {
            pagePath = "/WEB-INF/views" + path + ".jsp";
        }

        request.setAttribute("pagePath", pagePath);
        request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
    }

    private boolean shouldBypass(String path) {
        return isStaticResource(path)
               || path.endsWith(".jspf")
               || path.endsWith(".jsp")
               || isPublicServlet(path)
               || isQuestServlet(path);
    }

    private boolean isStaticResource(String path) {
        return path.startsWith("/css/")
               || path.startsWith("/js/")
               || path.startsWith("/images/")
               || path.startsWith("/fonts/")
               || path.matches(".*\\.(css|js|png|jpg|jpeg|gif|woff2|ttf|ico|map)$");
    }

    private boolean isPublicPage(String path) {
        return path.equals("/about") || path.startsWith("/html/");
    }
    private boolean isQuestServlet(String path) {
        return KNOWN_QUESTS.contains(path.substring(1));
    }
    private boolean isPublicServlet(String path) {
        return path.equals("/login") || path.equals("/logout") || path.equals("/sign_up");
    }
}
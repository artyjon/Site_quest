package com.quest.controller.authentication;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.quest.util.ServletUtil.forwardError;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        String contextPath = request.getContextPath();

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(contextPath + "/about");
        } catch (IOException e) {
            LOG.error("Error during login: {}", e.getMessage());
            request.setAttribute("error", "Ошибка при выходе из системы.");
            forwardError(request, response, "/WEB-INF/views/index.jsp");
        }
    }
}
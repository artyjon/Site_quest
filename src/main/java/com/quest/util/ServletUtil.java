package com.quest.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ServletUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ServletUtil.class);

    private ServletUtil() {}

    public static void forwardError(HttpServletRequest request, HttpServletResponse response, String page) {
        try {
            request.getRequestDispatcher(page).forward(request, response);
        } catch (Exception e) {
            LOG.error("Error: {}", e.getMessage());
        }
    }
}

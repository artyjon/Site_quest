package com.quest.controller.authentication;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class LogoutControllerTest {

    private LogoutController controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        controller = new LogoutController();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
    }

    @Test
    void givenActiveSession_whenDoGet_thenSessionInvalidatedAndRedirected() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(request.getContextPath()).thenReturn("/app");

        controller.doGet(request, response);

        verify(session).invalidate();
        verify(response).sendRedirect("/app/about");
    }

    @Test
    void givenIOException_whenDoGet_thenForwardToError() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(request.getContextPath()).thenReturn("/app");
        doThrow(new IOException("fail")).when(response).sendRedirect(anyString());
        when(request.getRequestDispatcher("/WEB-INF/views/index.jsp")).thenReturn(dispatcher);

        controller.doGet(request, response);

        verify(request).setAttribute(eq("error"), contains("Ошибка при выходе"));
        verify(dispatcher).forward(eq(request), eq(response));
    }
}
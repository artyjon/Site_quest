package com.quest.controller.authentication;

import com.quest.dto.SessionUser;
import com.quest.entity.User;
import com.quest.service.AuthenticationService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    private LoginController controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private AuthenticationService authService;

    @BeforeEach
    void setUp() {
        controller = new LoginController();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        authService = mock(AuthenticationService.class);

        controller.setAuthService(authService);
    }

    @Test
    void givenEmptyFields_whenPost_thenSetError() throws Exception {
        when(request.getParameter("login")).thenReturn("");
        when(request.getParameter("password")).thenReturn("");
        when(authService.isFieldEmpty(eq(""), eq(""))).thenReturn(true);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        controller.doPost(request, response);

        verify(request).setAttribute(eq("errorEmptyLines"), contains("Все поля должны быть заполнены"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void givenNonexistentUser_whenPost_thenSetError() throws Exception {
        when(request.getParameter("login")).thenReturn("nonexistent");
        when(request.getParameter("password")).thenReturn("pass");
        when(authService.isFieldEmpty(anyString(), anyString())).thenReturn(false);
        when(authService.getUserByLogin("nonexistent")).thenReturn(null);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        controller.doPost(request, response);

        verify(request).setAttribute(eq("errorLogin"), contains("не существует"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void givenWrongPassword_whenPost_thenSetError() throws Exception {
        when(request.getParameter("login")).thenReturn("user");
        when(request.getParameter("password")).thenReturn("wrong");
        when(authService.isFieldEmpty(anyString(), anyString())).thenReturn(false);
        when(authService.getUserByLogin("user")).thenReturn(new User());
        when(authService.isPasswordCorrect("user", "wrong")).thenReturn(false);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        controller.doPost(request, response);

        verify(request).setAttribute(eq("errorPassword"), contains("Неверный пароль"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void givenCorrectLogin_whenPost_thenRedirectToIndex() throws Exception {
        User user = new User("user", "User Name", "hashedPass");

        when(request.getParameter("login")).thenReturn("user");
        when(request.getParameter("password")).thenReturn("correct");
        when(authService.isFieldEmpty(anyString(), anyString())).thenReturn(false);
        when(authService.getUserByLogin("user")).thenReturn(user);
        when(authService.isPasswordCorrect("user", "correct")).thenReturn(true);
        when(request.getSession(true)).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        controller.doPost(request, response);

        verify(session).setAttribute(eq("user"), any(SessionUser.class));
        ArgumentCaptor<Map<String, String>> mapCaptor = ArgumentCaptor.forClass(Map.class);
        verify(session).setAttribute(eq("questNameMap"), mapCaptor.capture());

        Map<String, String> map = mapCaptor.getValue();
        assertTrue(map.containsKey("plant_worker"));

        verify(dispatcher).forward(request, response);
    }
}
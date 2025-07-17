package com.quest.controller.authentication;

import com.quest.service.AuthenticationService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RegistrationControllerTest {

    private RegistrationController controller;
    private AuthenticationService authService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        controller = new RegistrationController();
        authService = mock(AuthenticationService.class);
        controller.setAuthService(authService);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
    }

    @Test
    void givenEmptyFields_whenPost_thenSetErrorAttribute() throws Exception {
        when(request.getParameter("name")).thenReturn("");
        when(request.getParameter("login")).thenReturn("");
        when(request.getParameter("pass")).thenReturn("");
        when(request.getParameter("repass")).thenReturn("");
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        when(authService.isFieldEmpty(eq(""), eq(""), eq(""), eq(""))).thenReturn(true);

        controller.doPost(request, response);

        verify(request).setAttribute(eq("errorEmptyLines"), contains("Все поля должны быть заполнены"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void givenPasswordsNotEqual_whenPost_thenSetErrorAttribute() throws Exception {
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("login")).thenReturn("login");
        when(request.getParameter("pass")).thenReturn("12345678a");
        when(request.getParameter("repass")).thenReturn("otherpass");
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        when(authService.isFieldEmpty(any())).thenReturn(false);

        controller.doPost(request, response);

        verify(request).setAttribute(eq("errorPassRepeat"), contains("Пароли не совпадают"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void givenInvalidPassword_whenPost_thenSetErrorAttribute() throws Exception {
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("login")).thenReturn("login");
        when(request.getParameter("pass")).thenReturn("123");
        when(request.getParameter("repass")).thenReturn("123");
        when(authService.isFieldEmpty(any())).thenReturn(false);
        when(authService.isPasswordValid(anyString())).thenReturn(false);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        controller.doPost(request, response);

        verify(request).setAttribute(eq("errorPassword"), contains("Пароль должен быть минимум"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void givenUserExists_whenPost_thenSetErrorAttribute() throws Exception {
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("login")).thenReturn("existing_user");
        when(request.getParameter("pass")).thenReturn("12345678a");
        when(request.getParameter("repass")).thenReturn("12345678a");

        when(authService.isFieldEmpty(any())).thenReturn(false);
        when(authService.isPasswordValid(anyString())).thenReturn(true);
        when(authService.isUserExists("existing_user")).thenReturn(true);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        controller.doPost(request, response);

        verify(request).setAttribute(eq("errorLogin"), contains("Пользователь с таким логином уже существует"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void givenValidData_whenPost_thenUserIsSavedAndRedirected() throws Exception {
        when(request.getParameter("name")).thenReturn("Valid Name");
        when(request.getParameter("login")).thenReturn("validlogin");
        when(request.getParameter("pass")).thenReturn("12345678a");
        when(request.getParameter("repass")).thenReturn("12345678a");

        when(authService.isFieldEmpty(any())).thenReturn(false);
        when(authService.isPasswordValid("12345678a")).thenReturn(true);
        when(authService.isUserExists("validlogin")).thenReturn(false);
        when(request.getRequestDispatcher("/WEB-INF/views/login.jsp")).thenReturn(dispatcher);

        controller.doPost(request, response);

        verify(authService).saveValidUser("Valid Name", "validlogin", "12345678a");
        verify(dispatcher).forward(request, response);
    }
}
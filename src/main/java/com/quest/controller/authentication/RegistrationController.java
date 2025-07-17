package com.quest.controller.authentication;

import com.quest.config.DBConfig;
import com.quest.repository.UserRepository;
import com.quest.service.AuthenticationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.quest.util.ServletUtil.forwardError;

@Setter
@WebServlet("/sign_up")
public class RegistrationController extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    private transient AuthenticationService authService;

    @Override
    public void init() {
        if (authService == null) {
            this.authService = new AuthenticationService(
                    new UserRepository(DBConfig.getSessionFactory()));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String registrationPage = "/WEB-INF/views/sign_up.jsp";
        String loginPage = "/WEB-INF/views/login.jsp";
        try {
            request.getRequestDispatcher(registrationPage).forward(request, response);
        } catch (ServletException | IOException e) {
            LOG.error("Ошибка при открытии формы регистрации: {}", e.getMessage());
            request.setAttribute("errorEmptyLines", "Ошибка при открытии формы регистрации.");
            forwardError(request, response, loginPage);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (Exception e) {
            LOG.error("Encoding error: {}", e.getMessage());
        }

        response.setContentType("text/html; charset=UTF-8");

        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");
        String repass = request.getParameter("repass");
        String registrationPage = "/WEB-INF/views/sign_up.jsp";

        if (authService.isFieldEmpty(name, login, pass, repass)) {
            request.setAttribute("errorEmptyLines", "Все поля должны быть заполнены!");
            forwardError(request, response, registrationPage);
            return;
        }

        if (!pass.equals(repass)) {
            request.setAttribute("errorPassRepeat", "Пароли не совпадают!");
            forwardError(request, response, registrationPage);
            return;
        }

        if (!authService.isPasswordValid(pass)) {
            request.setAttribute("errorPassword", "Пароль должен быть минимум 8 символов, содержать букву и цифру!");
            forwardError(request, response, registrationPage);
            return;
        }

        try {
            if (authService.isUserExists(login)) {
                request.setAttribute("errorLogin", "Пользователь с таким логином уже существует!");
                forwardError(request, response, registrationPage);
                return;
            }

            authService.saveValidUser(name, login, pass);
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);

        } catch (ServletException | IOException e) {
            LOG.error("Ошибка при регистрации: {}", e.getMessage());
            request.setAttribute("errorEmptyLines", "Ошибка при регистрации.");
            forwardError(request, response, registrationPage);
        }
    }
}
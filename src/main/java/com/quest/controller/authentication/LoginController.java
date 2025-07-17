package com.quest.controller.authentication;

import com.quest.config.DBConfig;
import com.quest.dto.SessionUser;
import com.quest.entity.User;
import com.quest.repository.UserRepository;
import com.quest.service.AuthenticationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.quest.util.ServletUtil.forwardError;

@Setter
@WebServlet("/login")
public class LoginController extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

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

        try {
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            LOG.error("Ошибка при открытии login.jsp", e);
            request.setAttribute("errorLogin", "Ошибка при загрузке страницы входа.");
            forwardError(request, response, "/WEB-INF/views/index.jsp");
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

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String indexPage = "/WEB-INF/views/index.jsp";
        String loginPage = "/WEB-INF/views/login.jsp";

        try {

            User user = authService.getUserByLogin(login);

            if (authService.isFieldEmpty(login, password)) {
                request.setAttribute("errorEmptyLines", "Все поля должны быть заполнены!");
                forwardError(request, response, loginPage);
                return;
            }

            if (user == null) {
                request.setAttribute("errorLogin", "Пользователь с таким логином не существует!");
                forwardError(request, response, loginPage);
                return;
            }

            if (!authService.isPasswordCorrect(login, password)) {
                request.setAttribute("errorPassword", "Неверный пароль.");
                forwardError(request, response, loginPage);
                return;
            }

            HttpSession session = request.getSession(true);
            session.setAttribute("user", new SessionUser(user));
            session.setAttribute("questNameMap", questNameInit());
            request.getRequestDispatcher(indexPage).forward(request, response);

        } catch (IOException | ServletException e) {
            LOG.error("Error during login: {}", e.getMessage());
            request.setAttribute("errorLogin", "Ошибка при входе в систему.");
            forwardError(request, response, loginPage);
        }
    }

    private  Map<String,String> questNameInit() {
        Map<String, String> questName = new HashMap<>();
        questName.put("plant_worker", "Заводчанин");
        questName.put("space_marine", "Космодесантник");
        questName.put("snatch", "Художественный фильм \"Украли\"");
        return questName;
    }
}
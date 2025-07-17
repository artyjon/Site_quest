package com.quest.controller.quest;

import com.quest.config.DBConfig;
import com.quest.dto.QuestStatistics;
import com.quest.dto.SessionUser;
import com.quest.repository.UserRepository;
import com.quest.service.QuestService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

// Servlet setup in web.xml
public class QuestController extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(QuestController.class);

    @Setter
    private transient QuestService questService;
    private String questsPath;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        if (this.questService == null) {
            this.questService = new QuestService(
                    new UserRepository(DBConfig.getSessionFactory()));
        }
        try {
            this.questsPath = config.getInitParameter("questsPath");

            if (questsPath == null || questsPath.isBlank()) {
                LOG.error("Init-param 'questsPath' не указан или пустой в web.xml");
                this.questsPath = null;
            } else {
                LOG.info("QuestController initialized with questsPath: {}", questsPath);
            }
        } catch (Exception e) {
            LOG.error("Ошибка при инициализации QuestController: {}", e.getMessage(), e);
            this.questsPath = null;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {

        try {

            if (this.questsPath == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Сервер неправильно настроен: questsPath не инициализирован.");
                return;
            }

            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            SessionUser sessionUser = (SessionUser) session.getAttribute("user");
            String questName = request.getParameter("questName");
            String stage = request.getParameter("stage");

            if (stage == null) stage = "0";
            if (questName == null || questName.isBlank()) {
                LOG.error("Quest name is null or empty");
                response.sendRedirect("index.jsp");
                return;
            }

            QuestStatistics stat = sessionUser.getQuests()
                    .computeIfAbsent(questName, q -> new QuestStatistics());

            if (stage.contains("win")) {
                stat.incrementWin();
                questService.updateQuestStatistics(sessionUser.getId(), questName, stat);
            } else if (stage.contains("lose")) {
                stat.incrementLoss();
                questService.updateQuestStatistics(sessionUser.getId(), questName, stat);
            }

            session.setAttribute("user", sessionUser);

            request.setAttribute("pagePath", String.format("%s/%s/%s.jsp", questsPath, questName, questName));
            request.setAttribute("stage", stage);
            request.setAttribute("questInclude", String.format("%s/%s/stage%s.jspf", questsPath, questName, stage));

            request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);

        } catch (ServletException | IOException e) {
            LOG.error("Ошибка в квесте: {}", e.getMessage(), e);
        }
    }
}
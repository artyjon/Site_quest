package com.quest.controller.quest;

import com.quest.dto.SessionUser;
import com.quest.entity.User;
import com.quest.service.QuestService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestControllerTest {

    @InjectMocks
    private QuestController controller;

    @Mock
    private QuestService questService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    private SessionUser sessionUser;

    @BeforeEach
    void setUp() throws ServletException {
        sessionUser = new SessionUser(new User("login", "Ростислав", "hash"));
        sessionUser.getQuests().clear();
        controller.init(new MockServletConfig());
    }

    @Test
    void givenNoSession_whenProcessRequest_thenRedirectsToLogin() throws Exception {
        when(request.getSession(false)).thenReturn(null);

        controller.doGet(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    void givenNoQuestName_whenProcessRequest_thenRedirectsToIndex() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(sessionUser);
        when(request.getParameter("questName")).thenReturn(null);

        controller.doGet(request, response);

        verify(response).sendRedirect("index.jsp");
    }

    @Test
    void givenWinStage_whenProcessRequest_thenStatUpdated() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(sessionUser);
        when(request.getParameter("questName")).thenReturn("quest1");
        when(request.getParameter("stage")).thenReturn("stage_win");
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        controller.doGet(request, response);

        verify(questService).updateQuestStatistics(any(), eq("quest1"), any());
        verify(dispatcher).forward(request, response);
    }

    @Test
    void givenLoseStage_whenProcessRequest_thenStatUpdated() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(sessionUser);
        when(request.getParameter("questName")).thenReturn("quest1");
        when(request.getParameter("stage")).thenReturn("stage_lose");
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        controller.doGet(request, response);

        verify(questService).updateQuestStatistics(any(), eq("quest1"), any());
        verify(dispatcher).forward(request, response);
    }

    @Test
    void givenValidRequest_whenProcessRequest_thenForwardToIndexJsp() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(sessionUser);
        when(request.getParameter("questName")).thenReturn("quest1");
        when(request.getParameter("stage")).thenReturn("2");
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        controller.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }
}
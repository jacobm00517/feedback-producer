package com.example.secureportal.servlet;

import com.example.secureportal.util.PasswordUtils;
import com.example.secureportal.security.RateLimiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginServletTest {

    @InjectMocks
    private LoginServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSuccessfulLogin() throws Exception {
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("admin123");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/success.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(session).setAttribute("username", "admin");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testFailedLogin() throws Exception {
        when(request.getRemoteAddr()).thenReturn("127.0.0.2");
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("wrong");
        when(request.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("error"), anyString());
        verify(dispatcher).forward(request, response);
    }
}

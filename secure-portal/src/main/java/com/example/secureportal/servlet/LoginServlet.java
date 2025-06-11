package com.example.secureportal.servlet;

import java.io.IOException;

import com.example.secureportal.security.RateLimiter;
import com.example.secureportal.util.PasswordUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/LoginServlet")
	public class LoginServlet extends HttpServlet {
	
	    private static final long serialVersionUID = 2156710946462422794L;
	private final String storedHash = PasswordUtils.hashPassword("admin123");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String ip = req.getRemoteAddr();
        if (RateLimiter.isBlocked(ip)) {
            resp.sendError(429, "Too many failed attempts. Try again later.");
            return;
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if ("admin".equals(username) && PasswordUtils.checkPassword(password, storedHash)) {
            RateLimiter.reset(ip);
            req.getSession().setAttribute("username", username);
            req.getRequestDispatcher("/success.jsp").forward(req, resp);
        } else {
            RateLimiter.recordFailure(ip);
            req.setAttribute("error", "Invalid username or password.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}

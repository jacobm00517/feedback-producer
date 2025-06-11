package com.example.secureportal.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 8975734688671642036L;

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Invalidate the session
        HttpSession session = req.getSession(false); // false = don't create if it doesn't exist
        if (session != null) {
            session.invalidate();
        }

        // Redirect to login page
        resp.sendRedirect("login.jsp");
    }
}

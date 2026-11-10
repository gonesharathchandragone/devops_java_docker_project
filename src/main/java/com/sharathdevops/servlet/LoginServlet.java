package com.sharathdevops.servlet;

import com.sharathdevops.dao.UserDao;
import com.sharathdevops.model.User;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s != null && s.getAttribute("user") != null) {
            resp.sendRedirect(req.getContextPath() + "/courses");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.isBlank() || password == null || password.isEmpty()) {
            fail(req, resp, "Please enter username and password.");
            return;
        }
        try {
            User user = userDao.authenticate(username.trim(), password);
            if (user == null) {
                fail(req, resp, "Invalid username or password.");
                return;
            }
            HttpSession old = req.getSession(false);
            if (old != null) old.invalidate();          // prevent session fixation
            req.getSession(true).setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/courses");
        } catch (SQLException e) {
            throw new ServletException("Database error during login", e);
        }
    }

    private void fail(HttpServletRequest req, HttpServletResponse resp, String msg)
            throws ServletException, IOException {
        req.setAttribute("error", msg);
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }
}

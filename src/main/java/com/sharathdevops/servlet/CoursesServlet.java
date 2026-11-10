package com.sharathdevops.servlet;

import com.sharathdevops.dao.CourseDao;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/courses")
public class CoursesServlet extends HttpServlet {
    private final CourseDao courseDao = new CourseDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("courses", courseDao.findAll());
        } catch (SQLException e) {
            throw new ServletException("Could not load courses", e);
        }
        req.getRequestDispatcher("/WEB-INF/views/courses.jsp").forward(req, resp);
    }
}

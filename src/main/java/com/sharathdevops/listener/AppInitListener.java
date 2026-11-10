package com.sharathdevops.listener;

import com.sharathdevops.util.DB;
import com.sharathdevops.util.PasswordUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/** Creates tables and seeds demo data on startup (works on H2 and MySQL). */
@WebListener
public class AppInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try (Connection c = DB.getConnection(); Statement st = c.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS users ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "username VARCHAR(50) NOT NULL UNIQUE, "
                    + "password_hash VARCHAR(255) NOT NULL, "
                    + "full_name VARCHAR(100) NOT NULL)");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS courses ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "title VARCHAR(100) NOT NULL, "
                    + "description VARCHAR(255) NOT NULL, "
                    + "duration VARCHAR(30) NOT NULL, "
                    + "fee INT NOT NULL)");

            if (isEmpty(st, "users")) {
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO users (username, password_hash, full_name) VALUES (?,?,?)")) {
                    seedUser(ps, "admin", "admin123", "Institute Admin");
                    seedUser(ps, "student", "student123", "Demo Student");
                }
            }
            if (isEmpty(st, "courses")) {
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO courses (title, description, duration, fee) VALUES (?,?,?,?)")) {
                    seedCourse(ps, "DevOps with AWS", "Linux, Git, Jenkins, Docker, Kubernetes, Terraform and AWS from scratch.", "3 Months", 25000);
                    seedCourse(ps, "Docker & Kubernetes", "Containerize apps and run them on Kubernetes with Helm and CI/CD.", "6 Weeks", 15000);
                    seedCourse(ps, "Jenkins CI/CD Pipelines", "Build declarative pipelines with Maven, SonarQube, Nexus and deployments.", "4 Weeks", 10000);
                    seedCourse(ps, "Terraform & Ansible", "Infrastructure as Code and configuration management on cloud.", "5 Weeks", 12000);
                    seedCourse(ps, "Linux & Shell Scripting", "Linux administration and Bash scripting for automation.", "3 Weeks", 6000);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Database initialisation failed", e);
        }
    }

    private boolean isEmpty(Statement st, String table) throws Exception {
        try (ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM " + table)) {
            rs.next();
            return rs.getInt(1) == 0;
        }
    }

    private void seedUser(PreparedStatement ps, String user, String pass, String name) throws Exception {
        ps.setString(1, user);
        ps.setString(2, PasswordUtil.hash(pass));
        ps.setString(3, name);
        ps.executeUpdate();
    }

    private void seedCourse(PreparedStatement ps, String t, String d, String dur, int fee) throws Exception {
        ps.setString(1, t);
        ps.setString(2, d);
        ps.setString(3, dur);
        ps.setInt(4, fee);
        ps.executeUpdate();
    }
}

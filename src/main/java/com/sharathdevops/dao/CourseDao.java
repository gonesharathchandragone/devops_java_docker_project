package com.sharathdevops.dao;

import com.sharathdevops.model.Course;
import com.sharathdevops.util.DB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {

    public List<Course> findAll() throws SQLException {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT id, title, description, duration, fee FROM courses ORDER BY id";
        try (Connection c = DB.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Course(rs.getInt("id"), rs.getString("title"),
                        rs.getString("description"), rs.getString("duration"), rs.getInt("fee")));
            }
        }
        return list;
    }
}

package com.sharathdevops.dao;

import com.sharathdevops.model.User;
import com.sharathdevops.util.DB;
import com.sharathdevops.util.PasswordUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    /** Returns the User if credentials are valid, otherwise null. */
    public User authenticate(String username, String password) throws SQLException {
        String sql = "SELECT id, username, password_hash, full_name FROM users WHERE username = ?";
        try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && PasswordUtil.verify(password, rs.getString("password_hash"))) {
                    return new User(rs.getInt("id"), rs.getString("username"), rs.getString("full_name"));
                }
            }
        }
        return null;
    }
}

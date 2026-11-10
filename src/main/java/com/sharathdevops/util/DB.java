package com.sharathdevops.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/** Simple JDBC connection helper. Env vars DB_URL, DB_USER, DB_PASSWORD override db.properties. */
public final class DB {
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = DB.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) PROPS.load(in);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private DB() {}

    private static String cfg(String envKey, String propKey) {
        String v = System.getenv(envKey);
        return (v != null && !v.isBlank()) ? v : PROPS.getProperty(propKey, "");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                cfg("DB_URL", "db.url"), cfg("DB_USER", "db.user"), cfg("DB_PASSWORD", "db.password"));
    }
}

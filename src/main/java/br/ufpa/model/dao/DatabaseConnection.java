package br.ufpa.model.dao;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    // Relative path to the project
    private static final String DB_PATH = "database/album.db";

    public static Connection getConnection() throws SQLException {
        // Creates the "database" folder if it does not exist
        try {
            Files.createDirectories(Paths.get("database")); // Creates the folder and necessary subfolders
        } catch (Exception e) {
            throw new SQLException("Failed to create 'database' folder", e);
        }
        return DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
    }

    public static void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Characters (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                description TEXT,
                powers TEXT,
                team TEXT,
                skills TEXT,
                image BLOB,
                video BLOB,
                universe TEXT,
                threatLevel INTEGER,
                type TEXT CHECK(type IN ('HERO', 'VILLAIN'))
            )
            """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

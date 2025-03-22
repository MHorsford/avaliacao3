package br.ufpa.model.dao;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/*
 * Classe responsável por estabelecer a conexão com o banco de dados e criar a tabela SQLite
 */
public class DatabaseConnection {

    private static final String DB_PATH = "database/album.db";

    // Método para estabelecer uma conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        // Cria a pasta 'database' se ela não existir
        try {
            Files.createDirectories(Paths.get("src/database")); // Cria a pasta 'database'
        } catch (Exception e) {
            throw new SQLException("Failed to create 'database' folder", e);
        }
        return DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
    }

    // Método para criar a tabela 'Characters' no banco de dados
    // Este método é chamado quando a aplicação é iniciada
    // Tipo BLOBS são usados para armazenar imagens e vídeos no SQLite
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

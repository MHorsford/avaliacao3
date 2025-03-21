package br.ufpa.model.dao;

import br.ufpa.model.SuperHero;
import br.ufpa.model.SuperVillain;
import br.ufpa.model.Character;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterDAOImpl implements CharacterDAO {
    
    @Override
    public void save(SuperHero hero) {
        String sql = """
            INSERT INTO characters 
            (name, description, powers, team, skills, image, video, universe, type) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'HERO')
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            fillCommonData(pstmt, hero);
            pstmt.setString(8, hero.getUniverse());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(SuperVillain villain) {
        String sql = """
            INSERT INTO characters 
            (name, description, powers, team, skills, image, video, threatLevel, type) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'VILLAIN')
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            fillCommonData(pstmt, villain);
            pstmt.setInt(8, villain.getThreatLevel());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SuperHero> getAllHeroes() {
        List<SuperHero> heroes = new ArrayList<>();
        String sql = "SELECT * FROM characters WHERE type = 'HERO'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                SuperHero hero = new SuperHero(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("powers"),
                        rs.getString("team"),
                        rs.getString("skills"),
                        rs.getBytes("image"),
                        rs.getBytes("video"),
                        rs.getString("universe")
                );
                hero.setId(rs.getInt("id"));
                heroes.add(hero);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return heroes;
    }

    @Override
    public List<SuperVillain> getAllVillains() {
        List<SuperVillain> villains = new ArrayList<>();
        String sql = "SELECT * FROM characters WHERE type = 'VILLAIN'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                SuperVillain villain = new SuperVillain(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("powers"),
                        rs.getString("team"),
                        rs.getString("skills"),
                        rs.getBytes("image"),
                        rs.getBytes("video"),
                        rs.getInt("threatLevel")
                );
                villain.setId(rs.getInt("id"));
                villains.add(villain);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return villains;
    }

    // CharacterDAOImpl.java
    @Override
    public List<Character> getAllCharacters() {
        List<Character> allCharacters = new ArrayList<>();
        allCharacters.addAll(getAllHeroes()); // Adiciona heróis
        allCharacters.addAll(getAllVillains()); // Adiciona vilões
        return allCharacters;
    }

    @Override
    public void update(SuperHero hero) {
        String sql = """
            UPDATE characters SET 
            name = ?, description = ?, powers = ?, team = ?, 
            skills = ?, image = ?, video = ?, universe = ? 
            WHERE id = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            fillCommonData(pstmt, hero);
            pstmt.setString(8, hero.getUniverse()); // Agora o índice está correto
            pstmt.setInt(9, hero.getId()); // Correto agora

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(SuperVillain villain) {
        String sql = """
        UPDATE characters SET 
        name = ?, description = ?, powers = ?, team = ?, 
        skills = ?, image = ?, video = ?, threatLevel = ? 
        WHERE id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            fillCommonData(pstmt, villain);
            pstmt.setInt(8, villain.getThreatLevel()); // Índice corrigido
            pstmt.setInt(9, villain.getId()); // Correto agora

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(int id) {
        String sql = "DELETE FROM characters WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to fill common data for SuperHero
    private void fillCommonData(PreparedStatement pstmt, SuperHero hero) throws SQLException {
        pstmt.setString(1, hero.getName());
        pstmt.setString(2, hero.getDescription());
        pstmt.setString(3, hero.getPowers());
        pstmt.setString(4, hero.getTeam());
        pstmt.setString(5, hero.getSkills());

        if (hero.getImage() != null) {
            pstmt.setBytes(6, hero.getImage());
        } else {
            pstmt.setNull(6, Types.BLOB);
        }

        if (hero.getVideo() != null) {
            pstmt.setBytes(7, hero.getVideo());
        } else {
            pstmt.setNull(7, Types.BLOB);
        }

        pstmt.setString(8, hero.getUniverse());
    }

    // Helper method to fill common data for SuperVillain
    private void fillCommonData(PreparedStatement pstmt, SuperVillain villain) throws SQLException {
        pstmt.setString(1, villain.getName());
        pstmt.setString(2, villain.getDescription());
        pstmt.setString(3, villain.getPowers());
        pstmt.setString(4, villain.getTeam());
        pstmt.setString(5, villain.getSkills());

        if (villain.getImage() != null) {
            pstmt.setBytes(6, villain.getImage());
        } else {
            pstmt.setNull(6, Types.BLOB);
        }

        if (villain.getVideo() != null) {
            pstmt.setBytes(7, villain.getVideo());
        } else {
            pstmt.setNull(7, Types.BLOB);
        }

        pstmt.setInt(8, villain.getThreatLevel());
    }

    public SuperVillain findByName(String name) {
        String sql = "SELECT * FROM characters WHERE name = ? AND type = 'VILLAIN'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                SuperVillain villain = new SuperVillain(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("powers"),
                        rs.getString("team"),
                        rs.getString("skills"),
                        rs.getBytes("image"),
                        rs.getBytes("video"),
                        rs.getInt("threatLevel")
                );
                villain.setId(rs.getInt("id")); // Carregar o ID do banco
                return villain;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se não encontrar
    }
}

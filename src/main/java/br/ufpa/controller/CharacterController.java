package br.ufpa.controller;

import br.ufpa.model.*;
import br.ufpa.model.dao.CharacterDAOImpl;
import br.ufpa.model.Character;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class CharacterController {
    private CharacterDAOImpl dao;

    public CharacterController() {
        this.dao = new CharacterDAOImpl();
    }

    public void saveCharacter(Map<String, String> data) throws IOException {
        String type = data.containsKey("universe") ? "HERO" : "VILLAIN";

        if (type.equals("HERO")) {
            SuperHero hero = new SuperHero(
                    data.get("name"),
                    data.get("description"),
                    data.get("powers"),
                    data.get("team"),
                    data.get("skills"),
                    data.get("imagePath"), // Caminho da imagem
                    data.get("videoPath"), // Caminho do vídeo
                    data.get("universe")
            );
            dao.save(hero);
        } else {
            SuperVillain villain = new SuperVillain(
                    data.get("name"),
                    data.get("description"),
                    data.get("powers"),
                    data.get("team"),
                    data.get("skills"),
                    data.get("imagePath"),
                    data.get("videoPath"),
                    Integer.parseInt(data.get("threatLevel"))
            );
            dao.save(villain);
        }
    }

    public void updateCharacter(Character character, Map<String, String> updatedData) throws IOException {
        // Atualiza campos comuns
        character.setName(updatedData.get("name"));
        character.setDescription(updatedData.get("description"));
        character.setPowers(updatedData.get("powers"));
        character.setTeam(updatedData.get("team"));
        character.setSkills(updatedData.get("skills"));

        // Atualiza campos específicos (Herói/Vilão)
        if (character instanceof SuperHero) {
            ((SuperHero) character).setUniverse(updatedData.get("specificField"));
        } else {
            ((SuperVillain) character).setThreatLevel(Integer.parseInt(updatedData.get("specificField")));
        }

        // Atualiza mídia (imagem/vídeo) se novos caminhos forem fornecidos
        if (!updatedData.get("imagePath").isEmpty()) {
            character.setImage(Files.readAllBytes(Paths.get(updatedData.get("imagePath"))));
        }
        if (!updatedData.get("videoPath").isEmpty()) {
            character.setVideo(Files.readAllBytes(Paths.get(updatedData.get("videoPath"))));
        }

        // Chama o DAO para persistir
        if (character instanceof SuperHero) {
            dao.update((SuperHero) character);
        } else {
            dao.update((SuperVillain) character);
        }
    }

        // Método para exclusão
    public void deleteCharacter(int id) {
        dao.delete(id); // Chama o DAO
    }
}
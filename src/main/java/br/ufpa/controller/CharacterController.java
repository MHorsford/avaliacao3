package br.ufpa.controller;

import br.ufpa.model.*;
import br.ufpa.model.dao.CharacterDAOImpl;
import br.ufpa.model.Character;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/*
 * Essa Classe intermedia as ações da View com o Model
 * 
 */

public class CharacterController {
    private CharacterDAOImpl dao;

    public CharacterController() {
        this.dao = new CharacterDAOImpl();
    }

    // Metodo para salvar um personagem
    public void saveCharacter(Map<String, String> data, String type) throws IOException {
        if (type.equals("HERO")) {
            SuperHero hero = new SuperHero(
                data.get("name"),
                data.get("description"),
                data.get("powers"),
                data.get("team"),
                data.get("skills"),
                data.get("imagePath"),
                data.get("videoPath"),
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

    // Método para atualizar um personagem
    public void updateCharacter(Character character, Map<String, String> updatedData) throws IOException {

        character.setName(updatedData.get("name"));
        character.setDescription(updatedData.get("description"));
        character.setPowers(updatedData.get("powers"));
        character.setTeam(updatedData.get("team"));
        character.setSkills(updatedData.get("skills"));


        if (character instanceof SuperHero) {
            ((SuperHero) character).setUniverse(updatedData.get("specificField"));
        } else {
            ((SuperVillain) character).setThreatLevel(Integer.parseInt(updatedData.get("specificField")));
        }

        if (!updatedData.get("imagePath").isEmpty()) {
            character.setImage(Files.readAllBytes(Paths.get(updatedData.get("imagePath"))));
        }
        if (!updatedData.get("videoPath").isEmpty()) {
            character.setVideo(Files.readAllBytes(Paths.get(updatedData.get("videoPath"))));
        }

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
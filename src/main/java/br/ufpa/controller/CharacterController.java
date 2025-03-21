package br.ufpa.controller;

import br.ufpa.model.*;
import br.ufpa.model.dao.CharacterDAOImpl;
import java.io.IOException;
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

        // Método para exclusão
    public void deleteCharacter(int id) {
        dao.delete(id); // Chama o DAO
    }
}
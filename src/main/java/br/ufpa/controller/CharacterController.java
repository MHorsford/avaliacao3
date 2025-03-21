package br.ufpa.controller;

import br.ufpa.model.Character;
import br.ufpa.model.SuperHero;
import br.ufpa.model.SuperVillain;
import br.ufpa.model.dao.CharacterDAOImpl;
import br.ufpa.view.Window;


public class CharacterController {
    private CharacterDAOImpl dao;
    private Window view;
    // CharacterController.java
    public void addHero(SuperHero hero) {
        dao.save(hero);
        view.refreshAlbum(); // Atualiza o álbum após salvar
    }
}

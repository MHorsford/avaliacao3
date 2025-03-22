package br.ufpa.model.dao;

import br.ufpa.model.SuperHero;
import br.ufpa.model.SuperVillain;
import br.ufpa.model.Character;
import java.util.List;
/*
 * Implementando o conseito de interfaces e sobrecargas de métodos
 */
public interface CharacterDAO {
    void save(SuperHero hero);
    void save(SuperVillain villain);
    List<Character> getAllCharacters();
    List<SuperHero> getAllHeroes();
    List<SuperVillain> getAllVillains();
    void update(SuperHero hero);
    void update(SuperVillain villain);
    void delete(int id);
}


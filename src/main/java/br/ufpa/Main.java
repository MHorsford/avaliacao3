package br.ufpa;
// model
//import br.ufpa.model.Character;
import br.ufpa.model.SuperHero;
import br.ufpa.model.SuperVillain;
//dao
import br.ufpa.model.dao.CharacterDAOImpl;
import br.ufpa.model.dao.DatabaseConnection;
// view
import br.ufpa.view.Window;
// java
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        DatabaseConnection.createTable();

        CharacterDAOImpl dao = new CharacterDAOImpl();
        Window view = new Window();

        SuperHero blade = new SuperHero(
            "Blade",
            "Um vampiro humano que luta contra vampiros.",
            "Super força, velocidade, reflexos, agilidade, resistência, regeneração.",
            "Solo",
            "Luta, combate corpo a corpo, uso de armas.",
            "images/Blade.png",
            "videos/Blade.mp4",
            "Marvel"
        );
        SuperVillain knull = new SuperVillain(
            "Knull",
            "Vião Sindionte da Marvel.",
            "Super força, velocidade, reflexos, agilidade, resistência, regeneração.",
            "Simbiontes",
            "Varios",
            "images/Knull.png",
            "videos/Knull.mp4",
            10
        );

        /*dao.save(blade);
        dao.save(knull);*/

        System.out.println(dao.getAllCharacters());
        view.refreshAlbum();
        view.setVisible(true);


    }
}
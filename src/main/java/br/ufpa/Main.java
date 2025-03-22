package br.ufpa;
// model
import br.ufpa.model.Character;
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
/* 
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
        SuperHero batman = new SuperHero(
            "Batman",
            "Um super-herói da DC Comics.",
            "DINHEIRO",
            "Armadura de batman",
            "Luta, combate corpo a corpo, uso de armas.",
            "images/Batman.png",
            "videos/Batman.mp4",
            "DC"
        );
        SuperHero capitaoAmerica = new SuperHero(
            "Capitão América",
            "Um super-herói da Marvel.",
            "Super força, velocidade, reflexos, agilidade, resistência, regeneração.",
            "Escudo",
            "Luta, combate corpo a corpo, uso de armas.",
            "images/Capitao_America.png",
            "videos/Capitao_America.mp4",
            "Marvel"
        );
        SuperVillain reiDoCrime = new SuperVillain(
            "Rei do Crime",
            "Vilão Urbano da Marvel.",
            "Força a cima de um humano normal",
            "Mafioso",
            "Luta, combate corpo a corpo, uso de armas.",
            "images/Rei_do_Crime.png",
            "videos/Rei_do_Crime.mp4",
           5 
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

        dao.save(blade);
        dao.save(knull);
        dao.save(batman);
        dao.save(capitaoAmerica);
        dao.save(reiDoCrime);

*/
        System.out.println(dao.getAllCharacters());
        view.refreshAlbum();
        view.setVisible(true);

    }
}
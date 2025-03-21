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
import javafx.embed.swing.JFXPanel;
// java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Main {
    static {
        // Inicializa o JavaFX
        System.setProperty("javafx.preloader", "none");
        new JFXPanel(); // Força carregamento do toolkit
    }

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

        SuperHero homem_aranha = new SuperHero(
                "Homem Aranha",
                "Amigão da Visinhança.",
                "Super força, velocidade, reflexos, agilidade, resistência...",
                "Solo",
                "Vai teia",
                "images/Homem_Aranha.png",
                "videos/Homem_Aranha.mp4",
                "Marvel"
        );

        /*dao.save(homem_aranha);
        dao.save(blade);
        dao.save(knull);*/

        System.out.println(dao.getAllCharacters());
        view.refreshAlbum();
        view.setVisible(true);


    }
}
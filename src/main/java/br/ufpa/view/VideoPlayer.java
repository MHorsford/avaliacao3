package br.ufpa.view;

import br.ufpa.model.Character;
import br.ufpa.model.dao.CharacterDAOImpl;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
/*
 * VideoPlayer é uma classe que permite exibir um vídeo
 */
public class VideoPlayer {
    private JDialog dialog;
    private MediaPlayer mediaPlayer;
    private Character character;
    private File tempVideoFile;

    //  Construtor para o objeto Character
    // Abstração é utilizada para permitir os dois tipos derivados de Character (SuperHero e SuperVillain)
    public VideoPlayer(Character character) {
        this.character = character;
        initialize();
    }

    // Inicializa o diálogo e configura os componentes
    private void initialize() {
        dialog = new JDialog();
        dialog.setTitle("Player de Vídeo - " + character.getName());
        dialog.setSize(800, 600);
        dialog.setLayout(new BorderLayout());

        // Painel JavaFX
        JFXPanel jfxPanel = new JFXPanel();
        dialog.add(jfxPanel, BorderLayout.CENTER);

        // Botões de controle
        JPanel controlPanel = new JPanel();
        JButton btnPlay = new JButton("Play");
        JButton btnPause = new JButton("Pause");
        JButton btnClose = new JButton("Fechar");

        controlPanel.add(btnPlay);
        controlPanel.add(btnPause);
        controlPanel.add(btnClose);
        dialog.add(controlPanel, BorderLayout.SOUTH);

        // Carrega o vídeo
        loadVideo(jfxPanel);

        // Listeners
        btnPlay.addActionListener(e -> play());
        btnPause.addActionListener(e -> pause());
        btnClose.addActionListener(e -> dialog.dispose());

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    // Carrega o vídeo
    private void loadVideo(JFXPanel jfxPanel) {
        Platform.runLater(() -> {
            try {
                // Cria arquivo temporário
                tempVideoFile = File.createTempFile("video-", ".mp4");
                Files.write(tempVideoFile.toPath(), character.getVideo());

                // Configura mídia
                Media media = new Media(tempVideoFile.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                MediaView mediaView = new MediaView(mediaPlayer);
                mediaView.setPreserveRatio(true);

                Scene scene = new Scene(new javafx.scene.Group(mediaView), 800, 500);
                jfxPanel.setScene(scene);

                // Remove arquivo ao fechar a janela
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        if (mediaPlayer != null) mediaPlayer.dispose();
                        if (tempVideoFile != null) tempVideoFile.delete();
                    }
                });

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(dialog, "Erro ao carregar vídeo!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Métodos para controlar o vídeo (play e pause)
    private void play() {
        if (mediaPlayer != null) mediaPlayer.play();
    }

    private void pause() {
        if (mediaPlayer != null) mediaPlayer.pause();
    }
}
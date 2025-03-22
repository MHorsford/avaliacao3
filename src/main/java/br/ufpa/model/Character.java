package br.ufpa.model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * Classe representa um objeto abstrato que pode dar origem a um Super-Herói ou Super-Vilão
 * Implementação para o conceito de abstração
 */
public abstract class Character {
    // Atributos
    private int id;
    private String name;
    private String description;
    private String powers;
    private String team;
    private String skills;
    private byte[] image; 
    private byte[] video; 

    // Constructor Padrão
    public Character(String name, String description, String powers,
                     String team, String skills, byte[] image,
                     byte[] video) {
        this.name = name;
        this.description = description;
        this.powers = powers;
        this.team = team;
        this.skills = skills;
        this.image = image;
        this.video = video;
    }

    // Construtor sobrecarregado para aceitar o caminho do arquivo
    public Character(String name, String description, String powers,
                     String team, String skills, String imagePath,
                     String videoPath) throws IOException {
        this(
                name,
                description,
                powers,
                team,
                skills,
                convertResourceToBytes(imagePath),
                convertResourceToBytes(videoPath)
        );
    }

    // Metodo para o path do arquivo ser convertido em bytes
    protected static byte[] convertFileToBytes(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("O caminho do arquivo não pode ser nulo ou vazio.");
        }

        if (!Files.exists(Paths.get(filePath))) {
            throw new IOException("Arquivo não encontrado: " + filePath);
        }
        return Files.readAllBytes(Paths.get(filePath));
    }


    // Metodo para converter um recurso em bytes
    protected static byte[] convertResourceToBytes(String resourcePath) throws IOException {
        try (InputStream is = Character.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IOException("Recurso não encontrado: " + resourcePath);
            }
            return is.readAllBytes();
        }
    }


    // Getters e Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPowers() { return powers; }
    public void setPowers(String powers) { this.powers = powers; }

    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public byte[] getVideo() { return video; }
    public void setVideo(byte[] video) { this.video = video; }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", powers='" + powers + '\'' +
                ", team='" + team + '\'' +
                ", skills='" + skills + '\'' +
                ", image=" + image +
                ", video=" + video +
                '}';
    }
}

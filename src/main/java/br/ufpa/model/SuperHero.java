package br.ufpa.model;

import java.io.IOException;
/*
 * Classe representa  um  objeto do tipo Super-Herói
 */
public class SuperHero extends Character {
    private String universe; // Exemplo: Marvel/DC

    // contrutor padrão
    public SuperHero(String name, String description, String powers,
                     String team, String skills, byte[] image,
                     byte[] video, String universe) {
        super(name, description, powers, team, skills, image, video);
        this.universe = universe;
    }
    // Construtor com caminhos de arquivo
    public SuperHero(String name, String description, String powers,
                     String team, String skills, String imagePath,
                     String videoPath, String universe) throws IOException {
        super(name, description, powers, team, skills, imagePath, videoPath);
        this.universe = universe;
    }

    // Getter e Setter especificos da classe
    public String getUniverse() { return universe; }
    public void setUniverse(String universe) { this.universe = universe; }

    @Override
    public String toString() {
        return "SuperHero{" + super.toString() + "universe='" + universe + '\'' + '}';
    }
}

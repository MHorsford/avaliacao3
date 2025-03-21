package br.ufpa.model;

import java.io.IOException;

public class SuperHero extends Character {
    private String universe; // Example: Marvel/DC

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

    // Specific Getter/Setter
    public String getUniverse() { return universe; }
    public void setUniverse(String universe) { this.universe = universe; }

    @Override
    public String toString() {
        return "SuperHero{" + super.toString() + "universe='" + universe + '\'' + '}';
    }
}

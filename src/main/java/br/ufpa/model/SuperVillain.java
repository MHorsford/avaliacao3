package br.ufpa.model;

import java.io.IOException;

public class SuperVillain extends Character {
    private int threatLevel; // Example: 1 to 10

    public SuperVillain(String name, String description, String powers,
                        String team, String skills, byte[] image,
                        byte[] video, int threatLevel) {
        super(name, description, powers, team, skills, image, video);
        this.threatLevel = threatLevel;
    }
    // Construtor com caminhos de arquivo
    public SuperVillain(String name, String description, String powers,
                        String team, String skills, String imagePath,
                        String videoPath, int threatLevel) throws IOException {
        super(name, description, powers, team, skills, imagePath, videoPath);
        this.threatLevel = threatLevel;
    }
    // Specific Getter/Setter
    public int getThreatLevel() { return threatLevel; }
    public void setThreatLevel(int threatLevel) { this.threatLevel = threatLevel; }

    @Override
    public String toString() {
        return "SuperVillain{" + super.toString() + "threatLevel=" + threatLevel + '}';
    }
}

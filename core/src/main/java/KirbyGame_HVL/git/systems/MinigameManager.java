package KirbyGame_HVL.git.systems;

import KirbyGame_HVL.git.entities.player.Kirby;

public class MinigameManager {

    // Atributos
    private static ScoreManager scoreManager;
    private Kirby kirby;
    private float posKirbyX;
    private float posKirbyY;
    private int savedKirbyScore = 0;

    // Constructor
    public MinigameManager(Kirby kirby) {
        this.kirby = kirby;
        if (kirby != null) {
            this.savedKirbyScore = kirby.getCurrentScore();
            this.posKirbyX = kirby.getBody().getPosition().x;
            this.posKirbyY = kirby.getBody().getPosition().y;
        }
    }

    // Setters y Getters
    public void setKirby(Kirby kirby) {
        this.kirby = kirby;
        if (kirby != null) {
            this.savedKirbyScore = kirby.getCurrentScore();
        }
    }

    public static void setScore(int minigameScore) {
        if (scoreManager != null) {
            int totalScore = scoreManager.getCurrentScore() + minigameScore;
            scoreManager.setCurrentScore(totalScore);
        }
    }

    public float getPosKirbyX() {
        return posKirbyX;
    }

    public float getPosKirbyY() {
        return posKirbyY;
    }

    public int getSavedKirbyScore() {
        return savedKirbyScore;
    }
}

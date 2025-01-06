package KirbyGame_HVL.git.systems;

import KirbyGame_HVL.git.entities.player.Kirby;

public class MinigameManager {

    private static ScoreManager scoreManager;
    private boolean isMinigameRunning = false;
    private Kirby kirby;
    private float posKirbyX;
    private float posKirbyY;
    private int savedKirbyScore = 0;

    public MinigameManager(Kirby kirby) {
        this.kirby = kirby;
        if (kirby != null) {
            this.savedKirbyScore = kirby.getCurrentScore();
            this.posKirbyX = kirby.getBody().getPosition().x;
            this.posKirbyY = kirby.getBody().getPosition().y;
        }
    }

    public void setKirby(Kirby kirby) {
        this.kirby = kirby;
        if (kirby != null) {
            this.savedKirbyScore = kirby.getCurrentScore();
        }
    }

    public void launchMinigame(final MinigameWindow minigame) {
        if (isMinigameRunning) {                //contrl
            return;
        }

        if (kirby != null) {
            savedKirbyScore = kirby.getCurrentScore();
        }

        minigame.create();
        isMinigameRunning = true;
    }

    public static void setScore(int minigameScore) {
        if (scoreManager != null) {
            int totalScore = scoreManager.getCurrentScore() + minigameScore;
            scoreManager.setCurrentScore(totalScore);
        }
    }
    public static int getScore() {
        return scoreManager.getCurrentScore();
    }

    public boolean isMinigameActive() {
        return isMinigameRunning;
    }

    public void closeMinigame() {
        if (isMinigameRunning) {
            isMinigameRunning = false;
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

package KirbyGame_HVL.git.systems;

import KirbyGame_HVL.git.screens.gameplay.GameScreen;

public class GameStateManager {
    private float savedCamX;
    private float savedCamY;
    private int savedScore;
    private boolean isPaused = false;

    public void saveGameState(GameScreen gameScreen) {
        isPaused = true;
        savedCamX = gameScreen.getKirby().getBody().getPosition().x;
        savedCamY = gameScreen.getKirby().getBody().getPosition().y;
        savedScore = gameScreen.getKirby().getCurrentScore();
    }

    public void resumeGameState(GameScreen gameScreen) {
        gameScreen.getKirby().getBody().setTransform(savedCamX, savedCamY, 0);
        gameScreen.getKirby().setCurrentScore(savedScore);
        isPaused = false;
    }

    public void updateScoreFromMinigame(GameScreen gameScreen, int minigameScore) {
        int newScore = savedScore + minigameScore;          //cambiar
        gameScreen.getKirby().setCurrentScore(newScore);
    }

    public boolean isPaused() {
        return isPaused;
    }
}


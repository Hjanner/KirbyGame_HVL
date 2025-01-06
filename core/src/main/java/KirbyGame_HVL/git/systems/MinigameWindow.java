package KirbyGame_HVL.git.systems;

import com.badlogic.gdx.Game;

public abstract class MinigameWindow extends Game {
    protected MinigameManager manager;

    public MinigameWindow(MinigameManager manager) {
        this.manager = manager;
    }

    public abstract void sendScore(int score);
}

package KirbyGame_HVL.git.systems;

import com.badlogic.gdx.Game;

public abstract class MinigameWindow extends Game {

    // Atributos
    protected MinigameManager manager;

    // Constructor
    public MinigameWindow(MinigameManager manager) {
        this.manager = manager;
    }

    public abstract void sendScore(int score);
}

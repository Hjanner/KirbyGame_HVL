package KirbyGame_HVL.git.systems;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public abstract class MinigameWindow extends Game implements Screen {

    // Atributos
    protected MinigameManager manager;

    // Constructor
    public MinigameWindow(MinigameManager manager) {
        this.manager = manager;
    }

    public abstract void sendScore(int score);
}

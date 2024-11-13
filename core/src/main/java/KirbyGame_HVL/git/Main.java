package KirbyGame_HVL.git;

import KirbyGame_HVL.git.screens.gameplay.PantallaScene;
import KirbyGame_HVL.git.screens.mainmenu.PantallaGui;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public SpriteBatch batch;
    public PantallaGui pantallaini;
    public PantallaScene escena;

    @Override
    public void create() {
        batch = new SpriteBatch();
        pantallaini = new PantallaGui(this);
        escena = new PantallaScene(this);

        setScreen(pantallaini);

    }

    public SpriteBatch getBatch () {
        return batch;
    }
}


package KirbyGame_HVL.git;

import KirbyGame_HVL.git.entities.net.MultiplayerGameScreen;
import KirbyGame_HVL.git.screens.gameplay.GameScreen;
import KirbyGame_HVL.git.screens.mainmenu.PantallaGui;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    private AssetManager manager;
    public SpriteBatch batch;
    public PantallaGui pantallaini;
    public GameScreen gameScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        manager = new AssetManager();
        loadAssets();

        // Iniciar con el menú principal
        pantallaini = new PantallaGui(this);
        setScreen(pantallaini);

    }

    public void startMultiplayerGame(String host, int puerto) {
        MultiplayerGameScreen screen = new MultiplayerGameScreen(this, host, puerto);
        setScreen(screen);
    }

    private void loadAssets() {
        manager.load("assets/art/sprites/kirbystay.png", Texture.class);
        manager.load("assets/art/sprites/kirbywalking.png", Texture.class);
        manager.load("assets/art/sprites/kirbydown.png", Texture.class);
        manager.load("assets/art/sprites/kirbyslide.png", Texture.class);
        manager.load("assets/art/sprites/kirbyrun.png", Texture.class);
        manager.load("assets/art/sprites/kirbyjump.png", Texture.class);
        manager.finishLoading();
    }

    public void startGame(Main main, String host, int port, boolean isHost) {
        if (gameScreen != null) {
            gameScreen.dispose(); // Limpiar la instancia anterior si existe
        }
        gameScreen = new GameScreen(this, host, port, isHost);
        setScreen(gameScreen);
    }

    public SpriteBatch getBatch () {
        return batch;
    }

    public AssetManager getManager () {
        return manager;
    }
}


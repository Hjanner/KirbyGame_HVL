package KirbyGame_HVL.git;

import KirbyGame_HVL.git.miniGames.culebrita.GamePanelCulebrita;
import KirbyGame_HVL.git.miniGames.viejita.GamePanelViejita;
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
    public GamePanelCulebrita gameCulebrita;
    public GamePanelViejita gameViejita;

    @Override
    public void create() {
        batch = new SpriteBatch();
        pantallaini = new PantallaGui(this);
        gameScreen = new GameScreen(this);
        gameCulebrita = new GamePanelCulebrita(this);
        gameViejita = new GamePanelViejita();
        manager = new AssetManager();
        manager.load("assets/art/sprites/kirbystay.png", Texture.class);
        manager.load("assets/art/sprites/kirbywalking.png", Texture.class);
        manager.load("assets/art/sprites/kirbydown.png", Texture.class);
        manager.load("assets/art/sprites/kirbyslide.png", Texture.class);
        manager.load("assets/art/sprites/kirbyrun.png", Texture.class);
        manager.load("assets/art/sprites/kirbyjump.png", Texture.class);
        manager.load("assets/art/sprites/kirbyfall.png", Texture.class);
        manager.load("assets/art/sprites/kirbyfall2.png", Texture.class);
        manager.load("assets/art/sprites/kirbyfly.png", Texture.class);
        manager.load("assets/art/sprites/kirbyflybegin.png", Texture.class);
        manager.load("assets/art/sprites/kirbyflyfall.png", Texture.class);
        manager.load("assets/art/sprites/kirbyflyfallend.png", Texture.class);
        manager.finishLoading();
        setScreen(pantallaini);

    }

    public SpriteBatch getBatch () {
        return batch;
    }

    public AssetManager getManager () {
        return manager;
    }
}


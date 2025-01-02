package KirbyGame_HVL.git;

import KirbyGame_HVL.git.entities.player.Kirby;
import KirbyGame_HVL.git.systems.GameStateManager;
import KirbyGame_HVL.git.systems.ScreenManager;
import KirbyGame_HVL.git.systems.rendering.miniGames.culebrita.GamePanelCulebrita;
import KirbyGame_HVL.git.systems.rendering.miniGames.viejita.GamePanelViejita;
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

    private ScreenManager screenManager;
    private GameStateManager gameStateManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        manager = new AssetManager();
        gameStateManager = new GameStateManager();
        screenManager = new ScreenManager(this);

        pantallaini = new PantallaGui(this);
        gameCulebrita = new GamePanelCulebrita(this);
        gameViejita = new GamePanelViejita();
        manager = new AssetManager();

        // Texturas del Kirby
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
        manager.load("assets/art/sprites/kirbyDamage.png", Texture.class);

        // Texturas del WaddleDee
        manager.load("assets/art/spritesWaddleDee/WaddleDeeWalk.png", Texture.class);
        manager.load("assets/art/spritesWaddleDee/WaddleDeeDie.png", Texture.class);

        // Texturas del BrontoBurt
        manager.load("assets/art/spritesBrontoBurt/BrontoBurtFly.png", Texture.class);
        manager.load("assets/art/spritesBrontoBurt/BrontoBurtDie.png", Texture.class);

        // Texturas de la plataforma movil
        manager.load("assets/art/tilesets/Platform.png", Texture.class);

        manager.finishLoading();
        setScreen(pantallaini);

    }

    public SpriteBatch getBatch () {
        return batch;
    }

    public AssetManager getManager () {
        return manager;
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }
}


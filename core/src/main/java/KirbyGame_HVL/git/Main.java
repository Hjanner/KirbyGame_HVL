package KirbyGame_HVL.git;

import KirbyGame_HVL.git.screens.gameplay.GameScreen;
import KirbyGame_HVL.git.screens.mainmenu.PantallaGui;
import KirbyGame_HVL.git.systems.MinigameManager;
import KirbyGame_HVL.git.systems.rendering.miniGames.culebrita.GamePanelCulebrita;
import KirbyGame_HVL.git.systems.rendering.miniGames.viejita.GamePanelViejita;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {

    private AssetManager assetManager;
    public SpriteBatch batch;
    public PantallaGui pantallaini;
    public GameScreen gameScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        gameScreen = new GameScreen(this, 100, 1030, 0, 1);
        pantallaini = new PantallaGui(this);

        // Cargar texturas usando assetManager en lugar de manager
        assetManager.load("assets/art/sprites/kirbystay.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbywalking.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbydown.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbyslide.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbyrun.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbyjump.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbyfall.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbyfall2.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbyfly.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbyflybegin.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbyflyfall.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbyflyfallend.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbyDamage.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbyAbsorb.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbyAbsorbStay.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbyAbsorbWalk.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbyAbsorbJump.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbyAbsorbFall.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbyAbsorbFall2.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbyAbsorbDown.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbyAbsorbDamage.png", Texture.class);
        assetManager.load("assets/art/sprites/kirbyAbsorbSpit.png", Texture.class);

        // Texturas del WaddleDee
        assetManager.load("assets/art/spritesWaddleDee/WaddleDeeWalk.png", Texture.class);
        assetManager.load("assets/art/spritesWaddleDee/WaddleDeeDie.png", Texture.class);

        // Texturas del BrontoBurt
        assetManager.load("assets/art/spritesBrontoBurt/BrontoBurtFly.png", Texture.class);
        assetManager.load("assets/art/spritesBrontoBurt/BrontoBurtDie.png", Texture.class);

        // Texturas de la plataforma movil
        assetManager.load("assets/art/tilesets/Platform.png", Texture.class);

        assetManager.finishLoading();
        setScreen(pantallaini);
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public AssetManager getManager() {
        return assetManager;
    }
}

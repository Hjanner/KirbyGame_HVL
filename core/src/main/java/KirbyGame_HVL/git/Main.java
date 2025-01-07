package KirbyGame_HVL.git;

import KirbyGame_HVL.git.screens.gameplay.GameScreen;
import KirbyGame_HVL.git.screens.mainmenu.AboutScreen;
import KirbyGame_HVL.git.screens.mainmenu.HelpScreen;
import KirbyGame_HVL.git.screens.mainmenu.PantallaGui;
import KirbyGame_HVL.git.screens.minigames.culebrita.GamePanelCulebrita;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    private AssetManager assetManager;
    public SpriteBatch batch;
    public PantallaGui pantallaini;
    public GameScreen gameScreen;
    public HelpScreen helpScreen;
    public AboutScreen aboutScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        gameScreen = new GameScreen(this, 2600, 1350, 0, 1);
        pantallaini = new PantallaGui(this);
        helpScreen = new HelpScreen(this);
        aboutScreen = new AboutScreen(this);

        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbystay.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbywalking.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbydown.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbydash.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbyrun.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbyjump.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbyfall.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbyfall2.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbyfly.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbyflybegin.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbyflyfall.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbyflyfallend.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbyDamage.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbyAbsorb.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbyAbsorbStay.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbyAbsorbWalk.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbyAbsorbJump.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbyAbsorbFall.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbyAbsorbFall2.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbyAbsorbDown.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbyAbsorbDamage.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/kirbyAbsorbSpit.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/KirbyDamageFire.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesNormalKirby/KirbyAbsorbDamageFire.png", Texture.class);

        // Texturas del Fire Kirby
        assetManager.load("assets/art/sprites/SpritesFireKirby/FireKirbyStay.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesFireKirby/FireKirbyDown.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesFireKirby/FireKirbyWalk.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesFireKirby/FireKirbyRun.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesFireKirby/FireKirbyDash.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesFireKirby/FireKirbyJump.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesFireKirby/FireKirbyFall.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesFireKirby/FireKirbyFall2.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesFireKirby/FireKirbyFlyBegin.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesFireKirby/FireKirbyFly.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesFireKirby/FireKirbyFlyFall.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesFireKirby/FireKirbyFlyFallEnd.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesFireKirby/FireKirbyAttack.png", Texture.class);

        // Beam Kirby
        assetManager.load("assets/art/sprites/SpritesBeamKirby/BeamKirbyStay.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesBeamKirby/BeamKirbyDown.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesBeamKirby/BeamKirbyWalk.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesBeamKirby/BeamKirbyDash.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesBeamKirby/BeamKirbyRun.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesBeamKirby/BeamKirbyJump.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesBeamKirby/BeamKirbyFall.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesBeamKirby/BeamKirbyFall2.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesBeamKirby/BeamKirbyFlyBegin.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesBeamKirby/BeamKirbyFly.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesBeamKirby/BeamKirbyFlyFall.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesBeamKirby/BeamKirbyFlyFallEnd.png", Texture.class);
        assetManager.load("assets/art/sprites/SpritesBeamKirby/BeamKirbyAttack.png", Texture.class);

        // Texturas del WaddleDee
        assetManager.load("assets/art/sprites/spritesWaddleDee/WaddleDeeWalk.png", Texture.class);
        assetManager.load("assets/art/sprites/spritesWaddleDee/WaddleDeeDie.png", Texture.class);

        // Texturas del BrontoBurt
        assetManager.load("assets/art/sprites/spritesBrontoBurt/BrontoBurtFly.png", Texture.class);
        assetManager.load("assets/art/sprites/spritesBrontoBurt/BrontoBurtDie.png", Texture.class);

        // Texturas del HotHead
        assetManager.load("assets/art/sprites/spritesHotHead/HotHeadWalk.png", Texture.class);
        assetManager.load("assets/art/sprites/spritesHotHead/HotHeadDie.png", Texture.class);
        assetManager.load("assets/art/sprites/spritesHotHead/HotHeadDie2.png", Texture.class);
        assetManager.load("assets/art/sprites/spritesHotHead/HotHeadAttack.png", Texture.class);

        // Texturas del WaddleDoo
        assetManager.load("assets/art/sprites/spritesWaddleDoo/WaddleDooWalk.png", Texture.class);
        assetManager.load("assets/art/sprites/spritesWaddleDoo/WaddleDooDie.png", Texture.class);
        assetManager.load("assets/art/sprites/spritesWaddleDoo/WaddleDooAttack.png", Texture.class);

        // Texturas de la plataforma movil
        assetManager.load("assets/art/tilesets/Platform.png", Texture.class);

        // Texturas de la llave
        assetManager.load("assets/art/sprites/spritesItems/Key.png", Texture.class);

        // Texturas de las puertas de minijuegos
        assetManager.load("assets/art/sprites/spritesItems/Door1.png", Texture.class);
        assetManager.load("assets/art/sprites/spritesItems/Door2.png", Texture.class);
        setScreen(pantallaini);
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public AssetManager getManager() {
        return assetManager;
    }
}

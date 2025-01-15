package KirbyGame_HVL.git.screens.mainmenu;

import KirbyGame_HVL.git.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public abstract class Pantalla implements Screen {

    // Atributos
    protected Main main;
    protected Stage uiStage;
    protected Dialog controlsDialog;
    protected ImageButton helpButton;
    protected Skin skin;
    protected InputMultiplexer inputMultiplexer;

    // Constructor
    public Pantalla(Main main) {
        this.main = main;
        this.uiStage = new Stage();
        this.skin = new Skin(Gdx.files.internal("assets/ui/skin/quantum-horizon-ui.json"));
        this.inputMultiplexer = new InputMultiplexer(uiStage);
        setupHelpButton();
    }

    protected void setupHelpButton() {
        Texture helpTexture = new Texture(Gdx.files.internal("assets/ui/help_icon.png"));
        ImageButton.ImageButtonStyle helpStyle = new ImageButton.ImageButtonStyle();
        helpStyle.imageUp = new TextureRegionDrawable(new TextureRegion(helpTexture));

        helpButton = new ImageButton(helpStyle);
        helpButton.setPosition(Gdx.graphics.getWidth() - 60, Gdx.graphics.getHeight() - 60);
        helpButton.setSize(30, 30);

        // controles
        controlsDialog = new Dialog("\n   Controles del Juego", skin) {
            public void result(Object obj) {
                if (obj.equals(true)) hide();
            }
        };
        Label titleLabel = controlsDialog.getTitleLabel();
        titleLabel.setFontScale(1.6f);
        controlsDialog.text("\n\nMovimientos Basicos:\n\n" +
            "- Flechas izquierda/derecha + X: Correr\n" +
            "- Flechas izquierda/derecha: Mover\n" +
            "- Flecha abajo: Agacharse\n" +
            "- Flecha arriba: Saltar\n" +
            "- Flecha abajo + direccion: Dash\n\n" +
            "Habilidades:\n\n" +
            "- Z: Absorber/Lanzar ataque\n" +
            "- Flecha abajo (con enemigo): Poder\n" +
            "- Flecha arriba + C: Volar\n" +
            "- Z (en el aire): Lanzar nube de ataque\n\n\n");
        controlsDialog.button("Cerrar", true).pad(40);
        controlsDialog.pad(90);

        helpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controlsDialog.show(uiStage);
            }
        });

        uiStage.addActor(helpButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        uiStage.act(delta);
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        uiStage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        uiStage.dispose();
        skin.dispose();
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}
}

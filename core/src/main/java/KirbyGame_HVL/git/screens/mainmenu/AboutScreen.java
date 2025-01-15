package KirbyGame_HVL.git.screens.mainmenu;

import KirbyGame_HVL.git.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class AboutScreen extends Pantalla {

    // Atributos
    private static final Color LIGHT_PINK = new Color(1, 0.8f, 0.9f, 1);

    private Stage stage;
    private Skin skin;
    private Table mainTable;
    private TextButton backButton, informationButton;
    private Dialog informationDialog;
    private Texture textureBackGround;
    private TextureRegion textureRegionBackGround;
    private Sprite spriteBackGround;
    private SpriteBatch batch;
    private Sound soundClick;

    // Constructor
    public AboutScreen(Main main) {

        super(main);
        batch = main.getBatch();
        soundClick = Gdx.audio.newSound(Gdx.files.internal("assets/audio/music/clicky-mouse-click-182496.mp3"));
    }

    @Override
    public void show() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("assets/ui/skin/quantum-horizon-ui.json"));
        textureBackGround = new Texture("assets/art/backgrounds/Kirby_BackGround2.jpg");
        textureRegionBackGround = new TextureRegion(textureBackGround, 1460, 821);
        spriteBackGround = new Sprite(textureRegionBackGround);
        mainTable = new Table();
        mainTable.setFillParent(true);

        Label.LabelStyle titleStyle = new Label.LabelStyle(skin.get("default", Label.LabelStyle.class));
        titleStyle.fontColor = Color.GREEN;

        Label titleLabel = new Label("Acerca del Juego", titleStyle);
        titleLabel.setFontScale(2.5f);

        informationButton = new TextButton("Ver Informacion",skin);
        backButton = new TextButton("Volver", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundClick.play();
                main.setScreen(main.pantallaini);
            }
        });

        informationButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                soundClick.play();
                informationDialog.show(stage);
            }
        });

        mainTable.add(titleLabel).pad(40).row();
        mainTable.add(informationButton).width(300).height(70).pad(25).row();
        mainTable.add(backButton).width(200).height(60).pad(25);

        mainTable.addAction(Actions.sequence(Actions.fadeOut(0.01f), Actions.fadeIn(2)));
        stage.addActor(mainTable);
        Gdx.input.setInputProcessor(stage);

        createDialog();
    }

    // Creamos los dialogos
    private void createDialog() {

        informationDialog = new Dialog("    Informacion del Proyecto", skin) {
            public void result(Object obj) {
                if (obj.equals(true)) hide();
            }
        };

        Label titleLabel = informationDialog.getTitleLabel();
        titleLabel.setFontScale(1.3f);

        informationDialog.text("KirbyGame HVL\n\n" +
            "Version: 1.0.0\n\n" +
            "Desarrolladores:\n" +
            "- Hanner Hernández\n" +
            "- Alejandro Vielma\n\n" +
            "Tecnologías Utilizadas:\n" +
            "- Lenguaje: Java\n" +
            "- Framework: LibGDX\n" +
            "- Physics: Box2D\n" +
            "- UI: Scene2D\n\n" +
            "© 2025 Todos los derechos reservados\n\n\n");

        informationDialog.button("Cerrar", true).pad(40);
        informationDialog.pad(90);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(LIGHT_PINK.r, LIGHT_PINK.g, LIGHT_PINK.b, LIGHT_PINK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        spriteBackGround.draw(batch);
        batch.end();

        stage.act(delta);
        stage.draw();
    }
}

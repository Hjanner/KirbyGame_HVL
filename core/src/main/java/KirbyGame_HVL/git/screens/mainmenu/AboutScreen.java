package KirbyGame_HVL.git.screens.mainmenu;

import KirbyGame_HVL.git.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class AboutScreen extends Pantalla {
    private static final Color LIGHT_PINK = new Color(1, 0.8f, 0.9f, 1);
    private static final Color DARK_PINK = new Color(0.8f, 0.4f, 0.6f, 1);

    private Stage stage;
    private Skin skin;
    private Table mainTable;
    private TextButton backButton;

    public AboutScreen(Main main) {
        super(main);
    }

    @Override
    public void show() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("assets/ui/skin/uiskin.json"));
        mainTable = new Table();
        mainTable.setFillParent(true);

        Label.LabelStyle titleStyle = new Label.LabelStyle(skin.get("default", Label.LabelStyle.class));
        titleStyle.fontColor = DARK_PINK;

        Label titleLabel = new Label("Acerca del Juego", titleStyle);
        titleLabel.setFontScale(2.0f);

        String aboutText = "Kirby Game HVL\n\n" +
            "Version: 1.0.0\n\n" +
            "Desarrolladores:\n" +
            "- Hanner Hernández\n" +
            "- Alejandro Vielma\n\n" +
            "Tecnologías Utilizadas:\n" +
            "- Lenguaje: Java\n" +
            "- Framework: LibGDX\n" +
            "- Physics: Box2D\n" +
            "- UI: Scene2D\n\n" +
            "© 2025 Todos los derechos reservados";

        Label contentLabel = new Label(aboutText, skin);
        contentLabel.setWrap(true);

        backButton = new TextButton("Volver", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                main.setScreen(main.pantallaini);
            }
        });

        mainTable.add(titleLabel).pad(20).row();
        mainTable.add(contentLabel).width(600).pad(20).row();
        mainTable.add(backButton).width(200).height(60).pad(20);

        stage.addActor(mainTable);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(LIGHT_PINK.r, LIGHT_PINK.g, LIGHT_PINK.b, LIGHT_PINK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }
}

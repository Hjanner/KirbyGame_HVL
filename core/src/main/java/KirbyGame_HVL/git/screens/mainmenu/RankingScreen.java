package KirbyGame_HVL.git.screens.mainmenu;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.player.Kirby;
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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RankingScreen extends Pantalla {

    private Stage stage;
    private Skin skin;
    private Table mainTable;
    private Dialog rankingDialog;
    private TextButton rankingButton, backButton;
    private Texture textureBackGround;
    private TextureRegion textureRegionBackGround;
    private Sprite spriteBackGround;
    private SpriteBatch batch;
    private Sound soundClick;
    private static final Color LIGHT_PINK = new Color(1, 0.8f, 0.9f, 1);

    public RankingScreen(Main main) {
        super(main);
        batch = main.getBatch();
        soundClick = Gdx.audio.newSound(Gdx.files.internal("assets/audio/music/clicky-mouse-click-182496.mp3"));
    }

    @Override
    public void show() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("assets/ui/skin/quantum-horizon-ui.json"));
        textureBackGround = new Texture("assets/art/backgrounds/Kirby_BackGround.jpg");
        textureRegionBackGround = new TextureRegion(textureBackGround,1920 ,1080);
        spriteBackGround = new Sprite(textureRegionBackGround);
        mainTable = new Table();
        mainTable.setFillParent(true);

        Label.LabelStyle titleStyle = new Label.LabelStyle(skin.get("default", Label.LabelStyle.class));
        titleStyle.fontColor = Color.GREEN;

        Label titleLabel = new Label("RANKING", titleStyle);
        titleLabel.setFontScale(2.5f);

        rankingButton = new TextButton("Ver Ranking", skin);
        backButton = new TextButton("Volver", skin);

        mainTable.add(titleLabel).pad(20).row();
        mainTable.add(rankingButton).width(300).height(80).pad(30).row();
        mainTable.add(backButton).width(300).height(80).pad(30).row();

        mainTable.addAction(Actions.sequence(Actions.fadeOut(0.01f), Actions.fadeIn(3)));
        stage.addActor(mainTable);

        rankingDialog = new Dialog("\n  Ranking", skin) {
            public void result(Object obj) {
                if (obj.equals(true)) hide();
            }
        };

        Label titleLabel3 = rankingDialog.getTitleLabel();
        titleLabel3.setFontScale(1.6f);

        rankingDialog.text(manejadorRanking());
        rankingDialog.button("Cerrar", true).pad(20);
        rankingDialog.pad(120);

        rankingButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                soundClick.play();
                rankingDialog.show(stage);

            }
        });
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                soundClick.play();
                main.setScreen(main.pantallaini);
            }
        });

        Gdx.input.setInputProcessor(stage);

    }

    private String manejadorRanking() {
        return "";
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(LIGHT_PINK.r, LIGHT_PINK.g, LIGHT_PINK.b, LIGHT_PINK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        spriteBackGround.draw(batch);
        batch.end();

        stage.draw();
        stage.act(delta);

    }


}

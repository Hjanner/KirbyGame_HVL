package KirbyGame_HVL.git.screens.gameover;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.player.Kirby;
import KirbyGame_HVL.git.screens.mainmenu.Pantalla;
import KirbyGame_HVL.git.systems.MusicManager;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GameOverScreen extends Pantalla {

    private Stage stage;
    private Skin skin;
    private Table mainTable;
    private Dialog scoreDialog;
    private TextButton scoreButton, backButton;
    private Texture textureBackGround;
    private TextureRegion textureRegionBackGround;
    private Sprite spriteBackGround;
    private SpriteBatch batch;
    private Sound soundWin, soundClick;
    private Kirby kirby;
    private static final Color LIGHT_PINK = new Color(1, 0.8f, 0.9f, 1);

    public GameOverScreen(Main main, Kirby kirby) {
        super(main);
        this.kirby = kirby;
        batch = main.getBatch();
        soundWin = Gdx.audio.newSound(Gdx.files.internal("assets/audio/music/kirby-victory-dance.mp3"));
        soundClick = Gdx.audio.newSound(Gdx.files.internal("assets/audio/music/clicky-mouse-click-182496.mp3"));
    }

    @Override
    public void show() {
        soundWin.play();
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("assets/ui/skin/quantum-horizon-ui.json"));
        textureBackGround = new Texture("assets/art/backgrounds/Kirby_BackGround4.jpg");
        textureRegionBackGround = new TextureRegion(textureBackGround,1024 ,550);
        spriteBackGround = new Sprite(textureRegionBackGround);
        spriteBackGround.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mainTable = new Table();
        mainTable.setFillParent(true);

        Label.LabelStyle titleStyle = new Label.LabelStyle(skin.get("default", Label.LabelStyle.class));
        titleStyle.fontColor = Color.GREEN;

        Label titleLabel = new Label("FELICIDADES POR TERMINAR EL JUEGO\n\nESPERAMOS QUE LE HAYA GUSTADO!!!", titleStyle);
        titleLabel.setFontScale(2.5f);

        scoreButton = new TextButton("Ver Puntuacion", skin);
        backButton = new TextButton("Volver al Menu", skin);

        mainTable.add(titleLabel).pad(20).row();
        mainTable.add(scoreButton).width(300).height(80).pad(30).row();
        mainTable.add(backButton).width(300).height(80).pad(30).row();

        mainTable.addAction(Actions.sequence(Actions.fadeOut(0.01f), Actions.fadeIn(3)));
        stage.addActor(mainTable);

        scoreDialog = new Dialog("\n Puntuacion Obtenida", skin) {
            public void result(Object obj) {
                if (obj.equals(true)) hide();
            }
        };

        Label titleLabel3 = scoreDialog.getTitleLabel();
        titleLabel3.setFontScale(1.6f);

        // Obtener la fecha y hora actual
        LocalDateTime ahora = LocalDateTime.now();

        // Obtener la fecha
        LocalDate fecha = ahora.toLocalDate();

        // Obtener la hora
        LocalTime hora = ahora.toLocalTime();

        // Formatear la fecha y hora
        DateTimeFormatter formateadorFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formateadorHora = DateTimeFormatter.ofPattern("HH:mm");
        String fechaFormateada = fecha.format(formateadorFecha);
        String horaFormateada = hora.format(formateadorHora);

        scoreDialog.text("FECHA: " + fechaFormateada + "\n\nHORA: " +
            horaFormateada + "\n\nJUGADOR: " + kirby.getName() +
            "\n\nPUNTUACION: " + kirby.getCurrentScore() + " PUNTOS\n\n\n");


        scoreDialog.button("Cerrar", true).pad(20);
        scoreDialog.pad(90);

        scoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                soundClick.play();
                scoreDialog.show(stage);
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                soundClick.play();
                try (FileWriter fw = new FileWriter("Partidas.txt", true);
                     BufferedWriter bw = new BufferedWriter(fw)) {
                    bw.write(fechaFormateada +"|" + horaFormateada+"|" + kirby.getName() + "|" + kirby.getCurrentScore() + "|" + "\n");
                } catch (IOException e) {
                    System.out.println("Error al escribir en el archivo: " + e.getMessage());
                }
                main.setScreen(main.pantallaini);
            }
        });
        Gdx.input.setInputProcessor(stage);
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

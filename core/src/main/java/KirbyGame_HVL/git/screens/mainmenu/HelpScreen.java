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

public class HelpScreen extends Pantalla {

    // Atributos
    private static final Color LIGHT_PINK = new Color(1, 0.8f, 0.9f, 1);

    private Stage stage;
    private Skin skin;
    private Table mainTable;
    private Dialog controlsDialog, levelsDialog, scoreDialog;
    private TextButton backButton, controlsButton, levelsButton, scoreButton;
    private Texture textureBackGround;
    private TextureRegion textureRegionBackGround;
    private Sprite spriteBackGround;
    private SpriteBatch batch;
    private Sound soundClick;

    // Constructor
    public HelpScreen(Main main) {

        super(main);
        batch = main.getBatch();
        soundClick = Gdx.audio.newSound(Gdx.files.internal("assets/audio/music/clicky-mouse-click-182496.mp3"));
    }

    @Override
    public void show() {
        stage = new Stage();
        textureBackGround = new Texture("assets/art/backgrounds/Kirby_BackGround2.jpg");
        textureRegionBackGround = new TextureRegion(textureBackGround, 1460, 821);
        spriteBackGround = new Sprite(textureRegionBackGround);
        skin = new Skin(Gdx.files.internal("assets/ui/skin/quantum-horizon-ui.json"));
        setupMainMenu();
        createDialogs();
        Gdx.input.setInputProcessor(stage);
    }

    // Creamos las tablas y titulo de la pantalla
    private void setupMainMenu() {
        mainTable = new Table();
        mainTable.setFillParent(true);

        Label.LabelStyle titleStyle = new Label.LabelStyle(skin.get("default", Label.LabelStyle.class));
        titleStyle.fontColor = Color.GREEN;

        Label titleLabel = new Label("AYUDA", titleStyle);
        titleLabel.setFontScale(4.0f);

        controlsButton = new TextButton("Controles", skin);
        levelsButton = new TextButton("Niveles", skin);
        scoreButton = new TextButton("Sistema de Puntuacion", skin);
        backButton = new TextButton("Volver", skin);

        mainTable.add(titleLabel).pad(20).row();
        mainTable.add(controlsButton).width(300).height(80).pad(30).row();
        mainTable.add(levelsButton).width(300).height(80).pad(30).row();
        mainTable.add(scoreButton).width(340).height(80).pad(30).row();
        mainTable.add(backButton).width(200).height(80).pad(30);

        setupButtonListeners();
        mainTable.addAction(Actions.sequence(Actions.fadeOut(0.01f), Actions.fadeIn(2)));
        stage.addActor(mainTable);
    }

    // Creamos los dialogos
    private void createDialogs() {
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

        // niveles
        levelsDialog = new Dialog("\n\tNiveles del Juego", skin) {
            public void result(Object obj) {
                if (obj.equals(true)) hide();
            }
        };

        Label titleLabel2 = levelsDialog.getTitleLabel();
        titleLabel2.setFontScale(1.6f);
        levelsDialog.text("\nNivel 1 - Busqueda de Llaves:\n\n" +
            "- Encuentra 5 llaves para abrir la puerta\n\n" +
            "Nivel 2 - Eliminacion de Enemigos:\n\n" +
            "- Derrota una cantidad especifica de enemigos\n\n" +
            "Obstaculos:\n\n" +
            "- Plataformas moviles\n" +
            "- Agujeros y precipicios\n" +
            "- Espinas\n" +
            "- Puertas y mecanismos\n\n" +
            "Cada nivel incluye un minijuego ODS\n\n\n");
        levelsDialog.button("Cerrar", true).pad(20);
        levelsDialog.pad(90);

        // score
        scoreDialog = new Dialog("\n   Sistema de Puntuacion", skin) {
            public void result(Object obj) {
                if (obj.equals(true)) hide();
            }
        };

        Label titleLabel3 = scoreDialog.getTitleLabel();
        titleLabel3.setFontScale(1.6f);
        scoreDialog.text("\nPuntos por Enemigo:\n\n" +
            "- Waddle Dee: 20 puntos\n" +
            "- Bronto Burt: 30 puntos\n" +
            "- Waddle Doo: 40 puntos\n" +
            "- Hot Head: 50 puntos\n\n" +
            "Bonus:\n\n" +
            "- Obtener llave: 50 puntos\n" +
            "- Abrir puerta: 100 puntos\n\n" +
            "Penalizaciones:\n\n" +
            "- Golpe por enemigo: -10 puntos\n" +
            "- Golpe por espinas: -10 puntos\n" +
            "- Caida al vacio: -20 puntos\n"+
            "- Golpe por ataque de enemigo: -30 puntos\n\n\n");
        scoreDialog.button("Cerrar", true).pad(20);
        scoreDialog.pad(90);
    }

    // Registra los eventos del mouse si se presiono un boton
    private void setupButtonListeners() {
        controlsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundClick.play();
                controlsDialog.show(stage);
            }
        });

        levelsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundClick.play();
                levelsDialog.show(stage);
            }
        });

        scoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundClick.play();
                scoreDialog.show(stage);
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundClick.play();
                main.setScreen(main.pantallaini);
            }
        });
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

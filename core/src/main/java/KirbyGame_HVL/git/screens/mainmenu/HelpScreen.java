package KirbyGame_HVL.git.screens.mainmenu;

import KirbyGame_HVL.git.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class HelpScreen extends Pantalla {
    private static final Color LIGHT_PINK = new Color(1, 0.8f, 0.9f, 1);
    private static final Color DARK_PINK = new Color(0.8f, 0.4f, 0.6f, 1);

    private Stage stage;
    private Skin skin;
    private Table mainTable;
    private Dialog controlsDialog, levelsDialog, scoreDialog;
    private TextButton backButton, controlsButton, levelsButton, scoreButton;

    public HelpScreen(Main main) {
        super(main);
    }

    @Override
    public void show() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("assets/ui/skin/uiskin.json"));
        setupMainMenu();
        createDialogs();
        Gdx.input.setInputProcessor(stage);
    }

    private void setupMainMenu() {
        mainTable = new Table();
        mainTable.setFillParent(true);

        Label.LabelStyle titleStyle = new Label.LabelStyle(skin.get("default", Label.LabelStyle.class));
        titleStyle.fontColor = DARK_PINK;

        Label titleLabel = new Label("AYUDA", titleStyle);
        titleLabel.setFontScale(2.0f);

        controlsButton = new TextButton("Controles", skin);
        levelsButton = new TextButton("Niveles", skin);
        scoreButton = new TextButton("Sistema de Puntuacion", skin);
        backButton = new TextButton("Volver", skin);

        mainTable.add(titleLabel).pad(20).row();
        mainTable.add(controlsButton).width(300).height(60).pad(10).row();
        mainTable.add(levelsButton).width(300).height(60).pad(10).row();
        mainTable.add(scoreButton).width(300).height(60).pad(10).row();
        mainTable.add(backButton).width(200).height(60).pad(20);

        setupButtonListeners();
        stage.addActor(mainTable);
    }

    private void createDialogs() {
        // controles
        controlsDialog = new Dialog("Controles del Juego", skin) {
            public void result(Object obj) {
                if (obj.equals(true)) hide();
            }
        };
        controlsDialog.text("\nMovimiento Basico:\n" +
            "- Flechas izquierda/derecha: Mover\n" +
            "- Flecha abajo + dirección: Realizar dash\n\n" +
            "Habilidades:\n" +
            "- Z: \tAbsorber/Lanzar ataque\n" +
            "- Flecha abajo (con enemigo): \tAdquirir poder\n" +
            "- Flecha arriba + C: \tVolar\n" +
            "- Z (en el aire): \tLanzar nube de ataque");
        controlsDialog.button("Cerrar", true).pad(20);

        // niveles
        levelsDialog = new Dialog("Niveles del Juego", skin) {
            public void result(Object obj) {
                if (obj.equals(true)) hide();
            }
        };
        levelsDialog.text("\nNivel 1 - Busqueda de Llaves:\n" +
            "- Encuentra 5 llaves para abrir la puerta\n\n" +
            "Nivel 2 - Eliminacion de Enemigos:\n" +
            "- Derrota enemigos especificos\n\n" +
            "Obstaculos:\n" +
            "- Plataformas moviles\n" +
            "- Agujeros y precipicios\n" +
            "- Espinas\n" +
            "- Puertas y mecanismos\n\n" +
            "Cada nivel incluye un minijuego ODS");
        levelsDialog.button("Cerrar", true).pad(20);

        // score
        scoreDialog = new Dialog("Sistema de Puntuacion", skin) {
            public void result(Object obj) {
                if (obj.equals(true)) hide();
            }
        };
        scoreDialog.text("\nPuntos por Enemigo:\n" +
            "- Waddle Dee: 20 puntos\n" +
            "- Bronto Burt: 30 puntos\n" +
            "- Hot Head: 50 puntos\n\n" +
            "Bonus:\n" +
            "- Obtener llave: 50 puntos\n" +
            "- Abrir puerta: 100 puntos\n\n" +
            "Penalizaciones:\n" +
            "- Golpe por enemigo: -10 puntos\n" +
            "- Golpe por espinas: -10 puntos\n" +
            "- Caida al vacio: -20 puntos");
        scoreDialog.button("Cerrar", true).pad(20);
    }

    private void setupButtonListeners() {
        controlsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controlsDialog.show(stage);
            }
        });

        levelsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                levelsDialog.show(stage);
            }
        });

        scoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                scoreDialog.show(stage);
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                main.setScreen(main.pantallaini);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(LIGHT_PINK.r, LIGHT_PINK.g, LIGHT_PINK.b, LIGHT_PINK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }
}

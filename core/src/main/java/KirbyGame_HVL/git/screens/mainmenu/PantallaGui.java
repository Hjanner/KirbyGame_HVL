package KirbyGame_HVL.git.screens.mainmenu;
import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.screens.gameplay.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class PantallaGui extends Pantalla {

    /* Atributos:
       Entre estos atributos estan los distintos botones del menu, las tablas en donde se
       guardan dichos botones para almacenarlas ordenadamente y un spritebatch para poder
       cambiar de pantalla. Tambien se encuentra la skin de los botones y su respectivo Stage para
       operar con el escenario y añadir los distintoa actores.
    * */
    public GameScreen gameScreen;
    private Stage stage;
    private Skin skin;
    private Table tabla, tabla2;
    private TextButton jugar, ayuda, acerca_De, salir, singleplayer, multijugador;
    private SpriteBatch batch;

    /*Constructor en donde preparamos nuestro batch para utilizarlo
     * */
    public PantallaGui(Main main) {
        super(main);
        batch = main.getBatch();
    }

    /*Metodo en donde se mostraran los distintos botones con las opciones y si se
      clickea uno de esos botones hara una accion en especifico.
    * */
    @Override
    public void show () {
        stage = new Stage();
        skin = new Skin (Gdx.files.internal("assets/ui/skin/uiskin.json"));
        tabla = new Table();
        tabla.setFillParent(true);
        Table tabla2 = new Table ();
        tabla2.setFillParent(true);

        jugar = new TextButton("Jugar", skin);
        ayuda = new TextButton("Ayuda de juego", skin);
        acerca_De = new TextButton("Acerca de", skin);
        salir = new TextButton("Salir",skin);
        singleplayer = new TextButton("Singleplayer", skin);
        multijugador = new TextButton("Multijugador", skin);

        tabla.add(jugar).width(400).height(80).space(25).row();
        tabla.add(ayuda).width(400).height(80).space(25).row();
        tabla.add(acerca_De).width(400).height(80).space(25).row();
        tabla.add(salir).width(400).height(80).space(25).row();
        tabla2.add(singleplayer).width (400).height(200).space(25).row();
        tabla2.add(multijugador).width (400).height(200).space(25).row();

        /*Si se pulsa el boton de jugar mostrara la segunda tabla con una animacion
         * */
        jugar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                tabla.addAction(Actions.parallel(Actions.moveBy(0,1000,1.5f), Actions.fadeOut(2)));
                stage.addActor(tabla2);
                tabla2.addAction(Actions.sequence(Actions.fadeOut(0.01f), Actions.fadeIn(3)));

            }
        });

        /*Si se pulsa el boton cambia de pantalla a la del propio kirby.
         * */
        singleplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                main.setScreen(main.gameScreen);
            }
        });

        acerca_De.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                main.setScreen(main.gameCulebrita);
            }
        });

        ayuda.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                main.setScreen(main.gameViejita);
            }
        });

        salir.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                System.exit(0);
            }
        });

        stage.addActor(tabla);
        Gdx.input.setInputProcessor(stage);
    }

    /*Actualiza el escenario
     * */
    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act(delta);
    }


}


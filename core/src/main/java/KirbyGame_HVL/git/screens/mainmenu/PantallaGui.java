package KirbyGame_HVL.git.screens.mainmenu;
import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.screens.gameplay.GameScreen;
import KirbyGame_HVL.git.systems.NameManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.w3c.dom.Text;

public class PantallaGui extends Pantalla {

    /* Atributos:
       Entre estos atributos estan los distintos botones del menu, las tablas en donde se
       guardan dichos botones para almacenarlas ordenadamente y un spritebatch para poder
       cambiar de pantalla. Tambien se encuentra la skin de los botones y su respectivo Stage para
       operar con el escenario y añadir los distintoa actores.
    * */
    public GameScreen gameScreen;
    private Main main;
    private Stage stage;
    private Skin skin;
    private Table tabla, tabla2;
    private TextButton jugar, ayuda, acerca_De, salir, ranking, singleplayer, multijugador, backButton;
    private SpriteBatch batch;
    private Sprite spriteLogo, spriteBackGround;
    private float opacidad;
    private Texture textureLogo,textureBackGround;
    private TextureRegion textureRegionLogo,textureRegionBackGround;
    private boolean realizar;
    private boolean stop;
    private Music soundTrack;
    private Sound soundClick;
    private NameManager nameManager;

    /*Constructor en donde preparamos nuestro batch para utilizarlo
     * */
    public PantallaGui(Main main) {
        super(main);
        this.main = main;
        soundTrack = Gdx.audio.newMusic(Gdx.files.internal("assets/audio/music/energetic-bgm-242515.ogg"));
        soundClick = Gdx.audio.newSound(Gdx.files.internal("assets/audio/music/clicky-mouse-click-182496.mp3"));
        soundTrack.setVolume(0.3f);
        batch = main.getBatch();
        nameManager = new NameManager();
    }

    /*Metodo en donde se mostraran los distintos botones con las opciones y si se
      clickea uno de esos botones hara una accion en especifico.
    * */
    @Override
    public void show () {

        soundTrack.setLooping(true);
        soundTrack.play();
        textureLogo = new Texture("assets/art/backgrounds/Logo_Kirby.png");
        textureRegionLogo = new TextureRegion(textureLogo, 1920,1080);
        spriteLogo = new Sprite(textureRegionLogo);
        spriteLogo.setSize(930,700);
        spriteLogo.setPosition(-20,0);
        textureBackGround = new Texture("assets/art/backgrounds/Kirby_BackGround3.jpg");
        textureRegionBackGround = new TextureRegion(textureBackGround, 736, 414);
        spriteBackGround = new Sprite(textureRegionBackGround);
        spriteBackGround.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteLogo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        opacidad = 0f;
        realizar = true;
        stop = true;
        spriteLogo.setAlpha(opacidad);
        stage = new Stage();
        skin = new Skin (Gdx.files.internal("assets/ui/skin/quantum-horizon-ui.json"));
        tabla = new Table();
        tabla.setFillParent(true);
        Table tabla2 = new Table ();
        tabla2.setFillParent(true);
        Label.LabelStyle titleStyle = new Label.LabelStyle(skin.get("default", Label.LabelStyle.class));
        titleStyle.fontColor = Color.CYAN;
        Label titleLabel = new Label("MENU", titleStyle);
        titleLabel.setFontScale(3.5f);
        Label titleLabel2 = new Label("OPCION DE JUEGO", titleStyle);
        titleLabel2.setFontScale(3.5f);

        jugar = new TextButton("Jugar", skin);
        ayuda = new TextButton("Ayuda", skin);
        acerca_De = new TextButton("Acerca De", skin);
        salir = new TextButton("Salir",skin);
        ranking = new TextButton("Ranking", skin);
        singleplayer = new TextButton("Singleplayer", skin);
        multijugador = new TextButton("Multijugador", skin);
        backButton = new TextButton("Volver", skin);

        tabla.add(titleLabel).pad(20).row();
        tabla.add(jugar).width(400).height(80).space(25).row();
        tabla.add(ranking).width(400).height(80).space(25).row();
        tabla.add(ayuda).width(400).height(80).space(25).row();
        tabla.add(acerca_De).width(400).height(80).space(25).row();
        tabla.add(salir).width(400).height(80).space(25).row();
        tabla2.add(titleLabel2).pad(20).row();
        tabla2.add(singleplayer).width (400).height(100).space(25).row();
        tabla2.add(multijugador).width (400).height(100).space(25).row();

        /*Si se pulsa el boton de jugar mostrara la segunda tabla con una animacion
         * */
        jugar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                soundClick.play();
                tabla.addAction(Actions.parallel(Actions.moveBy(0,1000,1.5f), Actions.fadeOut(2)));
                stage.addActor(tabla2);
                tabla2.addAction(Actions.sequence(Actions.fadeOut(0.01f), Actions.fadeIn(3)));
                tabla2.add(backButton).width(200).height(80).space(40).row();
            }
        });

        /*Si se pulsa el boton cambia de pantalla a la del propio kirby.
         * */
        singleplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Dialog dialog = new Dialog("\n  Ingrese su nombre", skin);
                Label titleLabel = dialog.getTitleLabel();
                titleLabel.setFontScale(1.3f);

                TextField textField = new TextField("", skin);
                dialog.getContentTable().add(textField).expandX().fillX();

                TextButton buttonAcept =  new TextButton("Aceptar",skin);
                buttonAcept.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        soundClick.play();
                        String nombre = textField.getText();
                        if (nombre.isEmpty()) {
                            new Dialog("\n\n    Error", skin) {
                                {
                                    Label titleLabel = getTitleLabel();
                                    titleLabel.setFontScale(2.0f);
                                    text("El nombre no puede\nestar vacio!!");
                                    button("Aceptar", true);
                                }
                            }.show(stage);
                        } else {
                            new Dialog("\n\n   START GAME", skin) {
                                {
                                    Label titleLabel = getTitleLabel();
                                    titleLabel.setFontScale(1.6f);
                                    text("BIENVENIDO " + nombre + "\n\nQUE COMIENCE EL JUEGO!!");
                                    TextButton button = new TextButton("Aceptar",skin);
                                    button.addListener(new ClickListener () {

                                        @Override
                                        public void clicked(InputEvent event, float x, float y) {
                                            nameManager.setNombre(nombre);
                                            soundClick.play();
                                            soundTrack.stop();
                                            main.setScreen(main.gameScreen);
                                        }
                                    });

                                    button(button);
                                }
                            }.show(stage);
                        }
                    }
                });
                dialog.button(buttonAcept);

                TextButton buttonCancel = new TextButton("Cancelar",skin);
                buttonCancel.addListener(new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        soundClick.play();
                        dialog.hide();
                    }
                });

                dialog.button(buttonCancel);

                dialog.pad(90);
                dialog.show(stage);
            }
        });

        ranking.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                soundClick.play();
                main.setScreen(main.rankingScreen);
            }
        });

        acerca_De.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                soundClick.play();
                main.setScreen(main.aboutScreen);
            }
        });

        ayuda.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                soundClick.play();
                main.setScreen(main.helpScreen);
            }
        });

        salir.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                soundClick.play();
                System.exit(0);
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundClick.play();
                main.setScreen(main.pantallaini);
            }
        });

        Gdx.input.setInputProcessor(stage);


    }

    /*Actualiza el escenario
     * */
    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (opacidad <= 1 && realizar && stop) {
            opacidad += delta*0.3f;
            spriteLogo.setAlpha(opacidad);
        }

        else if (stop){
            realizar = false;
            opacidad -= delta*0.4f;
            spriteLogo.setAlpha(opacidad);
            if (opacidad <= 0) {
                realizar = true;
            }
        }

        batch.begin();
        spriteBackGround.draw(batch);
        spriteLogo.draw(batch);
        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            if (spriteLogo.getBoundingRectangle().contains(touchPos.x, touchPos.y) && stop) {
                stop = false;
                spriteLogo.setAlpha(0f);
                spriteLogo.setPosition(5000,5000);
                tabla.addAction(Actions.sequence(Actions.fadeOut(0.01f), Actions.fadeIn(2f)));
                stage.addActor(tabla);
            }
        }

        stage.draw();
        stage.act(delta);
    }


}


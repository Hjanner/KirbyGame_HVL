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

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

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

        rankingDialog = new Dialog("\n\tRanking", skin) {
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
        ArrayList <String> fecha = new ArrayList<>();
        ArrayList <String> hora = new ArrayList<>();
        ArrayList <String> nombre = new ArrayList<>();
        ArrayList <String> puntaje = new ArrayList<>();
        ArrayList aux = new ArrayList <>();

        try {

            FileReader fr = new FileReader("Partidas.txt");
            BufferedReader br = new BufferedReader(fr);

            String linea;
            String palabra = "";
            while ((linea = br.readLine()) != null) {

                for (int i = 0; i < linea.length(); i++) {
                    if (linea.charAt(i) != '|') {
                        palabra += linea.charAt(i);
                    }

                    else {
                        aux.add(palabra);
                        palabra = "";
                    }
                }

                fecha.add((String) aux.get(0));
                hora.add((String) aux.get(1));
                nombre.add((String) aux.get(2));
                puntaje.add((String) aux.get(3));
                aux.clear();
            }

            br.close();
            fr.close();

            String [] fechas = new String[fecha.size()];
            String [] horas = new String[hora.size()];
            String [] nombres = new String[nombre.size()];
            String [] puntajes = new String[puntaje.size()];

            for (int i = 0; i < puntaje.size(); i++) {
                fechas[i] = fecha.get(i);
                horas[i] = hora.get(i);
                nombres[i] = nombre.get(i);
                puntajes[i] = puntaje.get(i);
            }

            for (int i = 0; i < puntajes.length - 1; i++) {
                for (int j = 0; j < puntajes.length - i - 1; j++) {
                    if (Integer.parseInt(puntajes[j]) < Integer.parseInt(puntajes[j + 1])) {
                        String temp = puntajes[j];
                        puntajes[j] = puntajes[j + 1];
                        puntajes[j + 1] = temp;
                        String temp2 = fechas[j];
                        fechas[j] = fechas[j + 1];
                        fechas[j + 1] = temp2;
                        String temp3 = horas[j];
                        horas[j] = horas[j + 1];
                        horas[j + 1] = temp3;
                        String temp4 = nombres[j];
                        nombres[j] = nombres[j + 1];
                        nombres[j + 1] = temp4;
                    }
                }
            }

            String cadena = "";
            for (int i = 0; i < puntajes.length; i++) {
                cadena += fechas[i] + " " + horas[i] + " " + " " + nombres[i] + " " + puntajes[i] + " PUNTOS\n";
            }

            return cadena;

        } catch (IOException e) {

            System.out.println("Error al leer el archivo " + e.getMessage());
        }

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

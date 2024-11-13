package KirbyGame_HVL.git.screens.gameplay;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.player.Kirby;
import KirbyGame_HVL.git.screens.mainmenu.Pantalla;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PantallaScene extends Pantalla {

    /*Atributos:
      Los distintos actores que va a tener el escenario y su fondo
    * */
    private Stage escenario;
    private Kirby kirby;

    /*Constructor en donde se van a añadir los distintos actores del juego
      y se va a cargar el escenario o mapa
    * */
    public PantallaScene(Main main) {
        super(main);
        escenario = new Stage ();

        Gdx.input.setInputProcessor(escenario);
        kirby = new Kirby();
        escenario.addActor(kirby);

    }

    /*Actualiza el escenario
     * */
    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        escenario.draw();
        escenario.act();

    }
}

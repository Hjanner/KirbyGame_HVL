package KirbyGame_HVL.git.screens.gameplay;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.enemies.WaddleDee;
import KirbyGame_HVL.git.entities.items.Floor;
import KirbyGame_HVL.git.entities.items.Spikes;
import KirbyGame_HVL.git.entities.player.Kirby;
import KirbyGame_HVL.git.screens.mainmenu.Pantalla;
import KirbyGame_HVL.git.utils.helpers.TiledMapHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class GameScreen extends Pantalla implements ContactListener {

    private Stage stage;
    private World world;

    private Kirby kirby;
    private Main main;
    private OrthographicCamera cam;
    private TiledMapHelper tiledMapHelper;
    private OrthogonalTiledMapRenderer map;
    private Box2DDebugRenderer bdr;
    private Floor floor;
    private Spikes spikes;

    //enemies
    private ArrayList<WaddleDee> waddleDees;
    private Array<WaddleDee> waddleDeesToRemove;


    public GameScreen(Main main) {
        super(main);
        this.main = main;
        stage = new Stage ();
        waddleDees = new ArrayList<>();
        waddleDeesToRemove = new Array<>();
    }

    @Override
    public void show() {

        world = new World (new Vector2(0, 0), true);
        world.setContactListener(this);                                 // Añadir el listener de contactos
        kirby = new Kirby(world, main);
        stage.addActor(kirby);
        cam = (OrthographicCamera) stage.getCamera();
        cam.zoom = 0.65f;

        //enemies
        createWaddleDees();

        tiledMapHelper = new TiledMapHelper();
        map = tiledMapHelper.setupmap();
        bdr = new Box2DDebugRenderer();
        for (int i = 2; i < 4; i++) {
            floor = new Floor(world, map, i);
        }

        spikes = new Spikes(world, map, 4);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.53f, 0.81f, 0.92f, 1);

        world.step(1/60f,6,2);

        //muerte de enemies
        for (WaddleDee waddle : waddleDeesToRemove) {
            waddle.dispose();
            world.destroyBody(waddle.getBody());
            waddleDees.remove(waddle);
        }
        waddleDeesToRemove.clear();

        stage.act();
        update();
        map.render();
        stage.draw();
        //bdr.render(world, cam.combined);
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        kirby.dispose();

        for(WaddleDee waddle : waddleDees) {
            waddle.dispose();
        }
    }

    public void update () {
        cam.position.set(kirby.getFixture().getBody().getPosition(),0);
        cam.update();
        map.setView(cam);
    }

//enemies
    private void createWaddleDees() {
        WaddleDee waddleDee1 = new WaddleDee(world, main, 300, 1010); // Ajusta las coordenadas según necesites
        WaddleDee waddleDee2 = new WaddleDee(world, main, 500, 1010);
        WaddleDee waddleDee3 = new WaddleDee(world, main, 700, 1010);

        // Añadir los WaddleDees al stage y a la lista
        stage.addActor(waddleDee1);
        stage.addActor(waddleDee2);
        stage.addActor(waddleDee3);

        waddleDees.add(waddleDee1);
        waddleDees.add(waddleDee2);
        waddleDees.add(waddleDee3);
    }


//listenner
@Override
public void beginContact(Contact contact) {
    Object userDataA = contact.getFixtureA().getUserData();                 //obteniendo representacion fisica
    Object userDataB = contact.getFixtureB().getUserData();

    WaddleDee waddleDee = null;
    Kirby kirby = null;

    //identificando objetos
    if (userDataA instanceof WaddleDee) {
        waddleDee = (WaddleDee) userDataA;
        if (userDataB instanceof Kirby) {
            kirby = (Kirby) userDataB;
        }
    } else if (userDataB instanceof WaddleDee) {
        waddleDee = (WaddleDee) userDataB;
        if (userDataA instanceof Kirby) {
            kirby = (Kirby) userDataA;
        }
    }

    if (waddleDee != null && kirby != null && kirby.isDashing()) {
        if (!waddleDeesToRemove.contains(waddleDee, true)) {
            waddleDeesToRemove.add(waddleDee);
        }
    }
}

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}


}

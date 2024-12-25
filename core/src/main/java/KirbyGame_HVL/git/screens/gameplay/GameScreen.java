package KirbyGame_HVL.git.screens.gameplay;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.enemis.WaddleDee;
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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
        cam.zoom = 0.34f;

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
        cloud();

        //muerte de enemies
        for (WaddleDee waddle : waddleDeesToRemove) {
            waddle.dispose();
            waddle.die();
            world.destroyBody(waddle.getBody());
            waddleDees.remove(waddle);
        }
        waddleDeesToRemove.clear();

        stage.act();
        update();
        map.render();
        bdr.render(world, cam.combined);
        stage.draw();
    }

    //enemies
    private void createWaddleDees() {
        WaddleDee waddleDee1 = new WaddleDee(world, main, 400, 1010); // Ajusta las coordenadas según necesites
        WaddleDee waddleDee2 = new WaddleDee(world, main, 500, 1010);
        WaddleDee waddleDee3 = new WaddleDee(world, main, 600, 1010);

        // Añadir los WaddleDees al stage y a la lista
        stage.addActor(waddleDee1);
        stage.addActor(waddleDee2);
        stage.addActor(waddleDee3);

        waddleDees.add(waddleDee1);
        waddleDees.add(waddleDee2);
        waddleDees.add(waddleDee3);
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
        cam.position.set(kirby.getBody().getPosition(),0);
        cam.update();
        map.setView(cam);

    }

    public void cloud () {
        if (kirby.getCloud() != null) {
            stage.addActor(kirby.getCloud());
        }
    }

    private boolean setContact(Contact contact, Object userA, Object userB) {
        return ((contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB)) || (contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA)));
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Object userDataA = fixtureA.getUserData();
        Object userDataB = fixtureB.getUserData();

        // Colisión Kirby-WaddleDee
        if ((userDataA instanceof Kirby && userDataB instanceof WaddleDee) ||
            (userDataB instanceof Kirby && userDataA instanceof WaddleDee)) {

            Kirby kirby = (Kirby) (userDataA instanceof Kirby ? userDataA : userDataB);
            WaddleDee waddle = (WaddleDee) (userDataA instanceof WaddleDee ? userDataA : userDataB);

            if (kirby.isDashing()) {
                System.out.println("Waddle Dee eliminado por dash");
                if (!waddleDeesToRemove.contains(waddle, true)) {
                    waddleDeesToRemove.add(waddle);
                    //waddle.die();
                }
            } else {
                // Kirby recibe daño o rebota
                kirby.setColisionSuelo(true);
                System.out.println("Kirby ha colisionado con WaddleDee sin dash");
            }
        }

        // Colisión con el suelo
        if ((setContact(contact, this.kirby, "suelo")) ||
            (setContact(contact, this.kirby, "spikes"))) {
            kirby.setColisionSuelo(true);
        }else {
            kirby.setColisionSuelo(false);
        }
    }

    @Override
    public void endContact(Contact contact) {
        kirby.setColisionSuelo(false);
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

}

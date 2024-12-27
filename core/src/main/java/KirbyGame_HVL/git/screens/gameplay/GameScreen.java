package KirbyGame_HVL.git.screens.gameplay;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.States.StatesKirby.DashStateKirby;
import KirbyGame_HVL.git.entities.States.StatesKirby.EnumStates;
import KirbyGame_HVL.git.entities.States.StatesWaddleDee.DieStateWaddleDee;
import KirbyGame_HVL.git.entities.States.StatesWaddleDee.EnumStatesWaddleDee;
import KirbyGame_HVL.git.entities.enemis.EnemyFactory;
import KirbyGame_HVL.git.entities.enemis.WaddleDee;
import KirbyGame_HVL.git.entities.enemis.WaddleDeeFactory;
import KirbyGame_HVL.git.entities.items.CloudKirby;
import KirbyGame_HVL.git.entities.items.Floor;
import KirbyGame_HVL.git.entities.items.Hole;
import KirbyGame_HVL.git.entities.items.Spikes;
import KirbyGame_HVL.git.entities.player.Kirby;
import KirbyGame_HVL.git.screens.mainmenu.Pantalla;
import KirbyGame_HVL.git.utils.helpers.TiledMapHelper;
import com.badlogic.gdx.Gdx;
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
    private Hole hole;

    //enemies
    private ArrayList<WaddleDee> waddleDees;
    private Array<WaddleDee> waddleDeesToRemove;
    private EnemyFactory factory;

//ataques
    //nubes
    private ArrayList<CloudKirby> clouds;
    private Array<CloudKirby> cloudsToRemove;
    private float lastCloudCreationTime = 0;

    private boolean puedoResetKirby = false;


    public GameScreen(Main main) {
        super(main);
        this.main = main;
        stage = new Stage ();
        waddleDees = new ArrayList<>();
        waddleDeesToRemove = new Array<>();
        clouds = new ArrayList<>();
        cloudsToRemove = new Array<>();
        factory = new WaddleDeeFactory();
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
        hole = new Hole(world, map, 5);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.53f, 0.81f, 0.92f, 1);

        world.step(1/60f,6,2);

        if (puedoResetKirby) {
            kirby.resetPosition();
            puedoResetKirby = false;
        }


        //muerte de enemies
        deleteWaddleDees(delta);

        cloud();
        deleteClouds();


        stage.act();
        update();
        map.render();
        bdr.render(world, cam.combined);
        stage.draw();
    }

    //enemies
    private void createWaddleDees() {
        WaddleDee waddleDee1 = (WaddleDee) factory.createEnemy(world, main, 400, 1010); // Ajusta las coordenadas según necesites
        WaddleDee waddleDee2 = (WaddleDee) factory.createEnemy(world, main, 500, 1010);
        WaddleDee waddleDee3 = (WaddleDee) factory.createEnemy(world, main, 600, 1010);

        // Añadir los WaddleDees al stage y a la lista
        stage.addActor(waddleDee1);
        stage.addActor(waddleDee2);
        stage.addActor(waddleDee3);

        waddleDees.add(waddleDee1);
        waddleDees.add(waddleDee2);
        waddleDees.add(waddleDee3);
    }

    private void deleteWaddleDees(float delta){


        for (WaddleDee waddle : waddleDeesToRemove) {
            waddleDees.remove(waddle);
        }

        waddleDeesToRemove.clear();
    }

    public void update () {
        cam.position.set(kirby.getBody().getPosition(),0);
        cam.update();
        map.setView(cam);

    }

//nube
    public void cloud () {
        lastCloudCreationTime += Gdx.graphics.getDeltaTime();

        if (lastCloudCreationTime >= 1f && kirby.getCloud() != null) {
            stage.addActor(kirby.getCloud());
        }
    }

    public void deleteClouds(){
        for (CloudKirby cloud : cloudsToRemove) {
            world.destroyBody(cloud.getBody());     // Destruye el cuerpo en Box2D
            cloud.remove();                         // Elimina del stage
            clouds.remove(cloud);
        }
        cloudsToRemove.clear();
    }

//listener
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

            //muerte por dashing
            if (kirby.getcurrentState() instanceof DashStateKirby) {
                System.out.println("Waddle Dee eliminado por dash");
                if (!waddleDeesToRemove.contains(waddle, true)) {
                    waddle.setflipX(kirby.getFlipX());
                    waddle.setState(EnumStatesWaddleDee.DIE);
                    waddleDeesToRemove.add(waddle);
                    kirby.setState(EnumStates.STAY);
                }
            } else if (!(waddle.getcurrentState() instanceof DieStateWaddleDee)){
                // Kirby recibe daño o rebota
                kirby.setState(EnumStates.DAMAGE);
                kirby.setAnimation(EnumStates.DAMAGE);
                //aqui debe ir una animacion de muerte
            }
        }

        // muerte por cloud
        if ((userDataA instanceof CloudKirby && userDataB instanceof WaddleDee) ||
            (userDataB instanceof CloudKirby && userDataA instanceof WaddleDee)) {

            //elimino waddle
            WaddleDee waddle = (WaddleDee) (userDataA instanceof WaddleDee ? userDataA : userDataB);
            if (!waddleDeesToRemove.contains(waddle, true)) {
                waddle.setflipX(kirby.getFlipX());
                waddle.setState(EnumStatesWaddleDee.DIE);
                waddleDeesToRemove.add(waddle);
            }

            //elimino nube
            CloudKirby cloud = (CloudKirby) (userDataA instanceof CloudKirby ? userDataA : userDataB);
            if (!cloudsToRemove.contains(cloud, true)) {
                cloudsToRemove.add(cloud);
            }
        }

        // colision con el suelo
        if ((setContact(contact, this.kirby, "suelo"))) {
            kirby.setColisionSuelo(true);
        }else {
            kirby.setColisionSuelo(false);
        }

        if (setContact(contact, this.kirby, "spikes")) {
            kirby.setState(EnumStates.DAMAGE);
            kirby.setAnimation(EnumStates.DAMAGE);
        }

        if ((setContact(contact, this.kirby, "Hole"))) {
            puedoResetKirby = true;
            kirby.setState(EnumStates.STAY);
            kirby.setAnimation(EnumStates.STAY);
        } else {
            puedoResetKirby = false;
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

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        kirby.dispose();

        for(WaddleDee waddle : waddleDees) {
            waddle.dispose();
        }
    }

}

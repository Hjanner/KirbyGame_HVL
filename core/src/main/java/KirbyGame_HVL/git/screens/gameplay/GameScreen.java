package KirbyGame_HVL.git.screens.gameplay;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.States.StatesKirby.DashStateKirby;
import KirbyGame_HVL.git.entities.States.StatesKirby.EnumStates;
import KirbyGame_HVL.git.entities.States.StatesWaddleDee.DieStateWaddleDee;
import KirbyGame_HVL.git.entities.States.StatesWaddleDee.EnumStatesWaddleDee;
import KirbyGame_HVL.git.entities.States.statesBrontoBurt.EnumStatesBrontoBurt;
import KirbyGame_HVL.git.entities.enemis.EnemyFactory;
import KirbyGame_HVL.git.entities.enemis.brontoBurt.BrontoBurdFactory;
import KirbyGame_HVL.git.entities.enemis.brontoBurt.BrontoBurt;
import KirbyGame_HVL.git.entities.enemis.waddleDee.WaddleDee;
import KirbyGame_HVL.git.entities.enemis.waddleDee.WaddleDeeFactory;
import KirbyGame_HVL.git.entities.items.CloudKirby;
import KirbyGame_HVL.git.entities.items.Floor;
import KirbyGame_HVL.git.entities.items.Key;
import KirbyGame_HVL.git.entities.items.Hole;
import KirbyGame_HVL.git.entities.items.Spikes;
import KirbyGame_HVL.git.entities.player.Kirby;
import KirbyGame_HVL.git.screens.mainmenu.Pantalla;
import KirbyGame_HVL.git.utils.helpers.TiledMapHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;


public class GameScreen extends Pantalla implements ContactListener, Screen {

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


//items
    //keys
    private ArrayList<Key> keys;
    private Array<Key> keysToRemove;
    private Texture keyIconTexture;
    private Sprite keyIconSprite;
    private int keysCollected = 0;
    private final int TOTAL_KEYS = 3;
    private BitmapFont font;

    //enemies
    private EnemyFactory factory;
    private ArrayList<WaddleDee> waddleDees;
    private Array<WaddleDee> waddleDeesToRemove;
    private ArrayList<BrontoBurt> brontoBurts;
    private Array<BrontoBurt> brontoBurtsToRemove;

//ataques
    //nubes
    private ArrayList<CloudKirby> clouds;
    private Array<CloudKirby> cloudsToRemove;
    private float lastCloudCreationTime = 0;

    //helpers
    private boolean puedoResetKirby = false;

    public GameScreen(Main main) {
        super(main);
        this.main = main;
        stage = new Stage ();

        waddleDees = new ArrayList<>();
        waddleDeesToRemove = new Array<>();
        brontoBurts = new ArrayList<>();
        brontoBurtsToRemove = new Array<>();

        clouds = new ArrayList<>();
        cloudsToRemove = new Array<>();

        keys = new ArrayList<>();
        keysToRemove = new Array<>();

        font = new BitmapFont();
        font.setColor(Color.WHITE);
    }

    @Override
    public void show() {
        world = new World (new Vector2(0, 0), true);
        world.setContactListener(this);                                 // listener de contactos

        kirby = new Kirby(world, main);
        stage.addActor(kirby);
        cam = (OrthographicCamera) stage.getCamera();
        cam.zoom = 0.34f;

        //UI
        loadAssetsKey();

        //items
        createKeys();

        //enemies
        createWaddleDees();
        createBrontoBurts();

        tiledMapHelper = new TiledMapHelper();
        map = tiledMapHelper.setupmap();
        bdr = new Box2DDebugRenderer();

        //map elements
        createFloor();
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

        deleteKeys();

        //muerte de enemies
        deleteWaddleDees(delta);

        cloud();
        deleteClouds();

        stage.act();
        update();
        map.render();
        bdr.render(world, cam.combined);
        stage.draw();

        renderKeyContador();
    }

    public void update () {
        cam.position.set(kirby.getBody().getPosition(),0);
        cam.update();
        map.setView(cam);
    }

    public void createFloor(){
        for (int i = 2; i < 4; i++) {
            floor = new Floor(world, map, i);
        }
    }

//ITEMS
    //keys
    private void loadAssetsKey(){
        keyIconTexture = main.getManager().get("assets/art/sprites/kirbystay.png");
        keyIconSprite = new Sprite(keyIconTexture);
        keyIconSprite.setSize(16, 16);
    }

    private void createKeys(){
        Key key1 = new Key(world, main, 300, 1010);
        Key key2 = new Key(world, main, 500, 1010);
        Key key3 = new Key(world, main, 700, 1010);

        keys.add(key1);
        keys.add(key2);
        keys.add(key3);

        // Añade las llaves al stage
        for (Key key : keys) {
            stage.addActor(key);
        }
    }

    private void renderKeyContador() {
        Batch batch = stage.getBatch();
        batch.begin();

        batch.setProjectionMatrix(stage.getCamera().combined);

        float baseX = cam.position.x - cam.viewportWidth/2 * cam.zoom + 10;                             //posicion en pantalla
        float baseY = cam.position.y + cam.viewportHeight/2 * cam.zoom - 20;

        // Dibuja las llaves
        for (int i = 0; i < TOTAL_KEYS; i++) {
            keyIconSprite.setPosition(baseX + (i * 20), baseY);
            keyIconSprite.setAlpha(i < keysCollected ? 1f : 0.5f); // Llaves no recolectadas semi-transparentes
            keyIconSprite.draw(batch);
        }

        // Dibuja el contador
        font.getData().setScale(.8f);
        font.draw(batch, keysCollected + "/" + TOTAL_KEYS,
                baseX + (TOTAL_KEYS * 20) + 5,
                baseY + 9);

        batch.end();
    }

    private void deleteKeys(){
        for (Key key : keysToRemove) {
            world.destroyBody(key.getBody());
            key.remove();
        }
        keysToRemove.clear();
    }

//ENEMIES
    //BRONTO BURT
    private void createBrontoBurts(){
        factory = new BrontoBurdFactory();

        BrontoBurt brontoBurt1 = (BrontoBurt) factory.createEnemy(world, main, 500, 1100);
        BrontoBurt brontoBurt2 = (BrontoBurt) factory.createEnemy(world, main, 600, 1150);
        BrontoBurt brontoBurt3 = (BrontoBurt) factory.createEnemy(world, main, 750, 1050);

        stage.addActor(brontoBurt1);
        stage.addActor(brontoBurt2);
        stage.addActor(brontoBurt3);

        brontoBurts.add(brontoBurt1);
        brontoBurts.add(brontoBurt2);
        brontoBurts.add(brontoBurt3);
    }

    private void deleteBrontoBurts(float delta){
        for (BrontoBurt bronto : brontoBurtsToRemove) {
            brontoBurts.remove(bronto);
        }
        brontoBurtsToRemove.clear();
    }


    //WADDLE DEE
    private void createWaddleDees() {
        factory = new WaddleDeeFactory();

        WaddleDee waddleDee1 = (WaddleDee) factory.createEnemy(world, main, 400, 1010);                             // Ajusta las coordenadas segun necesites
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

//ITEMS
        // colision con llave
        if ((userDataA instanceof Kirby && userDataB instanceof Key) ||
            (userDataB instanceof Kirby && userDataA instanceof Key)) {

            Key keyInContact = (Key) (userDataA instanceof Key ? userDataA : userDataB);

            if (!keyInContact.isCollected()) {
                keyInContact.collect();
                for (Key key : keys) {
                    if (key.isCollected() && !keysToRemove.contains(key, true)) {
                        keysToRemove.add(key);
                    }
                }
                keysCollected++;
                //un  mensaje en pantalla ir en pantalla
            }
        }

//ENEMIES
        // colision Kirby-WaddleDee
        if ((userDataA instanceof Kirby && userDataB instanceof WaddleDee) ||
            (userDataB instanceof Kirby && userDataA instanceof WaddleDee)) {

            Kirby kirby = (Kirby) (userDataA instanceof Kirby ? userDataA : userDataB);
            WaddleDee waddle = (WaddleDee) (userDataA instanceof WaddleDee ? userDataA : userDataB);

            //muerte por dashing
            if (kirby.getcurrentState() instanceof DashStateKirby) {
                //waddle eliminado por dash
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
            }
        }

        // muerte por cloud Kirby-WaddleDee
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

        //colision kirby-bronto
        if ((userDataA instanceof Kirby && userDataB instanceof BrontoBurt) ||
            (userDataB instanceof Kirby && userDataA instanceof BrontoBurt)) {

            Kirby kirby = (Kirby) (userDataA instanceof Kirby ? userDataA : userDataB);
            BrontoBurt bronto = (BrontoBurt) (userDataA instanceof BrontoBurt ? userDataA : userDataB);

            //kirby recibe dano
            if (!(bronto.getcurrentState() instanceof DieStateWaddleDee)){
                // Kirby recibe daño o rebota
                kirby.setState(EnumStates.DAMAGE);
                kirby.setAnimation(EnumStates.DAMAGE);
            }
        }

        //colision nube-bronto
        if ((userDataA instanceof CloudKirby && userDataB instanceof BrontoBurt) ||
            (userDataB instanceof CloudKirby && userDataA instanceof BrontoBurt)) {

            //elimino bronto
            BrontoBurt bronto = (BrontoBurt) (userDataA instanceof BrontoBurt ? userDataA : userDataB);
            if (!brontoBurtsToRemove.contains(bronto, true)) {
                bronto.setflipX(kirby.getFlipX());
                bronto.setState(EnumStatesBrontoBurt.DIE);
                brontoBurtsToRemove.add(bronto);
            }

            //elimino nube
            CloudKirby cloud = (CloudKirby) (userDataA instanceof CloudKirby ? userDataA : userDataB);
            if (!cloudsToRemove.contains(cloud, true)) {
                cloudsToRemove.add(cloud);
            }
        }

//WORLD
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

        for (Key key : keys) {
            key.dispose();
        }
        font.dispose();
        keyIconTexture.dispose();
    }

}

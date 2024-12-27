package KirbyGame_HVL.git.screens.gameplay;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.enemis.WaddleDee;
import KirbyGame_HVL.git.entities.items.CloudKirby;
import KirbyGame_HVL.git.entities.items.Floor;
import KirbyGame_HVL.git.entities.items.Key;
import KirbyGame_HVL.git.entities.items.Spikes;
import KirbyGame_HVL.git.entities.player.Kirby;
import KirbyGame_HVL.git.screens.mainmenu.Pantalla;
import KirbyGame_HVL.git.utils.helpers.TiledMapHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
    private ArrayList<WaddleDee> waddleDees;
    private Array<WaddleDee> waddleDeesToRemove;

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
        keys = new ArrayList<>();
        keysToRemove = new Array<>();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
    }

    @Override
    public void show() {

        world = new World (new Vector2(0, 0), true);
        world.setContactListener(this);                                 // Añadir el listener de contactos

        kirby = new Kirby(world, main);

        stage.addActor(kirby);
        cam = (OrthographicCamera) stage.getCamera();
        cam.zoom = 0.34f;

        loadAssetsKey();
        createKeys();

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

        if (puedoResetKirby) {
            kirby.resetPosition();
            puedoResetKirby = false;
        }

        deleteKeys();

        //muerte de enemies
        deleteWaddleDees();

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

//ITEMS
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

        // posicion
        float baseX = cam.position.x - cam.viewportWidth/2 * cam.zoom + 10;
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
    //WADDLE DEE
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

    private void deleteWaddleDees(){
        for (WaddleDee waddle : waddleDeesToRemove) {
            waddle.dispose();
            waddle.die();
            world.destroyBody(waddle.getBody());
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
            if (kirby.isDashing()) {
                System.out.println("Waddle Dee eliminado por dash");
                if (!waddleDeesToRemove.contains(waddle, true)) {
                    waddleDeesToRemove.add(waddle);
                }
            } else {
                // Kirby recibe daño o rebota
                puedoResetKirby = true;
                kirby.setColisionSuelo(true);
                //aqui debe ir una animacion de muerte
            }
        }

        // muerte por cloud Kirby-WaddleDee
        if ((userDataA instanceof CloudKirby && userDataB instanceof WaddleDee) ||
            (userDataB instanceof CloudKirby && userDataA instanceof WaddleDee)) {

            //elimino waddle
            WaddleDee waddle = (WaddleDee) (userDataA instanceof WaddleDee ? userDataA : userDataB);
            if (!waddleDeesToRemove.contains(waddle, true)) {
                waddleDeesToRemove.add(waddle);
            }

            //elimino nube
            CloudKirby cloud = (CloudKirby) (userDataA instanceof CloudKirby ? userDataA : userDataB);
            if (!cloudsToRemove.contains(cloud, true)) {
                cloudsToRemove.add(cloud);
            }
        }

//WORLD
        // colision con el suelo
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

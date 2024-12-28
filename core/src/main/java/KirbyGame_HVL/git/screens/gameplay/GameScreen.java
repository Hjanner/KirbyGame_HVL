package KirbyGame_HVL.git.screens.gameplay;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.States.StatesKirby.DashStateKirby;
import KirbyGame_HVL.git.entities.States.StatesKirby.EnumStates;
import KirbyGame_HVL.git.entities.States.StatesWaddleDee.DieStateWaddleDee;
import KirbyGame_HVL.git.entities.States.StatesWaddleDee.EnumStatesWaddleDee;
import KirbyGame_HVL.git.entities.States.statesBrontoBurt.EnumStatesBrontoBurt;
import KirbyGame_HVL.git.entities.enemis.Enemy;
import KirbyGame_HVL.git.entities.enemis.EnemyFactory;
import KirbyGame_HVL.git.entities.enemis.brontoBurt.BrontoBurdFactory;
import KirbyGame_HVL.git.entities.enemis.brontoBurt.BrontoBurt;
import KirbyGame_HVL.git.entities.enemis.waddleDee.WaddleDee;
import KirbyGame_HVL.git.entities.enemis.waddleDee.WaddleDeeFactory;
import KirbyGame_HVL.git.entities.items.*;
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
import java.util.HashMap;
import java.util.Map;

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
    private Platform platform;

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
    private ArrayList<ArrayList<Enemy>> enemiesList;
    private Array<Enemy> enemiesToRemove;
    private Map<Integer, EnemyFactory> zonaFactories;           // Define que factory usar en cada zona
    private int[][][] enemyZonaCoordenadas = {
        {{500, 1010}, {550, 1010}, {600, 1010}, {100, 1010}},                // zona 1 - WaddleDees
        {{700, 1020}, {850, 1020}, {900, 1020}},                // z 2 - BrontoBurts
        {{500, 1100}, {600, 1150}, {750, 1050}},                // z 3 - BrontoBurts
        {{1200, 1040}, {1250, 1040}, {1300, 1040}}              // z 4 - WaddleDees
    };

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

        enemiesList = new ArrayList<>();
        enemiesToRemove = new Array<>();
        zonaFactories = new HashMap<>();
        zonaFactories.put(0, new WaddleDeeFactory());                   //se define el factory a usar por grupo
        zonaFactories.put(1, new BrontoBurdFactory());
        zonaFactories.put(2, new BrontoBurdFactory());
        zonaFactories.put(3, new WaddleDeeFactory());
        for (int i = 0; i < enemyZonaCoordenadas.length; i++) {         //se crean ls grupos
            enemiesList.add(new ArrayList<>());
        }

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
        createEnemies();

        tiledMapHelper = new TiledMapHelper();
        map = tiledMapHelper.setupmap();
        bdr = new Box2DDebugRenderer();

        //map elements
        createPlatformMoved();
        createFloor();
        spikes = new Spikes(world, map, 4);
        hole = new Hole(world, map, 5);
        platform = new Platform(world, map, 6);

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

        loadEnemies();
        cloud();

        deleteEnemies();
        deleteKeys();
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

//ENEMIES
    private void createEnemies() {
        // crea enemies por cada zonas
        for (int zona = 0; zona < enemyZonaCoordenadas.length; zona++) {
            ArrayList<Enemy> zoneEnemies = enemiesList.get(zona);
            if (zoneEnemies.isEmpty()) {
                createEnemiesZona(zona);
            }
        }
    }

    private void createEnemiesZona(int zona) {
        int[][] coordenadas = enemyZonaCoordenadas[zona];                   //obtiene coordenadas del grupo de enemies
        ArrayList<Enemy> zonaEnemies = enemiesList.get(zona);               //inicializo zona
        EnemyFactory factory = zonaFactories.get(zona);                     //obtengo factory segun la relacion hasmap

        //creo enemies
        for (int[] coordenada : coordenadas) {
            Enemy enemy = factory.createEnemy(world, main, coordenada[0], coordenada[1]);
            stage.addActor(enemy);
            zonaEnemies.add(enemy);
        }
    }

    private void loadEnemies() {
        if (enemiesList.isEmpty()) {
            createEnemies();
            return;
        }

        // si se eliminan todos los enemies de una zona o grupo se realiza un respawn
        for (int zona = 0; zona < enemyZonaCoordenadas.length; zona++) {
            ArrayList<Enemy> zoneEnemies = enemiesList.get(zona);
            if (zoneEnemies.isEmpty()) {
                createEnemiesZona(zona);
            }
        }
    }

    private void deleteEnemies() {
        for (ArrayList<Enemy> zoneEnemies : enemiesList) {
            for (Enemy enemy : new ArrayList<>(zoneEnemies)) {                          //  una copia para evitar errores
                if (enemiesToRemove.contains(enemy, true)) {
                    if (enemy.getBody() != null) {
                        world.destroyBody(enemy.getBody());
                    }
                    enemy.remove();
                    zoneEnemies.remove(enemy);                          // Eliminar de la lista de la zona
                }
            }
        }
        enemiesToRemove.clear();
    }

    private void manejadorEnemyColition(Kirby kirby, Enemy enemy) {
        if (kirby.getcurrentState() instanceof DashStateKirby) {
            //eliminacion de enemy por dash
            if (!enemiesToRemove.contains(enemy, true)) {
                enemy.setflipX(kirby.getFlipX());

                if (enemy instanceof WaddleDee) {
                    enemy.setState(EnumStatesWaddleDee.DIE);
                } else if (enemy instanceof BrontoBurt) {
                    enemy.setState(EnumStatesBrontoBurt.DIE);
                }

                enemiesToRemove.add(enemy);
                kirby.setState(EnumStates.STAY);
                enemy.remove();
            }
        } else {
            //kirby recibe daño
            kirby.setState(EnumStates.DAMAGE);
            kirby.setAnimation(EnumStates.DAMAGE);
        }
    }

    public void createFloor(){
        for (int i = 2; i < 4; i++) {
            floor = new Floor(world, map, i);
        }
    }

    private void createPlatformMoved() {

        PlatformMoved platformMoved1 = new PlatformMoved(world, main, 1000,1100);
        stage.addActor(platformMoved1);

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

    private void manejadorCloudEnemyCollision(CloudKirby cloud, Enemy enemy) {
        if (!enemiesToRemove.contains(enemy, true)) {
            enemy.setflipX(kirby.getFlipX());

            if (enemy instanceof WaddleDee) {
                enemy.setState(EnumStatesWaddleDee.DIE);
            } else if (enemy instanceof BrontoBurt) {
                enemy.setState(EnumStatesBrontoBurt.DIE);
            }

            enemiesToRemove.add(enemy);
            enemy.remove();
        }

        if (!cloudsToRemove.contains(cloud, true)) {
            cloudsToRemove.add(cloud);
        }
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
        //colision kirby-enemies y dash
        if ((userDataA instanceof Kirby && userDataB instanceof Enemy) ||
            (userDataB instanceof Kirby && userDataA instanceof Enemy)) {

            Kirby kirby = (Kirby) (userDataA instanceof Kirby ? userDataA : userDataB);
            Enemy enemy = (Enemy) (userDataA instanceof Enemy ? userDataA : userDataB);

            if (kirby.getcurrentState() instanceof DashStateKirby) {
                manejadorEnemyColition(kirby, enemy);
            } else {
                // debe llamar tambien a manejadorEnemyColition aqui para la logica de los puntos perdidos
                kirby.setState(EnumStates.DAMAGE);
                kirby.setAnimation(EnumStates.DAMAGE);
            }
        }

        // colision cloud-enemies
        if ((userDataA instanceof CloudKirby && userDataB instanceof Enemy) ||
            (userDataB instanceof CloudKirby && userDataA instanceof Enemy)) {

            CloudKirby cloud = (CloudKirby) (userDataA instanceof CloudKirby ? userDataA : userDataB);
            Enemy enemy = (Enemy) (userDataA instanceof Enemy ? userDataA : userDataB);

            manejadorCloudEnemyCollision(cloud, enemy);
        }


//WORLD
        // colision con el suelo
        if ((setContact(contact, this.kirby, "suelo")) ||
            (setContact(contact, this.kirby, "Plataforma"))) {
            kirby.setColisionSuelo(true);
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

        for (ArrayList<Enemy> zoneEnemies : enemiesList) {
            for (Enemy enemy : zoneEnemies) {
                enemy.dispose();
            }
        }

        for (Key key : keys) {
            key.dispose();
        }
        font.dispose();
        keyIconTexture.dispose();
    }

}

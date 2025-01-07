package KirbyGame_HVL.git.screens.gameplay;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.States.StatesKirby.AbsorbStateKirby;
import KirbyGame_HVL.git.entities.States.StatesKirby.DashStateKirby;
import KirbyGame_HVL.git.entities.States.StatesKirby.EnumStates;
import KirbyGame_HVL.git.entities.attacks.Attack;
import KirbyGame_HVL.git.entities.attacks.Beam;
import KirbyGame_HVL.git.entities.attacks.CloudKirby;
import KirbyGame_HVL.git.entities.attacks.Fire;
import KirbyGame_HVL.git.entities.enemis.Enemy;
import KirbyGame_HVL.git.entities.enemis.EnemyFactory;
import KirbyGame_HVL.git.entities.enemis.hotHead.HotHead;
import KirbyGame_HVL.git.entities.enemis.hotHead.HotHeadFactory;
import KirbyGame_HVL.git.entities.enemis.waddleDee.WaddleDeeFactory;
import KirbyGame_HVL.git.entities.enemis.waddleDoo.WaddleDoo;
import KirbyGame_HVL.git.entities.enemis.waddleDoo.WaddleDooFactory;
import KirbyGame_HVL.git.entities.items.*;
import KirbyGame_HVL.git.entities.player.Kirby;
import KirbyGame_HVL.git.screens.mainmenu.Pantalla;
import KirbyGame_HVL.git.systems.MinigameManager;
import KirbyGame_HVL.git.screens.minigames.culebrita.GamePanelCulebrita;
import KirbyGame_HVL.git.screens.minigames.laberinto.GamePanelLaberinto;
import KirbyGame_HVL.git.utils.helpers.TiledMapHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class GameScreen extends Pantalla implements ContactListener, Screen {

    private Stage stage;
    private World world;

    private Kirby kirby;
    private Main main;
    private OrthographicCamera cam;
    private TiledMapHelper tiledMapHelper;
    private OrthogonalTiledMapRenderer map;
    private Box2DDebugRenderer bdr;

//items
    //keys
    private ArrayList<Key> keys;
    private Array<Key> keysToRemove;
    private Texture keyIconTexture;
    private TextureRegion keyIconRegion;
    private Sprite keyIconSprite;
    private int keysCollected = 0;
    private final int TOTAL_KEYS = 5;
    private BitmapFont font;
    private Door door;
    private boolean levelCompleted = false;

    //enemies
    private ArrayList<ArrayList<Enemy>> enemiesList;
    private Array<Enemy> enemiesToRemove;
    private Map<Integer, EnemyFactory> zonaFactories;           // Define que factory usar en cada zona
    private int[][][] enemyZonaCoordenadas = {
        {{700, 1010}, {850, 1010}, {600, 1010}},           // zona 1 - WaddleDees
        {{700, 1020}, {850, 1020}, {900, 1020}},                        // z 2 - BrontoBurts
        {{500, 1100}, {600, 1150}, {750, 1050}},                        // z 3 - BrontoBurts
        {{1200, 1040}, {1250, 1040}, {1300, 1040}},                      // z 4 - WaddleDees
        {{500, 1010}, {1400, 1010}, {1500, 1010}},                         //g 5 - HotHeads
        {{250, 1010}, {550, 1010}, {600, 1010}}                          // z 6 - WaddleDoo
    };

    //ataques
    private Array<Fire> firesToRemove;

//nubes
private ArrayList<Attack> attacks;
    private Array<Attack> attacksToRemove;
    private float lastCloudCreationTime = 0;

//objetos del mapa
    private Floor floor;
    private Spikes spikes;
    private Hole hole;
    private Platform platformStatic;
    private ArrayList<PlatformMoved> platforms;

    //helpers
    private boolean puedoResetKirby = false;
    private MinigameManager minigameManager;
    private float initialX;
    private float initialY;
    private int nivel;
    private int helperScore = 0;

    public GameScreen(Main main, float initialX, float initialY, int helperScore, int nivel) {
        super(main);
        this.initialX = initialX ;
        this.initialY = initialY ;
        this.nivel = nivel;
        this.main = main;
        this.helperScore = helperScore;
        stage = new Stage ();

        enemiesList = new ArrayList<>();
        enemiesToRemove = new Array<>();
        zonaFactories = new HashMap<>();
        zonaFactories.put(0, new WaddleDeeFactory());                   //se define el factory a usar por grupo
        zonaFactories.put(1, new BrontoBurdFactory());
        zonaFactories.put(2, new BrontoBurdFactory());
        zonaFactories.put(3, new WaddleDeeFactory());
        zonaFactories.put(4, new HotHeadFactory());
        zonaFactories.put(5, new WaddleDooFactory());
        for (int i = 0; i < enemyZonaCoordenadas.length; i++) {         //se crean ls grupos
            enemiesList.add(new ArrayList<>());
        }

        firesToRemove = new Array<>();

        attacks = new ArrayList<>();
        attacksToRemove = new Array<>();

        keys = new ArrayList<>();
        keysToRemove = new Array<>();

        font = new BitmapFont();
        font.setColor(Color.WHITE);
    }

    public void miniGame() {
        minigameManager = new MinigameManager(kirby);                                                                   //toma los datos del kirby

        if (nivel == 1){
            GamePanelCulebrita minigame1 = new GamePanelCulebrita(main, minigameManager);
            main.setScreen(minigame1);
        }else if (nivel == 2){
            //GamePanelViejita minigame2 = new GamePanelViejita(main, minigameManager);
            GamePanelLaberinto minigame2 = new GamePanelLaberinto(main, minigameManager);
            main.setScreen(minigame2);
        }
    }

    @Override
    public void show() {
        super.show();
        world = new World (new Vector2(0, 0), true);
        world.setContactListener(this);                                 // listener de contactos

        kirby = new Kirby(world, main, initialX, initialY);
        setScoreInGame(helperScore);

        stage.addActor(kirby);
        cam = (OrthographicCamera) stage.getCamera();
        cam.zoom = 0.34f;

        //UI
        //loadAssetsKey();

        createEnemies();

        //items
        createKeys();
        createDoor();

        tiledMapHelper = new TiledMapHelper();
        map = tiledMapHelper.setupmap();
        bdr = new Box2DDebugRenderer();

        //map elements
        createPlatformMoved();
        createFloor();
        spikes = new Spikes(world, map, 4);
        hole = new Hole(world, map, 5);
        platformStatic = new Platform(world, map, 6);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.53f, 0.81f, 0.92f, 1);
        world.step(1/60f,6,2);

        loadAssetsKey();

        if (puedoResetKirby) {
            kirby.resetPosition();
            puedoResetKirby = false;
        }

        cloud();
        star();

        deleteEnemies();
        deleteKeys();
        deleteAttacks();

        updateMovementPlataforms(delta);
        stage.act();
        update();
        map.render();
        bdr.render(world, cam.combined);
        stage.draw();

        renderKeyContador();
        renderScore();

        //IU
        uiStage.act(delta);
        uiStage.draw();
    }

    private void renderKeyContador() {
        Batch batch = stage.getBatch();
        batch.begin();

        batch.setProjectionMatrix(stage.getCamera().combined);

        float baseX = Math.round(cam.position.x - cam.viewportWidth/2 * cam.zoom + 10);
        float baseY = Math.round(cam.position.y + cam.viewportHeight/2 * cam.zoom - 20);

        // Dibuja las llaves
        for (int i = 0; i < TOTAL_KEYS; i++) {
            keyIconSprite.setPosition(baseX + (i * 20), baseY + 2);
            keyIconSprite.setAlpha(i < keysCollected ? 1f : 0.5f); // llaves no recolectadas transparentes
            keyIconSprite.draw(batch);
        }

        // Dibuja el contador
        font.getData().setScale(.5f);
        font.draw(batch, keysCollected + "/" + TOTAL_KEYS,
            baseX + (TOTAL_KEYS * 20) + 5,
            baseY + 9);

        batch.end();
    }

    private void renderScore() {
        Batch batch = stage.getBatch();
        batch.begin();

        float scoreX = Math.round( cam.position.x + cam.viewportWidth/2 * cam.zoom - 65);
        float scoreY = Math.round(cam.position.y + cam.viewportHeight/2 * cam.zoom - 10);
        font.getData().setScale(.5f);
        font.draw(batch, "Score: " + kirby.getCurrentScore(),
            scoreX,
            scoreY);

        batch.end();
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

            if (enemy instanceof HotHead || enemy instanceof WaddleDoo){
                enemy.setKirby(kirby);
            }

            stage.addActor(enemy);
            zonaEnemies.add(enemy);
        }
    }

    private void loadEnemies() {

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
                enemy.setFlipX(kirby.getFlipX());
                enemy.setState(EnumStateEnemy.DIE);
                kirby.addPointsPerEnemy(enemy);                 //agrega puntos por eliminacion de enemy
                enemiesToRemove.add(enemy);
            }
        } else {
            //kirby recibe daño
            if (kirby.getPoder()) {
                kirby.setcurrentEnemy(null);
            }
            kirby.setDamageFire(false);
            kirby.setState(EnumStates.DAMAGE);
            kirby.setAnimation(EnumStates.DAMAGE);
            kirby.subPointsPerItem(EnumItemType.ENEMY);
        }
    }

//objetos mapa
    private void createDoor() {
        door = new Door(world, main, 100, 1050, true);
        stage.addActor(door);
    }

    public void createFloor(){
        for (int i = 2; i < 4; i++) {
            floor = new Floor(world, map, i);
        }
    }

    private void createPlatformMoved() {
        platforms = new ArrayList<>();
        PlatformMoved verticalPlatform = new PlatformMoved(world, main, 400, 1010, true);
        platforms.add(verticalPlatform);
        stage.addActor(verticalPlatform);

        PlatformMoved horizontalPlatform = new PlatformMoved(world, main, 800, 1010, false);
        platforms.add(horizontalPlatform);
        stage.addActor(horizontalPlatform);

        PlatformMoved horizontalPlatform1 = new PlatformMoved(world, main, 1000, 1010, false);
        platforms.add(horizontalPlatform1);
        stage.addActor(horizontalPlatform1);

    }

    private void updateMovementPlataforms(float delta){
        for (PlatformMoved platform : platforms) {
            platform.act(delta);
        }
    }

//ITEMS
    //keys
    private void loadAssetsKey(){
        keyIconTexture = main.getManager().get("assets/art/sprites/spritesItems/Key.png");
        keyIconRegion = new TextureRegion(keyIconTexture, 32,32);
        keyIconSprite = new Sprite(keyIconRegion);
        keyIconSprite.setSize(16, 16);
    }

    private void createKeys(){
        Key key1 = new Key(world, main, 105, 2120);
        Key key2 = new Key(world, main, 110, 1080);
        Key key3 = new Key(world, main, 370, 460);
        Key key4 = new Key(world, main, 3750, 1440);
        Key key5 = new Key(world, main, 3740, 2070);

        keys.add(key1);
        keys.add(key2);
        keys.add(key3);
        keys.add(key4);
        keys.add(key5);

        // Añade las llaves al stage
        for (Key key : keys) {
            stage.addActor(key);
        }
    }

    private void deleteKeys(){
        for (Key key : keysToRemove) {
            world.destroyBody(key.getBody());
            key.remove();
        }
        keysToRemove.clear();
    }

    private void manejadorKeyColition(){
        for (Key key : keys) {
            if (key.isCollected() && !keysToRemove.contains(key, true)) {
                keysToRemove.add(key);
            }
        }
        keysCollected++;
        kirby.addPointsPerItems(EnumItemType.KEY);
        //un  mensaje en pantalla ir en pantalla
    }

//nube
    public void cloud () {
        lastCloudCreationTime += Gdx.graphics.getDeltaTime();
        if (lastCloudCreationTime >= 1f && kirby.getCloud() != null) {
            stage.addActor(kirby.getCloud());
        }
    }

    public void star () {
        lastCloudCreationTime += Gdx.graphics.getDeltaTime();
        if (lastCloudCreationTime >= 1f && kirby.getStar() != null) {
            stage.addActor(kirby.getStar());
        }
    }

    public void deleteAttacks(){
        for (Attack attack : attacksToRemove) {
            if (attack.getBody() != null) {
                world.destroyBody(attack.getBody());     // Destruye el cuerpo en Box2D
                attack.remove();                         // Elimina del stage
                attacks.remove(attack);
            }
        }
        attacksToRemove.clear();
    }

    private void manejadorAttackEnemyCollision(Attack attack, Enemy enemy) {
        if (!attacksToRemove.contains(attack, true)) {                                       // en lo que haga contacto desaparece menos el Beam
            if (!(attack instanceof Beam)) {
                attacksToRemove.add(attack);
            }
        }

        if (!enemiesToRemove.contains(enemy, true)) {
            if (attack instanceof Fire || attack instanceof Beam) {
                enemy.setFlipX(attack.getSentido());
            }
            else {
                enemy.setFlipX(!attack.getSentido());
            }
            enemy.setState(EnumStateEnemy.DIE);
            kirby.addPointsPerEnemy(enemy);                 //agrega puntos por eliminacion de enemy
            enemiesToRemove.add(enemy);
        }
    }

    private void manejadorAttackKirbyCollision(Attack attack){

        if (attack.getAttackOfKirby()){                                                          //si el ataque es el kirby no pasa nada
            return;
        }

        //animaciones de muerte del kirby y debe ir logica de puntos
        if (attack instanceof Fire) {
            kirby.setDamageFire(true);
        }

        else{
            kirby.setDamageFire(false);
        }
        if (kirby.getPoder()){
            kirby.setcurrentEnemy(null);
        }
        kirby.setFlipx(!attack.getSentido());
        kirby.setState(EnumStates.DAMAGE);
        kirby.setAnimation(EnumStates.DAMAGE);
        kirby.subPointsPerItem(EnumItemType.ATTACK);               //resta punto por ser atacado por un enemigo

        if (!attacksToRemove.contains(attack, true)) {                                  // en lo que haga contacto desaparece
            if (!(attack instanceof Beam)) {
                attacksToRemove.add(attack);
            }
        }
    }

    private void manejadorEnemyKirbyAbsorbCollition(Kirby kirby, Enemy enemy) {
        kirby.setcurrentEnemy(enemy);
        if (!enemiesToRemove.contains(enemy, true)) {
            enemy.setState(EnumStateEnemy.DIE2);
            kirby.addPointsPerEnemy(enemy);
            enemiesToRemove.add(enemy);

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
                manejadorKeyColition();
            }
        }

        if ((userDataA instanceof Kirby && userDataB instanceof Door) ||
            (userDataB instanceof Kirby && userDataA instanceof Door)) {

            Door door = (Door) (userDataA instanceof Door ? userDataA : userDataB);
            if (keysCollected >= 0 ) {
                levelCompleted = true;
                kirby.addPointsPerItems(EnumItemType.DOOR);
                miniGame();
            }
        }

//ENEMIES
        //colision kirby-enemies y dash
        if ((userDataA instanceof Kirby && userDataB instanceof Enemy) ||
            (userDataB instanceof Kirby && userDataA instanceof Enemy)) {

            Kirby kirby = (Kirby) (userDataA instanceof Kirby ? userDataA : userDataB);
            Enemy enemy = (Enemy) (userDataA instanceof Enemy ? userDataA : userDataB);

            if (kirby.getcurrentState() instanceof AbsorbStateKirby) {
                manejadorEnemyKirbyAbsorbCollition(kirby, enemy);

            }
            else {
                // debe llamar tambien a manejadorEnemyColition aqui para la logica de los puntos perdidos
                manejadorEnemyColition(kirby, enemy);
            }
        }

        // colision attack-enemies
        if ((userDataA instanceof Attack && userDataB instanceof Enemy) ||
            (userDataB instanceof Attack && userDataA instanceof Enemy)) {

            Attack attack = (Attack) (userDataA instanceof Attack ? userDataA : userDataB);
            if (!attack.getAttackOfKirby()) return;

            Enemy enemy = (Enemy) (userDataA instanceof Enemy ? userDataA : userDataB);

            manejadorAttackEnemyCollision(attack, enemy);
        }

        // colision ataque-kirby
        if ((userDataA instanceof Attack && userDataB instanceof Kirby) ||
            (userDataB instanceof Attack && userDataA instanceof Kirby)) {

            Attack attack = (Attack) (userDataA instanceof Attack ? userDataA : userDataB);
            manejadorAttackKirbyCollision(attack);
        }

        if ((userDataA instanceof SensorKirby && userDataB instanceof Enemy) ||
            (userDataB instanceof SensorKirby && userDataA instanceof Enemy)) {

            Enemy enemy = (Enemy) (userDataA instanceof Enemy ? userDataA : userDataB);
            enemy.setKirby(kirby);
            enemy.setState(EnumStateEnemy.ATRACT);
        }

//WORLD
        // colision con el suelo
        if (setContact(contact, this.kirby, "suelo")
            || setContact(contact, this.kirby, "Plataforma")) {
            kirby.setColisionSuelo(true);
        }


        if (setContact(contact, this.kirby, "spikes")) {
            kirby.setDamageFire(false);
            kirby.setState(EnumStates.DAMAGE);
            kirby.setAnimation(EnumStates.DAMAGE);
            kirby.subPointsPerItem(EnumItemType.SPIKES);
        }

        if ((setContact(contact, this.kirby, "Hole"))) {
            puedoResetKirby = true;
            kirby.setState(EnumStates.STAY);
            kirby.setAnimation(EnumStates.STAY);
            kirby.subPointsPerItem(EnumItemType.HOLE);
        } else {
            puedoResetKirby = false;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Object userDataA = fixtureA.getUserData();
        Object userDataB = fixtureB.getUserData();

        if (setContact(contact, this.kirby, "suelo")
            || setContact(contact, this.kirby, "Plataforma")) {
            kirby.setColisionSuelo(false);
        }

        if ((userDataA instanceof SensorKirby && userDataB instanceof Enemy) ||
            (userDataB instanceof SensorKirby && userDataA instanceof Enemy)) {

            Enemy enemy = (Enemy) (userDataA instanceof Enemy ? userDataA : userDataB);
            enemy.setState(EnumStateEnemy.WALK);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    public Kirby getKirby() {
        return kirby;
    }

    public void setScoreInGame(int helperScore){
        kirby.setCurrentScore(helperScore);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        // Dispose Stage and Box2D World
        if (stage != null) {
            stage.dispose();
        }
        if (world != null) {
            world.dispose();
        }
        if (map != null) {
            map.dispose();
        }
        if (bdr != null) {
            bdr.dispose();
        }

        // Dispose Kirby
        if (kirby != null) {
            kirby.dispose();
        }

        // Dispose Enemies
        if (enemiesList != null) {
            for (ArrayList<Enemy> zoneEnemies : enemiesList) {
                for (Enemy enemy : zoneEnemies) {
                    if (enemy != null) {
                        enemy.dispose();
                    }
                }
                zoneEnemies.clear();
            }
            enemiesList.clear();
        }

        // Dispose Keys
        if (keys != null) {
            for (Key key : keys) {
                if (key != null) {
                    key.dispose();
                }
            }
            keys.clear();
        }

        // Dispose Platforms
        if (platforms != null) {
            for (PlatformMoved platform : platforms) {
                if (platform != null) {
                    platform.dispose();
                }
            }
            platforms.clear();
        }

        // Dispose UI elements
        if (font != null) {
            font.dispose();
        }
        if (keyIconTexture != null) {
            keyIconTexture.dispose();
        }
        if (door != null) {
            door.dispose();
        }

        // Clear collections
        if (enemiesToRemove != null) enemiesToRemove.clear();
        if (keysToRemove != null) keysToRemove.clear();
        if (attacksToRemove != null) attacksToRemove.clear();
        if (firesToRemove != null) firesToRemove.clear();
        if (attacks != null) attacks.clear();
    }

}

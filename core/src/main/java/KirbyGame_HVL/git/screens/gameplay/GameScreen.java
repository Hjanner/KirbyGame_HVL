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
import KirbyGame_HVL.git.entities.enemis.brontoBurt.BrontoBurdFactory;
import KirbyGame_HVL.git.entities.enemis.brontoBurt.BrontoBurt;
import KirbyGame_HVL.git.entities.enemis.hotHead.HotHead;
import KirbyGame_HVL.git.entities.enemis.hotHead.HotHeadFactory;
import KirbyGame_HVL.git.entities.enemis.waddleDee.WaddleDee;
import KirbyGame_HVL.git.entities.enemis.waddleDee.WaddleDeeFactory;
import KirbyGame_HVL.git.entities.enemis.waddleDoo.WaddleDoo;
import KirbyGame_HVL.git.entities.enemis.waddleDoo.WaddleDooFactory;
import KirbyGame_HVL.git.entities.items.*;
import KirbyGame_HVL.git.entities.player.Kirby;
import KirbyGame_HVL.git.screens.gameover.GameOverScreen;
import KirbyGame_HVL.git.screens.mainmenu.Pantalla;
import KirbyGame_HVL.git.systems.MinigameManager;
import KirbyGame_HVL.git.screens.minigames.culebrita.GamePanelCulebrita;
import KirbyGame_HVL.git.screens.minigames.laberinto.GamePanelLaberinto;
import KirbyGame_HVL.git.utils.helpers.TiledMapHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        {{2050, 816}, {1850, 740}, {1650, 740}, {2010, 696}, {2055, 624}, {1982, 528}, {1982, 528}, {1971, 244}, {2182, 327}},           // zona spanw k - WaddleDees
        {{1750, 130}, {1400, 140}, {1050, 110}, {700, 130}, {100, 110}, {2250, 130}, {2600, 140}, {2950, 130}, {2550, 916}, {2400, 916}},                        // z 2 - w dee piso
        {{1750, 1373}, {1400, 1373}, {1050, 1373}, {700, 1276}, {100, 1276}, {2000, 150}, {2400, 140}, {2600, 150}, {2450, 1020}, {2650, 1035}, {2000, 340}, {2000, 680}, {2000, 1070}, {2005, 1150}, {2005, 1250}, {2005, 1050}},                        // z 3 - BrontoBurts spawn piso
        {{1200, 1350}, {1250, 1040}, {1300, 747}, {1765, 1373}, {1665, 1373}, {2050, 1800}, {2251, 1732}, {2351, 1732}, {2551, 1732}},                      // z 4 - WaddleDees
        {{1450, 740}, {2750, 916}, {1500, 1222}, {2251, 1732}, {3036, 1732}, {3216, 77}, {109, 1732}, {220, 1829}, {988, 1780}, {1621, 1804}},                         //g 5 - HotHeads
        {{2600, 1300}, {1820, 461}, {600, 1010}, {2954, 1732}, {3780, 77}, {2780 , 77}, {1433 , 1253}, {1074, 1276}, {394, 1732}, {250, 2092}, {2224, 1732}, {3765, 77}} ,                         // z 6 - WaddleDoo
        {{2950, 1832}, {2850, 1802}, {600, 1010}, {2954, 1732}, {3350, 1600}, {3330, 1500}, {3340, 1400}, {600, 1460}, {650, 1460}, {650, 1600}, {650, 1860}, {231, 1990}, {220, 1980}},//bronto
        {{3650, 1360}, {3650, 1160}, {3700, 960}, {3650, 860}, {3850, 860}, {3650, 460}, {3650, 460}, {3650, 1160}, {3750, 900}, {3550, 840}, {3950, 9200}, {3450, 360}, {3650, 460}}, //bronto
        {{ 898, 1276}, {698, 1276}, {1237, 1804}, {1337, 1804}, {1400, 1804},  {1800, 1804}, {1900, 1804}},  //waddel
        {{2005, 1850}, {2105, 1870}, {2205, 1840}, {2720, 1350}, {2720, 1450}, {2720, 1250}, {2720, 1750}, {2720, 1650}, {2720, 1550}, {3512, 700}}
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
    private Music soundTrack;

    private SpriteBatch batch;
    private Texture brickTexture;
    private GameOverScreen gameOverScreen;
    private int waddleDeeCount, hotHeadCount, waddleDooCount, brontoBurtCount = 0;

    public GameScreen(Main main, float initialX, float initialY, int helperScore, int nivel) {
        super(main);
        this.initialX = initialX ;
        this.initialY = initialY ;
        this.nivel = nivel;
        this.main = main;
        this.helperScore = helperScore;
        soundTrack = Gdx.audio.newMusic(Gdx.files.internal("assets/audio/music/starforge-saga-281379.mp3"));
        soundTrack.setVolume(0.3f);
        stage = new Stage ();

        enemiesList = new ArrayList<>();
        enemiesToRemove = new Array<>();
        zonaFactories = new HashMap<>();
        zonaFactories.put(0, new WaddleDeeFactory());                   //se define el factory a usar por grupo
        zonaFactories.put(1, new WaddleDeeFactory());
        zonaFactories.put(2, new BrontoBurdFactory());
        zonaFactories.put(3, new WaddleDeeFactory());
        zonaFactories.put(4, new HotHeadFactory());
        zonaFactories.put(5, new WaddleDooFactory());
        zonaFactories.put(6, new BrontoBurdFactory());
        zonaFactories.put(7, new BrontoBurdFactory());
        zonaFactories.put(8, new WaddleDooFactory());
        zonaFactories.put(9, new BrontoBurdFactory());

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

        if (nivel == 1 && keysCollected == 5){
            GamePanelCulebrita minigame1 = new GamePanelCulebrita(main, minigameManager);
            main.setScreen(minigame1);
        }else if (nivel == 2 && waddleDeeCount >= 5 && waddleDooCount >= 5 && brontoBurtCount >= 5 && hotHeadCount >= 5){
            GamePanelLaberinto minigame2 = new GamePanelLaberinto(main, minigameManager);
            main.setScreen(minigame2);
        } else if (nivel == 3) {
            endGame();
        }
    }

    public void endGame() {
        soundTrack.stop();
        GameOverScreen gameOver = new GameOverScreen(main, kirby);
        main.setScreen(gameOver);
    }

    @Override
    public void show() {
        super.show();
        soundTrack.setLooping(true);
        soundTrack.play();
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

        batch = new SpriteBatch();
        brickTexture = new Texture(Gdx.files.internal("assets/art/minijuegos/logito.png"));

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

        if (nivel == 1 ) renderKeyContador();
        else if (nivel == 2) renderEnemiesContador();


        renderScore();
        renderName();

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

    private void renderEnemiesContador() {
        Batch batch = stage.getBatch();
        batch.begin();
        batch.setProjectionMatrix(stage.getCamera().combined);

        float baseX = Math.round(cam.position.x - cam.viewportWidth/2 * cam.zoom + 10);
        float baseY = Math.round(cam.position.y + cam.viewportHeight/2 * cam.zoom - 10);
        float espacioIconos = 10;
        float espacioIconosFilas = 10;

        // Renderizar contadores para cada tipo de enemigo
        renderEnemyTypeRow(batch, "WaddleDee", waddleDeeCount, baseX, baseY, 0);
        renderEnemyTypeRow(batch, "HotHead", hotHeadCount, baseX, baseY - espacioIconosFilas, 1);
        renderEnemyTypeRow(batch, "WaddleDoo", waddleDooCount, baseX, baseY - (espacioIconosFilas * 2), 2);
        renderEnemyTypeRow(batch, "BrontoBurt", brontoBurtCount, baseX, baseY - (espacioIconosFilas * 3), 3);

        batch.end();
    }

    private void renderEnemyTypeRow(Batch batch, String enemyType, int count, float baseX, float baseY, int row) {
        float espacio = 15;
        int maxEnemies = 5;

        font.getData().setScale(0.4f);
        font.draw(batch, enemyType + ": ", baseX, baseY);

        // pos inicial para los iconos depues del texto
        float iconsStartX = baseX + 40;

        Texture enemyTexture;
        TextureRegion enemyRegion;
        Sprite enemySprite;

        switch (enemyType) {
            case "WaddleDoo":
                enemyTexture = main.getManager().get("assets/art/sprites/spritesWaddleDoo/WaddleDooWalk.png");
                enemyRegion = new TextureRegion(enemyTexture, 0, 0, 32, 32); // Ajusta coordenadas según el spritesheet
                break;
            case "HotHead":
                enemyTexture = main.getManager().get("assets/art/sprites/spritesHotHead/HotHeadWalk.png");
                enemyRegion = new TextureRegion(enemyTexture, 0, 0, 32, 32);
                break;
            case "WaddleDee":
                enemyTexture = main.getManager().get("assets/art/sprites/spritesWaddleDee/WaddleDeeWalk.png");
                enemyRegion = new TextureRegion(enemyTexture, 0, 0, 32, 32);
                break;
            case "BrontoBurt":
                enemyTexture = main.getManager().get("assets/art/sprites/spritesBrontoBurt/BrontoBurtFly.png");
                enemyRegion = new TextureRegion(enemyTexture, 0, 0, 32, 32);
                break;
            default:
                enemyTexture = keyIconTexture;
                enemyRegion = keyIconRegion;
                break;
        }

        enemySprite = new Sprite(enemyRegion);
        enemySprite.setSize(10, 10);

        // Dibuja los iconos de enemigos
        for (int i = 0; i < maxEnemies; i++) {
            enemySprite.setPosition(iconsStartX + (i * espacio), baseY - 8);
            enemySprite.setAlpha(i < count ? 1f : 0.5f); // enemigos no eliminados transparentes
            enemySprite.draw(batch);
        }


        font.draw(batch, ((count >= 5) ? 5 : count) + "/" + maxEnemies,
            iconsStartX + (maxEnemies * espacio) + 5,
            baseY);
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

    private void renderName() {
        Batch batch = stage.getBatch();
        batch.begin();
        font.getData().setScale(0.34f);
        font.draw(batch, kirby.getName(),
            kirby.getBody().getPosition().x - 10,
            kirby.getBody().getPosition().y + 14);

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

    private void updateCountEnemiesDeleted(Enemy enemy){
        if (enemy instanceof WaddleDee) waddleDeeCount++;
        else if (enemy instanceof HotHead) hotHeadCount++;
        else if (enemy instanceof WaddleDoo) waddleDooCount++;
        else if (enemy instanceof BrontoBurt) brontoBurtCount++;

    }

    private void deleteEnemies() {
        for (ArrayList<Enemy> zoneEnemies : enemiesList) {
            for (Enemy enemy : new ArrayList<>(zoneEnemies)) {                          //  una copia para evitar errores
                if (enemiesToRemove.contains(enemy, true)) {
                    if (nivel == 2) updateCountEnemiesDeleted(enemy);
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
        int posx = 2670, posy = 1285;

        if(nivel == 3){
            posx = 3780;
            posy = 77;
        }

        door = new Door(world, main, posx, posy, true);
        stage.addActor(door);
    }

    public void createFloor(){
        for (int i = 2; i < 4; i++) {
            floor = new Floor(world, map, i);
        }
    }

    private void createPlatformMoved() {
        platforms = new ArrayList<>();
        PlatformMoved verticalPlatform = new PlatformMoved(world, main, 3475, 1300, true);
        platforms.add(verticalPlatform);
        stage.addActor(verticalPlatform);

        PlatformMoved horizontalPlatform = new PlatformMoved(world, main, 3660, 1300, false);
        platforms.add(horizontalPlatform);
        stage.addActor(horizontalPlatform);

        PlatformMoved horizontalPlatform1 = new PlatformMoved(world, main, 3475, 1000, false);
        platforms.add(horizontalPlatform1);
        stage.addActor(horizontalPlatform1);

        PlatformMoved horizontalPlatform2 = new PlatformMoved(world, main, 3660, 1000, false);
        platforms.add(horizontalPlatform2);
        stage.addActor(horizontalPlatform2);

        PlatformMoved horizontalPlatform3 = new PlatformMoved(world, main, 3475, 515, false);
        platforms.add(horizontalPlatform3);
        stage.addActor(horizontalPlatform3);

        PlatformMoved horizontalPlatform4 = new PlatformMoved(world, main, 3660, 515, false);
        platforms.add(horizontalPlatform4);
        stage.addActor(horizontalPlatform4);
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
                kirby.addPointsPerItems(EnumItemType.DOOR);
                miniGame();
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
            kirby.setcurrentEnemy(null);
            kirby.setState(EnumStates.DAMAGE);
            kirby.setAnimation(EnumStates.DAMAGE);
            kirby.subPointsPerItem(EnumItemType.SPIKES);
        }

        if ((setContact(contact, this.kirby, "Hole"))) {
            endGame();
            /*puedoResetKirby = true;
            kirby.setState(EnumStates.STAY);
            kirby.setAnimation(EnumStates.STAY);
            kirby.subPointsPerItem(EnumItemType.HOLE);*/
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

        if ((setContact(contact, this.kirby, "Hole"))) {
            puedoResetKirby = false;
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

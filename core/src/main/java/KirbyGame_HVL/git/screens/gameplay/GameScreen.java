package KirbyGame_HVL.git.screens.gameplay;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.Constants.Constants;
import KirbyGame_HVL.git.entities.items.Floor;
import KirbyGame_HVL.git.entities.items.Spikes;
import KirbyGame_HVL.git.entities.player.Kirby;
import KirbyGame_HVL.git.entities.States.EnumStates;
import KirbyGame_HVL.git.netgdx.KirbyState;
import KirbyGame_HVL.git.netgdx.client.Client;
import KirbyGame_HVL.git.screens.mainmenu.Pantalla;
import KirbyGame_HVL.git.utils.helpers.TiledMapHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends Pantalla implements Client.ClientStateListener {

    private Stage stage;
    private World world;
    private Kirby localKirby;
    private Kirby remoteKirby;
    private Main main;
    private OrthographicCamera cam;
    private TiledMapHelper tiledMapHelper;
    private OrthogonalTiledMapRenderer map;
    private Box2DDebugRenderer bdr;
    private Floor floor;
    private Spikes spikes;

    private Client client;
    private float stateSyncTimer = 0;
    private static final float STATE_SYNC_INTERVAL = 0.1f; // Send state 10 times per second
    private List<Kirby> players;


    public GameScreen(Main main, String host, int port) {
        super(main);
        this.main = main;
        stage = new Stage();

        client = new Client(host, port, this);

    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.8f), true);
        players = new ArrayList<>();

        localKirby = new Kirby(world, main);
        stage.addActor(localKirby);
        players.add(localKirby);

        client.start();                                                                                                 //inicia la conexion con el client

        cam = (OrthographicCamera) stage.getCamera();
        cam.zoom = 0.65f;

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

        world.step(1/60f, 6, 2);

        // Actualizar todos los Kirbys en la lista
//        for (Kirby kirby : players) {
//            update();
//            updateKirby(kirby);
//        }

        stage.act(delta);

        update();

        // renderizar el mapa y depurador
        map.render();
        bdr.render(world, cam.combined);

//        stage.getBatch().begin(); // Llama explícitamente a begin
//        // Dibujar a todos los Kirbys en la pantalla
//        for (Kirby kirby : players) {
//            kirby.draw(stage.getBatch(), 1); // Dibujar con transparencia completa
//        }
//        stage.getBatch().end();   // Termina el bloque SpriteBatch


        stage.draw();

        // Sync game state periodically
        stateSyncTimer += delta;
        if (stateSyncTimer >= STATE_SYNC_INTERVAL) {
            sendLocalKirbyState();
            stateSyncTimer = 0;
        }
    }

    private void sendLocalKirbyState() {
        if (client != null && localKirby != null) {
            KirbyState state = new KirbyState(localKirby);
            client.sendKirbyState(state);
        }
    }

    //recibe el estado de un Kirby remoto
    @Override
    public void onKirbyStateReceived(KirbyState state) {
        // Lazy initialization of remote Kirby
        if (remoteKirby == null) {
            remoteKirby = new Kirby(world, main);
            stage.addActor(remoteKirby);
            players.add(remoteKirby);
        }

        // Update remote Kirby's state
        if (remoteKirby != null) {
            // Convert pixel coordinates to Box2D meters
            float xInMeters = state.x / 32f;  // Adjust divisor based on your pixel-to-meter ratio
            float yInMeters = state.y / 32f;

            // Set body position directly
            remoteKirby.getBody().setTransform(xInMeters, yInMeters, 0);

            // Set animation
            try {
                remoteKirby.setAnimation(EnumStates.valueOf(state.currentAnimation));
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid animation state: " + state.currentAnimation);
            }

            // Set sprite flip
            remoteKirby.setFlipX(state.flipX);
        }
    }

    public void update() {
        cam.position.set(localKirby.getFixture().getBody().getPosition(), 0);
        cam.update();
        map.setView(cam);
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        localKirby.dispose();

        if (remoteKirby != null) {
            players.remove(remoteKirby);
            stage.getActors().removeValue(remoteKirby, true); // Remover del Stage
            remoteKirby.dispose();
        }

        if (client != null) {
            client.dispose();
        }
    }

//    private void updateKirby(Kirby kirby) {
//        // Aquí puedes añadir lógica específica para animaciones o colisiones
//        kirby.update(); // Metodo personalizado en Kirby para actualizar su estado
//    }
}

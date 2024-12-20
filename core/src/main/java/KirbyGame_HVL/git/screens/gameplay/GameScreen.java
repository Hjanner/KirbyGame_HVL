package KirbyGame_HVL.git.screens.gameplay;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.utils.helpers.Constants;
import KirbyGame_HVL.git.entities.items.Floor;
import KirbyGame_HVL.git.entities.items.Spikes;
import KirbyGame_HVL.git.entities.player.Kirby;
import KirbyGame_HVL.git.entities.States.EnumStates;
import KirbyGame_HVL.git.netgdx.KirbyState;
import KirbyGame_HVL.git.netgdx.client.Client;
import KirbyGame_HVL.git.netgdx.server.Server;
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
import com.badlogic.gdx.utils.Json;

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

    private Server server;
    private Client client;
    private float stateSyncTimer = 0;
    private static final float STATE_SYNC_INTERVAL = 0.1f; // Send state 10 times per second
    private List<Kirby> players;

    public GameScreen(Main main, String host, int port) {
        super(main);
        this.main = main;
        //stage = new Stage();
        client = new Client(host, port, this);
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.8f), true);
        players = new ArrayList<>();

        stage = new Stage();

        //confi camara
        cam = (OrthographicCamera) stage.getCamera();
        cam.zoom = 0.65f;

        stage.getViewport().setCamera(cam);

        //creo el kirbylocal
        localKirby = new Kirby(world, main);
        stage.addActor(localKirby);
        players.add(localKirby);

        client.start();                                                                                                 //inicia la conexion con el client

        Kirby remoteKirby1 = new Kirby(world, main);
        Kirby remoteKirby2 = new Kirby(world, main);
        players.add(remoteKirby1);
        players.add(remoteKirby2);
        stage.addActor(remoteKirby1);
        stage.addActor(remoteKirby2);





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

        //actualiza kyrbi y camara
        updateAllKirbys(delta);
        updateCamera();

        // Actualizar las entidades
        //float delta = Gdx.graphics.getDeltaTime();
        for (Kirby kirby : players) {
            kirby.act(Gdx.graphics.getDeltaTime());
        }

        // Dibujar las entidades
        stage.getBatch().begin();
        //stabatch.begin();
        for (Kirby kirby : players) {
            kirby.draw( stage.getBatch()  , 1);
        }
        //batch.end();
        stage.getBatch().end();

        stage.getBatch().setProjectionMatrix(cam.combined);

        //render mapa
        map.setView(cam);
        map.render();

        //rederiza actores
        stage.act(delta);
        stage.draw();

        bdr.render(world, cam.combined);

        // sincronizar estado
        stateSyncTimer += delta;
        if (stateSyncTimer >= STATE_SYNC_INTERVAL) {
            sendLocalKirbyState();
            stateSyncTimer = 0;
        }
    }

    @Override
    public void onKirbyStateReceived(KirbyState state) {
        if (remoteKirby == null) {
            remoteKirby = new Kirby(world, main);
            stage.addActor(remoteKirby);
            players.add(remoteKirby);
            System.out.println("Remote Kirby created and added to stage");
            pause();
        }

        // Convertir coordenadas
        float xInMeters = state.x / Constants.PPM;
        float yInMeters = state.y / Constants.PPM;

        // Actualizar el Kirby remoto en el hilo principal
        Gdx.app.postRunnable(() -> {
            remoteKirby.getBody().setTransform(xInMeters, yInMeters, 0);
            remoteKirby.setAnimation(EnumStates.valueOf(state.currentAnimation));
            remoteKirby.setFlipX(state.flipX);

            // Forzar actualización del sprite
            remoteKirby.updateAnimation(Gdx.graphics.getDeltaTime());
            System.out.println("Remote Kirby updated: " + xInMeters + ", " + yInMeters);
        });
    }

    // envia estado del Kirby local al servidor
    private void sendLocalKirbyState() {
        if (client != null && localKirby != null) {
            KirbyState state = new KirbyState(localKirby);
            client.sendKirbyState(state);
        }
    }

    private void updateAllKirbys(float delta) {
        for (Kirby kirby : players) {
            if (kirby != null) {
                if (kirby == localKirby) {
                    // Actualizar lógica del Kirby local
                    kirby.update(delta);
                } else {
                    // Para el Kirby remoto, solo actualizamos la animación
                    kirby.updateAnimation(delta);
                }
            }
        }
    }

    private void updateCamera() {
        cam.position.set(localKirby.getFixture().getBody().getPosition(), 0);
        cam.update();
        map.setView(cam);
    }


    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
        if (world != null) world.dispose();
        if (localKirby != null) localKirby.dispose();
        if (remoteKirby != null) {
            players.remove(remoteKirby);
            stage.getActors().removeValue(remoteKirby, true);
            remoteKirby.dispose();
        }
        if (client != null) client.dispose();
        if (map != null) map.dispose();
    }

}

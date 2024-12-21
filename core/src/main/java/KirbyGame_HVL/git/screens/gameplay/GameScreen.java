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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private boolean isHost;
    private Map<String, Kirby> kirbys;

    public GameScreen(Main main, String host, int port, boolean isHost) {
        super(main);
        this.main = main;
        this.isHost = isHost;
        this.kirbys = new HashMap<>();



        if (isHost) {
            // El primer jugador crea el servidor
            server = new Server(port);
            server.start();
        }

        // Todos los jugadores crean un cliente
        client = new Client(host, port, this);
        client.start();
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.8f), true);
        stage = new Stage();

        // Crear Kirby local
        localKirby = new Kirby(world, main);
        kirbys.put(localKirby.getId(), localKirby);
        stage.addActor(localKirby);

        // Configuración inicial
        setupCamera();
        setupMap();

    }

    @Override
    public void onKirbyStateReceived(KirbyState state) {
        Gdx.app.log("GameScreen", "Received state for Kirby: " + state.getId());

        Gdx.app.postRunnable(() -> {
            // Si es nuestro Kirby local, ignoramos la actualización
            if (localKirby != null && state.getId().equals(localKirby.getId())) {
                return;
            }

            // Buscar o crear Kirby remoto
            Kirby remoteKirby = kirbys.get(state.getId());
            if (remoteKirby == null) {
                Gdx.app.log("GameScreen", "Creating new remote Kirby: " + state.getId());
                remoteKirby = new Kirby(world, main);
                kirbys.put(state.getId(), remoteKirby);
                stage.addActor(remoteKirby);
            }

            // Actualizar estado
            updateKirbyState(remoteKirby, state);
        });
    }

    private void updateKirbyState(Kirby kirby, KirbyState state) {
        Gdx.app.log("GameScreen", "Updating Kirby state: " + state.getId() +
            " pos: " + state.getX() + "," + state.getY());

        // Convertir coordenadas de píxeles a metros
        float xInMeters = state.getX() / Constants.PPM;
        float yInMeters = state.getY() / Constants.PPM;

        kirby.getBody().setTransform(xInMeters, yInMeters, 0);
        kirby.setAnimation(EnumStates.valueOf(state.getCurrentAnimation()));
        kirby.setFlipX(state.isFlipX());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.53f, 0.81f, 0.92f, 1);

        world.step(1/60f, 6, 2);

        updateAndSyncStates(delta);

        renderWorld();


//        //actualiza kyrbi y camara
//        updateAllKirbys(delta);
//        updateCamera();

        // Actualizar las entidades
        //float delta = Gdx.graphics.getDeltaTime();
//        for (Kirby kirby : kirbys) {
//            kirby.act(Gdx.graphics.getDeltaTime());
//        }

//        // Dibujar las entidades
//        stage.getBatch().begin();
//        //stabatch.begin();
//        for (Kirby kirby : players) {
//            kirby.draw( stage.getBatch()  , 1);
//        }
//        //batch.end();
//        stage.getBatch().end();
//
//        stage.getBatch().setProjectionMatrix(cam.combined);
//
//        //render mapa
//        map.setView(cam);
//        map.render();
//
//        //rederiza actores
//        stage.act(delta);
//        stage.draw();
//
//        bdr.render(world, cam.combined);
//
//        // sincronizar estado
//        stateSyncTimer += delta;
//        if (stateSyncTimer >= STATE_SYNC_INTERVAL) {
//            sendLocalKirbyState();
//            stateSyncTimer = 0;
//        }

        stateSyncTimer += delta;
        if (stateSyncTimer >= STATE_SYNC_INTERVAL) {
            sendLocalKirbyState();
            stateSyncTimer = 0;
        }

    }

    private void updateAndSyncStates(float delta) {
        // Actualizar Kirby local
        if (localKirby != null) {
            localKirby.update(delta);
        }

        // Actualizar Kirbys remotos (solo animaciones)
        for (Kirby kirby : kirbys.values()) {
            if (kirby != localKirby) {
                kirby.updateAnimation(delta);
            }
        }

        // Enviar estado local
        stateSyncTimer += delta;
        if (stateSyncTimer >= STATE_SYNC_INTERVAL) {
            sendLocalKirbyState();
            stateSyncTimer = 0;
        }
    }

    private void renderWorld() {
        // Limpiar pantalla
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Actualizar cámara siguiendo al Kirby local
        updateCamera();

        // Renderizar mapa
        map.setView(cam);
        map.render();

        // Renderizar todos los Kirbys
        stage.getBatch().begin();
        for (Kirby kirby : kirbys.values()) {
            kirby.draw(stage.getBatch(), 1);
        }
        stage.getBatch().end();

        // Debug render si es necesario
        bdr.render(world, cam.combined);
    }
    // envia estado del Kirby local al servidor
    private void sendLocalKirbyState() {
        if (client != null && localKirby != null) {
            // Convertir coordenadas de metros a píxeles para enviar
            Vector2 position = localKirby.getBody().getPosition();
            KirbyState state = new KirbyState(
                localKirby.getId(),
                position.x * Constants.PPM,
                position.y * Constants.PPM,
                localKirby.getCurrentAnimationName(),
                localKirby.isFlipX(),
                "ffffffff" // color por defecto
            );
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

    private void setupCamera() {
        // Configuración de la cámara ortográfica
        cam = (OrthographicCamera) stage.getCamera();
        cam.zoom = 0.65f; // Ajusta el zoom de la cámara

        // Configurar viewport
        stage.getViewport().setCamera(cam);

        // Posición inicial de la cámara (puede ser la posición inicial del Kirby local)
        if (localKirby != null) {
            cam.position.set(localKirby.getBody().getPosition().x,
                localKirby.getBody().getPosition().y,
                0);
        }
        cam.update();
    }

    private void setupMap() {
        // Inicializar el helper del mapa
        tiledMapHelper = new TiledMapHelper();
        map = tiledMapHelper.setupmap();

        // Configurar el debug renderer para Box2D
        bdr = new Box2DDebugRenderer();

        // Crear los elementos del mapa
        for (int i = 2; i < 4; i++) {
            floor = new Floor(world, map, i);
        }
        spikes = new Spikes(world, map, 4);
    }

    // Función de actualización de cámara que sigue al Kirby local
    private void updateCamera() {
        if (localKirby != null) {
            // Obtener la posición actual del Kirby local
            Vector2 kirbyPosition = localKirby.getBody().getPosition();

            // Suavizar el movimiento de la cámara (interpolación lineal)
            float lerp = 0.1f;
            cam.position.x += (kirbyPosition.x - cam.position.x) * lerp;
            cam.position.y += (kirbyPosition.y - cam.position.y) * lerp;

            // Actualizar la cámara
            cam.update();
        }
    }


    @Override
    public void dispose() {
        for (Kirby kirby : kirbys.values()) {
            kirby.dispose();
        }
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

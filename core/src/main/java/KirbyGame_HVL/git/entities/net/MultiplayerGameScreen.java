package KirbyGame_HVL.git.entities.net;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.items.Floor;
import KirbyGame_HVL.git.entities.items.Spikes;
import KirbyGame_HVL.git.entities.player.Kirby;
import KirbyGame_HVL.git.entities.net.RemoteKirby;
import KirbyGame_HVL.git.entities.net.*;
import KirbyGame_HVL.git.screens.mainmenu.Pantalla;
import KirbyGame_HVL.git.utils.helpers.TiledMapHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.concurrent.ConcurrentHashMap;

public class MultiplayerGameScreen extends Pantalla implements NetworkMessageHandler {
    private Stage stage;
    private World world;
    private Kirby kirby;
    private OrthographicCamera cam;
    private Box2DDebugRenderer bdr;
    private GameClient client;
    private ConcurrentHashMap<String, RemoteKirby> remotePlayers;

    // Elementos del mapa
    private OrthogonalTiledMapRenderer map;
    private TiledMapHelper tiledMapHelper;
    private Floor floor;
    private Spikes spikes;

    public MultiplayerGameScreen(Main main, String host, int port) {
        super(main);
        stage = new Stage();
        remotePlayers = new ConcurrentHashMap<>();
        client = new GameClient(host, port, this);
    }

    @Override
    public void show() {
        // Inicialización del mundo y física
        world = new World(new Vector2(0, 0), true);
        kirby = new Kirby(world, main);
        stage.addActor(kirby);

        // Configuración de la cámara
        cam = (OrthographicCamera) stage.getCamera();
        cam.zoom = 0.65f;

        // Inicialización del mapa
        tiledMapHelper = new TiledMapHelper();
        map = tiledMapHelper.setupmap();

        // Inicialización del debug renderer
        bdr = new Box2DDebugRenderer();

        // Creación de pisos
        for (int i = 2; i < 4; i++) {
            floor = new Floor(world, map, i);
        }

        // Creación de spikes
        spikes = new Spikes(world, map, 4);

        // Conectar al servidor
        client.connect();
        sendJoinMessage();
    }

    @Override
    public void render(float delta) {
        // Limpieza de pantalla
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.53f, 0.81f, 0.92f, 1);

        // Actualización de física
        world.step(1/60f, 6, 2);
        stage.act();

        // Actualización de cámara y mapa
        updateCamera();
        map.setView(cam);

        // Renderizado
        map.render();
        stage.draw();
        bdr.render(world, cam.combined);

        // Envío de actualización de red
        sendUpdateMessage();
    }

    private void updateCamera() {
        // Actualizar posición de la cámara para seguir al Kirby local
        cam.position.set(kirby.getBody().getPosition().x, kirby.getBody().getPosition().y, 0);
        cam.update();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        map.dispose();
        kirby.dispose();
        for (RemoteKirby remoteKirby : remotePlayers.values()) {
            remoteKirby.dispose();
        }
        remotePlayers.clear();
    }

    private void sendJoinMessage() {
        NetworkMessage joinMessage = new NetworkMessage(
            kirby.getId(),
            kirby.getPositioX(),
            kirby.getPositioY(),
            kirby.getCurrentAnimationName(),
            kirby.getCurrentColor(),
            kirby.isFlipX(),
            NetworkMessage.MessageType.PLAYER_JOIN
        );
        client.sendMessage(joinMessage);
    }

    private void sendUpdateMessage() {
        NetworkMessage updateMessage = new NetworkMessage(
            kirby.getId(),
            kirby.getPositioX(),
            kirby.getPositioY(),
            kirby.getCurrentAnimationName(),
            kirby.getCurrentColor(),
            kirby.isFlipX(),
            NetworkMessage.MessageType.PLAYER_UPDATE
        );
        client.sendMessage(updateMessage);
    }

    @Override
    public void handleMessage(NetworkMessage message) {
        // Asegurarse de que las actualizaciones de UI se hagan en el thread principal
        Gdx.app.postRunnable(() -> {
            switch (message.getType()) {
                case PLAYER_JOIN:
                    handlePlayerJoin(message);
                    break;
                case PLAYER_LEAVE:
                    handlePlayerLeave(message);
                    break;
                case PLAYER_UPDATE:
                    handlePlayerUpdate(message);
                    break;
            }
        });
    }

    private void handlePlayerJoin(NetworkMessage message) {
        if (!remotePlayers.containsKey(message.getPlayerId())) {
            RemoteKirby remoteKirby = new RemoteKirby(world, main, message.getPlayerId());
            remotePlayers.put(message.getPlayerId(), remoteKirby);
            stage.addActor(remoteKirby);
        }
    }

    private void handlePlayerLeave(NetworkMessage message) {
        RemoteKirby remoteKirby = remotePlayers.remove(message.getPlayerId());
        if (remoteKirby != null) {
            remoteKirby.dispose();
        }
    }

    private void handlePlayerUpdate(NetworkMessage message) {
        RemoteKirby remoteKirby = remotePlayers.get(message.getPlayerId());
        if (remoteKirby != null) {
            remoteKirby.updateFromNetwork(
                message.getPosX(),
                message.getPosY(),
                message.getAnimation(),
                message.getColor(),
                message.isFlipX()
            );
        }
    }
}

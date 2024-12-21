package KirbyGame_HVL.git.netgdx.client;

import KirbyGame_HVL.git.utils.helpers.NetworkUtils;
import KirbyGame_HVL.git.netgdx.KirbyState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.Disposable;

import com.badlogic.gdx.utils.Json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client implements Disposable {
    private final String host;
    private final int port;
    private Socket client;
    private ExecutorService receiveExecutor;
    private ClientStateListener stateListener;

    private NetworkUtils networkUtils;


    public int getPort() {
        return port;
    }

    public Socket getClient() {
        return client;
    }

    public ClientStateListener getStateListener() {
        return stateListener;
    }

    public interface ClientStateListener {
        void onKirbyStateReceived(KirbyState state);
    }

    public Client(String host, int port, ClientStateListener listener) {
        this.host = host;
        this.port = port;
        this.stateListener = listener;
        this.receiveExecutor = Executors.newSingleThreadExecutor();
        this.networkUtils = new NetworkUtils();
    }

    public void start() {
        SocketHints hints = new SocketHints();
        hints.connectTimeout = 10000;

        try {
            client = Gdx.net.newClientSocket(Net.Protocol.TCP, host, port, hints);

            receiveExecutor.submit(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        String line = networkUtils.readLine(client.getInputStream());
                        if (line != null) {
                            try {
                                Json json = new Json();
                                KirbyState state = json.fromJson(KirbyState.class, line);

                                Gdx.app.postRunnable(() -> {
                                    if (stateListener != null) {
                                        stateListener.onKirbyStateReceived(state);
                                    }
                                });
                            } catch (Exception e) {
                                System.err.println("Error parsing JSON: " + e.getMessage());
                            }
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error al recibir los datos: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            System.err.println("Error conexion de cliente: " + e.getMessage());
        }
    }

    //majeneja mensajes especificos de este cliente
    public void sendKirbyState(KirbyState state) {
        if (client != null) {
            try {
                Json json = new Json();
                json.setQuoteLongValues(true); // Asegura que los números largos sean quoted
                String stateJson = json.toJson(state);
                stateJson += "\n"; // Añadir delimitador
                client.getOutputStream().write(stateJson.getBytes());
                client.getOutputStream().flush();
            } catch (IOException e) {
                System.err.println("Error sending Kirby state: " + e.getMessage());
            }
        }
    }

    @Override
    public void dispose() {
        if (receiveExecutor != null) {
            receiveExecutor.shutdownNow();
        }
        if (client != null) {
            client.dispose();
        }
    }


    public class MultiplayerStateHandler implements Client.ClientStateListener {
        private final List<KirbyState> remoteKirbyStates;

        public MultiplayerStateHandler() {
            this.remoteKirbyStates = new ArrayList<>();
        }

        @Override
        public void onKirbyStateReceived(KirbyState state) {
            // Verifica si el estado recibido pertenece a un Kirby remoto y actualiza la lista
            synchronized (remoteKirbyStates) {
                // Actualiza o agrega el estado remoto recibido
                remoteKirbyStates.removeIf(k -> k.getId().equals(state.getId()));
                remoteKirbyStates.add(state);
            }
        }

        public List<KirbyState> getRemoteKirbyStates() {
            synchronized (remoteKirbyStates) {
                return new ArrayList<>(remoteKirbyStates);
            }
        }
    }
}

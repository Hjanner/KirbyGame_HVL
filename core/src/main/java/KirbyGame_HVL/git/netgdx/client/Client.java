package KirbyGame_HVL.git.netgdx.client;

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
    }

    public void start() {
        SocketHints hints = new SocketHints();
        hints.connectTimeout = 10000;

        try {
            client = Gdx.net.newClientSocket(Net.Protocol.TCP, host, port, hints);

            //escucha constantemente mensajes desde el servidor
            receiveExecutor.submit(() -> {
                try {
                    byte[] readBuffer = new byte[1024];
                    int bytesRead;
                    StringBuilder messageBuilder = new StringBuilder();

                    //para recibir mensajes
                    while (!Thread.currentThread().isInterrupted() &&
                        (bytesRead = client.getInputStream().read(readBuffer)) != -1) {
                        String chunk = new String(readBuffer, 0, bytesRead);
                        messageBuilder.append(chunk);

                        //  JSON message checking
                        String message = messageBuilder.toString().trim();
                        int bracketStart = message.indexOf('{');
                        int bracketEnd = message.lastIndexOf('}');

                        if (bracketStart != -1 && bracketEnd != -1 && bracketStart < bracketEnd) {
                            String jsonMessage = message.substring(bracketStart, bracketEnd + 1);
                            try {
                                Json json = new Json();
                                KirbyState state = json.fromJson(KirbyState.class, jsonMessage);                        //convierte el mensaje JSON en un objeto KirbyState

                                //para ejecutar en el hilo principal del juego
                                Gdx.app.postRunnable(() -> {                                                                                                //notifica al cliente en el hilo proncipal
                                    if (stateListener != null) {
                                        stateListener.onKirbyStateReceived(state);                                      //notificar que se ha recibido un nuevo estado
                                    }
                                });

                                messageBuilder.setLength(0);
                            } catch (Exception e) {
                                System.err.println("Error parsing JSON: " + e.getMessage());
                                messageBuilder.setLength(0);
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
                String stateJson = json.toJson(state);

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

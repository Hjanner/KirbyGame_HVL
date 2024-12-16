package KirbyGame_HVL.git.netgdx.client;

import KirbyGame_HVL.git.netgdx.KirbyState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.Disposable;

import com.badlogic.gdx.utils.Json;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client implements Disposable {
    private final String host;
    private final int port;
    private Socket client;
    private ExecutorService receiveExecutor;
    private ClientStateListener stateListener;

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

            receiveExecutor.submit(() -> {
                try {
                    byte[] readBuffer = new byte[1024];
                    int bytesRead;
                    StringBuilder messageBuilder = new StringBuilder();

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
                                // state del kirby en formato JSON
                                Json json = new Json();
                                KirbyState state = json.fromJson(KirbyState.class, jsonMessage);

                                Gdx.app.postRunnable(() -> {                                                                                                //notifica al cliente en el hilo proncipal
                                    if (stateListener != null) {
                                        stateListener.onKirbyStateReceived(state);
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
}

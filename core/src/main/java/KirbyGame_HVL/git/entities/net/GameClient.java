package KirbyGame_HVL.git.entities.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class GameClient {
    private String host;
    private int port;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean connected;
    private ConcurrentHashMap<String, RemoteKirby> remotePlayers;
    private NetworkMessageHandler messageHandler;

    public GameClient(String host, int port, NetworkMessageHandler handler) {
        this.host = host;
        this.port = port;
        this.messageHandler = handler;
        this.remotePlayers = new ConcurrentHashMap<>();
    }

    public void connect() {
        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            connected = true;
            startListening();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startListening() {
        new Thread(() -> {
            while (connected) {
                try {
                    NetworkMessage message = (NetworkMessage) in.readObject();
                    messageHandler.handleMessage(message);
                } catch (Exception e) {
                    connected = false;
                }
            }
        }).start();
    }

    public void sendMessage(NetworkMessage message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

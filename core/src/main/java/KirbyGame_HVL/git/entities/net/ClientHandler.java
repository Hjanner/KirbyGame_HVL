package KirbyGame_HVL.git.entities.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private GameServer server;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String playerId;

    public ClientHandler(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                NetworkMessage message = (NetworkMessage) in.readObject();
                if (message.getType() == NetworkMessage.MessageType.PLAYER_JOIN) {
                    playerId = message.getPlayerId();
                    server.addClient(playerId, this);
                }
                server.broadcastMessage(message, playerId);
            }
        } catch (Exception e) {
            handleDisconnect();
        }
    }

    public void sendMessage(NetworkMessage message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            handleDisconnect();
        }
    }

    private void handleDisconnect() {
        try {
            server.removeClient(playerId);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

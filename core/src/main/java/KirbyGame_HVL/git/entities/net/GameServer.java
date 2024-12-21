package KirbyGame_HVL.git.entities.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class GameServer {
    private static final int MAX_PLAYERS = 4;
    private final int port;
    private ServerSocket serverSocket;
    private boolean running;
    private ConcurrentHashMap<String, ClientHandler> clients;

    public GameServer(int port) {
        this.port = port;
        this.clients = new ConcurrentHashMap<>();
    }

    public void start() {
        running = true;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (running) {
                if (clients.size() < MAX_PLAYERS) {
                    Socket clientSocket = serverSocket.accept();
                    ClientHandler handler = new ClientHandler(clientSocket, this);
                    new Thread(handler).start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(NetworkMessage message, String senderId) {
        clients.forEach((id, handler) -> {
            if (!id.equals(senderId)) {
                handler.sendMessage(message);
            }
        });
    }

    public void addClient(String playerId, ClientHandler handler) {
        clients.put(playerId, handler);
    }

    public void removeClient(String playerId) {
        clients.remove(playerId);
    }
}

package KirbyGame_HVL.git.netgdx.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.Disposable;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Disposable {

    private ServerSocket server;
    private final ExecutorService clientHandlers;
    private final int port;

    private List<ManejadorCliente> connectedClients = new CopyOnWriteArrayList<>();

    public Server(int port) {
        this.port = port;
        this.clientHandlers = Executors.newCachedThreadPool();                                                          //
    }

    public void broadcastKirbyState(String state) {                                                                     //envia el state del kirby como text
        for (ManejadorCliente client : connectedClients) {
            try {
                if (client.outputStream != null) {
                    client.outputStream.write(state.getBytes());
                    client.outputStream.flush();
                    System.out.println("Transmision de estado: " + state);
                }
            } catch (Exception e) {
                System.err.println("Error al transmitir estado: " + e.getMessage());
                connectedClients.remove(client);
            }
        }
    }

    public void start() {
        ServerSocketHints hints = new ServerSocketHints();
        hints.acceptTimeout = 0;
        server = Gdx.net.newServerSocket(Net.Protocol.TCP, "localhost", port, hints);
        System.out.println("Servidor activo en el puerto: " + port);

        new Thread(() -> {                                                                                              //nuevo hilo para nuevo cliente
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Esperando un nuevo cliente...");
                Socket client = server.accept(null);                                                               //se acepta una peticion
                if (client != null) {
                    clientHandlers.submit(new ManejadorCliente(client, connectedClients));                               //
                }
            }
        }).start();
    }

    @Override
    public void dispose() {
        try {
            clientHandlers.shutdownNow();
            if (server != null) {
                server.dispose();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al cerrar el servidor. ", e);
        }
    }


    //MANEJADOR DE CLIENTE
    public static class ManejadorCliente implements Runnable {
        private final Socket client;
        private OutputStream outputStream;                                                                              //flujo de salida
        private final List<ManejadorCliente> connectedClients;

        public ManejadorCliente(Socket client, List<ManejadorCliente> connectedClients) {
            this.client = client;
            this.connectedClients = connectedClients;
            this.outputStream = client.getOutputStream();
        }

        @Override
        public void run() {
            try {
                System.out.println("Cliente conectado desde: " + client.getRemoteAddress());

                connectedClients.add(this);                                                                             //se anade el cliente a la lista de clientes conectados

                byte[] readBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = client.getInputStream().read(readBuffer)) != -1) {                                  //manejo de error de lectura
                    String message = new String(readBuffer, 0, bytesRead).trim();
                    System.out.println("Mensaje recibido del cliente: " + message);

                    broadcastMessage(message);                                                                          //transmitir el mensaje recibido a todo los clientes
                }
            } catch (IOException e) {
                System.err.println("Error al manejar el cliente: " + e.getMessage());
            } finally {
                connectedClients.remove(this);
                if (client != null) client.dispose();
            }
        }

        //transmision de los estados a todos los clientes conectados
        private void broadcastMessage(String message) {
            for (ManejadorCliente handler : connectedClients) {
                try {
                    if (handler.outputStream != null) {
                        handler.outputStream.write(message.getBytes());
                        handler.outputStream.flush();
                    }
                } catch (IOException e) {
                    System.err.println("Error broadcasting message: " + e.getMessage());
                }
            }
        }

        public void sendMessage(String message) throws IOException {
            if (outputStream != null) {
                outputStream.write(message.getBytes());
                outputStream.flush();
            }
        }
    }
}

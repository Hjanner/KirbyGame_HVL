package KirbyGame_HVL.git.netv2.servidor;

import KirbyGame_HVL.git.entities.player.Kirby;
import KirbyGame_HVL.git.netv2.cliente.Cliente;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
//import com.badlogic.gdx.net.ServerSocket;
//import com.badlogic.gdx.net.ServerSocket;

public class Servidor {
    private final int puerto;
    private final ServerSocket serverSocket;
    private boolean continuar;
    private int nClientes;
   // private ServerSocket servidor;

    //private List<KirbyState> players;
    //private final ConcurrentHashMap<String, PlayerState> players;

    public Servidor(int puerto) throws Exception {
        this.puerto = puerto;
        this.serverSocket = new ServerSocket(puerto);
        this.continuar = true;
        this.nClientes = 0;
    }

    public int getPuerto() {
        return puerto;
    }

    public void stopServer() {
        this.continuar = false;
        try {
            serverSocket.close();
        } catch (Exception e) {
            System.err.println("Error al cerrar el servidor: " + e.getMessage());
        }
    }

    public void startServer() {
        new Thread(() -> {
            try {
                while (continuar) {
                    System.out.println("Esperando un nuevo cliente...");
                    Socket clientSocket = serverSocket.accept();                                        //aca se acepta un nuevo cliente si se crea la solicitud

                    // cuando recibe un nuevo cliente pasa por aca
                    nClientes++;
                    System.out.println("Se ha conectado el cliente " + nClientes);
                    AtencionCliente cliente = new AtencionCliente(clientSocket, nClientes);             //se lleva a atencionCliente
                    cliente.start();
                }
            } catch (Exception e) {
                if (continuar) {
                    System.err.println("Error en el servidor: " + e.getMessage());
                }
            }
        }).start();
    }

    //funcion cerrar servidor
}

/*
public class Servidor extends Thread{



public run (){
    Socket clientSocket;


    try {
        while (continuar) {
            System.out.println("Esperando un nuevo cliente...");
            Socket clientSocket = serverSocket.accept();                                        //aca se acepta un nuevo cliente si se crea la solicitud

            // cuando recibe un nuevo cliente pasa por aca
            nClientes++;
            System.out.println("Se ha conectado el cliente " + nClientes);
            AtencionCliente cliente = new AtencionCliente(clientSocket, nClientes);             //se lleva a atencionCliente
            cliente.start();
        }
    } catch (Exception e) {
        if (continuar) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
}
* */

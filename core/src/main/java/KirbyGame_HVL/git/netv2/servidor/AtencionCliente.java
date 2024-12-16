package KirbyGame_HVL.git.netv2.servidor;

import KirbyGame_HVL.git.entities.player.Kirby;

import java.io.*;
import java.net.Socket;

public class AtencionCliente extends Thread {

    private final Socket conexion;
    private final int nCliente;

    public AtencionCliente(Socket conexion, int nCliente) {
        this.conexion = conexion;
        this.nCliente = nCliente;
    }

    @Override
    public void run() {
        try {
            // flijos de datos de entrada y salida del cliente
            ObjectOutputStream salidaCliente = new ObjectOutputStream(conexion.getOutputStream());
            ObjectInputStream entradaCliente = new ObjectInputStream(conexion.getInputStream());

            System.out.println("Esperando mensaje inicial del cliente " + nCliente);

            //  primer mensaje del cliente
            String mensaje = (String) entradaCliente.readObject();
            System.out.println("Mensaje cliente " + nCliente + ": " + mensaje);

            // crear un nuevo KirbyState y enviarlo al cliente
            Kirby nuevoKirby = new Kirby();
            salidaCliente.writeObject(nuevoKirby);
            salidaCliente.flush();
            System.out.println("KirbyState creado y enviado al cliente " + nCliente + " con ID: " + nuevoKirby.getId());

            // leer respuesta del cliente
            mensaje = (String) entradaCliente.readObject();
            if ("recibido".equalsIgnoreCase(mensaje)) {
                salidaCliente.writeUTF("El ID del KirbyState es: " + nuevoKirby.getId());
                salidaCliente.flush();
            }

            salidaCliente.close();
            entradaCliente.close();
            conexion.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}


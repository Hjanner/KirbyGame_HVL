package KirbyGame_HVL.git.netv2.cliente;

import KirbyGame_HVL.git.entities.player.Kirby;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    private final String host;
    private final int puerto;
    private final Socket conexion;

    public Cliente(String host, int puerto) throws Exception {
        this.host = host;
        this.puerto = puerto;
        conexion = new Socket(host, puerto);
    }

    public String getHost() {
        return host;
    }

    public int getPuerto() {
        return puerto;
    }

    private char mostrarMenu() {
        Scanner teclado = new Scanner(System.in);

        System.out.println("****************");
        System.out.println("Menú de opciones");
        System.out.println("****************");
        System.out.println("1- Enviar saludo");
        System.out.println("2- Cerrar la conexión");
        System.out.print("Indique su opción: ");

        return teclado.next().charAt(0);

    }

    public void startClient() throws Exception {
        try (ObjectOutputStream salidaServidor = new ObjectOutputStream(conexion.getOutputStream());
             ObjectInputStream entradaServidor = new ObjectInputStream(conexion.getInputStream())) {

            // Mensaje inicial
            salidaServidor.writeObject("Conexión iniciada");
            salidaServidor.flush();

            // Recibir objeto Kirby
            Kirby kirbyRecibido = (Kirby) entradaServidor.readObject();
            //kirbyRecibido.initializeGraphics();
            System.out.println("Kirby recibido con ID: " + kirbyRecibido.getId());

            // Confirmación al servidor
            salidaServidor.writeObject("recibido");
            salidaServidor.flush();

            // Leer mensaje del servidor
            String mensajeServidor = entradaServidor.readUTF();
            System.out.println("Mensaje del servidor: " + mensajeServidor);

            entradaServidor.close();
            salidaServidor.close();
            conexion.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


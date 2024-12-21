package KirbyGame_HVL.git.netv2;

import KirbyGame_HVL.git.netv2.servidor.Servidor;

public class MainServidor {

    public static void main(String[] args) {
        System.out.println("Iniciado el servidor");
        try {
            Servidor servidor = new Servidor(2468);
            servidor.startServer();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}

package KirbyGame_HVL.git.netv2;

import KirbyGame_HVL.git.netv2.cliente.Cliente;

public class MainCliente {
    public static void main(String[] args) {
        System.out.println("Iniciado el cliente");

        try {
            Cliente cliente = new Cliente("127.0.0.1", 2468);
            cliente.startClient();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}

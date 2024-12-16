package KirbyGame_HVL.git.screens.mainmenu;

import KirbyGame_HVL.git.Main;
//import KirbyGame_HVL.git.netv2.cliente.Cliente;
//import KirbyGame_HVL.git.netv2.servidor.Servidor;
import KirbyGame_HVL.git.netgdx.server.Server;
import KirbyGame_HVL.git.netgdx.client.Client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class PantallaSetMultiplayer extends Pantalla {
    private Stage stage;
    private Skin skin;
    private Table tabla;
    private TextButton crearPartida, unirsePartida;
    private final Main main;

    //private Servidor servidor;
    private Server server;
    private String puertoStr;
    private String ip;

    public String getPort() {
        return puertoStr;
    }

    public void setPort(String port) {
        this.puertoStr = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public PantallaSetMultiplayer(Main main) {
        super(main);
        this.main = main;
    }

    @Override
    public void show() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("assets/ui/skin/uiskin.json"));
        tabla = new Table();
        tabla.setFillParent(true);

        crearPartida = new TextButton("Iniciar Partida", skin);
        tabla.add(crearPartida).width(400).height(80).space(25).row();
        unirsePartida = new TextButton("Unirse a una Partida", skin);
        tabla.add(unirsePartida).width(400).height(80).space(25).row();

        // listener para crear partida
        crearPartida.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mostrarDialogoUnirse(0);                                    //dialogo de configuracion
            }
        });

        // listener para unirse a partida
        unirsePartida.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mostrarDialogoUnirse(1);
            }
        });

        stage.addActor(tabla);
        Gdx.input.setInputProcessor(stage);
    }

    private void iniciarPartida( int puerto) {
        try {
            //s-c cn java.net
//            servidor = new Servidor(puerto);                                                                   //se crea e servidor en otro hilo
//            servidor.startServer();
//            System.out.println("Servidor iniciado en el puerto" + puerto);
//
//            Cliente cliente = new Cliente("127.0.0.1", 2468);                                               //se crea el cliente host
//            cliente.startClient();
//            System.out.println("Cliente conectado como host.");

            //Con JDX
            server = new Server(puerto);
            server.start();

            Client client = new Client("localhost", puerto, state -> System.out.println(state));
            client.start();

            Gdx.app.postRunnable(() -> main.setScreen(main.gameScreen));                                                    //a la pantalla del juego
        } catch (Exception ex) {
            System.err.println("Error al iniciar la partida: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void mostrarDialogoUnirse( int control) {                                   //control 0 para crear 1 para unirse
        Dialog dialogo = new Dialog("Unirse a una Partida", skin) {
            @Override
            protected void result(Object object) {
                if ((boolean) object) {
                    TextField ipField = (TextField) getContentTable().findActor("ipField");                 //se obtienen los valores del dialogo
                    TextField puertoField = (TextField) getContentTable().findActor("puertoField");

                    ip = ipField.getText();
                    puertoStr = puertoField.getText();

                    try {
                        int puerto = Integer.parseInt(puertoStr);
                        if (control == 0){
                            iniciarPartida(puerto);
                        } else  {
                            unirseAPartida(ip, puerto);                                                  //se llama a la funcio para unirse a la partida
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("El puerto ingresado no es valido: " + puertoStr);
                    }
                }
            }
        };

        //campos de texto
        TextField ipField = new TextField("127.0.0.1", skin);
        ipField.setName("ipField");
        TextField puertoField = new TextField("2468", skin);
        puertoField.setName("puertoField");

        dialogo.text("Introduce la IP y el puerto:").row();
        dialogo.getContentTable().add("IP:").left().pad(10);
        dialogo.getContentTable().add(ipField).width(200).row();
        dialogo.getContentTable().add("Puerto:").left().pad(10);
        dialogo.getContentTable().add(puertoField).width(200).row();
        dialogo.button("Unirse", true).button("Cancelar", false);
        dialogo.show(stage);
    }

    private void unirseAPartida(String ip, int puerto) {
        try {
            Client client = new Client("localhost", puerto, state -> System.out.println(state));
            client.start();

            System.out.println("Cliente conectado a " + ip + ":" + puerto);
        } catch (Exception ex) {
            System.err.println("Error al unirse a la partida: " + ex.getMessage());
            ex.printStackTrace();
        }
        Gdx.app.postRunnable(() -> main.setScreen(main.gameScreen));                                                    //a la pantalla del juego
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}

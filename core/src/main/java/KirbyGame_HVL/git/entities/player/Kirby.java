package KirbyGame_HVL.git.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Kirby extends Actor {

    /* Atributos:
     *  Texturas de los distintos movimientos del personaje de kirby,
     *  con el tamaño de sus respectivas regiones de cada movimiento, el array
     *  unidimensional que va a tener cada frame de los diversos movimentos y el objeto de
     *  la clase animacion que va a tener cada movimiento.
     *  Un objeto Sprite que se va a encargar de actualizar que animacion se esta haciendo
     *  en este momento y que movimiento, junto con una variable de velocidad constante y un
     *  booleano que nos va a indicar si debemos voltear la imagen o no.
     * */
    private Texture kirbywalktexture;
    private Texture kirbyStaytexture;
    private Texture kirbydowntexture;
    private Texture kirbyslidetexture;
    private Texture kirbyruntexture;
    private TextureRegion kirbywalkregion;
    private TextureRegion kirbyStayregion;
    private TextureRegion kirbydownregion;
    private TextureRegion kirbyslideregion;
    private TextureRegion kirbyrunregion;
    private TextureRegion [] kirbyframeswalk;
    private TextureRegion [] kirbyframesstay;
    private TextureRegion [] kirbyframesdown;
    private TextureRegion [] kirbyframesslide;
    private TextureRegion [] kirbyframesrun;
    private Sprite kirbysprite;
    float duracion = 0;
    private static final int velocidad = 300;
    private boolean flipX;

    private Animation kirbyanimationStay;
    private Animation kirbyanimationWalk;
    private Animation kirbyanimationDown;
    private Animation kirbyanimationSlide;
    private Animation kirbyanimationRun;
    private Animation currentAnimation;


    /* Constructor en donde se van a cargar todas las texturas y se incializan las regiones de
       todos los movimientos. Se extraen de las texturas frame por frame con el split para luego
       pasarlo a un array unidimensional mediante un bucle for, para luego inicializar las respectivas animaciones.
    * */
    public Kirby () {
        kirbyStaytexture = new Texture("assets/art/sprites/kirbystay.png");
        kirbyStayregion = new TextureRegion(kirbyStaytexture, 64, 32);
        kirbysprite = new Sprite(kirbyStayregion);
        kirbysprite.setSize(120,120);
        kirbysprite.setPosition(50,50);
        kirbywalktexture = new Texture("assets/art/sprites/kirbywalking.png");
        kirbywalkregion = new TextureRegion(kirbywalktexture,320, 32);
        kirbydowntexture = new Texture ("assets/art/sprites/kirbydown.png");
        kirbydownregion = new TextureRegion(kirbydowntexture, 64, 32);
        kirbyslidetexture = new Texture ("assets/art/sprites/kirbyslide.png");
        kirbyslideregion = new TextureRegion(kirbyslidetexture, 64, 32);
        kirbyruntexture = new Texture ("assets/art/sprites/kirbyrun.png");
        kirbyrunregion = new TextureRegion(kirbyruntexture, 256,32);
        TextureRegion [][] tempkirbystay = kirbyStayregion.split(64/2,32);
        TextureRegion[][] tempkirbywalk = kirbywalkregion.split(320/10,32);
        TextureRegion [][] tempkirbydown = kirbydownregion.split(64/2,32);
        TextureRegion [][] tempkirbyslide = kirbyslideregion.split(64/2, 32);
        TextureRegion [][] tempkirbyrun = kirbyrunregion.split(256/8, 32);
        kirbyframeswalk = new TextureRegion[tempkirbywalk.length * tempkirbywalk[0].length];
        kirbyframesstay = new TextureRegion [tempkirbystay.length * tempkirbystay[0].length];
        kirbyframesdown = new TextureRegion[tempkirbydown.length * tempkirbydown[0].length];
        kirbyframesslide = new TextureRegion[tempkirbyslide.length * tempkirbyslide[0].length];
        kirbyframesrun = new TextureRegion[tempkirbyrun.length * tempkirbyrun[0].length];
        int id = 0;
        for (int i = 0; i < tempkirbywalk.length; i++) {
            for (int j = 0; j < tempkirbywalk[i].length; j++){
                kirbyframeswalk[id] = tempkirbywalk[i][j];
                id++;
            }
        }
        id = 0;
        for (int i = 0; i < tempkirbystay.length; i++) {
            for (int j = 0; j < tempkirbystay[i].length; j++) {
                kirbyframesstay[id] = tempkirbystay[i][j];
                kirbyframesdown[id] = tempkirbydown[i][j];
                kirbyframesslide[id] = tempkirbyslide[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempkirbyrun.length; i++) {
            for (int j = 0; j < tempkirbyrun[i].length; j++) {
                kirbyframesrun[id] = tempkirbyrun[i][j];
                id++;
            }
        }


        kirbyanimationWalk = new Animation(0.1f, kirbyframeswalk);
        kirbyanimationStay = new Animation(2f,kirbyframesstay);
        kirbyanimationDown = new Animation(2f, kirbyframesdown);
        kirbyanimationSlide = new Animation(0.1f,kirbyframesslide);
        kirbyanimationRun = new Animation (0.1f, kirbyframesrun);
        currentAnimation = kirbyanimationStay;
    }

    /* Metodo que se utiliza para actualizar la posicion y animacion del Sprite
     * */
    @Override
    public void draw (Batch batch, float parentAlpha) {
        kirbysprite.draw(batch);
    }

    /*Metodo para eliminar las texturas una vez acabe el programa y no quede basura en la
      memoria.
    * */
    public void dispose () {
        kirbywalktexture.dispose();
        kirbyStaytexture.dispose();
        kirbyslidetexture.dispose();
        kirbydowntexture.dispose();
        kirbyruntexture.dispose();
    }

    /*Metodo que permitira realizar todas las acciones del actor kirby en el escenario
     * */
    @Override
    public void act (float delta) {
        super.act(delta);
        verificarmovimiento(delta);
        duracion += delta;
        TextureRegion frame = (TextureRegion) currentAnimation.getKeyFrame(duracion, true);
        kirbysprite.setRegion(frame);
        kirbysprite.flip(flipX,false);



    }

    /*Verifica que tecla se ha pulsado para realizar un movimiento y dependiendo de eso,
      realizara un movimiento u otro
    * */
    private void verificarmovimiento(float delta) {
        boolean izq = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean derecha = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean abajo = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean pushX = Gdx.input.isKeyPressed(Input.Keys.X);
        if (derecha && !izq && !abajo) {
            avanzar();
            kirbysprite.setColor(Color.BLUE);
            currentAnimation = kirbyanimationWalk;
            flipX = false;
            if (pushX) {
                run();
                kirbysprite.setColor(Color.GOLD);
                currentAnimation = kirbyanimationRun;
            }


        }
        else if (izq && !derecha && !abajo) {
            retroceder();
            kirbysprite.setColor((Color.BLUE));
            currentAnimation = kirbyanimationWalk;
            flipX = true;
            if (pushX) {
                runback();
                kirbysprite.setColor(Color.GOLD);
                currentAnimation = kirbyanimationRun;
                flipX = true;
            }
        }

        else if (abajo) {
            kirbysprite.setColor(Color.GREEN);
            currentAnimation = kirbyanimationDown;
            if (derecha) {
                slideright();
                kirbysprite.setColor(Color.ORANGE);
                currentAnimation = kirbyanimationSlide;
            }

            if (izq) {
                slideleft();
                kirbysprite.setColor(Color.ORANGE);
                currentAnimation = kirbyanimationSlide;
                flipX = true;
            }
        }

        else {
            kirbysprite.setColor(Color.WHITE);
            currentAnimation = kirbyanimationStay;
        }
    }


    /*Movimientos que puede hacer el kirby
     * */
    private void avanzar () {
        float x = kirbysprite.getX();
        float delta = Gdx.graphics.getDeltaTime();
        x = x + velocidad * delta;
        kirbysprite.setPosition(x,kirbysprite.getY());
    }

    private void retroceder () {
        float x = kirbysprite.getX();
        float delta = Gdx.graphics.getDeltaTime();
        x = x - velocidad * delta;
        kirbysprite.setPosition(x,kirbysprite.getY());
    }

    private void slideright () {
        float x = kirbysprite.getX();
        kirbysprite.setPosition(x + 10, kirbysprite.getY());
    }

    private void slideleft() {
        float x = kirbysprite.getX();
        kirbysprite.setPosition(x - 10, kirbysprite.getY());
    }

    private void run () {
        float x = kirbysprite.getX();
        float delta = Gdx.graphics.getDeltaTime();
        x = x + (velocidad + 10) * delta;
        kirbysprite.setPosition(x, kirbysprite.getY());
    }

    private void runback () {
        float x = kirbysprite.getX();
        float delta = Gdx.graphics.getDeltaTime();
        x = x - (velocidad + 10) * delta;
        kirbysprite.setPosition(x, kirbysprite.getY());
    }

}

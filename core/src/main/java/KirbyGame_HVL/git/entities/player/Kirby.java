package KirbyGame_HVL.git.entities.player;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.States.EnumStates;
import KirbyGame_HVL.git.entities.States.RunStateKirby;
import KirbyGame_HVL.git.entities.States.StateManager;
import KirbyGame_HVL.git.entities.States.WalkStateKirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;

import static KirbyGame_HVL.git.entities.Constants.Constants.PIXEL_IN_METER;

public class Kirby extends ActorWithBox2d implements Box2dPlayer, Serializable {

    /* Atributos:
     *  Texturas de los distintos movimientos del personaje de kirby,
     *  con el tamaño de sus respectivas regiones de cada movimiento, el array
     *  unidimensional que va a tener cada frame de los diversos movimentos y el objeto de
     *  la clase animacion que va a tener cada movimiento.
     *  Un objeto Sprite que se va a encargar de actualizar que animacion se esta haciendo
     *  en este momento y que movimiento, junto con una variable de velocidad constante y un
     *  booleano que nos va a indicar si debemos voltear la imagen o no.
     * */

    private final String id;
    //private static final long serialVersionUID = -1816334362202070857L;
    private static final long serialVersionUID = 1L;


    private Texture kirbywalktexture;
    private Texture kirbyStaytexture;
    private Texture kirbydowntexture;
    private Texture kirbyslidetexture;
    private Texture kirbyruntexture;
    private Texture kirbyjump;
    private TextureRegion kirbywalkregion;
    private TextureRegion kirbyStayregion;
    private TextureRegion kirbydownregion;
    private TextureRegion kirbyslideregion;
    private TextureRegion kirbyrunregion;
    private TextureRegion kirbyjumpregion;
    private TextureRegion [] kirbyframeswalk;
    private TextureRegion [] kirbyframesstay;
    private TextureRegion [] kirbyframesdown;
    private TextureRegion [] kirbyframesslide;
    private TextureRegion [] kirbyframesrun;
    private TextureRegion [] kirbyframesjump;
    private Sprite kirbysprite;
    float duracion = 0;
    private boolean flipX;
    private EnumStates typestate;
    private StateManager stateManager;
    private RunStateKirby stateRun;
    private WalkStateKirby stateWalk;
    private Animation kirbyanimationStay;
    private Animation kirbyanimationWalk;
    private Animation kirbyanimationDown;
    private Animation kirbyanimationDash;
    private Animation kirbyanimationRun;
    private Animation kirbyanimationJump;
    private Animation currentAnimation;


    /* Constructor en donde se van a cargar todas las texturas y se incializan las regiones de
       todos los movimientos. Se extraen de las texturas frame por frame con el split para luego
       pasarlo a un array unidimensional mediante un bucle for, para luego inicializar las respectivas animaciones.
    * */
    public Kirby() {
        this.id = generateId() ;
    }

    public Kirby (World world, Main main) {
        this.id = generateId();
        this.world = world;
        this.main = main;
        createBody(world);
        texture_animation();

    }
    private String generateId() {
        return "KIRBY-" + System.currentTimeMillis();
    }

    public String getId() {return id;}


    /* Metodo que se utiliza para actualizar la posicion y animacion del Sprite
     * */
    @Override
    public void draw (Batch batch, float parentAlpha) {
        kirbysprite.setPosition(body.getPosition().x - 18,body.getPosition().y - 8);
        kirbysprite.draw(batch);
    }

    public void createBody (World world) {
        BodyDef kirbybodydef = new BodyDef();
        kirbybodydef.position.set(180,1010);
        kirbybodydef.type = BodyDef.BodyType.DynamicBody;
        body = this.world.createBody(kirbybodydef);
        CircleShape kirbyshape = new CircleShape();
        kirbyshape.setRadius(8);
        fixture = body.createFixture(kirbyshape,0.01f);
        body.setFixedRotation(true);
        kirbyshape.dispose();
    }

    public void texture_animation () {
        kirbyStaytexture = main.getManager().get("assets/art/sprites/kirbystay.png");
        kirbyStayregion = new TextureRegion(kirbyStaytexture, 64, 32);
        kirbysprite = new Sprite(kirbyStayregion);
        kirbysprite.setSize(38,38);
        kirbywalktexture = main.getManager().get("assets/art/sprites/kirbywalking.png");
        kirbywalkregion = new TextureRegion(kirbywalktexture,320, 32);
        kirbydowntexture = main.getManager().get("assets/art/sprites/kirbydown.png");
        kirbydownregion = new TextureRegion(kirbydowntexture, 64, 32);
        kirbyslidetexture = main.getManager().get("assets/art/sprites/kirbyslide.png");
        kirbyslideregion = new TextureRegion(kirbyslidetexture, 64, 32);
        kirbyruntexture = main.getManager().get("assets/art/sprites/kirbyrun.png");
        kirbyrunregion = new TextureRegion(kirbyruntexture, 256,32);
        kirbyjump = main.getManager().get("assets/art/sprites/kirbyjump.png");
        kirbyjumpregion = new TextureRegion(kirbyjump, 32, 32);
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
        kirbyframesjump = new TextureRegion[1];
        kirbyframesjump[0] = kirbyjumpregion;
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

        kirbyanimationWalk = new Animation(0.08f, kirbyframeswalk);
        kirbyanimationStay = new Animation(0.8f,kirbyframesstay);
        kirbyanimationDown = new Animation(0.8f, kirbyframesdown);
        kirbyanimationDash = new Animation(0.5f,kirbyframesslide);
        kirbyanimationRun = new Animation (0.04f, kirbyframesrun);
        kirbyanimationJump = new Animation (1f,kirbyframesjump);
        currentAnimation = kirbyanimationStay;
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
        body.destroyFixture(fixture);
        world.destroyBody(body);
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

    private void verificarmovimiento(float delta) {
        boolean izq = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean derecha = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean abajo = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean pushX = Gdx.input.isKeyPressed(Input.Keys.X);
        boolean up = Gdx.input.isKeyPressed(Input.Keys.UP);
        if (derecha && !izq && !abajo) {
            body.applyLinearImpulse(5,0, body.getPosition().x, body.getPosition().y, true);
            body.setLinearVelocity(80,0);
            kirbysprite.setColor(Color.BLUE);
            currentAnimation = kirbyanimationWalk;
            flipX = false;
            if (pushX) {
                body.setLinearVelocity(120, 0);
                kirbysprite.setColor(Color.GOLD);
                currentAnimation = kirbyanimationRun;
            }


        }
        else if (izq && !derecha && !abajo) {
            body.applyLinearImpulse(-5,0, body.getPosition().x, body.getPosition().y, true);
            body.setLinearVelocity(-80,0);
            kirbysprite.setColor((Color.BLUE));
            currentAnimation = kirbyanimationWalk;
            flipX = true;
            if (pushX) {
                body.setLinearVelocity(-120,0);
                kirbysprite.setColor(Color.GOLD);
                currentAnimation = kirbyanimationRun;
                flipX = true;
            }
        }

        else if (abajo) {
            kirbysprite.setColor(Color.GREEN);
            currentAnimation = kirbyanimationDown;
            if (derecha) {
                body.applyLinearImpulse(30,0, body.getPosition().x, body.getPosition().y, true);
                kirbysprite.setColor(Color.ORANGE);
                currentAnimation = kirbyanimationDash;
            }

            if (izq) {
                body.applyLinearImpulse(-30,0, body.getPosition().x, body.getPosition().y, true);
                kirbysprite.setColor(Color.ORANGE);
                currentAnimation = kirbyanimationDash;
                flipX = true;
            }
        }

        else if (up) {
            body.applyLinearImpulse(0,30, body.getPosition().x, body.getPosition().y, true);
            currentAnimation = kirbyanimationJump;

        }
        else if (body.getLinearVelocity().y >= 0){
            Vector2 impulsoOpuesto = body.getLinearVelocity().cpy().scl(-0.8f);
            body.applyLinearImpulse(impulsoOpuesto.x, impulsoOpuesto.y, body.getPosition().x, body.getPosition().y, true);
            kirbysprite.setColor(Color.WHITE);
            currentAnimation = kirbyanimationStay;
        }


        body.applyLinearImpulse(0, -15, body.getPosition().x, body.getPosition().y, true);
    }

    public void setAnimation (EnumStates typestate) {

        switch (typestate) {

            case RUN:
                currentAnimation = kirbyanimationRun;
                break;
            case WALK:
                currentAnimation = kirbyanimationWalk;
                break;
            case DOWN:
                currentAnimation = kirbyanimationDown;
                break;
            case DASH:
                currentAnimation = kirbyanimationDash;
                break;
            case JUMP:
                currentAnimation = kirbyanimationJump;
                break;
        }
    }

    public void setState(EnumStates typestate) {

        switch (typestate) {

            case RUN:
                stateManager.setState(stateRun);
                break;
            case WALK:
                stateManager.setState(stateWalk);
                break;

        }
    }
}

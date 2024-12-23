package KirbyGame_HVL.git.entities.player;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.States.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Kirby extends ActorWithBox2d implements Box2dPlayer {

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
    private Texture kirbyjumptexture;
    private Texture kirbyfalltexture;
    private Texture kirbyfall2texture;
    private TextureRegion kirbywalkregion;
    private TextureRegion kirbyStayregion;
    private TextureRegion kirbydownregion;
    private TextureRegion kirbyslideregion;
    private TextureRegion kirbyrunregion;
    private TextureRegion kirbyjumpregion;
    private TextureRegion kirbyfallregion;
    private TextureRegion kirbyfall2region;
    private TextureRegion [] kirbyframeswalk;
    private TextureRegion [] kirbyframesstay;
    private TextureRegion [] kirbyframesdown;
    private TextureRegion [] kirbyframesslide;
    private TextureRegion [] kirbyframesrun;
    private TextureRegion [] kirbyframesjump;
    private TextureRegion [] kirbyframesfall;
    private TextureRegion [] kirbyframesfall2;
    private Sprite kirbysprite;
    float duracion = 0;
    private boolean flipX;
    private boolean opuesto;
    private boolean colisionSuelo;
    private StateManager stateManager;
    private RunStateKirby stateRun;
    private WalkStateKirby stateWalk;
    private StayStateKirby stateStay;
    private JumpStateKirby stateJump;
    private DashStateKirby stateDash;
    private FallStateKirby stateFall;
    private DownStateKirby stateDown;
    private Animation kirbyanimationStay;
    private Animation kirbyanimationWalk;
    private Animation kirbyanimationDown;
    private Animation kirbyanimationDash;
    private Animation kirbyanimationRun;
    private Animation kirbyanimationJump;
    private Animation kirbyanimationfall;
    private Animation kirbyanimationfall2;
    private Animation currentAnimation;


    /* Constructor en donde se van a cargar todas las texturas y se incializan las regiones de
       todos los movimientos. Se extraen de las texturas frame por frame con el split para luego
       pasarlo a un array unidimensional mediante un bucle for, para luego inicializar las respectivas animaciones.
    * */
    public Kirby (World world, Main main) {
        this.world = world;
        this.main = main;
        this.stateManager = new StateManager();
        this.stateStay = new StayStateKirby(this);
        this.stateRun = new RunStateKirby(this);
        this.stateWalk = new WalkStateKirby(this);
        this.stateJump = new JumpStateKirby(this);
        this.stateDash = new DashStateKirby(this);
        this.stateFall = new FallStateKirby(this);
        this.stateDown = new DownStateKirby(this);
        this.stateManager.setState(stateStay);
        createBody(world);
        texture_animation();

    }

    /* Metodo que se utiliza para actualizar la posicion y animacion del Sprite
     * */
    @Override
    public void draw (Batch batch, float parentAlpha) {
        kirbysprite.setPosition(body.getPosition().x - 11,body.getPosition().y - 5);
        kirbysprite.draw(batch);
    }

    public void createBody (World world) {
        BodyDef kirbybodydef = new BodyDef();
        kirbybodydef.position.set(180,1010);
        kirbybodydef.type = BodyDef.BodyType.DynamicBody;
        body = this.world.createBody(kirbybodydef);
        CircleShape kirbyshape = new CircleShape();
        kirbyshape.setRadius(5);
        fixture = body.createFixture(kirbyshape,0.008f);
        fixture.setUserData("kirby");
        body.setFixedRotation(true);
        kirbyshape.dispose();
    }

    public void texture_animation () {
        kirbyStaytexture = main.getManager().get("assets/art/sprites/kirbystay.png");
        kirbyStayregion = new TextureRegion(kirbyStaytexture, 64, 32);
        kirbysprite = new Sprite(kirbyStayregion);
        kirbysprite.setSize(20,20);
        kirbywalktexture = main.getManager().get("assets/art/sprites/kirbywalking.png");
        kirbywalkregion = new TextureRegion(kirbywalktexture,320, 32);
        kirbydowntexture = main.getManager().get("assets/art/sprites/kirbydown.png");
        kirbydownregion = new TextureRegion(kirbydowntexture, 64, 32);
        kirbyslidetexture = main.getManager().get("assets/art/sprites/kirbyslide.png");
        kirbyslideregion = new TextureRegion(kirbyslidetexture, 64, 32);
        kirbyruntexture = main.getManager().get("assets/art/sprites/kirbyrun.png");
        kirbyrunregion = new TextureRegion(kirbyruntexture, 256,32);
        kirbyjumptexture = main.getManager().get("assets/art/sprites/kirbyjump.png");
        kirbyjumpregion = new TextureRegion(kirbyjumptexture, 32, 32);
        kirbyfalltexture = main.getManager().get("assets/art/sprites/kirbyfall.png");
        kirbyfallregion = new TextureRegion(kirbyfalltexture, 1600,32);
        kirbyfall2texture = main.getManager().get("assets/art/sprites/kirbyfall2.png");
        kirbyfall2region = new TextureRegion(kirbyfall2texture, 1600,32);

        TextureRegion [][] tempkirbystay = kirbyStayregion.split(64/2,32);
        TextureRegion[][] tempkirbywalk = kirbywalkregion.split(320/10,32);
        TextureRegion [][] tempkirbydown = kirbydownregion.split(64/2,32);
        TextureRegion [][] tempkirbyslide = kirbyslideregion.split(192/6, 32);
        TextureRegion [][] tempkirbyrun = kirbyrunregion.split(256/8, 32);
        TextureRegion [][] tempkirbyfall = kirbyfallregion.split(1600/50,32);
        TextureRegion [][] tempkirbyfall2 = kirbyfall2region.split(1600/50,32);
        kirbyframeswalk = new TextureRegion[tempkirbywalk.length * tempkirbywalk[0].length];
        kirbyframesstay = new TextureRegion [tempkirbystay.length * tempkirbystay[0].length];
        kirbyframesdown = new TextureRegion[tempkirbydown.length * tempkirbydown[0].length];
        kirbyframesslide = new TextureRegion[tempkirbyslide.length * tempkirbyslide[0].length];
        kirbyframesrun = new TextureRegion[tempkirbyrun.length * tempkirbyrun[0].length];
        kirbyframesjump = new TextureRegion[1];
        kirbyframesjump[0] = kirbyjumpregion;
        kirbyframesfall = new TextureRegion[tempkirbyfall.length * tempkirbyfall[0].length];
        kirbyframesfall2 = new TextureRegion[tempkirbyfall2.length * tempkirbyfall2[0].length];
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
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempkirbyfall.length; i++) {
            for (int j = 0; j < tempkirbyfall[i].length; j++) {
                kirbyframesfall[id] = tempkirbyfall[i][j];
                kirbyframesfall2[id] = tempkirbyfall2[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempkirbyslide.length; i++) {
            for (int j = 0; j < tempkirbyslide[i].length; j++) {
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
        kirbyanimationDash = new Animation(0.08f,kirbyframesslide);
        kirbyanimationRun = new Animation (0.04f, kirbyframesrun);
        kirbyanimationJump = new Animation (1f,kirbyframesjump);
        kirbyanimationfall = new Animation(0.06f, kirbyframesfall);
        kirbyanimationfall2 = new Animation(0.06f, kirbyframesfall2);
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

    public Body getBody () {
        return fixture.getBody();
    }

    public void setFlipx (boolean flipX) {
        this.flipX = flipX;
    }

    public void setOpuesto (boolean opuesto) {
        this.opuesto = opuesto;
    }

    public void setColisionSuelo(boolean colisionSuelo) {
        this.colisionSuelo = colisionSuelo;
    }

    public boolean getColisionSuelo() {
        return colisionSuelo;
    }

    public State getcurrentState () {
        return stateManager.getState();
    }

    /*Metodo que permitira realizar todas las acciones del actor kirby en el escenario
     * */
    @Override
    public void act (float delta) {
        super.act(delta);
        stateManager.update(delta);
        retroceso();
        duracion += delta;
        TextureRegion frame = (TextureRegion) currentAnimation.getKeyFrame(duracion, true);
        kirbysprite.setRegion(frame);
        kirbysprite.flip(flipX,false);

    }

    private void retroceso () {
        body.applyLinearImpulse(0, -0.8f, body.getPosition().x, body.getPosition().y, true);
        if (opuesto) {
            Vector2 impulsoOpuesto = body.getLinearVelocity().cpy().scl(-0.1f);
            body.applyLinearImpulse(impulsoOpuesto.x, impulsoOpuesto.y, body.getPosition().x, body.getPosition().y, true);
        }
    }

    public void setAnimation (EnumStates typestate) {

        duracion = 0;
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
            case STAY:
                currentAnimation = kirbyanimationStay;
                break;
            case FALL:
                currentAnimation = kirbyanimationfall;
                break;
            case FALL2:
                currentAnimation = kirbyanimationfall2;
                break;
            default:
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
            case STAY:
                stateManager.setState(stateStay);
                break;
            case JUMP:
                stateManager.setState(stateJump);
                break;
            case FALL:
                stateManager.setState(stateFall);
                break;
            case DASH:
                stateManager.setState(stateDash);
                break;
            case DOWN:
                stateManager.setState(stateDown);
                break;

        }
    }
}

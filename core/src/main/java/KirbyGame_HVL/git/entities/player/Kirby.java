package KirbyGame_HVL.git.entities.player;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.States.*;
import KirbyGame_HVL.git.entities.States.StatesKirby.*;
import KirbyGame_HVL.git.entities.attacks.CloudKirby;
import KirbyGame_HVL.git.entities.attacks.Star;
import KirbyGame_HVL.git.entities.enemis.Enemy;
import KirbyGame_HVL.git.entities.items.SensorKirby;
import KirbyGame_HVL.git.entities.attacks.Fire;
import KirbyGame_HVL.git.entities.enemis.Enemy;
import KirbyGame_HVL.git.entities.items.EnumItemType;
import KirbyGame_HVL.git.systems.ScoreManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.w3c.dom.Text;

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
    private Texture kirbyflytexture;
    private Texture kirbyflybegintexture;
    private Texture kirbyflyfalltexture;
    private Texture kirbyflyfallendtexture;
    private Texture kirbyDamagetexture;
    private Texture kirbyAbsorbtexture;
    private Texture kirbyAbsorbStaytexture;
    private Texture kirbyAbsorbWalktexture;
    private Texture kirbyAbsorbJumptexture;
    private Texture kirbyAbsorbFalltexture;
    private Texture kirbyAbsorbFall2texture;
    private Texture kirbyAbsorbDowntexture;
    private Texture kirbyAbsorbDamagetexture;
    private Texture kirbyAbsorbSpittexture;
    private TextureRegion kirbywalkregion;
    private TextureRegion kirbyStayregion;
    private TextureRegion kirbydownregion;
    private TextureRegion kirbyslideregion;
    private TextureRegion kirbyrunregion;
    private TextureRegion kirbyjumpregion;
    private TextureRegion kirbyfallregion;
    private TextureRegion kirbyfall2region;
    private TextureRegion kirbyflyregion;
    private TextureRegion kirbyflybeginregion;
    private TextureRegion kirbyflyfallregion;
    private TextureRegion kirbyflyfallendregion;
    private TextureRegion kirbyDamageregion;
    private TextureRegion kirbyAbsorbregion;
    private TextureRegion kirbyAbsorbStayregion;
    private TextureRegion kirbyAbsorbWalkregion;
    private TextureRegion kirbyAbsorbJumpregion;
    private TextureRegion kirbyAbsorbFallregion;
    private TextureRegion kirbyAbsorbFall2region;
    private TextureRegion kirbyAbsorbDownregion;
    private TextureRegion kirbyAbsorbDamageregion;
    private TextureRegion kirbyAbsorbSpitregion;
    private TextureRegion [] kirbyframeswalk;
    private TextureRegion [] kirbyframesstay;
    private TextureRegion [] kirbyframesdown;
    private TextureRegion [] kirbyframesslide;
    private TextureRegion [] kirbyframesrun;
    private TextureRegion [] kirbyframesjump;
    private TextureRegion [] kirbyframesfall;
    private TextureRegion [] kirbyframesfall2;
    private TextureRegion [] kirbyframesfly;
    private TextureRegion [] kirbyframesflybegin;
    private TextureRegion [] kirbyframesflyfall;
    private TextureRegion [] kirbyframesflyfallend;
    private TextureRegion [] kirbyframesdamage;
    private TextureRegion [] kirbyframesAbsorb;
    private TextureRegion [] kirbyframesAbsorbStay;
    private TextureRegion [] kirbyframesAbsorbWalk;
    private TextureRegion [] kirbyframesAbsorbJump;
    private TextureRegion [] kirbyframesAbsorbFall;
    private TextureRegion [] kirbyframesAbsorbFall2;
    private TextureRegion [] kirbyframesAbsorbDown;
    private TextureRegion [] kirbyframesAbsorbDamage;
    private TextureRegion [] kirbyframesAbsorbSpit;
    private Sprite kirbysprite;
    private float duracion = 0;
    private boolean flipX;
    private boolean opuesto;
    private boolean colisionSuelo;
    private boolean realizar;
    private boolean poder;
    private StateManager stateManager;
    private RunStateKirby stateRun;
    private WalkStateKirby stateWalk;
    private StayStateKirby stateStay;
    private JumpStateKirby stateJump;
    private DashStateKirby stateDash;
    private FallStateKirby stateFall;
    private DownStateKirby stateDown;
    private FlyStateKirby stateFly;
    private DamageStateKirby stateDamage;
    private AbsorbStateKirby stateAbsorb;
    private Animation kirbyanimationStay;
    private Animation kirbyanimationWalk;
    private Animation kirbyanimationDown;
    private Animation kirbyanimationDash;
    private Animation kirbyanimationRun;
    private Animation kirbyanimationJump;
    private Animation kirbyanimationfall;
    private Animation kirbyanimationfall2;
    private Animation kirbyanimationfly;
    private Animation kirbyanimationflybegin;
    private Animation kirbyanimationflyfall;
    private Animation kirbyanimationflyfallend;
    private Animation kirbyanimationdamage;
    private Animation kirbyanimationAbsorb;
    private Animation kirbyanimationAbsorbStay;
    private Animation kirbyanimationAbsorbWalk;
    private Animation kirbyanimationAbsorbRun;
    private Animation kirbyanimationAbsorbJump;
    private Animation kirbyanimationAbsorbFall;
    private Animation kirbyanimationAbsorbFall2;
    private Animation kirbyanimationAbsorbDown;
    private Animation kirbyanimationAbsorbDamage;
    private Animation kirbyanimationAbsorbSpit;
    private Animation currentAnimation;
    private CloudKirby cloudkirby;
    private static ScoreManager scoreManager;

    private boolean fireKeyPressed = false;                 // Bandera para controlar el disparo
    private Enemy currentEnemy;
    private SensorKirby sensorKirby;
    private Star star;


    private float initialX;
    private float initialY;


    /* Constructor en donde se van a cargar todas las texturas y se incializan las regiones de
       todos los movimientos. Se extraen de las texturas frame por frame con el split para luego
       pasarlo a un array unidimensional mediante un bucle for, para luego inicializar las respectivas animaciones.
    * */
    public Kirby (World world, Main main, float initialX, float initialY)  {
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
        this.stateFly = new FlyStateKirby(this);
        this.stateDamage = new DamageStateKirby(this);
        this.stateAbsorb = new AbsorbStateKirby(this);
        this.stateManager.setState(stateStay);
        this.scoreManager = new ScoreManager();
        this.poder = false;
        this.currentEnemy = null;
        this.initialX = initialX;
        this.initialY = initialY;
        createBody(world, initialX, initialY);
        load_animation();
    }

    public void setPosition(float x, float y) {
        if (body != null) {
            body.setTransform(x, y, 0);
            body.setAwake(true);
        }
    }

//    public void resetPosition() {
//        body.setTransform(new Vector2(initialX, initialY), 0);
//        body.setLinearVelocity(0, 0);
//    }

    public void resetPosition() {
        if (body != null) {
            body.setTransform(initialX, initialY, 0);
            body.setLinearVelocity(0, 0);
            body.setAngularVelocity(0);
            body.setAwake(true);
        }
    }

    public World getWorld () {
        return this.world;
    }

    public float getInitialX() {
        return initialX;
    }

    public void setInitialX(float initialX) {
        this.initialX = initialX;
    }

    public float getInitialY() {
        return initialY;
    }

    public void setInitialY(float initialY) {
        this.initialY = initialY;
    }

    public void setCloud (CloudKirby cloudKirby) {
        this.cloudkirby = cloudKirby;
    }

    public CloudKirby getCloud () {
        return this.cloudkirby;
    }

    public boolean getFlipX () {
        return this.flipX;
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

    public boolean getRealizar() {
        return realizar;
    }

    public Animation getcurrentAnimation () {
        return currentAnimation;
    }

    public Animation getAbsorbWalkAnimation () {
        return kirbyanimationAbsorbWalk;
    }

    public Animation getAbsorbRunAnimation () {
        return kirbyanimationAbsorbRun;
    }

    public void setRealizar (boolean realizar) {
        this.realizar = realizar;
    }

    public State getcurrentState () {
        return stateManager.getState();
    }
    public void setSensorkirby (SensorKirby sensorKirby) {
        this.sensorKirby = sensorKirby;
    }

    public SensorKirby getSensorkirby () {
        return sensorKirby;
    }

    public void setcurrentEnemy (Enemy currentEnemy) {
        this.currentEnemy = currentEnemy;
    }

    public Enemy getCurrentEnemy () {
        return currentEnemy;
    }

    public Star getStar() {
        return star;
    }

    public void setStar (Star star) {
        this.star = star;
    }

    public void setPoder (boolean poder) {
        this.poder = poder;
    }

    public boolean getPoder () {
        return poder;
    }

    /* Metodo que se utiliza para actualizar la posicion y animacion del Sprite
     * */
    @Override
    public void draw (Batch batch, float parentAlpha) {
        kirbysprite.setPosition(body.getPosition().x - 11,body.getPosition().y - 5);
        kirbysprite.draw(batch);
    }

    public void createBody (World world, float initialX, float initialY) {
        BodyDef kirbybodydef = new BodyDef();
        kirbybodydef.position.set(initialX,initialY);
        kirbybodydef.type = BodyDef.BodyType.DynamicBody;
        body = this.world.createBody(kirbybodydef);
        CircleShape kirbyshape = new CircleShape();
        kirbyshape.setRadius(5);
        fixture = body.createFixture(kirbyshape,0.008f);
        fixture.setUserData(this);
        body.setFixedRotation(true);
        kirbyshape.dispose();
    }

    public void load_animation () {
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
        kirbyflytexture = main.getManager().get("assets/art/sprites/kirbyfly.png");
        kirbyflyregion = new TextureRegion(kirbyflytexture, 160,32);
        kirbyflybegintexture = main.getManager().get("assets/art/sprites/kirbyflybegin.png");
        kirbyflybeginregion = new TextureRegion(kirbyflybegintexture, 192,32);
        kirbyflyfalltexture = main.getManager().get("assets/art/sprites/kirbyflyfall.png");
        kirbyflyfallregion = new TextureRegion(kirbyflyfalltexture, 96,32);
        kirbyflyfallendtexture = main.getManager().get("assets/art/sprites/kirbyflyfallend.png");
        kirbyflyfallendregion = new TextureRegion(kirbyflyfallendtexture, 64,32);
        kirbyDamagetexture = main.getManager().get("assets/art/sprites/kirbyDamage.png");
        kirbyDamageregion = new TextureRegion(kirbyDamagetexture, 960,32);
        kirbyAbsorbtexture = main.getManager().get("assets/art/sprites/kirbyAbsorb.png");
        kirbyAbsorbregion = new TextureRegion(kirbyAbsorbtexture, 1600, 32);
        kirbyAbsorbStaytexture = main.getManager().get("assets/art/sprites/kirbyAbsorbStay.png");
        kirbyAbsorbStayregion = new TextureRegion(kirbyAbsorbStaytexture, 64, 32);
        kirbyAbsorbWalktexture = main.getManager().get("assets/art/sprites/kirbyAbsorbWalk.png");
        kirbyAbsorbWalkregion = new TextureRegion(kirbyAbsorbWalktexture, 416, 32);
        kirbyAbsorbJumptexture = main.getManager().get("assets/art/sprites/kirbyAbsorbJump.png");
        kirbyAbsorbJumpregion = new TextureRegion(kirbyAbsorbJumptexture, 32,32);
        kirbyAbsorbFalltexture = main.getManager().get("assets/art/sprites/kirbyAbsorbFall.png");
        kirbyAbsorbFallregion = new TextureRegion(kirbyAbsorbFalltexture, 1280, 32);
        kirbyAbsorbFall2texture = main.getManager().get("assets/art/sprites/kirbyAbsorbFall2.png");
        kirbyAbsorbFall2region = new TextureRegion(kirbyAbsorbFall2texture, 64, 32);
        kirbyAbsorbDowntexture = main.getManager().get("assets/art/sprites/kirbyAbsorbDown.png");
        kirbyAbsorbDownregion = new TextureRegion(kirbyAbsorbDowntexture, 192,32);
        kirbyAbsorbDamagetexture = main.getManager().get("assets/art/sprites/kirbyAbsorbDamage.png");
        kirbyAbsorbDamageregion = new TextureRegion(kirbyAbsorbDamagetexture, 32,32);
        kirbyAbsorbSpittexture = main.getManager().get("assets/art/sprites/kirbyAbsorbSpit.png");
        kirbyAbsorbSpitregion = new TextureRegion(kirbyAbsorbSpittexture, 160,32);

        TextureRegion [][] tempkirbystay = kirbyStayregion.split(64/2,32);
        TextureRegion[][] tempkirbywalk = kirbywalkregion.split(320/10,32);
        TextureRegion [][] tempkirbydown = kirbydownregion.split(64/2,32);
        TextureRegion [][] tempkirbyslide = kirbyslideregion.split(192/6, 32);
        TextureRegion [][] tempkirbyrun = kirbyrunregion.split(256/8, 32);
        TextureRegion [][] tempkirbyfall = kirbyfallregion.split(1600/50,32);
        TextureRegion [][] tempkirbyfall2 = kirbyfall2region.split(1600/50,32);
        TextureRegion [][] tempkirbyfly = kirbyflyregion.split(160/5, 32);
        TextureRegion [][] tempkirbyflybegin = kirbyflybeginregion.split(192/6, 32);
        TextureRegion [][] tempkirbyflyfall = kirbyflyfallregion.split(96/3, 32);
        TextureRegion [][] tempkirbyflyfallend = kirbyflyfallendregion.split(64/2, 32);
        TextureRegion [][] tempkirbydamage = kirbyDamageregion.split(960/30, 32);
        TextureRegion [][] tempkirbyAbsorb = kirbyAbsorbregion.split(1600/50, 32);
        TextureRegion [][] tempkirbyAbsorbStay = kirbyAbsorbStayregion.split(64/2, 32);
        TextureRegion [][] tempkirbyAbsorbWalk = kirbyAbsorbWalkregion.split(416/13,32);
        TextureRegion [][] tempkirbyAbsorbFall = kirbyAbsorbFallregion.split(1280/40, 32);
        TextureRegion [][] tempkirbyAbsorbFall2 = kirbyAbsorbFall2region.split(64/2, 32);
        TextureRegion [][] tempkirbyAbsorbDown = kirbyAbsorbDownregion.split(192/6,32);
        TextureRegion [][] tempkirbyAbsorbSpit = kirbyAbsorbSpitregion.split(160/5,32);
        kirbyframeswalk = new TextureRegion[tempkirbywalk.length * tempkirbywalk[0].length];
        kirbyframesstay = new TextureRegion [tempkirbystay.length * tempkirbystay[0].length];
        kirbyframesdown = new TextureRegion[tempkirbydown.length * tempkirbydown[0].length];
        kirbyframesslide = new TextureRegion[tempkirbyslide.length * tempkirbyslide[0].length];
        kirbyframesrun = new TextureRegion[tempkirbyrun.length * tempkirbyrun[0].length];
        kirbyframesjump = new TextureRegion[1];
        kirbyframesjump[0] = kirbyjumpregion;
        kirbyframesfall = new TextureRegion[tempkirbyfall.length * tempkirbyfall[0].length];
        kirbyframesfall2 = new TextureRegion[tempkirbyfall2.length * tempkirbyfall2[0].length];
        kirbyframesfly = new TextureRegion[tempkirbyfly.length * tempkirbyfly[0].length];
        kirbyframesflybegin = new TextureRegion[tempkirbyflybegin.length * tempkirbyflybegin[0].length];
        kirbyframesflyfall = new TextureRegion[tempkirbyflyfall.length * tempkirbyflyfall[0].length];
        kirbyframesflyfallend = new TextureRegion[tempkirbyflyfallend.length * tempkirbyflyfallend[0].length];
        kirbyframesdamage = new TextureRegion[tempkirbydamage.length * tempkirbydamage[0].length];
        kirbyframesAbsorb = new TextureRegion[tempkirbyAbsorb.length * tempkirbyAbsorb[0].length];
        kirbyframesAbsorbStay = new TextureRegion[tempkirbyAbsorbStay.length * tempkirbyAbsorbStay[0].length];
        kirbyframesAbsorbWalk = new TextureRegion[tempkirbyAbsorbWalk.length * tempkirbyAbsorbWalk[0].length];
        kirbyframesAbsorbJump = new TextureRegion[1];
        kirbyframesAbsorbJump[0] = kirbyAbsorbJumpregion;
        kirbyframesAbsorbFall = new TextureRegion [tempkirbyAbsorbFall.length * tempkirbyAbsorbFall[0].length];
        kirbyframesAbsorbFall2 = new TextureRegion[tempkirbyAbsorbFall2.length * tempkirbyAbsorbFall2[0].length];
        kirbyframesAbsorbDown = new TextureRegion[tempkirbyAbsorbDown.length * tempkirbyAbsorbDown[0].length];
        kirbyframesAbsorbDamage = new TextureRegion[1];
        kirbyframesAbsorbDamage[0] = kirbyAbsorbDamageregion;
        kirbyframesAbsorbSpit = new TextureRegion[tempkirbyAbsorbSpit.length * tempkirbyAbsorbSpit[0].length];

        int id = 0;
        for (int i = 0; i < tempkirbywalk.length; i++) {
            for (int j = 0; j < tempkirbywalk[i].length; j++){
                kirbyframeswalk[id] = tempkirbywalk[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempkirbydamage.length; i++) {
            for (int j = 0; j < tempkirbydamage[i].length; j++){
                kirbyframesdamage[id] = tempkirbydamage[i][j];
                id++;
            }
        }


        id = 0;
        for (int i = 0; i < tempkirbyAbsorb.length; i++) {
            for (int j = 0; j < tempkirbyAbsorb[i].length; j++){
                kirbyframesAbsorb[id] = tempkirbyAbsorb[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempkirbyAbsorbSpit.length; i++) {
            for (int j = 0; j < tempkirbyAbsorbSpit[i].length; j++){
                kirbyframesAbsorbSpit[id] = tempkirbyAbsorbSpit[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempkirbyAbsorbDown.length; i++) {
            for (int j = 0; j < tempkirbyAbsorbDown[i].length; j++){
                kirbyframesAbsorbDown[id] = tempkirbyAbsorbDown[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempkirbyAbsorbFall.length; i++) {
            for (int j = 0; j < tempkirbyAbsorbFall[i].length; j++){
                kirbyframesAbsorbFall[id] = tempkirbyAbsorbFall[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempkirbyAbsorbFall2.length; i++) {
            for (int j = 0; j < tempkirbyAbsorbFall2[i].length; j++){
                kirbyframesAbsorbFall2[id] = tempkirbyAbsorbFall2[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempkirbyAbsorbStay.length; i++) {
            for (int j = 0; j < tempkirbyAbsorbStay[i].length; j++){
                kirbyframesAbsorbStay[id] = tempkirbyAbsorbStay[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempkirbyAbsorbWalk.length; i++) {
            for (int j = 0; j < tempkirbyAbsorbWalk[i].length; j++){
                kirbyframesAbsorbWalk[id] = tempkirbyAbsorbWalk[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempkirbystay.length; i++) {
            for (int j = 0; j < tempkirbystay[i].length; j++) {
                kirbyframesstay[id] = tempkirbystay[i][j];
                kirbyframesdown[id] = tempkirbydown[i][j];
                kirbyframesflyfallend[id] = tempkirbyflyfallend[i][j];
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
        for (int i = 0; i< tempkirbyfly.length; i++) {
            for (int j = 0; j < tempkirbyfly[i].length; j++) {
                kirbyframesfly[id] = tempkirbyfly[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i< tempkirbyflyfall.length; i++) {
            for (int j = 0; j < tempkirbyflyfall[i].length; j++) {
                kirbyframesflyfall[id] = tempkirbyflyfall[i][j];
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
        for (int i = 0; i < tempkirbyflybegin.length; i++) {
            for (int j = 0; j < tempkirbyflybegin[i].length; j++) {
                kirbyframesflybegin[id] = tempkirbyflybegin[i][j];
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
        kirbyanimationflybegin = new Animation(0.05f, kirbyframesflybegin);
        kirbyanimationfly = new Animation(0.08f, kirbyframesfly);
        kirbyanimationflyfall = new Animation(0.08f, kirbyframesflyfall);
        kirbyanimationflyfallend = new Animation(0.1f, kirbyframesflyfallend);
        kirbyanimationdamage = new Animation (0.1f, kirbyframesdamage);
        kirbyanimationAbsorb = new Animation(0.08f, kirbyframesAbsorb);
        kirbyanimationAbsorbStay = new Animation(0.8f, kirbyframesAbsorbStay);
        kirbyanimationAbsorbWalk = new Animation(0.08f, kirbyframesAbsorbWalk);
        kirbyanimationAbsorbRun = new Animation(0.04f, kirbyframesAbsorbWalk);
        kirbyanimationAbsorbJump = new Animation(1f, kirbyframesAbsorbJump);
        kirbyanimationAbsorbFall = new Animation(0.06f, kirbyframesAbsorbFall);
        kirbyanimationAbsorbFall2 = new Animation(0.06f, kirbyframesAbsorbFall2);
        kirbyanimationAbsorbDown = new Animation(0.06f, kirbyframesAbsorbDown);
        kirbyanimationAbsorbDamage = new Animation(1f, kirbyframesAbsorbDamage);
        kirbyanimationAbsorbSpit = new Animation(0.07f, kirbyframesAbsorbSpit);
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
        stateManager.update(delta);
        retroceso();
        duracion += delta;
        TextureRegion frame = (TextureRegion) currentAnimation.getKeyFrame(duracion, true);
        kirbysprite.setRegion(frame);
        kirbysprite.flip(flipX,false);
    }

    private void shootFire() {
        Fire fire = new Fire(world, this, !flipX);
        getStage().addActor(fire);
    }

    private void retroceso () {
        if (stateManager.getState() instanceof FlyStateKirby) {
            body.applyForce(0, -13f, body.getPosition().x, body.getPosition().y, true);
            if (currentAnimation == kirbyanimationflyfall) {
                body.applyForce(0, -10f, body.getPosition().x, body.getPosition().y, true);
            }
        }
        else {
            if (!(stateManager.getState() instanceof DamageStateKirby)) {
                body.applyLinearImpulse(0, -0.8f, body.getPosition().x, body.getPosition().y, true);
            }

        }
        if (opuesto) {
            if (stateManager.getState() instanceof DamageStateKirby) {
                Vector2 impulsoOpuesto = body.getLinearVelocity().cpy().scl(-1);
                body.applyLinearImpulse(impulsoOpuesto.x, -0.8f, body.getPosition().x, body.getPosition().y, true);
            }
            else {
                Vector2 impulsoOpuesto = body.getLinearVelocity().cpy().scl(-0.1f);
                body.applyLinearImpulse(impulsoOpuesto.x, impulsoOpuesto.y, body.getPosition().x, body.getPosition().y, true);
            }
        }

        else {
            Vector2 impulsoOpuesto = body.getLinearVelocity().cpy().scl(-0.003f);
            body.applyLinearImpulse(impulsoOpuesto.x, impulsoOpuesto.y, body.getPosition().x, body.getPosition().y, true);
        }
    }

    public void setDuracion(float duracion) {
        this.duracion = duracion;
    }

    public void setAnimation (EnumStates typestate) {

        switch (typestate) {

            case RUN:
                if (getCurrentEnemy() == null) {
                    currentAnimation = kirbyanimationRun;
                }
                else {
                    if (!getPoder()) {
                        currentAnimation = kirbyanimationAbsorbRun;
                    }
                    else{
                        // Logica cuando tenga el poder de un enemigo
                    }
                }
                break;
            case WALK:
                if (getCurrentEnemy() == null) {
                    currentAnimation = kirbyanimationWalk;
                }
                else {
                    if (!getPoder()) {
                        currentAnimation = kirbyanimationAbsorbWalk;
                    }
                    else{
                        // Logica cuando tenga el poder de un enemigo
                    }
                }
                break;
            case DOWN:
                if (getCurrentEnemy() == null) {
                    currentAnimation = kirbyanimationDown;
                }
                else {
                    if (!getPoder()) {
                        currentAnimation = kirbyanimationAbsorbDown;
                        setcurrentEnemy(null);
                    }
                    else{
                        // Logica cuando tenga el poder de un enemigo
                    }
                }
                break;
            case DASH:
                currentAnimation = kirbyanimationDash;
                break;
            case JUMP:
                if (getCurrentEnemy() == null) {
                    currentAnimation = kirbyanimationJump;
                }
                else {
                    if (!getPoder()) {
                        currentAnimation = kirbyanimationAbsorbJump;
                    }
                    else{
                        // Logica cuando tenga el poder de un enemigo
                    }
                }
                break;
            case STAY:
                if (getCurrentEnemy() == null) {
                    currentAnimation = kirbyanimationStay;
                }
                else {
                    if (!getPoder()) {
                        currentAnimation = kirbyanimationAbsorbStay;
                    }
                    else{
                        // Logica cuando tenga el poder de un enemigo
                    }
                }
                break;
            case FALL:
                if (getCurrentEnemy() == null) {
                    currentAnimation = kirbyanimationfall;
                }
                else {
                    if (!getPoder()) {
                        currentAnimation = kirbyanimationAbsorbFall;
                    }
                    else{
                        // Logica cuando tenga el poder de un enemigo
                    }
                }
                break;
            case FALL2:
                if (getCurrentEnemy() == null) {
                    currentAnimation = kirbyanimationfall2;
                }
                else {
                    if (!getPoder()) {
                        currentAnimation = kirbyanimationAbsorbFall2;
                    }
                    else{
                        // Logica cuando tenga el poder de un enemigo
                    }
                }
                break;
            case FLY:
                currentAnimation = kirbyanimationflybegin;
                break;
            case FLY2:
                currentAnimation = kirbyanimationfly;
                break;
            case FLY3:
                currentAnimation = kirbyanimationflyfall;
                break;
            case FLY4:
                currentAnimation = kirbyanimationflyfallend;
                break;
            case DAMAGE:
                if (getCurrentEnemy() == null) {
                    currentAnimation = kirbyanimationdamage;
                }
                else {
                    if (!getPoder()) {
                        currentAnimation = kirbyanimationAbsorbDamage;
                    }
                    else{
                        // Logica cuando tenga el poder de un enemigo
                    }
                }
                break;
            case ABSORB:
                currentAnimation = kirbyanimationAbsorb;
                break;
            case ATTACK:
                if (!getPoder()) {
                    currentAnimation = kirbyanimationAbsorbSpit;
                }

                else{

                }
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
            case FLY:
                stateManager.setState(stateFly);
                break;
            case DAMAGE:
                stateManager.setState(stateDamage);
                break;
            case ABSORB:
                stateManager.setState(stateAbsorb);
                break;
            default:
                break;

        }
    }

    public void subPointsPerItem(EnumItemType damageType){
        scoreManager.recibirDamage(damageType);         //resta puntos segun el tipo de item
    }

    public void addPointsPerItems(EnumItemType type){
        scoreManager.takeItems(type);                   //recibe punto por items tomados, llave puerta
    }

    public void addPointsPerEnemy(Enemy enemy){
        scoreManager.enemyDelete(enemy);                //agrega puntos segun el enemigo eliminado
    }

    public int getCurrentScore() {
        return scoreManager.getCurrentScore();
    }

    public void setCurrentScore(int score) {
        scoreManager.setCurrentScore(score);
    }

    public Main getMain(){ return main;}

}

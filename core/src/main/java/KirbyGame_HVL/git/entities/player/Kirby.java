package KirbyGame_HVL.git.entities.player;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.States.*;
import KirbyGame_HVL.git.entities.States.StatesKirby.*;
import KirbyGame_HVL.git.entities.attacks.Beam;
import KirbyGame_HVL.git.entities.attacks.CloudKirby;
import KirbyGame_HVL.git.entities.attacks.Star;
import KirbyGame_HVL.git.entities.enemis.Enemy;
import KirbyGame_HVL.git.entities.enemis.hotHead.HotHead;
import KirbyGame_HVL.git.entities.enemis.waddleDoo.WaddleDoo;
import KirbyGame_HVL.git.entities.items.SensorKirby;
import KirbyGame_HVL.git.entities.attacks.Fire;
import KirbyGame_HVL.git.entities.items.EnumItemType;
import KirbyGame_HVL.git.systems.NameManager;
import KirbyGame_HVL.git.systems.ScoreManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Kirby extends ActorWithBox2d {

    /* Atributos:
     *  Texturas de los distintos movimientos del personaje de kirby,
     *  con el tamaño de sus respectivas regiones de cada movimiento, el array
     *  unidimensional que va a tener cada frame de los diversos movimentos y el objeto de
     *  la clase animacion que va a tener cada movimiento.
     *  Un objeto Sprite que se va a encargar de actualizar que animacion se esta haciendo
     *  en este momento y que movimiento, junto con una variable de velocidad constante y un
     *  booleano que nos va a indicar si debemos voltear la imagen o no.
     * */

    // Kirby Normal
    private Texture kirbywalktexture;
    private Texture kirbyStaytexture;
    private Texture kirbydowntexture;
    private Texture kirbydashtexture;
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
    private Texture kirbyDamageFireTexture;
    private Texture kirbyAbsorbDamageFireTexture;
    private TextureRegion kirbywalkregion;
    private TextureRegion kirbyStayregion;
    private TextureRegion kirbydownregion;
    private TextureRegion kirbydashregion;
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
    private TextureRegion kirbyDamageFireTextureRegion;
    private TextureRegion kirbyAbsorbDamageFireTextureRegion;
    private TextureRegion [] kirbyframeswalk;
    private TextureRegion [] kirbyframesstay;
    private TextureRegion [] kirbyframesdown;
    private TextureRegion [] kirbyframesdash;
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
    private TextureRegion [] kirbyFramesDamageFire;
    private TextureRegion [] kirbyFramesAbsorbDamageFire;

    // Fire Kirby
    private Texture FireKirbyStayTexture;
    private TextureRegion FireKirbyStayTextureRegion;
    private TextureRegion [] FireKirbyStayFrames;
    private Texture FireKirbyDownTexture;
    private TextureRegion FireKirbyDownTextureRegion;
    private TextureRegion [] FireKirbyDownFrames;
    private Texture FireKirbyWalkTexture;
    private TextureRegion FireKirbyWalkTextureRegion;
    private TextureRegion [] FireKirbyWalkFrames;
    private Texture FireKirbyRunTexture;
    private TextureRegion FireKirbyRunTextureRegion;
    private TextureRegion [] FireKirbyRunFrames;
    private Texture FireKirbyDashTexture;
    private TextureRegion FireKirbyDashTextureRegion;
    private TextureRegion [] FireKirbyDashFrames;
    private Texture FireKirbyJumpTexture;
    private TextureRegion FireKirbyJumpTextureRegion;
    private TextureRegion [] FireKirbyJumpFrames;
    private Texture FireKirbyFallTexture;
    private TextureRegion FireKirbyFallTextureRegion;
    private TextureRegion [] FireKirbyFallFrames;
    private Texture FireKirbyFall2Texture;
    private TextureRegion FireKirbyFall2TextureRegion;
    private TextureRegion [] FireKirbyFall2Frames;
    private Texture FireKirbyFlyBeginTexture;
    private TextureRegion FireKirbyFlyBeginTextureRegion;
    private TextureRegion [] FireKirbyFlyBeginFrames;
    private Texture FireKirbyFlyTexture;
    private TextureRegion FireKirbyFlyTextureRegion;
    private TextureRegion [] FireKirbyFlyFrames;
    private Texture FireKirbyFlyFallTexture;
    private TextureRegion FireKirbyFlyFallTextureRegion;
    private TextureRegion [] FireKirbyFlyFallFrames;
    private Texture FireKirbyFlyFallEndTexture;
    private TextureRegion FireKirbyFlyFallEndTextureRegion;
    private TextureRegion [] FireKirbyFlyFallEndFrames;
    private Texture FireKirbyAttackTexture;
    private TextureRegion FireKirbyAttackTextureRegion;
    private TextureRegion [] FireKirbyAttackFrames;

    // Beam Kirby
    private Texture BeamKirbyStayTexture;
    private TextureRegion BeamKirbyStayTextureRegion;
    private TextureRegion [] BeamKirbyStayFrames;
    private Texture BeamKirbyDownTexture;
    private TextureRegion BeamKirbyDownTextureRegion;
    private TextureRegion [] BeamKirbyDownFrames;
    private Texture BeamKirbyDashTexture;
    private TextureRegion BeamKirbyDashTextureRegion;
    private TextureRegion [] BeamKirbyDashFrames;
    private Texture BeamKirbyWalkTexture;
    private TextureRegion BeamKirbyWalkTextureRegion;
    private TextureRegion [] BeamKirbyWalkFrames;
    private Texture BeamKirbyRunTexture;
    private TextureRegion BeamKirbyRunTextureRegion;
    private TextureRegion [] BeamKirbyRunFrames;
    private Texture BeamKirbyJumpTexture;
    private TextureRegion BeamKirbyJumpTextureRegion;
    private TextureRegion [] BeamKirbyJumpFrames;
    private Texture BeamKirbyFallTexture;
    private TextureRegion BeamKirbyFallTextureRegion;
    private TextureRegion [] BeamKirbyFallFrames;
    private Texture BeamKirbyFall2Texture;
    private TextureRegion BeamKirbyFall2TextureRegion;
    private TextureRegion [] BeamKirbyFall2Frames;
    private Texture BeamKirbyFlyBeginTexture;
    private TextureRegion BeamKirbyFlyBeginTextureRegion;
    private TextureRegion [] BeamKirbyFlyBeginFrames;
    private Texture BeamKirbyFlyTexture;
    private TextureRegion BeamKirbyFlyTextureRegion;
    private TextureRegion [] BeamKirbyFlyFrames;
    private Texture BeamKirbyFlyFallTexture;
    private TextureRegion BeamKirbyFlyFallTextureRegion;
    private TextureRegion [] BeamKirbyFlyFallFrames;
    private Texture BeamKirbyFlyFallEndTexture;
    private TextureRegion BeamKirbyFlyFallEndTextureRegion;
    private TextureRegion [] BeamKirbyFlyFallEndFrames;
    private Texture BeamKirbyAttackTexture;
    private TextureRegion BeamKirbyAttackTextureRegion;
    private TextureRegion [] BeamKirbyAttackFrames;

    private Sprite kirbysprite;
    private float duracion = 0;
    private boolean opuesto;
    private boolean colisionSuelo;
    private boolean realizar;
    private boolean poder;
    private boolean damageFire;
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
    private AttackStateKirby stateAttack;

    // Animaciones de Kirby Normal
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
    private Animation kirbyAnimationDamageFire;
    private Animation kirbyAnimationAbsorbDamageFire;

    // Animaciones de Fire Kirby
    private Animation FireKirbyAnimationStay;
    private Animation FireKirbyAnimationDown;
    private Animation FireKirbyAnimationWalk;
    private Animation FireKirbyAnimationRun;
    private Animation FireKirbyAnimationDash;
    private Animation FireKirbyAnimationJump;
    private Animation FireKirbyAnimationFall;
    private Animation FireKirbyAnimationFall2;
    private Animation FireKirbyAnimationFlyBegin;
    private Animation FireKirbyAnimationFly;
    private Animation FireKirbyAnimationFlyFall;
    private Animation FireKirbyAnimationFlyFallEnd;
    private Animation FireKirbyAnimationAttack;

    // Beam Kirby
    private Animation BeamKirbyAnimationStay;
    private Animation BeamKirbyAnimationDown;
    private Animation BeamKirbyAnimationWalk;
    private Animation BeamKirbyAnimationDash;
    private Animation BeamKirbyAnimationRun;
    private Animation BeamKirbyAnimationJump;
    private Animation BeamKirbyAnimationFall;
    private Animation BeamKirbyAnimationFall2;
    private Animation BeamKirbyAnimationFlyBegin;
    private Animation BeamKirbyAnimationFly;
    private Animation BeamKirbyAnimationFlyFall;
    private Animation BeamKirbyAnimationFlyFallEnd;
    private Animation BeamKirbyAnimationAttack;

    private Animation currentAnimation;
    private CloudKirby cloudkirby;
    private ScoreManager scoreManager;
    private NameManager nameManager;

    private boolean fireKeyPressed;                 // Bandera para controlar el disparo
    private boolean beamKeyPressed;

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
        this.stateAttack = new AttackStateKirby(this);
        this.stateManager.setState(stateStay);
        this.scoreManager = new ScoreManager();
        this.poder = false;
        this.currentEnemy = null;
        this.fireKeyPressed = true;
        this.damageFire = false;
        this.initialX = initialX;
        this.initialY = initialY;
        nameManager = new NameManager();
        createBody(world, initialX, initialY);
        load_animation();
    }

    public void resetPosition() {
        body.setTransform(new Vector2(initialX, initialY), 0);
        body.setLinearVelocity(0, 0);
    }

    public World getWorld () {
        return this.world;
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

    public String getName() {
        return nameManager.getNombre();
    }

    public void setPoder (boolean poder) {
        this.poder = poder;
    }

    public boolean getPoder () {
        return poder;
    }

    public void setFireKeyPressed (boolean fireKeyPressed) {
        this.fireKeyPressed = fireKeyPressed;
    }

    public void setDamageFire (boolean damageFire) {
        this.damageFire = damageFire;
    }

    public void setBeamKeyPressed (boolean beamKeyPressed) {
        this.beamKeyPressed = beamKeyPressed;
    }

    public boolean getBeamKeyPressed () {
        return beamKeyPressed;
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

        // Normal Kirby
        kirbyStaytexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbystay.png");
        kirbyStayregion = new TextureRegion(kirbyStaytexture, 64, 32);
        kirbysprite = new Sprite(kirbyStayregion);
        kirbysprite.setSize(20,20);
        kirbywalktexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbywalking.png");
        kirbywalkregion = new TextureRegion(kirbywalktexture,320, 32);
        kirbydowntexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbydown.png");
        kirbydownregion = new TextureRegion(kirbydowntexture, 64, 32);
        kirbydashtexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbydash.png");
        kirbydashregion = new TextureRegion(kirbydashtexture, 64, 32);
        kirbyruntexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbyrun.png");
        kirbyrunregion = new TextureRegion(kirbyruntexture, 256,32);
        kirbyjumptexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbyjump.png");
        kirbyjumpregion = new TextureRegion(kirbyjumptexture, 32, 32);
        kirbyfalltexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbyfall.png");
        kirbyfallregion = new TextureRegion(kirbyfalltexture, 1600,32);
        kirbyfall2texture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbyfall2.png");
        kirbyfall2region = new TextureRegion(kirbyfall2texture, 1600,32);
        kirbyflytexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbyfly.png");
        kirbyflyregion = new TextureRegion(kirbyflytexture, 160,32);
        kirbyflybegintexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbyflybegin.png");
        kirbyflybeginregion = new TextureRegion(kirbyflybegintexture, 192,32);
        kirbyflyfalltexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbyflyfall.png");
        kirbyflyfallregion = new TextureRegion(kirbyflyfalltexture, 96,32);
        kirbyflyfallendtexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbyflyfallend.png");
        kirbyflyfallendregion = new TextureRegion(kirbyflyfallendtexture, 64,32);
        kirbyDamagetexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbyDamage.png");
        kirbyDamageregion = new TextureRegion(kirbyDamagetexture, 960,32);
        kirbyAbsorbtexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbyAbsorb.png");
        kirbyAbsorbregion = new TextureRegion(kirbyAbsorbtexture, 1600, 32);
        kirbyAbsorbStaytexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbyAbsorbStay.png");
        kirbyAbsorbStayregion = new TextureRegion(kirbyAbsorbStaytexture, 64, 32);
        kirbyAbsorbWalktexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbyAbsorbWalk.png");
        kirbyAbsorbWalkregion = new TextureRegion(kirbyAbsorbWalktexture, 416, 32);
        kirbyAbsorbJumptexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbyAbsorbJump.png");
        kirbyAbsorbJumpregion = new TextureRegion(kirbyAbsorbJumptexture, 32,32);
        kirbyAbsorbFalltexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbyAbsorbFall.png");
        kirbyAbsorbFallregion = new TextureRegion(kirbyAbsorbFalltexture, 1280, 32);
        kirbyAbsorbFall2texture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbyAbsorbFall2.png");
        kirbyAbsorbFall2region = new TextureRegion(kirbyAbsorbFall2texture, 64, 32);
        kirbyAbsorbDowntexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbyAbsorbDown.png");
        kirbyAbsorbDownregion = new TextureRegion(kirbyAbsorbDowntexture, 192,32);
        kirbyAbsorbDamagetexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbyAbsorbDamage.png");
        kirbyAbsorbDamageregion = new TextureRegion(kirbyAbsorbDamagetexture, 32,32);
        kirbyAbsorbSpittexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/kirbyAbsorbSpit.png");
        kirbyAbsorbSpitregion = new TextureRegion(kirbyAbsorbSpittexture, 160,32);
        kirbyDamageFireTexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/KirbyDamageFire.png");
        kirbyDamageFireTextureRegion = new TextureRegion(kirbyDamageFireTexture, 960,32);
        kirbyAbsorbDamageFireTexture = main.getManager().get("assets/art/sprites/SpritesNormalKirby/KirbyAbsorbDamageFire.png");
        kirbyAbsorbDamageFireTextureRegion = new TextureRegion(kirbyAbsorbDamageFireTexture, 832, 32);

        // Fire Kirby
        FireKirbyStayTexture = main.getManager().get("assets/art/sprites/SpritesFireKirby/FireKirbyStay.png");
        FireKirbyStayTextureRegion = new TextureRegion(FireKirbyStayTexture, 512, 32);
        FireKirbyDownTexture = main.getManager().get("assets/art/sprites/SpritesFireKirby/FireKirbyDown.png");
        FireKirbyDownTextureRegion = new TextureRegion(FireKirbyDownTexture, 512,32);
        FireKirbyWalkTexture = main.getManager().get("assets/art/sprites/SpritesFireKirby/FireKirbyWalk.png");
        FireKirbyWalkTextureRegion = new TextureRegion(FireKirbyWalkTexture, 576,32);
        FireKirbyRunTexture = main.getManager().get("assets/art/sprites/SpritesFireKirby/FireKirbyRun.png");
        FireKirbyRunTextureRegion = new TextureRegion(FireKirbyRunTexture, 256,32);
        FireKirbyDashTexture = main.getManager().get("assets/art/sprites/SpritesFireKirby/FireKirbyDash.png");
        FireKirbyDashTextureRegion = new TextureRegion(FireKirbyDashTexture, 192,32);
        FireKirbyJumpTexture = main.getManager().get("assets/art/sprites/SpritesFireKirby/FireKirbyJump.png");
        FireKirbyJumpTextureRegion = new TextureRegion(FireKirbyJumpTexture, 64,32);
        FireKirbyFallTexture = main.getManager().get("assets/art/sprites/SpritesFireKirby/FireKirbyFall.png");
        FireKirbyFallTextureRegion = new TextureRegion(FireKirbyFallTexture, 2560,32);
        FireKirbyFall2Texture = main.getManager().get("assets/art/sprites/SpritesFireKirby/FireKirbyFall2.png");
        FireKirbyFall2TextureRegion = new TextureRegion(FireKirbyFall2Texture, 2560,32);
        FireKirbyFlyBeginTexture = main.getManager().get("assets/art/sprites/SpritesFireKirby/FireKirbyFlyBegin.png");
        FireKirbyFlyBeginTextureRegion = new TextureRegion(FireKirbyFlyBeginTexture, 192,32);
        FireKirbyFlyTexture = main.getManager().get("assets/art/sprites/SpritesFireKirby/FireKirbyFly.png");
        FireKirbyFlyTextureRegion = new TextureRegion(FireKirbyFlyTexture, 320,32);
        FireKirbyFlyFallTexture = main.getManager().get("assets/art/sprites/SpritesFireKirby/FireKirbyFlyFall.png");
        FireKirbyFlyFallTextureRegion = new TextureRegion(FireKirbyFlyFallTexture, 192,32);
        FireKirbyFlyFallEndTexture = main.getManager().get("assets/art/sprites/SpritesFireKirby/FireKirbyFlyFallEnd.png");
        FireKirbyFlyFallEndTextureRegion = new TextureRegion(FireKirbyFlyFallEndTexture, 64,32);
        FireKirbyAttackTexture = main.getManager().get("assets/art/sprites/SpritesFireKirby/FireKirbyAttack.png");
        FireKirbyAttackTextureRegion = new TextureRegion(FireKirbyAttackTexture, 1280,32);

        // Beam Kirby
        BeamKirbyStayTexture = main.getManager().get("assets/art/sprites/SpritesBeamKirby/BeamKirbyStay.png");
        BeamKirbyStayTextureRegion = new TextureRegion(BeamKirbyStayTexture, 64, 32);
        BeamKirbyDownTexture = main.getManager().get("assets/art/sprites/SpritesBeamKirby/BeamKirbyDown.png");
        BeamKirbyDownTextureRegion = new TextureRegion(BeamKirbyDownTexture, 64, 32);
        BeamKirbyDashTexture = main.getManager().get("assets/art/sprites/SpritesBeamKirby/BeamKirbyDash.png");
        BeamKirbyDashTextureRegion = new TextureRegion(BeamKirbyDashTexture, 192, 32);
        BeamKirbyWalkTexture = main.getManager().get("assets/art/sprites/SpritesBeamKirby/BeamKirbyWalk.png");
        BeamKirbyWalkTextureRegion = new TextureRegion(BeamKirbyWalkTexture, 384, 32);
        BeamKirbyRunTexture = main.getManager().get("assets/art/sprites/SpritesBeamKirby/BeamKirbyRun.png");
        BeamKirbyRunTextureRegion = new TextureRegion(BeamKirbyRunTexture, 256, 32);
        BeamKirbyJumpTexture = main.getManager().get("assets/art/sprites/SpritesBeamKirby/BeamKirbyJump.png");
        BeamKirbyJumpTextureRegion = new TextureRegion(BeamKirbyJumpTexture, 64, 32);
        BeamKirbyFallTexture = main.getManager().get("assets/art/sprites/SpritesBeamKirby/BeamKirbyFall.png");
        BeamKirbyFallTextureRegion = new TextureRegion(BeamKirbyFallTexture, 2560, 32);
        BeamKirbyFall2Texture = main.getManager().get("assets/art/sprites/SpritesBeamKirby/BeamKirbyFall2.png");
        BeamKirbyFall2TextureRegion = new TextureRegion(BeamKirbyFall2Texture, 2560, 32);
        BeamKirbyFlyBeginTexture = main.getManager().get("assets/art/sprites/SpritesBeamKirby/BeamKirbyFlyBegin.png");
        BeamKirbyFlyBeginTextureRegion = new TextureRegion(BeamKirbyFlyBeginTexture, 192, 32);
        BeamKirbyFlyTexture = main.getManager().get("assets/art/sprites/SpritesBeamKirby/BeamKirbyFly.png");
        BeamKirbyFlyTextureRegion = new TextureRegion(BeamKirbyFlyTexture, 160, 32);
        BeamKirbyFlyFallTexture = main.getManager().get("assets/art/sprites/SpritesBeamKirby/BeamKirbyFlyFall.png");
        BeamKirbyFlyFallTextureRegion = new TextureRegion(BeamKirbyFlyFallTexture, 96, 32);
        BeamKirbyFlyFallEndTexture = main.getManager().get("assets/art/sprites/SpritesBeamKirby/BeamKirbyFlyFallEnd.png");
        BeamKirbyFlyFallEndTextureRegion = new TextureRegion(BeamKirbyFlyFallEndTexture, 64, 32);
        BeamKirbyAttackTexture = main.getManager().get("assets/art/sprites/SpritesBeamKirby/BeamKirbyAttack.png");
        BeamKirbyAttackTextureRegion = new TextureRegion(BeamKirbyAttackTexture, 320,32);


        // Normal Kirby
        TextureRegion [][] tempkirbystay = kirbyStayregion.split(64/2,32);
        TextureRegion[][] tempkirbywalk = kirbywalkregion.split(320/10,32);
        TextureRegion [][] tempkirbydown = kirbydownregion.split(64/2,32);
        TextureRegion [][] tempkirbydash = kirbydashregion.split(192/6, 32);
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
        TextureRegion [][] tempkirbyDamageFire = kirbyDamageFireTextureRegion.split(960/30,32);
        TextureRegion [][] tempkirbyAbsorbDamageFire = kirbyAbsorbDamageFireTextureRegion.split(832/26,32);

        // Fire Kirby
        TextureRegion [][] tempFireKirbyStay = FireKirbyStayTextureRegion.split(512/16, 32);
        TextureRegion [][] tempFireKirbyDown = FireKirbyDownTextureRegion.split(512/16, 32);
        TextureRegion [][] tempFireKirbyWalk = FireKirbyWalkTextureRegion.split(576/18, 32);
        TextureRegion [][] tempFireKirbyRun = FireKirbyRunTextureRegion.split(256/8, 32);
        TextureRegion [][] tempFireKirbyDash = FireKirbyDashTextureRegion.split(192/6, 32);
        TextureRegion [][] tempFireKirbyJump = FireKirbyJumpTextureRegion.split(64/2, 32);
        TextureRegion [][] tempFireKirbyFall = FireKirbyFallTextureRegion.split(2560/80, 32);
        TextureRegion [][] tempFireKirbyFall2 = FireKirbyFall2TextureRegion.split(2560/80, 32);
        TextureRegion [][] tempFireKirbyFlyBegin = FireKirbyFlyBeginTextureRegion.split(192/6, 32);
        TextureRegion [][] tempFireKirbyFly = FireKirbyFlyTextureRegion.split(320/10, 32);
        TextureRegion [][] tempFireKirbyFlyFall = FireKirbyFlyFallTextureRegion.split(192/6, 32);
        TextureRegion [][] tempFireKirbyFlyFallEnd = FireKirbyFlyFallEndTextureRegion.split(64/2, 32);
        TextureRegion [][] tempFireKirbyAttack = FireKirbyAttackTextureRegion.split(1280/40, 32);

        // Beam Kirby
        TextureRegion [][] tempBeamKirbyStay = BeamKirbyStayTextureRegion.split(64/2, 32);
        TextureRegion [][] tempBeamKirbyDown = BeamKirbyDownTextureRegion.split(64/2, 32);
        TextureRegion [][] tempBeamKirbyDash = BeamKirbyDashTextureRegion.split(192/6, 32);
        TextureRegion [][] tempBeamKirbyWalk = BeamKirbyWalkTextureRegion.split(384/12, 32);
        TextureRegion [][] tempBeamKirbyRun = BeamKirbyRunTextureRegion.split(256/8, 32);
        TextureRegion [][] tempBeamKirbyJump = BeamKirbyJumpTextureRegion.split(64/2, 32);
        TextureRegion [][] tempBeamKirbyFall = BeamKirbyFallTextureRegion.split(2560/80, 32);
        TextureRegion [][] tempBeamKirbyFall2 = BeamKirbyFall2TextureRegion.split(2560/80, 32);
        TextureRegion [][] tempBeamKirbyFlyBegin = BeamKirbyFlyBeginTextureRegion.split(192/6, 32);
        TextureRegion [][] tempBeamKirbyFly = BeamKirbyFlyTextureRegion.split(160/5, 32);
        TextureRegion [][] tempBeamKirbyFlyFall = BeamKirbyFlyFallTextureRegion.split(96/3, 32);
        TextureRegion [][] tempBeamKirbyFlyFallEnd = BeamKirbyFlyFallEndTextureRegion.split(64/2, 32);
        TextureRegion [][] tempBeamKirbyAttack = BeamKirbyAttackTextureRegion.split(320/10,32);


        // Normal Kirby
        kirbyframeswalk = new TextureRegion[tempkirbywalk.length * tempkirbywalk[0].length];
        kirbyframesstay = new TextureRegion [tempkirbystay.length * tempkirbystay[0].length];
        kirbyframesdown = new TextureRegion[tempkirbydown.length * tempkirbydown[0].length];
        kirbyframesdash = new TextureRegion[tempkirbydash.length * tempkirbydash[0].length];
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
        kirbyFramesDamageFire = new TextureRegion[tempkirbyDamageFire.length * tempkirbyDamageFire[0].length];
        kirbyFramesAbsorbDamageFire = new TextureRegion[tempkirbyAbsorbDamageFire.length * tempkirbyAbsorbDamageFire[0].length];

        // Fire Kirby
        FireKirbyStayFrames = new TextureRegion[tempFireKirbyStay.length * tempFireKirbyStay[0].length];
        FireKirbyDownFrames = new TextureRegion[tempFireKirbyDown.length * tempFireKirbyDown[0].length];
        FireKirbyWalkFrames = new TextureRegion[tempFireKirbyWalk.length * tempFireKirbyWalk[0].length];
        FireKirbyRunFrames = new TextureRegion[tempFireKirbyRun.length * tempFireKirbyRun[0].length];
        FireKirbyDashFrames = new TextureRegion[tempFireKirbyDash.length * tempFireKirbyDash[0].length];
        FireKirbyJumpFrames = new TextureRegion[tempFireKirbyJump.length * tempFireKirbyJump[0].length];
        FireKirbyFallFrames = new TextureRegion[tempFireKirbyFall.length * tempFireKirbyFall[0].length];
        FireKirbyFall2Frames = new TextureRegion[tempFireKirbyFall2.length * tempFireKirbyFall2[0].length];
        FireKirbyFlyBeginFrames = new TextureRegion[tempFireKirbyFlyBegin.length * tempFireKirbyFlyBegin[0].length];
        FireKirbyFlyFrames = new TextureRegion[tempFireKirbyFly.length * tempFireKirbyFly[0].length];
        FireKirbyFlyFallFrames = new TextureRegion[tempFireKirbyFlyFall.length * tempFireKirbyFlyFall[0].length];
        FireKirbyFlyFallEndFrames = new TextureRegion[tempFireKirbyFlyFallEnd.length * tempFireKirbyFlyFallEnd[0].length];
        FireKirbyAttackFrames = new TextureRegion[tempFireKirbyAttack.length * tempFireKirbyAttack[0].length];

        // Beam Kirby
        BeamKirbyStayFrames = new TextureRegion[tempBeamKirbyStay.length * tempBeamKirbyStay[0].length];
        BeamKirbyDownFrames = new TextureRegion[tempBeamKirbyDown.length * tempBeamKirbyDown[0].length];
        BeamKirbyDashFrames = new TextureRegion[tempBeamKirbyDash.length * tempBeamKirbyDash[0].length];
        BeamKirbyWalkFrames = new TextureRegion[tempBeamKirbyWalk.length * tempBeamKirbyWalk[0].length];
        BeamKirbyRunFrames = new TextureRegion[tempBeamKirbyRun.length * tempBeamKirbyRun[0].length];
        BeamKirbyJumpFrames = new TextureRegion[tempBeamKirbyJump.length * tempBeamKirbyJump[0].length];
        BeamKirbyFallFrames = new TextureRegion[tempBeamKirbyFall.length * tempBeamKirbyFall[0].length];
        BeamKirbyFall2Frames = new TextureRegion[tempBeamKirbyFall2.length * tempBeamKirbyFall2[0].length];
        BeamKirbyFlyBeginFrames = new TextureRegion[tempBeamKirbyFlyBegin.length * tempBeamKirbyFlyBegin[0].length];
        BeamKirbyFlyFrames = new TextureRegion[tempBeamKirbyFly.length * tempBeamKirbyFly[0].length];
        BeamKirbyFlyFallFrames = new TextureRegion[tempBeamKirbyFlyFall.length * tempBeamKirbyFlyFall[0].length];
        BeamKirbyFlyFallEndFrames = new TextureRegion[tempBeamKirbyFlyFallEnd.length * tempBeamKirbyFlyFallEnd[0].length];
        BeamKirbyAttackFrames = new TextureRegion[tempBeamKirbyAttack.length * tempBeamKirbyAttack[0].length];


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
        for (int i = 0; i < tempkirbyDamageFire.length; i++) {
            for (int j = 0; j < tempkirbyDamageFire[i].length; j++){
                kirbyFramesDamageFire[id] = tempkirbyDamageFire[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempkirbyAbsorbDamageFire.length; i++) {
            for (int j = 0; j < tempkirbyAbsorbDamageFire[i].length; j++){
                kirbyFramesAbsorbDamageFire[id] = tempkirbyAbsorbDamageFire[i][j];
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
        for (int i = 0; i < tempkirbydash.length; i++) {
            for (int j = 0; j < tempkirbydash[i].length; j++) {
                kirbyframesdash[id] = tempkirbydash[i][j];
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

        id = 0;
        for (int i = 0; i < tempFireKirbyStay.length; i++) {
            for (int j = 0; j < tempFireKirbyStay[i].length; j++) {
                FireKirbyStayFrames[id] = tempFireKirbyStay[i][j];
                FireKirbyDownFrames[id] = tempFireKirbyDown[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempFireKirbyWalk.length; i++) {
            for (int j = 0; j < tempFireKirbyWalk[i].length; j++) {
                FireKirbyWalkFrames[id] = tempFireKirbyWalk[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempFireKirbyRun.length; i++) {
            for (int j = 0; j < tempFireKirbyRun[i].length; j++) {
                FireKirbyRunFrames[id] = tempFireKirbyRun[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempFireKirbyDash.length; i++) {
            for (int j = 0; j < tempFireKirbyDash[i].length; j++) {
                FireKirbyDashFrames[id] = tempFireKirbyDash[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempFireKirbyJump.length; i++) {
            for (int j = 0; j < tempFireKirbyJump[i].length; j++) {
                FireKirbyJumpFrames[id] = tempFireKirbyJump[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempFireKirbyFall.length; i++) {
            for (int j = 0; j < tempFireKirbyFall[i].length; j++) {
                FireKirbyFallFrames[id] = tempFireKirbyFall[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempFireKirbyFall2.length; i++) {
            for (int j = 0; j < tempFireKirbyFall2[i].length; j++) {
                FireKirbyFall2Frames[id] = tempFireKirbyFall2[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempFireKirbyFlyBegin.length; i++) {
            for (int j = 0; j < tempFireKirbyFlyBegin[i].length; j++) {
                FireKirbyFlyBeginFrames[id] = tempFireKirbyFlyBegin[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempFireKirbyFly.length; i++) {
            for (int j = 0; j < tempFireKirbyFly[i].length; j++) {
                FireKirbyFlyFrames[id] = tempFireKirbyFly[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempFireKirbyFlyFall.length; i++) {
            for (int j = 0; j < tempFireKirbyFlyFall[i].length; j++) {
                FireKirbyFlyFallFrames[id] = tempFireKirbyFlyFall[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempFireKirbyFlyFallEnd.length; i++) {
            for (int j = 0; j < tempFireKirbyFlyFallEnd[i].length; j++) {
                FireKirbyFlyFallEndFrames[id] = tempFireKirbyFlyFallEnd[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempFireKirbyAttack.length; i++) {
            for (int j = 0; j < tempFireKirbyAttack[i].length; j++) {
                FireKirbyAttackFrames[id] = tempFireKirbyAttack[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempBeamKirbyStay.length; i++) {
            for (int j = 0; j < tempBeamKirbyStay[i].length; j++) {
                BeamKirbyStayFrames[id] = tempBeamKirbyStay[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempBeamKirbyDown.length; i++) {
            for (int j = 0; j < tempBeamKirbyDown[i].length; j++) {
                BeamKirbyDownFrames[id] = tempBeamKirbyDown[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempBeamKirbyDash.length; i++) {
            for (int j = 0; j < tempBeamKirbyDash[i].length; j++) {
                BeamKirbyDashFrames[id] = tempBeamKirbyDash[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempBeamKirbyWalk.length; i++) {
            for (int j = 0; j < tempBeamKirbyWalk[i].length; j++) {
                BeamKirbyWalkFrames[id] = tempBeamKirbyWalk[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempBeamKirbyRun.length; i++) {
            for (int j = 0; j < tempBeamKirbyRun[i].length; j++) {
                BeamKirbyRunFrames[id] = tempBeamKirbyRun[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempBeamKirbyJump.length; i++) {
            for (int j = 0; j < tempBeamKirbyJump[i].length; j++) {
                BeamKirbyJumpFrames[id] = tempBeamKirbyJump[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempBeamKirbyFall.length; i++) {
            for (int j = 0; j < tempBeamKirbyFall[i].length; j++) {
                BeamKirbyFallFrames[id] = tempBeamKirbyFall[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempBeamKirbyFall2.length; i++) {
            for (int j = 0; j < tempBeamKirbyFall2[i].length; j++) {
                BeamKirbyFall2Frames[id] = tempBeamKirbyFall2[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempBeamKirbyFlyBegin.length; i++) {
            for (int j = 0; j < tempBeamKirbyFlyBegin[i].length; j++) {
                BeamKirbyFlyBeginFrames[id] = tempBeamKirbyFlyBegin[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempBeamKirbyFly.length; i++) {
            for (int j = 0; j < tempBeamKirbyFly[i].length; j++) {
                BeamKirbyFlyFrames[id] = tempBeamKirbyFly[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempBeamKirbyFlyFall.length; i++) {
            for (int j = 0; j < tempBeamKirbyFlyFall[i].length; j++) {
                BeamKirbyFlyFallFrames[id] = tempBeamKirbyFlyFall[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempBeamKirbyFlyFallEnd.length; i++) {
            for (int j = 0; j < tempBeamKirbyFlyFallEnd[i].length; j++) {
                BeamKirbyFlyFallEndFrames[id] = tempBeamKirbyFlyFallEnd[i][j];
                id++;
            }
        }

        id = 0;
        for (int i = 0; i < tempBeamKirbyAttack.length; i++) {
            for (int j = 0; j < tempBeamKirbyAttack[i].length; j++) {
                BeamKirbyAttackFrames[id] = tempBeamKirbyAttack[i][j];
                id++;
            }
        }

        // Animaciones Del Normal Kirby
        kirbyanimationWalk = new Animation(0.08f, kirbyframeswalk);
        kirbyanimationStay = new Animation(0.8f,kirbyframesstay);
        kirbyanimationDown = new Animation(0.8f, kirbyframesdown);
        kirbyanimationDash = new Animation(0.08f,kirbyframesdash);
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
        kirbyAnimationDamageFire = new Animation(0.1f, kirbyFramesDamageFire);
        kirbyAnimationAbsorbDamageFire = new Animation(0.1f, kirbyFramesAbsorbDamageFire);

        // Animaciones de Fire Kirby
        FireKirbyAnimationStay = new Animation(0.2f, FireKirbyStayFrames);
        FireKirbyAnimationDown = new Animation(0.2f, FireKirbyDownFrames);
        FireKirbyAnimationWalk = new Animation(0.05f, FireKirbyWalkFrames);
        FireKirbyAnimationRun = new Animation(0.04f, FireKirbyRunFrames);
        FireKirbyAnimationDash = new Animation(0.1f, FireKirbyDashFrames);
        FireKirbyAnimationJump = new Animation(0.08f, FireKirbyJumpFrames);
        FireKirbyAnimationFall = new Animation(0.06f, FireKirbyFallFrames);
        FireKirbyAnimationFall2 = new Animation(0.06f, FireKirbyFall2Frames);
        FireKirbyAnimationFlyBegin = new Animation(0.05f, FireKirbyFlyBeginFrames);
        FireKirbyAnimationFly = new Animation(0.05f, FireKirbyFlyFrames);
        FireKirbyAnimationFlyFall = new Animation (0.07f, FireKirbyFlyFallFrames);
        FireKirbyAnimationFlyFallEnd = new Animation(0.1f, FireKirbyFlyFallEndFrames);
        FireKirbyAnimationAttack = new Animation (0.05f, FireKirbyAttackFrames);

        // Animaciones del Beam Kirby
        BeamKirbyAnimationStay = new Animation(0.8f, BeamKirbyStayFrames);
        BeamKirbyAnimationDown = new Animation(0.8f, BeamKirbyDownFrames);
        BeamKirbyAnimationDash = new Animation(0.08f, BeamKirbyDashFrames);
        BeamKirbyAnimationWalk = new Animation(0.06f, BeamKirbyWalkFrames);
        BeamKirbyAnimationRun = new Animation(0.05f, BeamKirbyRunFrames);
        BeamKirbyAnimationJump = new Animation(0.07f, BeamKirbyJumpFrames);
        BeamKirbyAnimationFall = new Animation(0.06f, BeamKirbyFallFrames);
        BeamKirbyAnimationFall2 = new Animation(0.06f, BeamKirbyFall2Frames);
        BeamKirbyAnimationFlyBegin = new Animation(0.05f, BeamKirbyFlyBeginFrames);
        BeamKirbyAnimationFly = new Animation(0.08f, BeamKirbyFlyFrames);
        BeamKirbyAnimationFlyFall = new Animation(0.08f, BeamKirbyFlyFallFrames);
        BeamKirbyAnimationFlyFallEnd = new Animation(0.1f, BeamKirbyFlyFallEndFrames);
        BeamKirbyAnimationAttack = new Animation(0.1f, BeamKirbyAttackFrames);

        currentAnimation = kirbyanimationStay;
    }

    /*Metodo para eliminar las texturas una vez acabe el programa y no quede basura en la
      memoria.
    * */
    public void dispose () {
        kirbywalktexture.dispose();
        kirbyStaytexture.dispose();
        kirbydashtexture.dispose();
        kirbydowntexture.dispose();
        kirbyruntexture.dispose();
        kirbyAbsorbDamagetexture.dispose();
        kirbyAbsorbDowntexture.dispose();
        kirbyAbsorbFall2texture.dispose();
        kirbyAbsorbFalltexture.dispose();
        kirbyAbsorbJumptexture.dispose();
        kirbyAbsorbSpittexture.dispose();
        kirbyAbsorbtexture.dispose();
        kirbyfall2texture.dispose();
        kirbyDamagetexture.dispose();
        kirbyflybegintexture.dispose();
        kirbyflyfallendtexture.dispose();
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

    public void shootFire() {
        if (fireKeyPressed) {
            Fire fire = new Fire(world, this, !flipX);
            getStage().addActor(fire);
        }
    }

    public void shootBeam () {
        if (beamKeyPressed) {
            Beam beam = new Beam(world, this, !flipX);
            getStage().addActor(beam);
        }
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
                        if (getCurrentEnemy() instanceof HotHead) {
                            currentAnimation = FireKirbyAnimationRun;
                        }

                        else if (getCurrentEnemy() instanceof WaddleDoo) {
                            currentAnimation = BeamKirbyAnimationRun;
                        }

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
                        if (getCurrentEnemy() instanceof HotHead) {
                            currentAnimation = FireKirbyAnimationWalk;
                        }

                        else if (getCurrentEnemy() instanceof WaddleDoo) {
                            currentAnimation = BeamKirbyAnimationWalk;
                        }
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
                    }
                    else{
                        if (getCurrentEnemy() instanceof HotHead) {
                            currentAnimation = FireKirbyAnimationDown;
                        }

                        else if (getCurrentEnemy() instanceof WaddleDoo) {
                            currentAnimation = BeamKirbyAnimationDown;
                        }
                    }
                }
                break;
            case DASH:
                if (getCurrentEnemy() == null) {
                    currentAnimation = kirbyanimationDash;
                } else {
                    if (getCurrentEnemy() instanceof HotHead) {
                        currentAnimation = FireKirbyAnimationDash;
                    }

                    else if (getCurrentEnemy() instanceof WaddleDoo) {
                        currentAnimation = BeamKirbyAnimationDash;
                    }
                }
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
                        if (getCurrentEnemy() instanceof HotHead) {
                            currentAnimation = FireKirbyAnimationJump;
                        }

                        else if (getCurrentEnemy() instanceof WaddleDoo) {
                            currentAnimation = BeamKirbyAnimationJump;
                        }
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
                        if (getCurrentEnemy() instanceof HotHead) {
                            currentAnimation = FireKirbyAnimationStay;
                        }

                        else if (getCurrentEnemy() instanceof WaddleDoo) {
                            currentAnimation = BeamKirbyAnimationStay;
                        }
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
                        if (getCurrentEnemy() instanceof HotHead) {
                            currentAnimation = FireKirbyAnimationFall;
                        }

                        else if (getCurrentEnemy() instanceof WaddleDoo) {
                            currentAnimation = BeamKirbyAnimationFall;
                        }
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
                        if (getCurrentEnemy() instanceof HotHead) {
                            currentAnimation = FireKirbyAnimationFall2;
                        }

                        else if (getCurrentEnemy() instanceof WaddleDoo) {
                            currentAnimation = BeamKirbyAnimationFall2;
                        }
                    }
                }
                break;
            case FLY:
                if (getCurrentEnemy() == null) {
                    currentAnimation = kirbyanimationflybegin;
                } else {
                    if (getCurrentEnemy() instanceof HotHead) {
                        currentAnimation = FireKirbyAnimationFlyBegin;
                    }

                    else if (getCurrentEnemy() instanceof WaddleDoo) {
                        currentAnimation = BeamKirbyAnimationFlyBegin;
                    }
                }
                break;
            case FLY2:
                if (getCurrentEnemy() == null) {
                    currentAnimation = kirbyanimationfly;
                } else {
                    if (getCurrentEnemy() instanceof HotHead) {
                        currentAnimation = FireKirbyAnimationFly;
                    }

                    else if (getCurrentEnemy() instanceof WaddleDoo) {
                        currentAnimation = BeamKirbyAnimationFly;
                    }
                }
                break;
            case FLY3:
                if (getCurrentEnemy() == null) {
                    currentAnimation = kirbyanimationflyfall;
                } else {
                    if (getCurrentEnemy() instanceof HotHead) {
                        currentAnimation = FireKirbyAnimationFlyFall;
                    }

                    else if (getCurrentEnemy() instanceof WaddleDoo) {
                        currentAnimation = BeamKirbyAnimationFlyFall;
                    }
                }
                break;
            case FLY4:
                if (getCurrentEnemy() == null) {
                    currentAnimation = kirbyanimationflyfallend;
                } else {
                    if (getCurrentEnemy() instanceof HotHead) {
                        currentAnimation = FireKirbyAnimationFlyFallEnd;
                    }

                    else if (getCurrentEnemy() instanceof WaddleDoo) {
                        currentAnimation = BeamKirbyAnimationFlyFallEnd;
                    }
                }
                break;
            case DAMAGE:
                if (getCurrentEnemy() == null) {
                    if (damageFire) {
                        currentAnimation = kirbyAnimationDamageFire;
                    }
                    else{
                        currentAnimation = kirbyanimationdamage;
                    }
                }
                else {
                    if (!getPoder()) {
                        if (damageFire) {
                            currentAnimation = kirbyAnimationAbsorbDamageFire;
                        }
                        else{
                            currentAnimation = kirbyanimationAbsorbDamage;
                        }

                    }
                    else{
                        if (damageFire) {
                            currentAnimation = kirbyAnimationDamageFire;
                        }
                        else{
                            currentAnimation = kirbyanimationdamage;
                        }
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
                    if (getCurrentEnemy() instanceof HotHead) {
                        currentAnimation = FireKirbyAnimationAttack;
                    }

                    else if (getCurrentEnemy() instanceof WaddleDoo) {
                        currentAnimation = BeamKirbyAnimationAttack;
                    }
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
            case ATTACK:
                stateManager.setState(stateAttack);
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

}

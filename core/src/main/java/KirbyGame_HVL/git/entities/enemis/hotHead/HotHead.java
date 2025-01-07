package KirbyGame_HVL.git.entities.enemis.hotHead;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.States.State;
import KirbyGame_HVL.git.entities.States.StateManager;
import KirbyGame_HVL.git.entities.States.stateHotHead.*;
import KirbyGame_HVL.git.entities.enemis.Enemy;
import KirbyGame_HVL.git.entities.attacks.Fire;
import KirbyGame_HVL.git.entities.enemis.EnumEnemyType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class HotHead extends Enemy {


    private Texture hotHeadWalkTexture;
    private Texture hotHeadDieTexture;
    private Texture hotHeadDie2Texture;
    private Texture hotHeadAttackTexture;
    private TextureRegion hotHeadWalkRegion;
    private TextureRegion hotHeadDieRegion;
    private TextureRegion hotHeadDie2Region;
    private TextureRegion hotHeadAttackRegion;
    private TextureRegion[] hotHeadWalkFrames;
    private TextureRegion[] hotHeadDieFrames;
    private TextureRegion[] hotHeadDie2Frames;
    private TextureRegion[] hotHeadAttackFrames;
    private Animation HotHeadWalkAnimation;
    private Animation HotHeadDieAnimation;
    private Animation HotHeadDie2Animation;
    private Animation HotHeadAttackAnimation;
    private Animation currentAnimation;
    private Sprite hotHeadSprite;

    private boolean isDead = false;
    private boolean isDisposed = false;

    private boolean canShootFire;

    private StateManager stateManager;
    private WalkStateHotHead stateWalk;
    private DieStateHotHead stateDie;
    private Die2StateHotHead stateDie2;
    private AtractStateHotHead stateAtract;
    private AttackStateHotHead stateAttack;

    public HotHead(World world, Main main, float x, float y) {
        this.world = world;
        this.main = main;
        this.flipX = false;
        this.type = EnumEnemyType.HOTHEAD;
        this.stateManager = new StateManager();
        this.stateWalk = new WalkStateHotHead(this);
        this.stateDie = new DieStateHotHead(this);
        this.stateDie2 = new Die2StateHotHead(this);
        this.stateAtract = new AtractStateHotHead(this);
        this.stateAttack = new AttackStateHotHead(this);
        this.canShootFire = true;
        stateManager.setState(stateWalk);
        createBody(world, x, y);
        loadTextures();
    }

    @Override
    public boolean getFlipX () {
        return flipX;
    }

    @Override
    public void setFlipX (boolean flipX) {
        this.flipX = flipX;
    }

    public void setCanShootFire (boolean canShootFire) {
        this.canShootFire = canShootFire;
    }

    public boolean getCanShootFire () {
        return canShootFire;
    }

    public Body getBody() {return body;}

    public World getWorld () {
        return this.world;
    }

    @Override
    public State getcurrentState() {
        return stateManager.getState();
    }


    public void createBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(4.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.8f;
        fixtureDef.restitution = 0.1f;

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        body.setFixedRotation(true);
        shape.dispose();
    }


    private void loadTextures() {
        hotHeadWalkTexture = main.getManager().get("assets/art/sprites/spritesHotHead/HotHeadWalk.png");
        hotHeadWalkRegion = new TextureRegion(hotHeadWalkTexture, 256, 32);
        hotHeadDieTexture = main.getManager().get("assets/art/sprites/spritesHotHead/HotHeadDie.png");
        hotHeadDieRegion = new TextureRegion(hotHeadDieTexture, 32,32);
        hotHeadDie2Texture = main.getManager().get("assets/art/sprites/spritesHotHead/HotHeadDie2.png");
        hotHeadDie2Region = new TextureRegion(hotHeadDie2Texture, 32,32);
        hotHeadAttackTexture = main.getManager().get("assets/art/sprites/spritesHotHead/HotHeadAttack.png");
        hotHeadAttackRegion = new TextureRegion(hotHeadAttackTexture, 384,32);
        hotHeadSprite = new Sprite(hotHeadWalkRegion);
        hotHeadSprite.setSize(18, 18);

        TextureRegion[][] tempFramesWalk = hotHeadWalkRegion.split(256/8, 32);
        TextureRegion[][] tempFramesAttack = hotHeadAttackRegion.split(384/12,32);
        hotHeadWalkFrames = new TextureRegion[tempFramesWalk.length * tempFramesWalk[0].length];
        hotHeadDieFrames = new TextureRegion[1];
        hotHeadDie2Frames = new TextureRegion[1];
        hotHeadAttackFrames = new TextureRegion[tempFramesAttack.length * tempFramesAttack[0].length];
        hotHeadDieFrames[0] = hotHeadDieRegion;
        hotHeadDie2Frames[0] = hotHeadDie2Region;


        int index = 0;
        for (int i = 0; i < tempFramesWalk.length; i++) {
            for (int j = 0; j < tempFramesWalk[i].length; j++) {
                hotHeadWalkFrames[index] = tempFramesWalk[i][j];
                index++;
            }
        }

        index = 0;
        for (int i = 0; i < tempFramesAttack.length; i++) {
            for (int j = 0; j < tempFramesAttack[i].length; j++) {
                hotHeadAttackFrames[index] = tempFramesAttack[i][j];
                index++;
            }
        }

        HotHeadWalkAnimation = new Animation(0.15f, hotHeadWalkFrames);
        HotHeadDieAnimation = new Animation(1f, hotHeadDieFrames);
        HotHeadDie2Animation = new Animation(1f, hotHeadDie2Frames);
        HotHeadAttackAnimation = new Animation(0.1f, hotHeadAttackFrames);
        currentAnimation = HotHeadWalkAnimation;
    }

    @Override
    public void act(float delta) {
        if (isDisposed) {
            return;
        }
        super.act(delta);
        stateManager.update(delta);
        updateAnimation(delta);
    }

    public void shootFire() {
        Fire fire = new Fire(world, this, !flipX);
        getStage().addActor(fire);
    }

    public void updateAnimation(float delta) {
        duration += delta;
        TextureRegion frame = (TextureRegion) currentAnimation.getKeyFrame(duration, true);
        hotHeadSprite.setRegion(frame);
        hotHeadSprite.setFlip(flipX, false);
    }

    @Override
    public void setState(EnumStateEnemy typeState) {

        switch (typeState) {

            case WALK:
                stateManager.setState(stateWalk);
                break;
            case DIE:
                stateManager.setState(stateDie);
                break;
            case DIE2:
                stateManager.setState(stateDie2);
                break;
            case ATRACT:
                stateManager.setState(stateAtract);
                break;
            case ATTACK:
                stateManager.setState(stateAttack);
                break;
            default:
                break;
        }
    }

    @Override
    public void setAnimation(EnumStateEnemy typestate) {

        switch (typestate) {

            case WALK:
                currentAnimation = HotHeadWalkAnimation;
                break;
            case DIE:
                currentAnimation = HotHeadDieAnimation;
                break;
            case DIE2:
                currentAnimation = HotHeadDie2Animation;
                break;
            case ATTACK:
                currentAnimation = HotHeadAttackAnimation;
                break;
            default:
                break;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isDisposed || isDead) {
            return;
        }
        hotHeadSprite.setPosition(body.getPosition().x - 10, body.getPosition().y - 5);
        hotHeadSprite.draw(batch);
    }

    public void dispose() {
        if (!isDisposed) {
            isDisposed = true;
            this.remove();
        }
    }

}

package KirbyGame_HVL.git.entities.enemis.waddleDoo;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.States.State;
import KirbyGame_HVL.git.entities.States.StateManager;
import KirbyGame_HVL.git.entities.States.stateWaddleDoo.*;
import KirbyGame_HVL.git.entities.attacks.Beam;
import KirbyGame_HVL.git.entities.enemis.Enemy;
import KirbyGame_HVL.git.entities.enemis.EnumEnemyType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public class WaddleDoo extends Enemy {

    private Texture waddleDooWalkTexture;
    private Texture waddleDooDieTexture;
    private Texture waddleDooAttackTexture;
    private TextureRegion waddleDooWalkTextureRegion;
    private TextureRegion waddleDooDieTextureRegion;
    private TextureRegion waddleDooAttackTextureRegion;
    private TextureRegion [] waddleDooWalkFrames;
    private TextureRegion [] waddleDooDieFrames;
    private TextureRegion [] waddleDooAttackFrames;

    private Animation waddleDooAnimationWalk;
    private Animation waddleDooAnimationDie;
    private Animation waddleDooAnimationAttack;
    private Animation currentAnimation;

    private Sprite waddleDooSprite;

    private boolean isDead = false;
    private boolean isDisposed = false;

    private boolean canShootBeam;

    private StateManager stateManager;
    private WalkStateWaddleDoo stateWalk;
    private DieStateWaddleDoo stateDie;
    private Die2StateWaddleDoo stateDie2;
    private AtractStateWaddleDoo stateAtract;
    private AttackStateWaddleDoo stateAttack;

    public WaddleDoo (World world, Main main, float x, float y) {
        this.world = world;
        this.main = main;
        this.flipX = false;
        this.type = EnumEnemyType.WADDLEDOO;
        this.stateManager = new StateManager();
        this.stateWalk = new WalkStateWaddleDoo(this);
        this.stateDie = new DieStateWaddleDoo(this);
        this.stateDie2 = new Die2StateWaddleDoo(this);
        this.stateAtract = new AtractStateWaddleDoo(this);
        this.stateAttack = new AttackStateWaddleDoo(this);
        this.canShootBeam = true;
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

    public Body getBody() {return body;}

    public World getWorld () {
        return this.world;
    }

    public boolean getCanShootBeam() {
        return canShootBeam;
    }

    public void setCanShootBeam(boolean canShootBeam) {
        this.canShootBeam = canShootBeam;
    }

    @Override
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
        fixtureDef.restitution = 0.0f;

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        body.setFixedRotation(true);
        shape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isDisposed || isDead) {
            return;
        }
        waddleDooSprite.setPosition(body.getPosition().x - 10, body.getPosition().y - 5);
        waddleDooSprite.draw(batch);

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

    private void loadTextures() {
        waddleDooWalkTexture = main.getManager().get("assets/art/sprites/spritesWaddleDoo/WaddleDooWalk.png");
        waddleDooWalkTextureRegion = new TextureRegion(waddleDooWalkTexture, 256,32);
        waddleDooDieTexture = main.getManager().get("assets/art/sprites/spritesWaddleDoo/WaddleDooDie.png");
        waddleDooDieTextureRegion = new TextureRegion(waddleDooDieTexture, 32,32);
        waddleDooAttackTexture = main.getManager().get("assets/art/sprites/spritesWaddleDoo/WaddleDooAttack.png");
        waddleDooAttackTextureRegion = new TextureRegion(waddleDooAttackTexture, 1280,32);
        TextureRegion [][] tempWaddleDooWalk = waddleDooWalkTextureRegion.split(256/8,32);
        TextureRegion [][] tempWaddleDooAttack = waddleDooAttackTextureRegion.split(1280/40,32);
        waddleDooWalkFrames = new TextureRegion[tempWaddleDooWalk.length * tempWaddleDooWalk[0].length];
        waddleDooDieFrames = new TextureRegion[1];
        waddleDooDieFrames[0] = waddleDooDieTextureRegion;
        waddleDooAttackFrames = new TextureRegion[tempWaddleDooAttack.length * tempWaddleDooAttack[0].length];
        waddleDooSprite = new Sprite(waddleDooWalkTextureRegion);
        waddleDooSprite.setSize(18,18);

        int index = 0;
        for (int i = 0; i < tempWaddleDooWalk.length; i++) {
            for (int j = 0; j < tempWaddleDooWalk[i].length; j++) {
                waddleDooWalkFrames[index] = tempWaddleDooWalk[i][j];
                index++;
            }
        }

        index = 0;
        for (int i = 0; i < tempWaddleDooAttack.length; i++) {
            for (int j = 0; j < tempWaddleDooAttack[i].length; j++) {
                waddleDooAttackFrames[index] = tempWaddleDooAttack[i][j];
                index++;
            }
        }

        waddleDooAnimationWalk = new Animation(0.1f, waddleDooWalkFrames);
        waddleDooAnimationDie = new Animation(1f, waddleDooDieFrames);
        waddleDooAnimationAttack = new Animation(0.06f, waddleDooAttackFrames);
        currentAnimation = waddleDooAnimationWalk;

    }

    public void shootBeam () {
        if (canShootBeam) {
            Beam beam = new Beam(world, this, !flipX);
            getStage().addActor(beam);
        }
    }

    @Override
    public void updateAnimation(float delta) {
        duration += delta;
        TextureRegion frame = (TextureRegion) currentAnimation.getKeyFrame(duration, true);
        waddleDooSprite.setRegion(frame);
        waddleDooSprite.setFlip(flipX, false);
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
                currentAnimation = waddleDooAnimationWalk;
                break;
            case DIE:
                currentAnimation = waddleDooAnimationDie;
                break;
            case ATTACK:
                currentAnimation = waddleDooAnimationAttack;
                break;
            default:
                break;
        }
    }

    @Override
    public State getcurrentState() {
        return stateManager.getState();
    }

    public void dispose() {
        if (!isDisposed) {
            isDisposed = true;
            this.remove();
        }
    }
}

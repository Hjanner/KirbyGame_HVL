package KirbyGame_HVL.git.entities.enemis.brontoBurt;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.States.State;
import KirbyGame_HVL.git.entities.States.StateManager;
import KirbyGame_HVL.git.entities.States.StatesWaddleDee.AtractStateWaddleDee;
import KirbyGame_HVL.git.entities.States.statesBrontoBurt.AtractStateBrontoBurt;
import KirbyGame_HVL.git.entities.States.statesBrontoBurt.Die2StateBrontoBurt;
import KirbyGame_HVL.git.entities.States.statesBrontoBurt.DieStateBrontoBurt;
import KirbyGame_HVL.git.entities.States.statesBrontoBurt.FlyStateBrontoBurt;
import KirbyGame_HVL.git.entities.enemis.Enemy;
import KirbyGame_HVL.git.entities.enemis.EnumEnemyType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.MathUtils;

public class BrontoBurt extends Enemy {
    private Texture brontoBurtFlyTexture;
    private TextureRegion brontoBurtFlyRegion;
    private TextureRegion[] brontoBurtFlyFrames;
    private Texture brontoBurtDieTexture;
    private TextureRegion brontoBurtDieRegion;
    private TextureRegion[] brontoBurtDieFrames;
    private Animation flyAnimation;
    private Animation dieAnimation;
    private Animation currentAnimation;
    private Sprite brontoBurtSprite;

    private float duration = 0;
    private boolean flipX;
    private float startY;
    private float amplitude = 30f;                          // amplitud del movimiento ondulado
    private float frequency = 2f;                           // frecuencia cambio de  movimiento
    private float time = 0;                                 // tiempo para el movimiento ondulado

    private StateManager stateManager;
    private FlyStateBrontoBurt flyBrontoBurt;
    private DieStateBrontoBurt dieBrontoBurt;
    private AtractStateBrontoBurt atractBrontoBurt;
    private Die2StateBrontoBurt die2BrontoBurt;
    private boolean isDisposed = false;

    public BrontoBurt(World world, Main main, float x, float y) {
        this.world = world;
        this.main = main;
        this.stateManager = new StateManager();
        this.flyBrontoBurt = new FlyStateBrontoBurt(this);
        this.dieBrontoBurt = new DieStateBrontoBurt(this);
        this.atractBrontoBurt = new AtractStateBrontoBurt(this);
        this.die2BrontoBurt = new Die2StateBrontoBurt(this);
        this.stateManager.setState(flyBrontoBurt);
        this.startY = y;
        this.type = EnumEnemyType.BRONTO;
        createBody(world, x, y);
        loadTextures();
    }

    @Override
    public void createBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.KinematicBody;      // KinematicBody no lo afecta la gravedad
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(6f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.1f;

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        body.setFixedRotation(true);
        shape.dispose();
    }

    private void loadTextures() {
        brontoBurtFlyTexture = main.getManager().get("assets/art/spritesBrontoBurt/BrontoBurtFly.png");
        brontoBurtFlyRegion = new TextureRegion(brontoBurtFlyTexture, 96, 32);
        brontoBurtDieTexture = main.getManager().get("assets/art/spritesBrontoBurt/BrontoBurtDie.png");
        brontoBurtDieRegion = new TextureRegion(brontoBurtDieTexture, 32, 32);

        TextureRegion[][] tempFrames = brontoBurtFlyRegion.split(96/3, 32);
        brontoBurtFlyFrames = new TextureRegion[tempFrames.length * tempFrames[0].length];
        brontoBurtDieFrames = new TextureRegion[1];
        brontoBurtDieFrames[0] = brontoBurtDieRegion;

        int index = 0;
        for (int i = 0; i < tempFrames.length; i++) {
            for (int j = 0; j < tempFrames[i].length; j++) {
                brontoBurtFlyFrames[index++] = tempFrames[i][j];
            }
        }

        flyAnimation = new Animation(0.1f, brontoBurtFlyFrames);
        dieAnimation = new Animation(1, brontoBurtDieFrames);
        brontoBurtSprite = new Sprite(brontoBurtFlyFrames[0]);
        brontoBurtSprite.setSize(24, 24);                               //ajusta el tamano de sprite
        currentAnimation = flyAnimation;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        brontoBurtSprite.setPosition(body.getPosition().x - 12, body.getPosition().y - 6);
        brontoBurtSprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        if (isDisposed) {
            return;
        }
        super.act(delta);
        this.stateManager.update(delta);
        updateAnimation(delta);
    }



    public void setAnimation(EnumStateEnemy typeState) {
        switch (typeState) {
            case FLY:
                currentAnimation = flyAnimation;
                break;
            case DIE:
                currentAnimation = dieAnimation;
                break;
            default:
                break;
        }
    }

    @Override
    public void updateAnimation(float delta) {
        duration += delta;
        TextureRegion frame = (TextureRegion) currentAnimation.getKeyFrame(duration, true);
        brontoBurtSprite.setRegion(frame);
        brontoBurtSprite.setFlip(flipX, false);
    }

    @Override
    public void setState(EnumStateEnemy typeState) {
        switch (typeState) {
            case FLY:
                stateManager.setState(flyBrontoBurt);
                break;
            case DIE:
                stateManager.setState(dieBrontoBurt);
                break;
            case ATRACT:
                stateManager.setState(atractBrontoBurt);
                break;
            case DIE2:
                stateManager.setState(die2BrontoBurt);
                break;
            default:
                break;
        }
    }

    public void setflipX(boolean flipX) {
        this.flipX = flipX;
    }

    public boolean getflipX() {
        return flipX;
    }

    @Override
    public State getcurrentState() {
        return this.stateManager.getState();
    }

    public void dispose() {
        if (!isDisposed) {
            isDisposed = true;
            this.remove();
        }
    }

    public Body getBody() {
        return body;
    }

    public World getWorld() {
        return this.world;
    }

    public float getStartY() {
        return startY;
    }
}

package KirbyGame_HVL.git.entities.enemis.waddleDee;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.States.State;
import KirbyGame_HVL.git.entities.States.StateManager;
import KirbyGame_HVL.git.entities.States.StatesWaddleDee.AtractStateWaddleDee;
import KirbyGame_HVL.git.entities.States.StatesWaddleDee.Die2StateWaddleDee;
import KirbyGame_HVL.git.entities.States.StatesWaddleDee.DieStateWaddleDee;
import KirbyGame_HVL.git.entities.States.StatesWaddleDee.WalkStateWaddleDee;
import KirbyGame_HVL.git.entities.enemis.Enemy;
import KirbyGame_HVL.git.entities.enemis.EnumEnemyType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public class WaddleDee extends Enemy {

    private Texture waddleDeeWalkTexture;
    private TextureRegion waddleDeeWalkRegion;
    private TextureRegion[] waddleDeeWalkFrames;
    private Texture waddleDeeDieTexture;
    private TextureRegion waddleDeeDieRegion;
    private TextureRegion[] waddleDeeDieFrames;
    private Animation walkAnimation;
    private Animation dieAnimation;
    private Animation currentAnimation;
    private Sprite waddleDeeSprite;
    private boolean flipX;

    private StateManager stateManager;
    private WalkStateWaddleDee walkWaddleDee;
    private DieStateWaddleDee dieWaddleDee;
    private AtractStateWaddleDee atractWaddleDee;
    private Die2StateWaddleDee die2WaddleDee;
    private boolean isDisposed = false;

    public WaddleDee(World world, Main main, float x, float y) {
        this.world = world;
        this.main = main;
        this.type = EnumEnemyType.WADDLE;
        this.stateManager = new StateManager();
        this.walkWaddleDee = new WalkStateWaddleDee(this);
        this.dieWaddleDee = new DieStateWaddleDee(this);
        this.atractWaddleDee = new AtractStateWaddleDee(this);
        this.die2WaddleDee = new Die2StateWaddleDee(this);
        this.stateManager.setState(walkWaddleDee);
        createBody(world, x, y);
        loadTextures();
    }

    @Override
    public void createBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();                        //def del cuerpo
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;            //cuerpo dinamico
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(4.5f);                                 // Similar al Kirby

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.1f;

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);                       // para detectar colisiones, hace ref al waddle dee

        body.setFixedRotation(true);
        shape.dispose();
    }

    private void loadTextures() {
        waddleDeeWalkTexture = main.getManager().get("assets/art/spritesWaddleDee/WaddleDeeWalk.png");
        waddleDeeWalkRegion = new TextureRegion(waddleDeeWalkTexture, 256, 32); // probar con 320 de width
                    waddleDeeDieTexture = main.getManager().get("assets/art/spritesWaddleDee/WaddleDeeDie.png");
        waddleDeeDieRegion = new TextureRegion(waddleDeeDieTexture, 32, 32);


        TextureRegion[][] tempFrames = waddleDeeWalkRegion.split(256/8, 32);
        waddleDeeWalkFrames = new TextureRegion[tempFrames.length * tempFrames[0].length];                                 // 4 walking frames
        waddleDeeDieFrames = new TextureRegion[1];
        waddleDeeDieFrames[0] = waddleDeeDieRegion;

        int index = 0;
        for (int i = 0; i < tempFrames.length; i++) {
            for (int j = 0; j < tempFrames[i].length; j++) {
                waddleDeeWalkFrames[index++] = tempFrames[i][j];
            }
        }

        // crear animation y y sprite
        walkAnimation = new Animation(0.1f, waddleDeeWalkFrames);
        dieAnimation = new Animation(1, waddleDeeDieFrames);
        waddleDeeSprite = new Sprite(waddleDeeWalkFrames[0]);
        waddleDeeSprite.setSize(15, 15);
        currentAnimation = walkAnimation;
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

    public void setAnimation (EnumStateEnemy typeState) {
        switch (typeState) {
            case WALK:
                currentAnimation = walkAnimation;
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
        waddleDeeSprite.setRegion(frame);
        waddleDeeSprite.setFlip(flipX, false);
    }

    @Override
    public void setState(EnumStateEnemy typeState) {
        switch (typeState) {
            case WALK:
                stateManager.setState(walkWaddleDee);
                break;
            case DIE:
                stateManager.setState(dieWaddleDee);
                break;
            case ATRACT:
                stateManager.setState(atractWaddleDee);
                break;
            case DIE2:
                stateManager.setState(die2WaddleDee);
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
    public State getcurrentState () {
        return this.stateManager.getState();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        waddleDeeSprite.setPosition(body.getPosition().x - 8, body.getPosition().y - 5);
        waddleDeeSprite.draw(batch);
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

    public World getWorld () {
        return this.world;
    }
}

package KirbyGame_HVL.git.entities.enemis.hotHead;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.States.State;
import KirbyGame_HVL.git.entities.enemis.Enemy;
import KirbyGame_HVL.git.entities.attacks.Fire;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class HotHead extends Enemy {
    private World world;
    private Main main;
    private Body body;
    private Fixture fixture;

    private Texture hotHeadTexture;
    private TextureRegion hotHeadRegion;
    private TextureRegion[] hotHeadFrames;
    private Animation walkAnimation;
    private Sprite hotHeadSprite;

    private float duration = 0;
    private boolean flipX = false;
    private float movementSpeed = 25f;
    private float movementTime = 0;
    private float cambioDireccion = MathUtils.random(1.5f, 3.0f);

    private float detectionRadius = 50f;                                       // rango de detecccion del Kirby
    private boolean isAggressive = false;
    private float agresiveSpeed = 40f;
    private float normalSpeed = 25f;
    private float currentSpeed;

    private boolean isDead = false;
    private boolean isDisposed = false;
    private float accumulatedtimer = 0;

    private boolean canShootFire = true;
    private float fireTimer = 0f;
    private static final float FIRE_COOLDOWN = 2.0f;

    private Body kirbyBody;

    public HotHead(World world, Main main, float x, float y) {
        this.world = world;
        this.main = main;
        currentSpeed = normalSpeed;
        createBody(world, x, y);
        loadTextures();
    }

    public void createBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(6);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.8f;
        fixtureDef.restitution = 0.0f;

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        body.setFixedRotation(true);
        shape.dispose();
    }

    private void loadTextures() {
        hotHeadTexture = main.getManager().get("assets/art/spritesWaddleDee/WaddleDeeDie.png");
        hotHeadRegion = new TextureRegion(hotHeadTexture, 128, 32);

        TextureRegion[][] tempFrames = hotHeadRegion.split(32, 32);
        hotHeadFrames = new TextureRegion[4];

        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                hotHeadFrames[index++] = tempFrames[i][j];
            }
        }

        walkAnimation = new Animation(0.15f, hotHeadFrames);
        hotHeadSprite = new Sprite(hotHeadFrames[0]);
        hotHeadSprite.setSize(24, 24);
    }

    @Override
    public void act(float delta) {
        if (isDisposed) {
            return;
        }
        super.act(delta);
        updateComportamiento(delta);
        updateAnimation(delta);
    }

    private void updateComportamiento(float delta) {
        movementTime += delta;
        accumulatedtimer += delta;

        if (movementTime > cambioDireccion) {                   //cambio de direccion
            movementTime = 0;
            flipX = !flipX;
            movementSpeed = -movementSpeed;
            cambioDireccion = MathUtils.random(1.5f, 5.0f);
        }

        if (movementSpeed > 0) {
            flipX = !flipX;
        }

        //manejo de deteccion de kirby cerca
        if (kirbyBody != null) {
            Vector2 kirbyPos = kirbyBody.getPosition();
            Vector2 hotHeadPos = body.getPosition();
            float distance = kirbyPos.dst(hotHeadPos);

            isAggressive = distance <= detectionRadius;

            if (isAggressive) {
                // se mueve hacie el kirby
                float direction = kirbyPos.x > hotHeadPos.x ? 1 : -1;
                movementSpeed = agresiveSpeed * direction;
                flipX = direction < 0;
            }
        }

        updateAttack(delta);
        body.setLinearVelocity(movementSpeed, body.getLinearVelocity().y);                                              //movimiento
    }

    private void updateAttack(float delta) {
        if (!canShootFire) {
            fireTimer += delta;
            if (fireTimer >= FIRE_COOLDOWN) {
                canShootFire = true;
                fireTimer = 0f;
            }
        }

        if (isAggressive && canShootFire) {
            shootFire();
            canShootFire = false;
        }
    }

    private void shootFire() {
        Fire fire = new Fire(world, this, !flipX);
        getStage().addActor(fire);
    }

    public void updateAnimation(float delta) {
        duration += delta;
        TextureRegion frame = (TextureRegion) walkAnimation.getKeyFrame(duration, true);
        hotHeadSprite.setRegion(frame);
        hotHeadSprite.setFlip(flipX, false);
    }

    @Override
    public void setState(EnumStateEnemy typeState) {

    }

    @Override
    public State getcurrentState() {
        return null;
    }

    public void die() {
        isDead = true;
        dispose();
        remove();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isDisposed || isDead) {
            return;
        }
        hotHeadSprite.setPosition(body.getPosition().x - 12, body.getPosition().y - 6);
        hotHeadSprite.draw(batch);
    }

    public Body getBody() {return body;}

    public void setKirbyBody(Body kirbyBody) {this.kirbyBody = kirbyBody;}

    public Body getKirbyBody() {return kirbyBody;}

    public void dispose() {
        if (!isDisposed) {
            if (world != null && body != null) {
                world.destroyBody(body);
                body = null;
            }
            if (hotHeadTexture != null) {
                hotHeadTexture.dispose();
            }
            isDisposed = true;
            this.remove();
        }
    }

}

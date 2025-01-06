package KirbyGame_HVL.git.entities.attacks;

import KirbyGame_HVL.git.entities.player.ActorWithBox2d;
import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;
import org.w3c.dom.Text;

public class Fire extends Attack {
    private Texture fireTexture;
    private TextureRegion fireRegion;
    private TextureRegion[] fireFrames;
    private Sprite fireSprite;
    private static final float FIRE_LIFETIME = 1.2f;
    private float fireSpeed = 60f;
    private Animation fireanimation;

    public Fire(World world, ActorWithBox2d actor, boolean sentido) {
        this.world = world;
        this.actor = actor;
        this.sentido = !sentido;
        this.duracion = 0;
        load_textures();
        this.accumulatedtimer = 0;
        if (actor instanceof Kirby) {this.attackOfkirby = true;}
        createBody(this.world, this.actor, sentido);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        fireSprite.setPosition(body.getPosition().x - 8, body.getPosition().y - 9);
        fireSprite.draw(batch);
    }

    @Override
    public void createBody(World world, ActorWithBox2d actor, boolean direction) {
        BodyDef bodyDef = new BodyDef();
        if (direction) {
            if (actor instanceof Kirby) {
                bodyDef.position.set(actor.getBody().getPosition().x + 13f,
                    actor.getBody().getPosition().y + 2);
            }
            else {
                bodyDef.position.set(actor.getBody().getPosition().x + 8f,
                    actor.getBody().getPosition().y + 2);
            }
        } else {
            if (actor instanceof Kirby) {
                bodyDef.position.set(actor.getBody().getPosition().x - 13f,
                    actor.getBody().getPosition().y + 2);
            }
            else {
                bodyDef.position.set(actor.getBody().getPosition().x - 8f,
                    actor.getBody().getPosition().y + 2);
            }
        }

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(4);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.01f;
        fixtureDef.isSensor = true;

        fixture = body.createFixture(fixtureDef);
        fixture.setSensor(true);
        fixture.setUserData(this);

        float direccionMultiplier = sentido ? 1 : -1;
        body.setLinearVelocity(fireSpeed * direccionMultiplier, 0);

        shape.dispose();
    }

    private void load_textures () {
        fireTexture = new Texture("assets/art/sprites/SpritesAttacks/fireball.png");
        fireRegion = new TextureRegion(fireTexture, 128, 32);
        fireSprite = new Sprite(fireRegion);
        TextureRegion[][] tempFire = fireRegion.split(128/4,32);
        fireFrames = new TextureRegion[tempFire.length * tempFire[0].length];
        fireSprite.setSize(15, 15);

        int index = 0;
        for (int i = 0; i < tempFire.length; i++) {
            for (int j = 0; j < tempFire[i].length; j++) {
                fireFrames[index] = tempFire[i][j];
                index++;
            }
        }

        fireanimation = new Animation(0.08f, fireFrames);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateAnimation(delta);
        accumulatedtimer += delta;

        float currentXVel = body.getLinearVelocity().x;
        float desiredXVel = !sentido ? fireSpeed : -fireSpeed;

        float velChange = desiredXVel - currentXVel;
        float impulse = body.getMass() * velChange;
        body.applyLinearImpulse(new Vector2(impulse, 0), body.getWorldCenter(), true);

        // autodestruccion automatica del fuego  de cierto tiempo
        if (accumulatedtimer > FIRE_LIFETIME) {
            if (world != null && body != null) {
                world.destroyBody(body);
                body = null;
            }
            remove();
            dispose();
        }
    }

    private void updateAnimation(float delta) {
        duracion += delta;
        TextureRegion frame = (TextureRegion) fireanimation.getKeyFrame(duracion, true);
        fireSprite.setRegion(frame);
        fireSprite.setFlip(sentido, true);
    }

    public void dispose() {
        if (fireTexture != null) {
            fireTexture.dispose();
            fireTexture = null;
        }
    }

    public ActorWithBox2d getActor() {
        return actor;
    }

}

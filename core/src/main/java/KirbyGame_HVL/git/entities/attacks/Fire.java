package KirbyGame_HVL.git.entities.attacks;

import KirbyGame_HVL.git.entities.player.ActorWithBox2d;
import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;

public class Fire extends Attack {
    private Texture fireTexture;
    private TextureRegion fireRegion;
    private Sprite fireSprite;
    private ActorWithBox2d actor;
    private float accumulatedTimer;
    private static final float FIRE_LIFETIME = 1.2f;
    private float fireSpeed = 60f;
    private boolean sentido;

    public Fire(World world, ActorWithBox2d actor, boolean sentido) {
        this.world = world;
        this.actor = actor;
        this.sentido = sentido;
        this.fireTexture = new Texture("assets/art/sprites/fireball.png");
        this.fireRegion = new TextureRegion(fireTexture, 32, 32);
        this.fireSprite = new Sprite(fireRegion);
        this.fireSprite.setSize(40, 40);
        this.accumulatedTimer = 0;
        if (actor instanceof Kirby) {this.attackOfkirby = true;}
        createBody(this.world, this.actor, sentido);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        fireSprite.setPosition(body.getPosition().x - 12, body.getPosition().y - 4);
        fireSprite.draw(batch);
    }

    @Override
    public void createBody(World world, ActorWithBox2d actor, boolean direction) {
        BodyDef bodyDef = new BodyDef();
        if (direction) {
            bodyDef.position.set(actor.getBody().getPosition().x + 8f,
                actor.getBody().getPosition().y);
        } else {
            bodyDef.position.set(actor.getBody().getPosition().x - 8f,
                actor.getBody().getPosition().y);
        }

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10,4);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.01f;
        fixtureDef.friction = 0.2f;
        fixtureDef.isSensor = true;

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        float direccionMultiplier = sentido ? 1 : -1;
        body.setLinearVelocity(fireSpeed * direccionMultiplier, 0);

        shape.dispose();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        accumulatedTimer += delta;

        float currentXVel = body.getLinearVelocity().x;
        float desiredXVel = sentido ? fireSpeed : -fireSpeed;

        float velChange = desiredXVel - currentXVel;
        float impulse = body.getMass() * velChange;
        body.applyLinearImpulse(new Vector2(impulse, 0), body.getWorldCenter(), true);

        // autodestruccion automatica del fuego  de cierto tiempo
        if (accumulatedTimer > FIRE_LIFETIME) {
            if (world != null && body != null) {
                world.destroyBody(body);
                body = null;
            }
            remove();
            dispose();
        }
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

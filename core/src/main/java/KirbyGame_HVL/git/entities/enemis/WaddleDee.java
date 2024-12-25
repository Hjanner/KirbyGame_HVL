package KirbyGame_HVL.git.entities.enemis;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class WaddleDee extends Actor {
    private World world;
    private Main main;
    private Body body;
    private Fixture fixture;

    private Texture waddleDeeTexture;
    private TextureRegion waddleDeeRegion;
    private TextureRegion[] waddleDeeFrames;
    private Animation walkAnimation;
    private Sprite waddleDeeSprite;

    private float duration = 0;
    private boolean flipX = false;
    private float movementSpeed = 30f;                  // Pixels per second
    private float movementTime = 0;
    private float cambioDireccionIntervalo = 2f;

    private boolean isDead = false;
    private boolean isDisposed = false;

    public WaddleDee(World world, Main main, float x, float y) {
        this.world = world;
        this.main = main;
        createBody(world, x, y);
        loadTextures();
    }

    private void createBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();                        //def del cuerpo
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;            //cuerpo dinamico
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(8);                                 // Similar al Kirby

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.1f;

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);                       // para detectar colisiones, hace ref al waddle dee

        body.setFixedRotation(true);
        shape.dispose();
    }

    private void loadTextures() {
        waddleDeeTexture = main.getManager().get("assets/art/sprites/kirbywalking.png");
        waddleDeeRegion = new TextureRegion(waddleDeeTexture, 128, 32); // probar con 320 de width

        TextureRegion[][] tempFrames = waddleDeeRegion.split(32, 32);
        waddleDeeFrames = new TextureRegion[4];                                 // 4 walking frames

        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                waddleDeeFrames[index++] = tempFrames[i][j];
            }
        }

        // crear animation y y sprite
        walkAnimation = new Animation(0.15f, waddleDeeFrames);
        waddleDeeSprite = new Sprite(waddleDeeFrames[0]);
        waddleDeeSprite.setSize(32, 32);
    }

    @Override
    public void act(float delta) {
        if (isDisposed) {
            return;
        }
        super.act(delta);
        updateMovement(delta);
        updateAnimation(delta);
    }

    private void updateMovement(float delta) {
        movementTime += delta;

        // cambiar deireccion
        if (movementTime > cambioDireccionIntervalo) {
            movementTime = 0;
            flipX = !flipX;
            movementSpeed = -movementSpeed;
        }

        body.setLinearVelocity(movementSpeed, body.getLinearVelocity().y);                                              // aplica movimiento

        body.applyLinearImpulse(0, -9.8f, body.getPosition().x, body.getPosition().y, true);       // gravedad

        //los puedo mandar a volar para arriba
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            body.applyLinearImpulse(100, 0, body.getPosition().x, body.getPosition().y, true);
        }

        //los puedo mandar a volar en una direccion
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            body.applyLinearImpulse(100, 100, body.getPosition().x, body.getPosition().y, true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            body.applyLinearImpulse(-100, -100, body.getPosition().x, body.getPosition().y, true);
        }
    }

    private void updateAnimation(float delta) {
        duration += delta;
        TextureRegion frame = (TextureRegion) walkAnimation.getKeyFrame(duration, true);
        waddleDeeSprite.setRegion(frame);
        waddleDeeSprite.setFlip(flipX, false);
    }

    public void handleCollision(Kirby kirby) {
        System.out.println("kirby muerto");
    }

    public void die() {
        isDead = true;
        body.setActive(false);          // Desactivar físicas
        System.out.println("muerto");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isDisposed || isDead) {
            return;
        }
        waddleDeeSprite.setPosition(body.getPosition().x - 16, body.getPosition().y - 8);
        waddleDeeSprite.draw(batch);
    }

    public void dispose() {
        if (!isDisposed) {
            isDisposed = true;
            this.remove();                          // Remover el actor del stage
        }
    }

    // Getters for collision detection
    public Body getBody() {
        return body;
    }
}

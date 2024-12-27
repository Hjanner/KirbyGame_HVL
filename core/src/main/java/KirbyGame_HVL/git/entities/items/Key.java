package KirbyGame_HVL.git.entities.items;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.player.ActorWithBox2d;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;

public class Key extends ActorWithBox2d {
    private Texture keyTexture;
    private Sprite keySprite;
    private Main main;
    private boolean isCollected;
    private float originalY;
    private float time;
    private final float FLOAT_AMPLITUDE = 5f;        // que tanto flota arriba y abajo
    private final float FLOAT_SPEED = 3f;           // velocidad  animation

    public Key(World world, Main main, float x, float y) {
        this.world = world;
        this.main = main;
        this.isCollected = false;
        this.originalY = y;
        this.time = 0;

        createBody(world, x, y);
        loadTextures();
    }

    private void loadTextures() {
        keyTexture = main.getManager().get("assets/art/sprites/kirbystay.png");
        keySprite = new Sprite(keyTexture);
        keySprite.setSize(16, 16);
    }


    private void createBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(8f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;                         // sensor

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!isCollected) {
            keySprite.setPosition(body.getPosition().x - 8, body.getPosition().y - 8);
            keySprite.draw(batch);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!isCollected) {
            time += delta;
            float newY = originalY + (MathUtils.sin(time * FLOAT_SPEED) * FLOAT_AMPLITUDE);
            body.setTransform(body.getPosition().x, newY, 0);
        }
    }

    public void collect() {
        isCollected = true;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public Body getBody () {
        return fixture.getBody();
    }

    public void dispose() {
        if (keyTexture != null) {
            keyTexture.dispose();
            keyTexture = null;
        }
    }

}

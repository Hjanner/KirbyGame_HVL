package KirbyGame_HVL.git.entities.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import KirbyGame_HVL.git.Main;

public class Door extends Actor {
    private Body body;
    private World world;
    private Main main;
    private Texture doorTexture;
    private Sprite doorSprite;
    private boolean isActive;

    public Door(World world, Main main, float x, float y) {
        this.world = world;
        this.main = main;
        this.isActive = false;
        createBody(x, y);
        loadTextures();
    }

    private void createBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16, 32);                     // Ajusta el tamaño según necesites

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;                         // sensor

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();

        this.body = body;
    }

    private void loadTextures() {
        doorTexture = main.getManager().get("assets/art/sprites/kirbystay.png");
        doorSprite = new Sprite(doorTexture);
        doorSprite.setSize(16, 32);
    }

    public Body getBody() {
        return body;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition(body.getPosition().x, body.getPosition().y);
    }

    public void dispose() {
        if (body != null) {
            world.destroyBody(body);
        }
    }
}

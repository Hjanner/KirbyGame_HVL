package KirbyGame_HVL.git.entities.items;

import KirbyGame_HVL.git.entities.player.ActorWithBox2d;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import KirbyGame_HVL.git.Main;

public class Door extends ActorWithBox2d {

// Atributos
    // Texturas de la puerta
    private Texture doorTexture;
    private TextureRegion doorTextureRegion;
    private Sprite doorSprite;

    // Constructor
    public Door(World world, Main main, float x, float y, boolean tipoPuerta) {
        this.world = world;
        this.main = main;
        createBody(x, y);
        loadTextures(tipoPuerta);
    }

    // Dibujamos la puerta
    @Override
    public void draw(Batch batch, float parentAlpha) {
        doorSprite.setPosition(body.getPosition().x - 9.5f, body.getPosition().y - 12);
        doorSprite.draw(batch);
    }

    // Creamos el cuerpo de la puerta
    private void createBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8.5f, 12);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        // Sensor
        fixtureDef.isSensor = true;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();

        this.body = body;
    }

    // Cargamos las texturas
    private void loadTextures(boolean tipoPuerta) {
        if (tipoPuerta) {
            doorTexture = main.getManager().get("assets/art/sprites/spritesItems/Door1.png");
        } else {
            doorTexture = main.getManager().get("assets/art/sprites/spritesItems/Door2.png");
        }
        doorTextureRegion = new TextureRegion(doorTexture, 34,42);
        doorSprite = new Sprite(doorTextureRegion);
        doorSprite.setSize(19, 26.5f);
    }

    // Actualizamos la puerta
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    // Eliminamos cualquier tipo de residuo
    public void dispose() {
        if (body != null) {
            world.destroyBody(body);
        }
    }
}

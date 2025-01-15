package KirbyGame_HVL.git.entities.items;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.player.ActorWithBox2d;
import KirbyGame_HVL.git.entities.player.Box2dSpace;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class PlatformMoved extends ActorWithBox2d {

// Atributos
    // Texturas la plataforma
    private Texture platformTexture;
    private TextureRegion platformTextureRegion;
    private Sprite platformSprite;

    // Variables para el movimiento de la plataforma
    private static final float MOVEMENT_SPEED = 30f;
    private static final float CAMBIO_DIRECCION = 5f;

    // true para vertical, false para horizontal
    private boolean isVerticalM;
    private float duracion = 0;
    private boolean movimeintoPositivo;

    // Constructor
    public PlatformMoved(World world, Main main, float x, float y, boolean isVerticalMovement) {
        this.world = world;
        this.main = main;
        this.isVerticalM = isVerticalMovement;
        this.movimeintoPositivo = true;

        createBody(world,x, y);
        loadTextures();
    }

    // Creamos el cuerpo de la plataforma movil
    public void createBody(World world,float x, float y) {

        BodyDef bodydef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        bodydef.type = BodyDef.BodyType.KinematicBody;
        bodydef.position.set(x,y);
        body = world.createBody(bodydef);
        shape.setAsBox(61.5f,7);
        fixture = body.createFixture(shape,1);
        fixture.setUserData("Plataforma");
        shape.dispose();
    }

    // Cargamos las texturas de la plataforma
    private void loadTextures () {

        platformTexture = main.getManager().get("assets/art/tilesets/Platform.png");
        platformTextureRegion = new TextureRegion(platformTexture, 130,18);
        platformSprite = new Sprite(platformTextureRegion);
        platformSprite.setSize(122.5f,12.5f);
    }

    // Actualizamos la plataforma movil
    @Override
    public void act(float delta) {
        super.act(delta);
        updateMovement(delta);
    }

    // Actualizamos el movimiento de la plataforma
    public void updateMovement(float delta) {
        duracion += delta;

        if (isVerticalM) {

            // Cambiar direccion
            if (duracion >= CAMBIO_DIRECCION) {
                duracion = 0;
                movimeintoPositivo = !movimeintoPositivo;
            }

            // Aplicar velocidad
            float velocityY = movimeintoPositivo ? MOVEMENT_SPEED : -MOVEMENT_SPEED;
            body.setLinearVelocity(0, velocityY);
        } else {
            if (duracion >= CAMBIO_DIRECCION) {
                duracion = 0;
                movimeintoPositivo = !movimeintoPositivo;
            }

            // Aplicar velocidad
            float velocityX = movimeintoPositivo ? MOVEMENT_SPEED : -MOVEMENT_SPEED;
            body.setLinearVelocity(velocityX, 0);
        }
    }

    // Dibujamos la plataforma
    @Override
    public void draw(Batch batch, float parentAlpha) {

        platformSprite.setPosition(body.getPosition().x - 62, body.getPosition().y - 4f);
        platformSprite.draw(batch);
    }

    @Override
    public void dispose() {
        if (body != null) {
            body.getWorld().destroyBody(body);
            body = null;
        }
    }
}

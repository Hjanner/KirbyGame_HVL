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
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class PlatformMoved extends ActorWithBox2d {

    private Texture platformTexture;
    private TextureRegion platformTextureRegion;
    private Sprite platformSprite;
    private boolean sentido;

    public PlatformMoved (World world, Main main, float x, float y) {

        this.world = world;
        this.main = main;
        createBody(world,x, y);
        loadTextures();
    }

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

    private void loadTextures () {

        platformTexture = main.getManager().get("assets/art/tilesets/Platform.png");
        platformTextureRegion = new TextureRegion(platformTexture, 130,18);
        platformSprite = new Sprite(platformTextureRegion);
        platformSprite.setSize(122.5f,12.5f);
    }
    @Override
    public void act(float delta) {
        super.act(delta);

        // Verificar en que sentido va la plataforma, si se mueve horizontal o verticalmente
        if (sentido) {

        }

        else {
            // Logica de movimiento de las plataformas
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        platformSprite.setPosition(body.getPosition().x - 62, body.getPosition().y - 4f);
        platformSprite.draw(batch);
    }

    @Override
    public void dispose() {

    }
}

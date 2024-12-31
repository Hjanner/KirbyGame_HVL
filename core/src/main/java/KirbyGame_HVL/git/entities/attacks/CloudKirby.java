package KirbyGame_HVL.git.entities.attacks;

import KirbyGame_HVL.git.entities.player.ActorWithBox2d;
import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class CloudKirby extends Attack {

    private Texture kirbyCloudTexture;
    private TextureRegion kirbyCloudRegion;
    private Sprite kirbycloudsprite;
    private ActorWithBox2d actor;
    private float accumulatedtimer;
    private static final float CLOUD_LIFETIME = 0.8f;

    public CloudKirby (World world, ActorWithBox2d actor, boolean sentido) {
        this.world = world;
        this.actor = actor;
        this.kirbyCloudTexture = new Texture("assets/art/sprites/kirbycloud.png");
        this.kirbyCloudRegion = new TextureRegion(kirbyCloudTexture, 32,32);
        this.kirbycloudsprite = new Sprite(kirbyCloudRegion);
        this.kirbycloudsprite.setSize(25,25);
        this.accumulatedtimer = 0;
        if (actor instanceof Kirby) {this.attackOfkirby = true;}
        createBody(this.world, this.actor, sentido);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.kirbycloudsprite.setPosition(body.getPosition().x - 12,body.getPosition().y - 4);
        this.kirbycloudsprite.draw(batch);
    }

    @Override
    public void createBody (World world, ActorWithBox2d actor, boolean sentido) {
        BodyDef bodyDef = new BodyDef();
        if (sentido) {
            bodyDef.position.set(actor.getBody().getPosition().x + 5.5f, actor.getBody().getPosition().y + 2);
        }
        else {
            bodyDef.position.set(actor.getBody().getPosition().x - 5.5f, actor.getBody().getPosition().y + 2);
        }
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(3,3);

        fixture = body.createFixture(shape,0.01f);
        fixture.setUserData(this);
        shape.dispose();
    }

    public Body getBody () {
        return fixture.getBody();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.accumulatedtimer += delta;

        //este metodo esta al peloooo pelucha
        if (accumulatedtimer > CLOUD_LIFETIME) {
            if (world != null && body != null) {
                world.destroyBody(body);            //Eliminacion directa del cuerpo fisico ojito! se ejecuta antes de eliminar el actor para evitar referencias fisicas residuales
                body = null;

            }
            remove();                                //se elimina al actor del stage
            dispose();                              //elimina cualquier textura o recurso asociado con la nube
        }

    }

    public ActorWithBox2d getActor() {
        return actor;
    }


    public void dispose() {
        if (kirbyCloudTexture != null) {
            kirbyCloudTexture.dispose();
            kirbyCloudTexture = null;
        }
    }
}


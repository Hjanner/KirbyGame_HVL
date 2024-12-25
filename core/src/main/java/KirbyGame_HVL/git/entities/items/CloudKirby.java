package KirbyGame_HVL.git.entities.items;

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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class CloudKirby extends ActorWithBox2d {

    private Texture kirbyCloudTexture;
    private TextureRegion kirbyCloudRegion;
    private Sprite kirbycloudsprite;
    private Kirby kirby;
    private float accumulatedtimer;

    public CloudKirby (World world, Kirby kirby, boolean sentido) {
        this.world = world;
        this.kirby = kirby;
        this.kirbyCloudTexture = new Texture("assets/art/sprites/kirbycloud.png");
        this.kirbyCloudRegion = new TextureRegion(kirbyCloudTexture, 32,32);
        this.kirbycloudsprite = new Sprite(kirbyCloudRegion);
        this.kirbycloudsprite.setSize(25,25);
        this.accumulatedtimer = 0;
        createBody(this.world, this.kirby, sentido);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.kirbycloudsprite.setPosition(body.getPosition().x - 12,body.getPosition().y - 4);
        this.kirbycloudsprite.draw(batch);
    }

    public void createBody (World world, Kirby kirby, boolean sentido) {
        BodyDef bodyDef = new BodyDef();
        if (sentido) {
            bodyDef.position.set(kirby.getBody().getPosition().x + 5, kirby.getBody().getPosition().y + 2);
        }
        else {
            bodyDef.position.set(kirby.getBody().getPosition().x - 5, kirby.getBody().getPosition().y + 2);
        }
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(3,3);
        fixture = body.createFixture(shape,0.01f);
        fixture.setUserData("Cloud");
        shape.dispose();
    }

    public Body getBody () {
        return fixture.getBody();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.accumulatedtimer += delta;
        if (accumulatedtimer > 0.6f) {
            addAction(Actions.removeActor());
        }
    }
}

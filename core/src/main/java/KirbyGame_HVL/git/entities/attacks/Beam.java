package KirbyGame_HVL.git.entities.attacks;

import KirbyGame_HVL.git.entities.player.ActorWithBox2d;
import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public class Beam extends Attack {

    private Texture beamTexture;
    private TextureRegion beamRegion;
    private TextureRegion[] beamFrames;
    private Sprite beamSprite;
    private static final float BEAM_LIFETIME = 1.2f;
    private Animation beamAnimation;
    private Animation currentAnimation;

    public Beam (World world, ActorWithBox2d actor, boolean sentido) {
        this.world = world;
        this.actor = actor;
        this.sentido = !sentido;
        this.duracion = 0;
        load_textures();
        this.accumulatedtimer = 0;
        if (actor instanceof Kirby) {this.attackOfkirby = true;}
        createBody(this.world, this.actor, sentido);
    }

    private void load_textures() {
        if (actor instanceof Kirby) {
            beamTexture = new Texture("assets/art/sprites/SpritesAttacks/BeamKirby.png");
            beamRegion = new TextureRegion(beamTexture, 1200, 50);
            TextureRegion [][] tempBeam = beamRegion.split(1200/24,50);
            beamFrames = new TextureRegion[tempBeam.length * tempBeam[0].length];
            beamSprite = new Sprite(beamRegion);
            beamSprite.setSize(35,35);

            int id = 0;
            for (int i = 0; i < tempBeam.length; i++) {
                for (int j = 0; j < tempBeam[i].length; j++) {
                    beamFrames[id] = tempBeam[i][j];
                    id++;
                }
            }

            beamAnimation = new Animation(0.03f, beamFrames);

        }

        else {
            beamTexture = new Texture("assets/art/sprites/SpritesAttacks/Beam.png");
            beamRegion = new TextureRegion(beamTexture, 1200, 50);
            TextureRegion [][] tempBeam = beamRegion.split(1200/24,50);
            beamFrames = new TextureRegion[tempBeam.length * tempBeam[0].length];
            beamSprite = new Sprite(beamRegion);
            beamSprite.setSize(35,35);

            int id = 0;
            for (int i = 0; i < tempBeam.length; i++) {
                for (int j = 0; j < tempBeam[i].length; j++) {
                    beamFrames[id] = tempBeam[i][j];
                    id++;
                }
            }

            beamAnimation = new Animation(0.02f, beamFrames);
        }
        currentAnimation = beamAnimation;
    }

    @Override
    public void createBody(World world, ActorWithBox2d actor, boolean direction) {

        BodyDef bodyDef = new BodyDef();
        if (direction) {
            if (actor instanceof Kirby) {
                bodyDef.position.set(actor.getBody().getPosition().x + 19f,
                    actor.getBody().getPosition().y + 10.5f);
            }
            else {
                bodyDef.position.set(actor.getBody().getPosition().x + 20f,
                    actor.getBody().getPosition().y + 10);
            }
        } else {
            if (actor instanceof Kirby) {
                bodyDef.position.set(actor.getBody().getPosition().x - 20f,
                    actor.getBody().getPosition().y + 10.5f);
            }
            else {
                bodyDef.position.set(actor.getBody().getPosition().x - 20f,
                    actor.getBody().getPosition().y + 10);
            }
        }

        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(12,16);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.01f;
        fixtureDef.isSensor = true;

        fixture = body.createFixture(fixtureDef);
        fixture.setSensor(true);
        fixture.setUserData(this);

        shape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (actor instanceof Kirby) {
            if (actor.getFlipX()) {
                beamSprite.setPosition(body.getPosition().x - 20, body.getPosition().y - 19);
            } else {
                beamSprite.setPosition(body.getPosition().x - 15.5f, body.getPosition().y - 19);
            }
        }
        else {
            beamSprite.setPosition(body.getPosition().x - 16, body.getPosition().y - 18);
        }
        beamSprite.draw(batch);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateAnimation(delta);
        accumulatedtimer += delta;
        // autodestruccion automatica del fuego  de cierto tiempo
        if (actor instanceof Kirby) {
            if (accumulatedtimer > 0.7f) {
                if (world != null && body != null) {
                    world.destroyBody(body);
                    body = null;
                }
                remove();
                dispose();
            }
        }
        else {
            if (accumulatedtimer > BEAM_LIFETIME) {
                if (world != null && body != null) {
                    world.destroyBody(body);
                    body = null;
                }
                remove();
                dispose();
            }
        }


    }

    private void updateAnimation(float delta) {
        duracion += delta;
        TextureRegion frame = (TextureRegion) currentAnimation.getKeyFrame(duracion, true);
        beamSprite.setRegion(frame);
        beamSprite.setFlip(sentido, false);
    }
}

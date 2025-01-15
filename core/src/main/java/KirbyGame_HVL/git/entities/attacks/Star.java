package KirbyGame_HVL.git.entities.attacks;

import KirbyGame_HVL.git.entities.player.ActorWithBox2d;
import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public class Star extends Attack {

    // Atributos
    private Texture kirbyStarTexture;
    private TextureRegion kirbyStarRegion;
    private TextureRegion [] kirbyframesStar;
    private Animation kirbyanimationStar;
    private Sprite kirbyStarsprite;
    private static final float STAR_LIFETIME = 1.5f;

    // Constructor
    public Star (World world, ActorWithBox2d actor, boolean sentido) {
        this.world = world;
        this.actor = actor;
        this.sentido = sentido;
        load_animation();
        this.accumulatedtimer = 0;
        this.duracion = 0;
        if (actor instanceof Kirby) {this.attackOfkirby = true;}
        createBody(this.world, this.actor, sentido);

    }

    // Cargamos las texturas y animaciones de la estrella
    private void load_animation() {
        kirbyStarTexture = new Texture("assets/art/sprites/SpritesAttacks/kirbyStar.png");
        kirbyStarRegion = new TextureRegion(kirbyStarTexture, 128,32);
        kirbyStarsprite = new Sprite(kirbyStarRegion);
        kirbyStarsprite.setSize(15,15);
        TextureRegion [][] tempkirbyStar = kirbyStarRegion.split(128/4, 32);
        kirbyframesStar = new TextureRegion[tempkirbyStar.length * tempkirbyStar[0].length];

        int id = 0;
        for (int i = 0; i < tempkirbyStar.length; i++) {
            for (int j = 0; j < tempkirbyStar[i].length; j++) {
                kirbyframesStar[id] = tempkirbyStar[i][j];
                id++;
            }
        }

        kirbyanimationStar = new Animation(0.08f, kirbyframesStar);

    }

    // Dibujamos la estrella
    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.kirbyStarsprite.setPosition(body.getPosition().x - 6,body.getPosition().y - 4);
        this.kirbyStarsprite.draw(batch);
    }

    // Creamos el cuerpo de la estrella
    @Override
    public void createBody (World world, ActorWithBox2d actor, boolean sentido) {
        BodyDef bodyDef = new BodyDef();
        if (sentido) {
            bodyDef.position.set(actor.getBody().getPosition().x + 6.5f, actor.getBody().getPosition().y + 2);
        }
        else {
            bodyDef.position.set(actor.getBody().getPosition().x - 6.5f, actor.getBody().getPosition().y + 2);
        }
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(3.5f);

        fixture = body.createFixture(shape,0.01f);
        fixture.setUserData(this);
        shape.dispose();
    }

    // Actualizamos la estrella
    @Override
    public void act(float delta) {
        super.act(delta);
        duracion += delta;
        TextureRegion frame = (TextureRegion) kirbyanimationStar.getKeyFrame(duracion, true);
        kirbyStarsprite.setRegion(frame);
        kirbyStarsprite.flip(sentido,false);
        this.accumulatedtimer += delta;

        // Eliminamos la estrella despues de un cierto tiempo
        if (accumulatedtimer > STAR_LIFETIME) {
            if (world != null && body != null) {
                // Eliminacion directa del cuerpo fisico ojito! se ejecuta antes de eliminar el actor para evitar referencias fisicas residuales
                world.destroyBody(body);
                body = null;
            }
            // Se elimina al actor del stage
            remove();
            // Elimina cualquier textura o recurso asociado con la nube
            dispose();
        }
    }

    public void dispose() {
        if (kirbyStarTexture != null) {
            kirbyStarTexture.dispose();
            kirbyStarTexture = null;
        }
    }
}

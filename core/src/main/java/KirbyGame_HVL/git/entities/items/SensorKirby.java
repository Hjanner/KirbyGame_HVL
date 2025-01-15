package KirbyGame_HVL.git.entities.items;

import KirbyGame_HVL.git.entities.player.ActorWithBox2d;
import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class SensorKirby extends ActorWithBox2d {

    // Atributos
    private Kirby kirby;

    // Constructor
    public SensorKirby (World world, Kirby kirby, boolean sentido) {
        this.world = world;
        this.kirby = kirby;
        createBody(this.world, this.kirby, sentido);

    }

    // Setters y Getters

    public World getWorld () {
        return this.world;
    }

    // Creamos el cuerpo del sensor
    private void createBody(World world, Kirby kirby, boolean sentido) {
        BodyDef bodyDef = new BodyDef();
        if (sentido) {
            bodyDef.position.set(kirby.getBody().getPosition().x + 22f, kirby.getBody().getPosition().y + 1);
        }
        else {
            bodyDef.position.set(kirby.getBody().getPosition().x - 22f, kirby.getBody().getPosition().y + 1);
        }
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(20,2);
        fixture = body.createFixture(shape,0.01f);
        fixture.setSensor(true);
        fixture.setUserData(this);
        shape.dispose();
    }



    @Override
    public void dispose() {
        if (body != null) {
            body.getWorld().destroyBody(body);
            body = null;
        }
    }
}

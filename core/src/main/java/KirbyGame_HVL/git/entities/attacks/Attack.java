package KirbyGame_HVL.git.entities.attacks;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.player.ActorWithBox2d;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Attack extends ActorWithBox2d {

    // Atributos
    protected World world;
    protected Body body;
    protected Fixture fixture;
    protected Main main;
    protected boolean sentido;
    protected boolean attackOfkirby = false;
    protected ActorWithBox2d actor;
    protected float duracion;
    protected float accumulatedtimer;

    // Funcion para crear el body del ataque
    public abstract void createBody(World world, ActorWithBox2d actor, boolean sentido);

    // Setters y Getters

    public boolean getSentido() {
        return sentido;
    }

    public boolean getAttackOfKirby(){
        return attackOfkirby;
    }

    public Body getBody(){
        return this.body;
    }


    @Override
    public void dispose() {
        if (body != null) {
            world.destroyBody(body);
        }
    }
}

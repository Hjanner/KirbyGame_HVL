package KirbyGame_HVL.git.entities.attacks;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.player.ActorWithBox2d;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Attack extends ActorWithBox2d {
    protected World world;
    protected Body body;
    protected Fixture fixture;
    protected Main main;
    protected boolean sentido;
    protected boolean attackOfkirby = false;

    public abstract void createBody(World world, ActorWithBox2d actor, boolean sentido);

    public void setSentido(boolean sentido){
        this.sentido = sentido;
    }

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

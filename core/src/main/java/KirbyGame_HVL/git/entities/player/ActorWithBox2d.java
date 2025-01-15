package KirbyGame_HVL.git.entities.player;

import KirbyGame_HVL.git.Main;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class ActorWithBox2d extends Actor {

    // Atributos
    protected World world;
    protected Body body;
    protected Fixture fixture;
    protected Main main;
    protected boolean flipX;

    //  Setters y Getters

    public Body getBody() {
        return body;
    }

    public void setFlipX(boolean flipX) {
        this.flipX = flipX;
    }

    public boolean getFlipX() {
        return flipX;
    }

    // Dispose

    public abstract void dispose();
}

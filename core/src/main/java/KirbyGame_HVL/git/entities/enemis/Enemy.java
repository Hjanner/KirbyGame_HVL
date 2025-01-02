package KirbyGame_HVL.git.entities.enemis;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.States.State;
import KirbyGame_HVL.git.entities.States.StatesWaddleDee.EnumStatesWaddleDee;
import KirbyGame_HVL.git.entities.player.ActorWithBox2d;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Enemy extends ActorWithBox2d {

    protected World world;
    protected Body body;
    protected Fixture fixture;
    protected Main main;
    protected boolean flipX;
    protected Object currentState;
    protected EnumEnemyType type;

    public Enemy() {
        this.flipX = false;
    }

    public abstract void createBody(World world, float x, float y);
    public abstract void updateAnimation(float delta);

    public abstract void setState(EnumStateEnemy typeState);
    public abstract State getcurrentState();

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public boolean getFlipX() {
        return flipX;
    }

    public void setflipX(boolean flipX) {
        this.flipX = flipX;
    }

    public Enemy(EnumEnemyType type) {
        this.type = type;
    }

    public EnumEnemyType getType() {
        return type;
    }

    public void dispose() {
        if (body != null) {
            world.destroyBody(body);
        }
    }
}

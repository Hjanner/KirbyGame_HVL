package KirbyGame_HVL.git.entities.enemis;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.States.State;
import KirbyGame_HVL.git.entities.player.Kirby;
import KirbyGame_HVL.git.entities.player.ActorWithBox2d;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Enemy extends ActorWithBox2d {

    // Atributos
    protected World world;
    protected Body body;
    protected Fixture fixture;
    protected Main main;
    protected boolean flipX;
    protected EnumEnemyType type;
    protected float duration = 0;
    protected Kirby kirby;

    public Enemy() {
        this.flipX = false;
    }

    public abstract void createBody(World world, float x, float y);
    public abstract void updateAnimation(float delta);

    public abstract void setState(EnumStateEnemy typeState);
    public abstract void setAnimation (EnumStateEnemy typestate);

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Enemy(EnumEnemyType type) {
        this.type = type;
    }

    public EnumEnemyType getType() {
        return type;
    }

    public void setKirby (Kirby kirby) {
        this.kirby = kirby;
    }

    public Kirby getKirby () {
        return this.kirby;
    }
    public void setDuration (float duration) {
        this.duration = duration;
    }

    public void dispose() {
        if (body != null) {
            world.destroyBody(body);
        }
    }


}

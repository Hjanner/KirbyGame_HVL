package KirbyGame_HVL.git.entities.enemis;

import KirbyGame_HVL.git.Main;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Enemy extends Actor {

    protected World world;
    protected Body body;
    protected Fixture fixture;
    protected Main main;

    public abstract void createBody(World world, float x, float y);
    public abstract void updateAnimation (float delta);
}

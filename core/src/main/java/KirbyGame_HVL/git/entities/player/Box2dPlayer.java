package KirbyGame_HVL.git.entities.player;

import com.badlogic.gdx.physics.box2d.World;

public interface Box2dPlayer {

    void createBody (World world, float x, float y);
}

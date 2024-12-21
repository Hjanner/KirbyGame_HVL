package KirbyGame_HVL.git.entities.player;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;

public interface Box2dSpace {

    void createBody(World world, OrthogonalTiledMapRenderer map, int layerindex);
}

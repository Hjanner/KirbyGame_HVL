package KirbyGame_HVL.git.entities.items;

import KirbyGame_HVL.git.entities.player.ActorWithBox2d;
import KirbyGame_HVL.git.entities.player.Box2dSpace;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Spikes extends ActorWithBox2d implements Box2dSpace {

    // Constructor
    public Spikes (World world, OrthogonalTiledMapRenderer map, int layerindex) {
        createBody (world, map, layerindex);
    }

    // Creamos el cuerpo de los pinchos
    @Override
    public void createBody(World world, OrthogonalTiledMapRenderer map, int layerindex) {
        BodyDef bodydef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        for (MapObject object : map.getMap().getLayers().get(layerindex).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject)object).getRectangle();
            bodydef.type = BodyDef.BodyType.StaticBody;
            bodydef.position.set((rectangle.x + rectangle.width / 2), (rectangle.y + rectangle.height /2));
            body = world.createBody(bodydef);
            shape.setAsBox(rectangle.width / 2 ,(rectangle.height - 1) / 2);
            fixture = body.createFixture(shape, 1);
            fixture.setUserData("spikes");
        }

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

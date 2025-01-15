package KirbyGame_HVL.git.entities.items;

import KirbyGame_HVL.git.entities.player.ActorWithBox2d;
import KirbyGame_HVL.git.entities.player.Box2dSpace;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

public class Floor extends ActorWithBox2d implements Box2dSpace {

    // Constructor
    public Floor (World world,OrthogonalTiledMapRenderer map, int layerindex) {
        createBody(world, map, layerindex);
    }

    // Creamos el suelo y las paredes
    @Override
    public void createBody (World world, OrthogonalTiledMapRenderer map, int layerindex) {
        BodyDef bodydef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        for (MapObject object : map.getMap().getLayers().get(layerindex).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject)object).getRectangle();
            bodydef.type = BodyDef.BodyType.StaticBody;
            bodydef.position.set((rectangle.x + (rectangle.width - 0.5f) / 2), (rectangle.y + (rectangle.height - 0.5f) /2));
            body = world.createBody(bodydef);
            shape.setAsBox((rectangle.width-0.5f) / 2 ,(rectangle.height-0.5f)  / 2);
            fixture = body.createFixture(shape, 1);
            fixture.setUserData("suelo");
        }

        shape.dispose();
    }

    // Eliminamos cualquier tipo de residuo
    @Override
    public void dispose() {
        if (body != null) {
            body.getWorld().destroyBody(body);
            body = null;
        }

    }
}

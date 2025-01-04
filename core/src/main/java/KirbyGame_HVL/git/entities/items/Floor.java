package KirbyGame_HVL.git.entities.items;

import KirbyGame_HVL.git.entities.player.ActorWithBox2d;
import KirbyGame_HVL.git.entities.player.Box2dSpace;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

public class Floor extends ActorWithBox2d implements Box2dSpace {

    public Floor(World world, OrthogonalTiledMapRenderer map, int layerindex) {
        createBody(world, map, layerindex);
    }

    public void createBody(World world, OrthogonalTiledMapRenderer map, int layerindex) {
        BodyDef bodydef = new BodyDef();
        PolygonShape shape = new PolygonShape();

        for (MapObject object : map.getMap().getLayers().get(layerindex).getObjects().getByType(RectangleMapObject.class)) {
            RectangleMapObject rectObject = (RectangleMapObject) object;
            Rectangle rect = rectObject.getRectangle();

            // Leer la rotación del objeto
            Float rotation = rectObject.getProperties().get("rotation", Float.class);
            if (rotation == null) {
                rotation = 0f; // Si no hay rotación, usar 0
            }

            // **Invertir la rotación**
            rotation = -rotation;

            // Configurar el cuerpo estático
            bodydef.type = BodyDef.BodyType.StaticBody;

            // **Restar 41.08 unidades solo a la coordenada Y si hay rotación**
            if (rotation != 0f) {
                bodydef.position.set(
                    (rect.x + rect.width / 2),// - 65f,
                    (rect.y + rect.height / 2)// + 50f
                );
            } else {
                bodydef.position.set(
                    rect.x + rect.width / 2,
                    rect.y + rect.height / 2
                );
            }

            body = world.createBody(bodydef);

            // Crear la forma como un polígono rotado
            float[] vertices = {
                -rect.width / 2, -rect.height / 2,
                rect.width / 2, -rect.height / 2,
                rect.width / 2,  rect.height / 2,
                -rect.width / 2,  rect.height / 2
            };

            Polygon polygon = new Polygon(vertices);
            polygon.setOrigin(0, 0); // El origen para rotar será (0, 0)
            polygon.setRotation(rotation); // Aplicar la rotación invertida

            // Obtener los vértices transformados
            float[] transformedVertices = polygon.getTransformedVertices();
            float[] box2dVertices = new float[transformedVertices.length / 2 * 2];

            for (int i = 0; i < transformedVertices.length; i += 2) {
                box2dVertices[i] = transformedVertices[i];
                box2dVertices[i + 1] = transformedVertices[i + 1];
            }

            shape.set(box2dVertices);

            // Crear el fixture
            fixture = body.createFixture(shape, 1);
            fixture.setUserData("suelo");
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

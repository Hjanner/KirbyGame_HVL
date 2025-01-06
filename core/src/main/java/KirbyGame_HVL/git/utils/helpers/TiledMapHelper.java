package KirbyGame_HVL.git.utils.helpers;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class TiledMapHelper {

    // Atributos
    private TiledMap tiledmap;

    // Constructores
    public TiledMapHelper() {
    }

    public OrthogonalTiledMapRenderer setupmap() {
        tiledmap = new TmxMapLoader().load("assets/art/tilesets/kirbymapfirstlevel.tmx");

        // Procesar la capa de colisiones
        processCollisionLayer();

        return new OrthogonalTiledMapRenderer(tiledmap);
    }

    private void processCollisionLayer() {
        MapObjects objects = tiledmap.getLayers().get("wall").getObjects();

        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectObject = (RectangleMapObject) object;
                Rectangle rect = rectObject.getRectangle();

                // Leer y depurar rotación
                Float rotation = rectObject.getProperties().get("rotation", Float.class);
                System.out.println("Objeto ID: " + object.getName());
                System.out.println("Rectángulo: " + rect);
                System.out.println("Rotación: " + rotation);

                if (rotation != null) {
                    Polygon polygon = new Polygon(new float[]{
                        rect.x, rect.y,
                        rect.x + rect.width, rect.y,
                        rect.x + rect.width, rect.y + rect.height,
                        rect.x, rect.y + rect.height
                    });

                    polygon.setOrigin(rect.x + rect.width / 2, rect.y + rect.height / 2);
                    polygon.setRotation(rotation);

                    // Aquí puedes usar el polígono para colisiones
                    System.out.println("Polígono con rotación: " + rotation);
                }
            }
        }
    }

}

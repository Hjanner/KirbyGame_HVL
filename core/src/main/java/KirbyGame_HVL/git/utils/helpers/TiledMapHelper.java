package KirbyGame_HVL.git.utils.helpers;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledMapHelper {

    // Atributos
    private TiledMap tiledmap;

    // Constructores
    public TiledMapHelper () {

    }

    public OrthogonalTiledMapRenderer setupmap () {
        tiledmap = new TmxMapLoader().load("assets/art/tilesets/kirbymapfirstlevel.tmx");
        return new OrthogonalTiledMapRenderer(tiledmap);
    }
}

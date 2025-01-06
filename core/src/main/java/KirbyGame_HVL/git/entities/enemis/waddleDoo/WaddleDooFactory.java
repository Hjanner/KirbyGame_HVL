package KirbyGame_HVL.git.entities.enemis.waddleDoo;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.enemis.Enemy;
import KirbyGame_HVL.git.entities.enemis.EnemyFactory;
import com.badlogic.gdx.physics.box2d.World;

public class WaddleDooFactory extends EnemyFactory {

    @Override
    public Enemy createEnemy(World world, Main main, float x, float y) {
        return new WaddleDoo(world, main, x, y);
    }
}

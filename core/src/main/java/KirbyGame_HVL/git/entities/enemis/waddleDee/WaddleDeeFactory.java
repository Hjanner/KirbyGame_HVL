package KirbyGame_HVL.git.entities.enemis.waddleDee;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.enemis.Enemy;
import KirbyGame_HVL.git.entities.enemis.EnemyFactory;
import com.badlogic.gdx.physics.box2d.World;

public class WaddleDeeFactory extends EnemyFactory {
    @Override
    public Enemy createEnemy(World world, Main main, float x, float y) {
        return new WaddleDee(world, main, x, y);
    }
}

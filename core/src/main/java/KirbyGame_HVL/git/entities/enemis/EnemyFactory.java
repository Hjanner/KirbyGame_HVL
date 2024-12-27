package KirbyGame_HVL.git.entities.enemis;

import KirbyGame_HVL.git.Main;
import com.badlogic.gdx.physics.box2d.World;

public abstract class EnemyFactory {

    public abstract Enemy createEnemy(World world, Main main, float x, float y);
}

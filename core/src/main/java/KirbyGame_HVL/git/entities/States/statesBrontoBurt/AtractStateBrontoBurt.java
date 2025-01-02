package KirbyGame_HVL.git.entities.States.statesBrontoBurt;

import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.enemis.brontoBurt.BrontoBurt;
import com.badlogic.gdx.math.Vector2;

public class AtractStateBrontoBurt extends StateBrontoBurt {

    public AtractStateBrontoBurt(BrontoBurt brontoBurt) {
        super(brontoBurt);
    }

    @Override
    public void start() {
        brontoBurt.setAnimation(EnumStateEnemy.DIE);
    }

    @Override
    public void update(float delta) {

        Vector2 direccion = brontoBurt.getKirby().getBody().getPosition().sub(brontoBurt.getBody().getPosition());
        direccion.nor();

        brontoBurt.getBody().applyLinearImpulse(direccion.x * 60, direccion.y * 60, brontoBurt.getBody().getPosition().x,brontoBurt.getBody().getPosition().y, true);

    }

    @Override
    public void end() {

    }
}

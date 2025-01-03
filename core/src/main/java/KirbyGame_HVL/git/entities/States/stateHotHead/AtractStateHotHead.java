package KirbyGame_HVL.git.entities.States.stateHotHead;

import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.enemis.hotHead.HotHead;
import com.badlogic.gdx.math.Vector2;

public class AtractStateHotHead extends StateHotHead {


    public AtractStateHotHead(HotHead hotHead) {
        super(hotHead);
    }

    @Override
    public void start() {

        hotHead.setAnimation(EnumStateEnemy.DIE2);
    }

    @Override
    public void update(float delta) {

        Vector2 direccion = hotHead.getKirby().getBody().getPosition().sub(hotHead.getBody().getPosition());
        direccion.nor();

        hotHead.getBody().applyLinearImpulse(direccion.x * 80, direccion.y * 80, hotHead.getBody().getPosition().x,hotHead.getBody().getPosition().y, true);
    }

    @Override
    public void end() {

    }
}

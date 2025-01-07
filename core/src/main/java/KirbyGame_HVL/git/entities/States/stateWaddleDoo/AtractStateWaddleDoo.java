package KirbyGame_HVL.git.entities.States.stateWaddleDoo;

import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.enemis.waddleDoo.WaddleDoo;
import com.badlogic.gdx.math.Vector2;

public class AtractStateWaddleDoo extends StateWaddleDoo {

    public AtractStateWaddleDoo(WaddleDoo waddleDoo) {
        super(waddleDoo);
    }

    @Override
    public void start() {
        waddleDoo.setAnimation(EnumStateEnemy.DIE);
    }

    @Override
    public void update(float delta) {

        Vector2 direccion = waddleDoo.getKirby().getBody().getPosition().sub(waddleDoo.getBody().getPosition());
        direccion.nor();

        waddleDoo.getBody().applyLinearImpulse(direccion.x * 80, direccion.y * 80, waddleDoo.getBody().getPosition().x,waddleDoo.getBody().getPosition().y, true);
    }

    @Override
    public void end() {

    }
}

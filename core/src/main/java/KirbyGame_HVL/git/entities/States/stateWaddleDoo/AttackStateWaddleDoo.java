package KirbyGame_HVL.git.entities.States.stateWaddleDoo;

import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.enemis.waddleDoo.WaddleDoo;
import com.badlogic.gdx.math.Vector2;

public class AttackStateWaddleDoo extends StateWaddleDoo {

    private float acummulatedtimer;
    private float acummulatedtimer2;
    public AttackStateWaddleDoo(WaddleDoo waddleDoo) {
        super(waddleDoo);
    }

    @Override
    public void start() {

        acummulatedtimer = 0;
        acummulatedtimer2 = 0;
    }

    @Override
    public void update(float delta) {
        Vector2 impulsoOpuesto = waddleDoo.getBody().getLinearVelocity().cpy().scl(-1);
        waddleDoo.getBody().applyLinearImpulse(impulsoOpuesto.x, -0.8f, waddleDoo.getBody().getPosition().x, waddleDoo.getBody().getPosition().y, true);

        acummulatedtimer += delta;

        if (acummulatedtimer > 1f) {
            waddleDoo.shootBeam();
            waddleDoo.setCanShootBeam(false);
            acummulatedtimer2 += delta;
            if (acummulatedtimer2 > 1f) {
                waddleDoo.setState(EnumStateEnemy.WALK);
                waddleDoo.setDuration(0);
                waddleDoo.setAnimation(EnumStateEnemy.WALK);
            }
        }

        waddleDoo.getBody().applyLinearImpulse(0,-40f, waddleDoo.getBody().getPosition().x, waddleDoo.getBody().getPosition().y, true);
    }

    @Override
    public void end() {

    }
}

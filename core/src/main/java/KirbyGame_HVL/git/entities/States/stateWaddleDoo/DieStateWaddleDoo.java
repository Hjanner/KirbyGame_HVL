package KirbyGame_HVL.git.entities.States.stateWaddleDoo;

import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.enemis.waddleDoo.WaddleDoo;

public class DieStateWaddleDoo extends StateWaddleDoo {

    private float acummulatedtimer;

    public DieStateWaddleDoo(WaddleDoo waddleDoo) {
        super(waddleDoo);
    }

    @Override
    public void start() {
        acummulatedtimer = 0;
        if (waddleDoo.getFlipX()){
            waddleDoo.getBody().applyLinearImpulse(-50,50,
                waddleDoo.getBody().getPosition().x,
                waddleDoo.getBody().getPosition().y, true);
        }

        else {
            waddleDoo.getBody().applyLinearImpulse(50,50,
                waddleDoo.getBody().getPosition().x,
                waddleDoo.getBody().getPosition().y, true);
        }
        waddleDoo.setAnimation(EnumStateEnemy.DIE);
    }

    @Override
    public void update(float delta) {

        acummulatedtimer += delta;
        if (acummulatedtimer > 0.8f) {
            waddleDoo.dispose();
            waddleDoo.getWorld().destroyBody(waddleDoo.getBody());
        }

        // Impulso continuo durante la muerte
        if (waddleDoo.getFlipX()) {
            waddleDoo.getBody().applyLinearImpulse(-100, 30,
                waddleDoo.getBody().getPosition().x,
                waddleDoo.getBody().getPosition().y, true);
        } else {
            waddleDoo.getBody().applyLinearImpulse(100, 30,
                waddleDoo.getBody().getPosition().x,
                waddleDoo.getBody().getPosition().y, true);
        }
    }

    @Override
    public void end() {

    }
}

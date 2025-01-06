package KirbyGame_HVL.git.entities.States.StatesKirby;

import KirbyGame_HVL.git.entities.enemis.hotHead.HotHead;
import KirbyGame_HVL.git.entities.enemis.waddleDoo.WaddleDoo;
import KirbyGame_HVL.git.entities.player.Kirby;

public class AttackStateKirby extends StateKirby {

    private float acummulatedtimer;
    public AttackStateKirby(Kirby kirby) {
        super(kirby);
    }

    @Override
    public void start() {
        acummulatedtimer = 0;
    }

    @Override
    public void update(float delta) {

        acummulatedtimer += delta;

        if (acummulatedtimer > 0.2f) {
            if (kirby.getCurrentEnemy() instanceof HotHead) {
                kirby.shootFire();
                kirby.setFireKeyPressed(false);
            }

            else if (kirby.getCurrentEnemy() instanceof WaddleDoo) {
                kirby.shootBeam();
                kirby.setBeamKeyPressed(false);
            }
        }

        if (acummulatedtimer > 0.7f) {
            if (kirby.getColisionSuelo()) {
                kirby.setState(EnumStates.STAY);
                kirby.setDuracion(0);
                kirby.setAnimation(EnumStates.STAY);
            }

            else {
                kirby.setOpuesto(false);
                kirby.setState(EnumStates.FALL);
                kirby.setDuracion(0);
                kirby.setAnimation(EnumStates.FALL2);
            }
        }
    }

    @Override
    public void end() {

    }
}

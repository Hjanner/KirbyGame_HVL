package KirbyGame_HVL.git.entities.States.stateHotHead;

import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.enemis.hotHead.HotHead;

public class AttackStateHotHead extends StateHotHead {

    private float acummulatedtimer;
    public AttackStateHotHead(HotHead hotHead) {
        super(hotHead);
    }

    @Override
    public void start() {

        acummulatedtimer = 0;
    }

    @Override
    public void update(float delta) {

        acummulatedtimer += delta;

        if (acummulatedtimer > 0.8f) {
            hotHead.shootFire();
            hotHead.setCanShootFire(false);
            hotHead.setState(EnumStateEnemy.WALK);
            hotHead.setDuration(0);
            hotHead.setAnimation(EnumStateEnemy.WALK);
        }

        hotHead.getBody().applyLinearImpulse(0,-40f, hotHead.getBody().getPosition().x, hotHead.getBody().getPosition().y, true);
    }


    @Override
    public void end() {

    }
}

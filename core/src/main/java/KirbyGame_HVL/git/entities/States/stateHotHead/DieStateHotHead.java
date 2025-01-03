package KirbyGame_HVL.git.entities.States.stateHotHead;

import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.enemis.hotHead.HotHead;

public class DieStateHotHead extends StateHotHead {

    private float acummulatedtimer;

    public DieStateHotHead(HotHead hotHead) {
        super(hotHead);
    }

    @Override
    public void start() {

        acummulatedtimer = 0;
        if (hotHead.getFlipX()){
            hotHead.getBody().applyLinearImpulse(-50,50,
                hotHead.getBody().getPosition().x,
                hotHead.getBody().getPosition().y, true);
        }

        else {
            hotHead.getBody().applyLinearImpulse(50,50,
                hotHead.getBody().getPosition().x,
                hotHead.getBody().getPosition().y, true);
        }
        hotHead.setAnimation(EnumStateEnemy.DIE);
    }

    @Override
    public void update(float delta) {

        acummulatedtimer += delta;
        if (acummulatedtimer > 0.8f) {
            hotHead.dispose();
            hotHead.getWorld().destroyBody(hotHead.getBody());
        }

        // Impulso continuo durante la muerte
        if (hotHead.getFlipX()) {
            hotHead.getBody().applyLinearImpulse(-100, 30,
                hotHead.getBody().getPosition().x,
                hotHead.getBody().getPosition().y, true);
        } else {
            hotHead.getBody().applyLinearImpulse(100, 30,
                hotHead.getBody().getPosition().x,
                hotHead.getBody().getPosition().y, true);
        }
    }

    @Override
    public void end() {

    }
}

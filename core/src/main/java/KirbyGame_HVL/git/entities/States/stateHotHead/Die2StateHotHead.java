package KirbyGame_HVL.git.entities.States.stateHotHead;

import KirbyGame_HVL.git.entities.enemis.hotHead.HotHead;

public class Die2StateHotHead extends StateHotHead {


    public Die2StateHotHead(HotHead hotHead) {
        super(hotHead);
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float delta) {

        hotHead.dispose();
        hotHead.getWorld().destroyBody(hotHead.getBody());
    }

    @Override
    public void end() {

    }
}

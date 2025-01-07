package KirbyGame_HVL.git.entities.States.stateWaddleDoo;

import KirbyGame_HVL.git.entities.enemis.waddleDoo.WaddleDoo;

public class Die2StateWaddleDoo extends StateWaddleDoo {

    public Die2StateWaddleDoo(WaddleDoo waddleDoo) {
        super(waddleDoo);
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float delta) {

        waddleDoo.dispose();
        waddleDoo.getWorld().destroyBody(waddleDoo.getBody());
    }

    @Override
    public void end() {

    }
}

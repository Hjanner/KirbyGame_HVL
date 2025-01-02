package KirbyGame_HVL.git.entities.States.StatesWaddleDee;

import KirbyGame_HVL.git.entities.enemis.waddleDee.WaddleDee;

public class Die2StateWaddleDee extends StateWaddleDee {

    public Die2StateWaddleDee(WaddleDee waddleDee) {
        super(waddleDee);
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float delta) {

        waddleDee.dispose();
        waddleDee.getWorld().destroyBody(waddleDee.getBody());
    }

    @Override
    public void end() {

    }
}

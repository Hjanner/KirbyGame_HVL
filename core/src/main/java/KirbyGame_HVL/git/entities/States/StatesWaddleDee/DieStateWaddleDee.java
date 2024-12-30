package KirbyGame_HVL.git.entities.States.StatesWaddleDee;

import KirbyGame_HVL.git.entities.enemis.waddleDee.WaddleDee;

public class DieStateWaddleDee extends StateWaddleDee {

    private float acummulatedtimer;

    public DieStateWaddleDee(WaddleDee waddleDee) {
        super(waddleDee);
    }

    @Override
    public void start() {

        acummulatedtimer = 0;
        if (waddleDee.getflipX()){
            waddleDee.getBody().applyLinearImpulse(-50,50,waddleDee.getBody().getPosition().x, waddleDee.getBody().getPosition().y, true);
        }

        else {
            waddleDee.getBody().applyLinearImpulse(50,50,waddleDee.getBody().getPosition().x, waddleDee.getBody().getPosition().y, true);
        }
        waddleDee.setAnimation(EnumStatesWaddleDee.DIE);
    }

    @Override
    public void update(float delta) {
        acummulatedtimer += delta;
        if (acummulatedtimer > 0.8f) {
            waddleDee.dispose();
            waddleDee.getWorld().destroyBody(waddleDee.getBody());
        }

        if (waddleDee.getflipX()){
            waddleDee.getBody().applyLinearImpulse(-120,20,waddleDee.getBody().getPosition().x, waddleDee.getBody().getPosition().y, true);
        }

        else {
            waddleDee.getBody().applyLinearImpulse(120,20,waddleDee.getBody().getPosition().x, waddleDee.getBody().getPosition().y, true);
        }

    }

    @Override
    public void end() {

    }
}

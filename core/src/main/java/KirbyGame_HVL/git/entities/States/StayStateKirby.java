package KirbyGame_HVL.git.entities.States;

import KirbyGame_HVL.git.entities.player.Kirby;

public class StayStateKirby extends StateKirby {

    public StayStateKirby(Kirby kirby) {
        super(kirby);
    }

    @Override
    public void start() {

        kirby.setAnimation(EnumStates.STAY);
    }

    @Override
    public void update(float delta) {


    }

    @Override
    public void end() {


    }

}

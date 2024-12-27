package KirbyGame_HVL.git.entities.States.StatesKirby;

import KirbyGame_HVL.git.entities.States.State;
import KirbyGame_HVL.git.entities.player.Kirby;

public abstract class StateKirby implements State {

    protected Kirby kirby;

    public StateKirby (Kirby kirby) {
        this.kirby = kirby;
    }

}

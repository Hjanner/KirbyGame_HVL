package KirbyGame_HVL.git.entities.States;

import KirbyGame_HVL.git.entities.player.Kirby;

public abstract class StateKirby implements State {

    protected Kirby kirby;

    public StateKirby (Kirby kirby) {
        this.kirby = kirby;
    }
    
}

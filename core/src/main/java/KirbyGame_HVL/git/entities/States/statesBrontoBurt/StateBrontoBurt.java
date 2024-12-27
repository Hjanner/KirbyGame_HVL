package KirbyGame_HVL.git.entities.States.statesBrontoBurt;

import KirbyGame_HVL.git.entities.States.State;
import KirbyGame_HVL.git.entities.enemis.brontoBurt.BrontoBurt;

public abstract class StateBrontoBurt implements State {
    protected BrontoBurt brontoBurt;

    public StateBrontoBurt(BrontoBurt brontoBurt ){
        this.brontoBurt = brontoBurt;
    }
}

package KirbyGame_HVL.git.entities.States.stateHotHead;

import KirbyGame_HVL.git.entities.States.State;
import KirbyGame_HVL.git.entities.enemis.hotHead.HotHead;

public abstract class StateHotHead implements State {
    protected HotHead hotHead;

    public StateHotHead(HotHead hotHead ){
        this.hotHead = hotHead;
    }
}

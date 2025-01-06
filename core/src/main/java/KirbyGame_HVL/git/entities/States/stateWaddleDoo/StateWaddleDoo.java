package KirbyGame_HVL.git.entities.States.stateWaddleDoo;

import KirbyGame_HVL.git.entities.States.State;
import KirbyGame_HVL.git.entities.enemis.waddleDoo.WaddleDoo;

public abstract class StateWaddleDoo implements State {

    protected WaddleDoo waddleDoo;
    public StateWaddleDoo (WaddleDoo waddleDoo) {
        this.waddleDoo = waddleDoo;
    }
}

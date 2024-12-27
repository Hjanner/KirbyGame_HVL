package KirbyGame_HVL.git.entities.States.StatesWaddleDee;

import KirbyGame_HVL.git.entities.States.State;
import KirbyGame_HVL.git.entities.enemis.WaddleDee;

public abstract class StateWaddleDee implements State {

    protected WaddleDee waddleDee;

    public StateWaddleDee(WaddleDee waddleDee) {
        this.waddleDee = waddleDee;
    }

}

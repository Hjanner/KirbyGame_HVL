package KirbyGame_HVL.git.entities.States;

import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class DashStateKirby extends StateKirby {


    public DashStateKirby(Kirby kirby) {

        super(kirby);
    }

    @Override
    public void start() {
        System.out.println("Estado Dash");
    }

    @Override
    public void update(float delta) {


        if (!Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            kirby.setState(EnumStates.STAY);
            kirby.setAnimation(EnumStates.STAY);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && kirby.getBody().getLinearVelocity().x == 0) {
            kirby.setState(EnumStates.DOWN);
            kirby.setAnimation(EnumStates.DOWN);
        }

        else if (kirby.getBody().getLinearVelocity().y < 0) {
            kirby.setState(EnumStates.FALL);
            kirby.setAnimation(EnumStates.FALL2);
        }

    }

    @Override
    public void end() {

    }

}

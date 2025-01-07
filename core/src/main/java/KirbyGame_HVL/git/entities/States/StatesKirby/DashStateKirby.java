package KirbyGame_HVL.git.entities.States.StatesKirby;

import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class DashStateKirby extends StateKirby {

    private float acummulatedtimer;
    public DashStateKirby(Kirby kirby) {

        super(kirby);
    }

    @Override
    public void start() {
        System.out.println("Estado Dash");
        kirby.setOpuesto(false);
        acummulatedtimer = 0;
    }

    @Override
    public void update(float delta) {

        acummulatedtimer += delta;
        if (acummulatedtimer > 0.35f) {
            kirby.setOpuesto(true);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.DOWN) && kirby.getColisionSuelo() && !Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            kirby.setState(EnumStates.STAY);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.STAY);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && kirby.getColisionSuelo() && !Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            kirby.setState(EnumStates.DOWN);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.DOWN);
        }

        else if (!kirby.getColisionSuelo()) {
            kirby.setState(EnumStates.FALL);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.FALL2);
        }



    }

    @Override
    public void end() {

    }

}

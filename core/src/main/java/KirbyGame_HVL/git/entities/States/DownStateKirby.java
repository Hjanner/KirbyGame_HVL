package KirbyGame_HVL.git.entities.States;

import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class DownStateKirby extends StateKirby {

    public DownStateKirby(Kirby kirby) {
        super(kirby);
    }

    @Override
    public void start() {
        System.out.println("Estado abajo");
    }

    @Override
    public void update(float delta) {
        if (!Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            kirby.setState(EnumStates.STAY);
            kirby.setAnimation(EnumStates.STAY);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            kirby.setState(EnumStates.DASH);
            kirby.setAnimation(EnumStates.DASH);
            kirby.getBody().applyLinearImpulse(60,0, kirby.getBody().getPosition().x, kirby.getBody().getPosition().y, true);
            kirby.setFlipx(false);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            kirby.setState(EnumStates.DASH);
            kirby.setAnimation(EnumStates.DASH);
            kirby.getBody().applyLinearImpulse(-120,0, kirby.getBody().getPosition().x, kirby.getBody().getPosition().y, true);
            kirby.setFlipx(true);
        }


    }

    @Override
    public void end() {

    }
}

package KirbyGame_HVL.git.entities.States.StatesKirby;

import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class JumpStateKirby extends StateKirby {

    public JumpStateKirby(Kirby kirby) {
        super(kirby);
    }

    @Override
    public void start() {
    }

    @Override
    public void update(float delta) {

        if (kirby.getBody().getLinearVelocity().y <= 0) {
            kirby.setState(EnumStates.FALL);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.FALL);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            kirby.getBody().applyForce(50,0, kirby.getBody().getPosition().x,kirby.getBody().getPosition().y, true);
            kirby.setFlipx(false);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            kirby.getBody().applyForce(-50,0, kirby.getBody().getPosition().x,kirby.getBody().getPosition().y, true);
            kirby.setFlipx(true);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.C)) {
            kirby.setState(EnumStates.FLY);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.FLY);
        }


    }

    @Override
    public void end() {

    }
}

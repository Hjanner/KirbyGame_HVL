package KirbyGame_HVL.git.entities.States;

import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class StayStateKirby extends StateKirby {

    public StayStateKirby(Kirby kirby) {
        super(kirby);
    }

    @Override
    public void start() {

        kirby.setAnimation(EnumStates.STAY);
        System.out.println("Estado estatico");
    }

    @Override
    public void update(float delta) {

        if (kirby.getBody().getLinearVelocity().y < 0) {
            kirby.setAnimation(EnumStates.FALL2);
            kirby.setState(EnumStates.FALL);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            kirby.setAnimation(EnumStates.WALK);
            kirby.setState(EnumStates.WALK);
            kirby.setOpuesto(true);
        }

        else if (!Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            kirby.setAnimation(EnumStates.WALK);
            kirby.setState(EnumStates.WALK);
            kirby.setOpuesto(true);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            kirby.setState(EnumStates.DOWN);
            kirby.setAnimation(EnumStates.DOWN);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            kirby.setState(EnumStates.JUMP);
            kirby.setAnimation(EnumStates.JUMP);
            kirby.setOpuesto(false);
        }
    }

    @Override
    public void end() {


    }

}

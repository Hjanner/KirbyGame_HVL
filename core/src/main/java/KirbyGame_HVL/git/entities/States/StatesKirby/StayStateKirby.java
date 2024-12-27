package KirbyGame_HVL.git.entities.States.StatesKirby;

import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class StayStateKirby extends StateKirby {

    private float accumulatedtimer;

    public StayStateKirby(Kirby kirby) {
        super(kirby);
    }

    @Override
    public void start() {

        kirby.setOpuesto(false);
        System.out.println("Estado estatico");
        accumulatedtimer = 0;
    }

    @Override
    public void update(float delta) {

        accumulatedtimer += delta;
        if (accumulatedtimer > 0.16f) {
            kirby.setAnimation(EnumStates.STAY);
        }

        if (kirby.getBody().getLinearVelocity().y < -0.5f && !kirby.getColisionSuelo()) {
            kirby.setState(EnumStates.FALL);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.FALL2);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            kirby.setState(EnumStates.WALK);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.WALK);
            kirby.setOpuesto(true);
        }

        else if (!Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            kirby.setState(EnumStates.WALK);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.WALK);
            kirby.setOpuesto(true);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            kirby.setState(EnumStates.DOWN);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.DOWN);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            kirby.setState(EnumStates.JUMP);
            kirby.setAnimation(EnumStates.JUMP);
            kirby.setOpuesto(false);
            kirby.getBody().applyLinearImpulse(0,50, kirby.getBody().getPosition().x,kirby.getBody().getPosition().y, true);
        }
    }

    @Override
    public void end() {


    }

}

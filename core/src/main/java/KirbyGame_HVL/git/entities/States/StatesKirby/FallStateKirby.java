package KirbyGame_HVL.git.entities.States.StatesKirby;

import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class FallStateKirby extends StateKirby {

    private float accumulatedtimer;

    public FallStateKirby(Kirby kirby) {
        super(kirby);
    }

    @Override
    public void start() {

        System.out.println("Estado cayendo");
        accumulatedtimer = 0;
    }

    @Override
    public void update(float delta) {

        kirby.getBody().applyLinearImpulse(0,-1f, kirby.getBody().getPosition().x,kirby.getBody().getPosition().y, true);
        accumulatedtimer += delta;
        if (kirby.getColisionSuelo()) {
            kirby.setState(EnumStates.STAY);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.STAY);
            kirby.setRealizar(false);
            kirby.setOpuesto(true);
        }

        if (accumulatedtimer > 0.16f && kirby.getRealizar()) {
            kirby.setAnimation(EnumStates.FALL2);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.C) && !Gdx.input.isKeyPressed(Input.Keys.Z)) {
            kirby.setState(EnumStates.FLY);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.FLY);
            kirby.getBody().applyLinearImpulse(0,20,kirby.getBody().getPosition().x,kirby.getBody().getPosition().y, true);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            kirby.getBody().applyForce(50,0, kirby.getBody().getPosition().x,kirby.getBody().getPosition().y, true);
            kirby.setFlipx(false);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            kirby.getBody().applyForce(-50,0, kirby.getBody().getPosition().x,kirby.getBody().getPosition().y, true);
            kirby.setFlipx(true);
        }

    }

    @Override
    public void end() {

    }
}

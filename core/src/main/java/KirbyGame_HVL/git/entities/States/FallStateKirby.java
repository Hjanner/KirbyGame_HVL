package KirbyGame_HVL.git.entities.States;

import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class FallStateKirby extends StateKirby {

    public FallStateKirby(Kirby kirby) {
        super(kirby);
    }

    @Override
    public void start() {

        System.out.println("Estado cayendo");
    }

    @Override
    public void update(float delta) {

        kirby.getBody().applyLinearImpulse(0,-1f, kirby.getBody().getPosition().x,kirby.getBody().getPosition().y, true);
        if (kirby.getColisionSuelo()) {
            kirby.setState(EnumStates.STAY);
            kirby.setAnimation(EnumStates.STAY);
            kirby.setOpuesto(true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            kirby.getBody().applyLinearImpulse(2,0, kirby.getBody().getPosition().x,kirby.getBody().getPosition().y, true);
            kirby.setFlipx(false);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            kirby.getBody().applyLinearImpulse(-2,0, kirby.getBody().getPosition().x,kirby.getBody().getPosition().y, true);
            kirby.setFlipx(true);
        }

    }

    @Override
    public void end() {

    }
}

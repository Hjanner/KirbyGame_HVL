package KirbyGame_HVL.git.entities.States;

import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class JumpStateKirby extends StateKirby {

    public JumpStateKirby(Kirby kirby) {
        super(kirby);
    }

    @Override
    public void start() {
        kirby.getBody().applyLinearImpulse(0,50,kirby.getBody().getPosition().x,kirby.getBody().getPosition().y, true);
        System.out.println("Estado Saltando");
    }

    @Override
    public void update(float delta) {

        if (kirby.getBody().getLinearVelocity().y <= 0) {
            kirby.setState(EnumStates.FALL);
            kirby.setAnimation(EnumStates.FALL);
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

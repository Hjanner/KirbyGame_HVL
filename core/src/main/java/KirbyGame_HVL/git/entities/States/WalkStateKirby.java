package KirbyGame_HVL.git.entities.States;

import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class WalkStateKirby extends StateKirby{

    public WalkStateKirby(Kirby kirby) {
        super(kirby);
    }

    @Override
    public void start() {

        kirby.getFixture().getBody().applyLinearImpulse(5,0, kirby.getFixture().getBody().getPosition().x, kirby.getFixture().getBody().getPosition().y, true);
        kirby.getFixture().getBody().setLinearVelocity(80,0);
        kirby.setAnimation(EnumStates.WALK);

    }

    @Override
    public void update(float delta) {

        if (kirby.getFixture().getBody().getLinearVelocity().y < 0) {
            kirby.setState(EnumStates.FALL);
            kirby.setAnimation(EnumStates.FALL);
        }

    }

    @Override
    public void end() {

    }
}

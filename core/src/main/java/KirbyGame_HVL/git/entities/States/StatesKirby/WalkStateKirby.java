package KirbyGame_HVL.git.entities.States.StatesKirby;

import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class WalkStateKirby extends StateKirby {

    public WalkStateKirby(Kirby kirby) {
        super(kirby);
    }

    @Override
    public void start() {
        System.out.println("Estado caminando");
    }

    @Override
    public void update(float delta) {

        if (kirby.getBody().getLinearVelocity().y < 0) {
            kirby.setState(EnumStates.FALL);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.FALL2);
            kirby.setOpuesto(false);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            kirby.getBody().setLinearVelocity(60,0);
            kirby.setFlipx(false);
        }

        else if (!Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            kirby.getBody().setLinearVelocity(-60,0);
            kirby.setFlipx(true);
        }

        if (kirby.getBody().getLinearVelocity().x == 0) {
            kirby.setState(EnumStates.STAY);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.STAY);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            kirby.setState(EnumStates.DOWN);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.DOWN);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            kirby.setState(EnumStates.JUMP);
            kirby.setAnimation(EnumStates.JUMP);
            kirby.setOpuesto(false);
            kirby.getBody().applyLinearImpulse(0,50,kirby.getBody().getPosition().x,kirby.getBody().getPosition().y, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            kirby.setState(EnumStates.RUN);
            kirby.setAnimation(EnumStates.RUN);
        }


    }

    @Override
    public void end() {

    }
}

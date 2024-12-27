package KirbyGame_HVL.git.entities.States.StatesKirby;

import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class RunStateKirby extends StateKirby {


    public RunStateKirby (Kirby kirby) {
        super (kirby);
    }

    @Override
    public void start() {
        System.out.println("Estado Corriendo");
    }

    @Override
    public void update (float delta) {

        if (kirby.getBody().getLinearVelocity().y < 0) {
            kirby.setState(EnumStates.FALL);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.FALL2);
            kirby.setOpuesto(false);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.X)) {
            kirby.getBody().setLinearVelocity(170,0);
            kirby.setFlipx(false);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.X)) {
            kirby.getBody().setLinearVelocity(-170,0);
            kirby.setFlipx(true);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            kirby.setState(EnumStates.WALK);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.WALK);
            kirby.setFlipx(false);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            kirby.setState(EnumStates.WALK);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.WALK);
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
            kirby.getBody().applyLinearImpulse(0,80,kirby.getBody().getPosition().x,kirby.getBody().getPosition().y, true);
        }
    }

    @Override
    public void end () {

    }
}

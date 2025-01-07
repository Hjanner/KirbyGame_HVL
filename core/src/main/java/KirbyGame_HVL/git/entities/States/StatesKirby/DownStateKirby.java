package KirbyGame_HVL.git.entities.States.StatesKirby;

import KirbyGame_HVL.git.entities.enemis.hotHead.HotHead;
import KirbyGame_HVL.git.entities.enemis.waddleDoo.WaddleDoo;
import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class DownStateKirby extends StateKirby {

    private float acummulatedtimer = 0;
    public DownStateKirby(Kirby kirby) {
        super(kirby);
    }

    @Override
    public void start() {
        System.out.println("Estado abajo");
        kirby.setOpuesto(true);
        acummulatedtimer = 0;
    }

    @Override
    public void update(float delta) {

        acummulatedtimer += delta;
        if (kirby.getCurrentEnemy() != null && acummulatedtimer > 0.2f && !kirby.getPoder()) {
            if (kirby.getCurrentEnemy() instanceof HotHead) {
                kirby.setPoder(true);
                kirby.setState(EnumStates.STAY);
                kirby.setDuracion(0);
                kirby.setAnimation(EnumStates.STAY);
            }

            else if (kirby.getCurrentEnemy() instanceof WaddleDoo) {
                kirby.setPoder(true);
                kirby.setState(EnumStates.STAY);
                kirby.setDuracion(0);
                kirby.setAnimation(EnumStates.STAY);
            }

            else {
                kirby.setPoder(false);
                kirby.setcurrentEnemy(null);
                kirby.setState(EnumStates.STAY);
                kirby.setDuracion(0);
                kirby.setAnimation(EnumStates.STAY);
            }
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            kirby.setState(EnumStates.STAY);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.STAY);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            kirby.setState(EnumStates.DASH);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.DASH);
            kirby.getBody().applyLinearImpulse(35,0, kirby.getBody().getPosition().x, kirby.getBody().getPosition().y, true);
            kirby.setFlipx(false);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            kirby.setState(EnumStates.DASH);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.DASH);
            kirby.getBody().applyLinearImpulse(-35,0, kirby.getBody().getPosition().x, kirby.getBody().getPosition().y, true);
            kirby.setFlipx(true);
        }

        else if (!kirby.getColisionSuelo()) {
            kirby.setState(EnumStates.FALL);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.FALL2);
        }




    }

    @Override
    public void end() {

    }
}

package KirbyGame_HVL.git.entities.States.StatesKirby;

import KirbyGame_HVL.git.entities.attacks.CloudKirby;
import KirbyGame_HVL.git.entities.attacks.Star;
import KirbyGame_HVL.git.entities.enemis.hotHead.HotHead;
import KirbyGame_HVL.git.entities.enemis.waddleDoo.WaddleDoo;
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
        accumulatedtimer = 0;
        kirby.setFireKeyPressed(true);
        kirby.setBeamKeyPressed(true);
        kirby.setDamageFire(false);

    }

    @Override
    public void update(float delta) {

        if (kirby.getSensorkirby() != null) {
            kirby.getWorld().destroyBody(kirby.getSensorkirby().getBody());
            kirby.setSensorkirby(null);
        }
        accumulatedtimer += delta;
        if (accumulatedtimer > 0.25f) {
            kirby.setAnimation(EnumStates.STAY);
        }

        if (!kirby.getColisionSuelo()) {
            kirby.setOpuesto(false);
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
        if (accumulatedtimer > 0.16f) {
            if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
                if (kirby.getCurrentEnemy() == null) {
                    kirby.setState(EnumStates.ABSORB);
                    kirby.setDuracion(0);
                    kirby.setAnimation(EnumStates.ABSORB);
                }

                else {
                    if ((kirby.getCurrentEnemy() instanceof HotHead || kirby.getCurrentEnemy() instanceof WaddleDoo) && kirby.getPoder()) {
                        kirby.setOpuesto(true);
                        kirby.setState(EnumStates.ATTACK);
                        kirby.setDuracion(0);
                        kirby.setAnimation(EnumStates.ATTACK);
                    }
                }

            }
        }


        if (accumulatedtimer > 0.5f) {

            if (Gdx.input.isKeyPressed(Input.Keys.Z) && kirby.getCurrentEnemy() != null) {
                if (!kirby.getPoder()) {
                    kirby.setDuracion(0);
                    kirby.setAnimation(EnumStates.ATTACK);
                    if (kirby.getFlipX()) {
                        Star star = new Star(kirby.getWorld(), kirby, false);
                        star.getBody().applyLinearImpulse(-35, 0, star.getBody().getPosition().x, star.getBody().getPosition().y, true);
                        kirby.setStar(star);
                        kirby.setcurrentEnemy(null);
                    } else {
                        Star star = new Star(kirby.getWorld(), kirby, true);
                        star.getBody().applyLinearImpulse(35, 0, star.getBody().getPosition().x, star.getBody().getPosition().y, true);
                        kirby.setStar(star);
                        kirby.setcurrentEnemy(null);
                    }
                    kirby.setState(EnumStates.STAY);
                }
            }
        }
    }

    @Override
    public void end() {


    }

}

package KirbyGame_HVL.git.entities.States.StatesKirby;

import KirbyGame_HVL.git.entities.attacks.Star;
import KirbyGame_HVL.git.entities.enemis.hotHead.HotHead;
import KirbyGame_HVL.git.entities.enemis.waddleDoo.WaddleDoo;
import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;

public class FallStateKirby extends StateKirby {

    private float accumulatedtimer;
    private float accumulatedtimer2;
    private Sound soundStar;

    public FallStateKirby(Kirby kirby) {
        super(kirby);
    }

    @Override
    public void start() {

        accumulatedtimer = 0;
        accumulatedtimer2 = 0;
        kirby.setFireKeyPressed(true);
        kirby.setBeamKeyPressed(true);
        kirby.setDamageFire(false);
        soundStar = Gdx.audio.newSound(Gdx.files.internal("assets/audio/music/00f9 - SE_STARSHOT2.wav"));

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
            if (kirby.getCurrentEnemy() == null || kirby.getPoder()) {
                kirby.setState(EnumStates.FLY);
                kirby.setDuracion(0);
                kirby.setAnimation(EnumStates.FLY);
                kirby.getBody().applyLinearImpulse(0, 20, kirby.getBody().getPosition().x, kirby.getBody().getPosition().y, true);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Z) && kirby.getCurrentEnemy() != null) {
            accumulatedtimer2 += delta;
            if (!kirby.getPoder()) {
                kirby.setDuracion(0);
                kirby.setAnimation(EnumStates.ATTACK);
                if (kirby.getFlipX()) {
                    soundStar.play();
                    Star star = new Star(kirby.getWorld(), kirby, false);
                    star.getBody().applyLinearImpulse(-35, 0, star.getBody().getPosition().x, star.getBody().getPosition().y, true);
                    kirby.setStar(star);
                    kirby.setcurrentEnemy(null);
                } else {
                    soundStar.play();
                    Star star = new Star(kirby.getWorld(), kirby, true);
                    star.getBody().applyLinearImpulse(35, 0, star.getBody().getPosition().x, star.getBody().getPosition().y, true);
                    kirby.setStar(star);
                    kirby.setcurrentEnemy(null);
                }
                kirby.setRealizar(true);
                kirby.setState(EnumStates.FALL);
            } else {
                if ((kirby.getCurrentEnemy() instanceof HotHead || kirby.getCurrentEnemy() instanceof WaddleDoo) && kirby.getPoder()) {
                    if (accumulatedtimer2 > 0.3f) {
                        kirby.setOpuesto(false);
                        kirby.setState(EnumStates.ATTACK);
                        kirby.setDuracion(0);
                        kirby.setAnimation(EnumStates.ATTACK);
                    }
                }

            }
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

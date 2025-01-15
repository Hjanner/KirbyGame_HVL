package KirbyGame_HVL.git.entities.States.StatesKirby;

import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class DamageStateKirby extends StateKirby {

    private float acummulatedtimer;
    private float acummulatedtimer2;
    private Sound soundDamage;
    public DamageStateKirby(Kirby kirby) {
        super(kirby);
    }

    @Override
    public void start() {

        acummulatedtimer = 0;
        acummulatedtimer2 = 0;
        kirby.setDuracion(0);
        kirby.setOpuesto(false);
        kirby.setPoder(false);
        soundDamage = Gdx.audio.newSound(Gdx.files.internal("assets/audio/music/Voicy_Kirby Hurt Sound.mp3"));
        soundDamage.play();
    }

    @Override
    public void update(float delta) {
        acummulatedtimer += delta;

        if (kirby.getFlipX()) {
            if (acummulatedtimer < 0.6f) {
                if (kirby.getCurrentEnemy() == null) {
                    kirby.getBody().applyLinearImpulse(10, 5, kirby.getBody().getPosition().x, kirby.getBody().getPosition().y, true);
                }
                else {
                    if (!kirby.getPoder()) {
                        kirby.getBody().applyLinearImpulse(15, 0, kirby.getBody().getPosition().x, kirby.getBody().getPosition().y, true);
                    }

                    else {
                        kirby.getBody().applyLinearImpulse(10, 5, kirby.getBody().getPosition().x, kirby.getBody().getPosition().y, true);
                    }
                }
            }
        }

        else {
            if (acummulatedtimer < 0.6f) {
                if (kirby.getCurrentEnemy() == null) {
                    kirby.getBody().applyLinearImpulse(-10, 5, kirby.getBody().getPosition().x, kirby.getBody().getPosition().y, true);
                }
                else {
                    if (!kirby.getPoder()) {
                        kirby.getBody().applyLinearImpulse(-15, 0, kirby.getBody().getPosition().x, kirby.getBody().getPosition().y, true);
                    }

                    else {
                        kirby.getBody().applyLinearImpulse(-10, 5, kirby.getBody().getPosition().x, kirby.getBody().getPosition().y, true);
                    }

                }
            }
        }

        if (acummulatedtimer > 0.6f) {

            acummulatedtimer2 += delta;
            kirby.setOpuesto(true);
            if (kirby.getSensorkirby() != null) {
                kirby.getWorld().destroyBody(kirby.getSensorkirby().getBody());
                kirby.setSensorkirby(null);
            }

            if (acummulatedtimer2 > 1f) {
                if (!kirby.getColisionSuelo()) {
                    kirby.setState(EnumStates.FALL);
                    kirby.setDuracion(0);
                    kirby.setAnimation(EnumStates.FALL2);
                    kirby.setOpuesto(false);
                }

                else {
                    kirby.setState(EnumStates.STAY);
                    kirby.setDuracion(0);
                    kirby.setAnimation(EnumStates.STAY);
                    kirby.setOpuesto(true);
                }
            }
        }

    }

    @Override
    public void end() {

    }
}

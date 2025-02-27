package KirbyGame_HVL.git.entities.States.StatesKirby;

import KirbyGame_HVL.git.entities.items.SensorKirby;
import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;

public class AbsorbStateKirby extends StateKirby {

    private float acummulatedtimer;
    private Sound soundAbsorb;
    public AbsorbStateKirby(Kirby kirby) {
        super(kirby);
    }

    @Override
    public void start() {

        kirby.setOpuesto(true);
        acummulatedtimer = 0;
        soundAbsorb = Gdx.audio.newSound(Gdx.files.internal("assets/audio/music/suction.mp3"));
        soundAbsorb.loop();
        soundAbsorb.play();
    }

    @Override
    public void update(float delta) {

        if (!Gdx.input.isKeyPressed(Input.Keys.Z)) {
            acummulatedtimer += delta;
            if (acummulatedtimer > 0.8f) {
                if (kirby.getColisionSuelo()) {
                    kirby.setState(EnumStates.STAY);
                    kirby.setDuracion(0);
                    kirby.setAnimation(EnumStates.STAY);
                }

                else {
                    kirby.setOpuesto(false);
                    kirby.setState(EnumStates.FALL);
                    kirby.setDuracion(0);
                    kirby.setAnimation(EnumStates.FALL2);
                }
            }
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.Z) && kirby.getCurrentEnemy() == null) {
            if (kirby.getFlipX() && kirby.getSensorkirby() == null) {
                SensorKirby sensorkirby = new SensorKirby(kirby.getWorld(), kirby, false);
                kirby.setSensorkirby(sensorkirby);
            }

            else if (kirby.getSensorkirby() == null){
                SensorKirby sensorkirby = new SensorKirby(kirby.getWorld(), kirby, true);
                kirby.setSensorkirby(sensorkirby);
            }
        }


    }

    @Override
    public void end() {

        soundAbsorb.stop();
    }
}

package KirbyGame_HVL.git.entities.States.StatesKirby;

import KirbyGame_HVL.git.entities.attacks.CloudKirby;
import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;

public class FlyStateKirby extends StateKirby {

    private float accumulatedtimer, accumulatedtimer2;
    private Sound soundFly;
    public FlyStateKirby(Kirby kirby) {
        super(kirby);
    }

    @Override
    public void start() {
        kirby.setOpuesto(false);
        accumulatedtimer = 0;
        accumulatedtimer2 = 0;
        soundFly = Gdx.audio.newSound(Gdx.files.internal("assets/audio/music/00fc - SE_AIRSHOT.wav"));
    }

    @Override
    public void update(float delta) {

        accumulatedtimer += delta;
        if (Gdx.input.isKeyPressed(Input.Keys.C) && accumulatedtimer > 0.16f) {
            kirby.getBody().applyForce(0,50f, kirby.getBody().getPosition().x, kirby.getBody().getPosition().y, true);
            kirby.setAnimation(EnumStates.FLY2);
            soundFly.play();
            accumulatedtimer2 += delta;
            if (accumulatedtimer2 > 0.04f) {
                soundFly.stop();
            }
        }

        else if (kirby.getBody().getLinearVelocity().y <= 0) {
            kirby.setAnimation(EnumStates.FLY3);
        }

        else if (!Gdx.input.isKeyPressed(Input.Keys.C)){
            accumulatedtimer2 = 0;
        }



        if (Gdx.input.isKeyPressed(Input.Keys.Z) && kirby.getColisionSuelo() && !Gdx.input.isKeyPressed(Input.Keys.C)) {
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.FLY4);
            if (kirby.getFlipX()) {
                CloudKirby cloudkirby = new CloudKirby(kirby.getWorld(), kirby, false);
                cloudkirby.getBody().applyLinearImpulse(-35, 0, cloudkirby.getBody().getPosition().x, cloudkirby.getBody().getPosition().y, true);
                kirby.setCloud(cloudkirby);
            }
            else {
                CloudKirby cloudkirby = new CloudKirby(kirby.getWorld(), kirby, true);
                cloudkirby.getBody().applyLinearImpulse(35, 0, cloudkirby.getBody().getPosition().x, cloudkirby.getBody().getPosition().y, true);
                kirby.setCloud(cloudkirby);
            }
            kirby.setState(EnumStates.STAY);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.Z) && !kirby.getColisionSuelo() && !Gdx.input.isKeyPressed(Input.Keys.C)) {
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.FLY4);
            if (kirby.getFlipX()) {
                CloudKirby cloudkirby = new CloudKirby(kirby.getWorld(), kirby, false);
                cloudkirby.getBody().applyLinearImpulse(-35, 0, cloudkirby.getBody().getPosition().x, cloudkirby.getBody().getPosition().y, true);
                kirby.setCloud(cloudkirby);
            }
            else {
                CloudKirby cloudkirby = new CloudKirby(kirby.getWorld(), kirby, true);
                cloudkirby.getBody().applyLinearImpulse(35, 0, cloudkirby.getBody().getPosition().x, cloudkirby.getBody().getPosition().y, true);
                kirby.setCloud(cloudkirby);
            }
            kirby.setState(EnumStates.FALL);
            kirby.setRealizar(true);
            accumulatedtimer = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            kirby.getBody().applyForce(20,0,kirby.getBody().getPosition().x, kirby.getBody().getPosition().y, true);
            kirby.setFlipx(false);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            kirby.getBody().applyForce(-20,0,kirby.getBody().getPosition().x, kirby.getBody().getPosition().y, true);
            kirby.setFlipx(true);
        }

    }

    @Override
    public void end() {

        soundFly.stop();
    }
}

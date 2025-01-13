package KirbyGame_HVL.git.entities.States.StatesKirby;

import KirbyGame_HVL.git.entities.attacks.Star;
import KirbyGame_HVL.git.entities.enemis.hotHead.HotHead;
import KirbyGame_HVL.git.entities.enemis.waddleDoo.WaddleDoo;
import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;

public class JumpStateKirby extends StateKirby {

    private float acummulatedtimer;
    private Sound soundJump, soundStar;
    public JumpStateKirby(Kirby kirby) {
        super(kirby);
    }

    @Override
    public void start() {

        acummulatedtimer = 0;
        kirby.setFireKeyPressed(true);
        kirby.setBeamKeyPressed(true);
        soundJump = Gdx.audio.newSound(Gdx.files.internal("assets/audio/music/jump-15984.mp3"));
        soundStar = Gdx.audio.newSound(Gdx.files.internal("assets/audio/music/00f9 - SE_STARSHOT2.wav"));
        soundJump.play();
    }

    @Override
    public void update(float delta) {

        acummulatedtimer += delta;

        if (acummulatedtimer > 0.25f) {
            kirby.setAnimation(EnumStates.JUMP);
        }
        if (kirby.getBody().getLinearVelocity().y <= 0) {
            kirby.setState(EnumStates.FALL);
            kirby.setDuracion(0);
            kirby.setAnimation(EnumStates.FALL);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            kirby.getBody().applyForce(50,0, kirby.getBody().getPosition().x,kirby.getBody().getPosition().y, true);
            kirby.setFlipx(false);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            kirby.getBody().applyForce(-50,0, kirby.getBody().getPosition().x,kirby.getBody().getPosition().y, true);
            kirby.setFlipx(true);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.C)) {
            if (kirby.getCurrentEnemy() == null || kirby.getPoder()) {
                kirby.setState(EnumStates.FLY);
                kirby.setDuracion(0);
                kirby.setAnimation(EnumStates.FLY);
            }
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.Z) && kirby.getCurrentEnemy() != null) {
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
                kirby.setState(EnumStates.JUMP);
            } else {
                if ((kirby.getCurrentEnemy() instanceof HotHead || kirby.getCurrentEnemy() instanceof WaddleDoo) && kirby.getPoder()) {
                    kirby.setOpuesto(false);
                    kirby.setState(EnumStates.ATTACK);
                    kirby.setDuracion(0);
                    kirby.setAnimation(EnumStates.ATTACK);
                }
            }
        }


    }

    @Override
    public void end() {

    }
}

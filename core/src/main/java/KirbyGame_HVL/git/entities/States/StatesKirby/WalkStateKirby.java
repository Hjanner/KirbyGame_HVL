package KirbyGame_HVL.git.entities.States.StatesKirby;

import KirbyGame_HVL.git.entities.attacks.Star;
import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class WalkStateKirby extends StateKirby {

    private float acummulatedtimer, acummulatedtimer2;
    public WalkStateKirby(Kirby kirby) {
        super(kirby);
    }

    @Override
    public void start() {

        acummulatedtimer = 0;
        acummulatedtimer2 = 0;
    }

    @Override
    public void update(float delta) {

        acummulatedtimer += delta;

        if (acummulatedtimer > 0.25f) {
            kirby.setAnimation(EnumStates.WALK);
        }
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

        if (kirby.getcurrentAnimation() != kirby.getAbsorbWalkAnimation()) {
            acummulatedtimer2 += delta;
            if (acummulatedtimer2 > 0.2f) {
                if (Gdx.input.isKeyPressed(Input.Keys.Z) && kirby.getCurrentEnemy() == null) {
                    kirby.setState(EnumStates.ABSORB);
                    kirby.setDuracion(0);
                    kirby.setAnimation(EnumStates.ABSORB);

                }
            }
        }

        if (acummulatedtimer > 0.3f) {

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
                    kirby.setState(EnumStates.WALK);
                } else {

                }
            }
        }


    }

    @Override
    public void end() {

    }
}

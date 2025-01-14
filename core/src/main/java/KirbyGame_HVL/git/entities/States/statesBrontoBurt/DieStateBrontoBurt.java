package KirbyGame_HVL.git.entities.States.statesBrontoBurt;

import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.enemis.brontoBurt.BrontoBurt;

public class DieStateBrontoBurt extends StateBrontoBurt {
    private float acummulatedtimer;

    public DieStateBrontoBurt(BrontoBurt brontoBurt) {
        super(brontoBurt);
    }

    @Override
    public void start() {
        acummulatedtimer = 0;

        // Impulso mas vertical que el Waddle Dee ya que es un enemigo volador
        if (brontoBurt.getFlipX()) {
            brontoBurt.getBody().applyLinearImpulse(-150, 50,
                brontoBurt.getBody().getPosition().x,
                brontoBurt.getBody().getPosition().y, true);
        } else {
            brontoBurt.getBody().applyLinearImpulse(150, 50,
                brontoBurt.getBody().getPosition().x,
                brontoBurt.getBody().getPosition().y, true);
        }
        brontoBurt.setAnimation(EnumStateEnemy.DIE);
    }

    @Override
    public void update(float delta) {
        acummulatedtimer += delta;
        if (acummulatedtimer > 0.8f) {
            brontoBurt.dispose();
            brontoBurt.getWorld().destroyBody(brontoBurt.getBody());
        }

        // Impulso continuo durante la muerte
        if (brontoBurt.getFlipX()) {
            brontoBurt.getBody().applyLinearImpulse(-150, 30,
                brontoBurt.getBody().getPosition().x,
                brontoBurt.getBody().getPosition().y, true);
        } else {
            brontoBurt.getBody().applyLinearImpulse(150, 30,
                brontoBurt.getBody().getPosition().x,
                brontoBurt.getBody().getPosition().y, true);
        }
    }

    @Override
    public void end() {
    }
}

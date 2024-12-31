package KirbyGame_HVL.git.entities.States.statesBrontoBurt;

import KirbyGame_HVL.git.entities.enemis.brontoBurt.BrontoBurt;

public class Die2StateBrontoBurt extends StateBrontoBurt {

    public Die2StateBrontoBurt(BrontoBurt brontoBurt) {
        super(brontoBurt);
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float delta) {

        brontoBurt.dispose();
        brontoBurt.getWorld().destroyBody(brontoBurt.getBody());
    }

    @Override
    public void end() {

    }
}

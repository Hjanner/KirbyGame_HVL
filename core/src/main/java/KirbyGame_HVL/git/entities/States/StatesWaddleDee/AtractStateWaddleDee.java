package KirbyGame_HVL.git.entities.States.StatesWaddleDee;

import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.enemis.waddleDee.WaddleDee;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class AtractStateWaddleDee extends StateWaddleDee {

    public AtractStateWaddleDee(WaddleDee waddleDee) {
        super(waddleDee);
    }

    @Override
    public void start() {

        waddleDee.setAnimation(EnumStateEnemy.DIE);

    }

    @Override
    public void update(float delta) {

        Vector2 direccion = waddleDee.getKirby().getBody().getPosition().sub(waddleDee.getBody().getPosition());
        direccion.nor();

        waddleDee.getBody().applyLinearImpulse(direccion.x * 60, direccion.y * 60, waddleDee.getBody().getPosition().x,waddleDee.getBody().getPosition().y, true);
    }

    @Override
    public void end() {

    }
}

package KirbyGame_HVL.git.entities.States.StatesWaddleDee;

import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.enemis.waddleDee.WaddleDee;

public class WalkStateWaddleDee extends StateWaddleDee {

    private float movementSpeed = 30f;                  // Pixels per second
    private float movementTime = 0;
    private float cambioDireccionIntervalo = 2f;

    public WalkStateWaddleDee(WaddleDee waddleDee) {
        super(waddleDee);
    }

    @Override
    public void start() {
        waddleDee.setAnimation(EnumStateEnemy.WALK);
    }

    @Override
    public void update(float delta) {

        movementTime += delta;

        // cambiar de direccion
        if (movementTime > cambioDireccionIntervalo) {
            movementTime = 0;
            waddleDee.setFlipX(true);
            movementSpeed = -movementSpeed;
        }

        if (movementSpeed > 0) {
            waddleDee.setFlipX(false);
        }

        waddleDee.getBody().setLinearVelocity(movementSpeed, waddleDee.getBody().getLinearVelocity().y);                                              // aplica movimiento
        waddleDee.getBody().applyLinearImpulse(0, -20f, waddleDee.getBody().getPosition().x, waddleDee.getBody().getPosition().y, true);       // gravedad

    }

    @Override
    public void end() {

    }
}

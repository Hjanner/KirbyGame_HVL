package KirbyGame_HVL.git.entities.States.stateWaddleDoo;

import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.enemis.waddleDoo.WaddleDoo;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class WalkStateWaddleDoo extends StateWaddleDoo {

    private float movementSpeed;
    private float movementTime;
    private float cambioDireccion;
    private float detectionRadius;                                       // rango de detecccion del Kirby
    private boolean isAggressive;
    private float agresiveSpeed;
    private float beamTimer;
    private static final float BEAM_COOLDOWN = 2.0f;

    public WalkStateWaddleDoo(WaddleDoo waddleDoo) {
        super(waddleDoo);
    }

    @Override
    public void start() {
        movementSpeed = 25f;
        movementTime = 0;
        cambioDireccion = MathUtils.random(1.5f, 3.0f);
        detectionRadius = 50f;
        isAggressive = false;
        agresiveSpeed = 40f;
        beamTimer = 0;
    }

    @Override
    public void update(float delta) {

        movementTime += delta;

        if (movementTime > cambioDireccion) {                   //cambio de direccion
            movementTime = 0;
            waddleDoo.setFlipX(true);
            movementSpeed = -movementSpeed;
            cambioDireccion = MathUtils.random(1.5f, 5.0f);
        }

        if (movementSpeed > 0) {
            waddleDoo.setFlipX(false);
        }

        //manejo de deteccion de kirby cerca
        if (waddleDoo.getKirby().getBody() != null) {
            Vector2 kirbyPos = waddleDoo.getKirby().getBody().getPosition();
            Vector2 hotHeadPos = waddleDoo.getBody().getPosition();
            float distance = kirbyPos.dst(hotHeadPos);

            isAggressive = distance <= detectionRadius;

            if (isAggressive) {
                // se mueve hacia el kirby
                float direction = kirbyPos.x > hotHeadPos.x ? 1 : -1;
                movementSpeed = agresiveSpeed * direction;
                waddleDoo.setFlipX(direction < 0);
            }
        }

        if (!waddleDoo.getCanShootBeam()) {
            beamTimer += delta;
            if (beamTimer >= BEAM_COOLDOWN) {
                waddleDoo.setCanShootBeam(true);
                beamTimer = 0f;
            }
        }

        if (isAggressive && waddleDoo.getCanShootBeam()) {
            waddleDoo.setState(EnumStateEnemy.ATTACK);
            waddleDoo.setDuration(0);
            waddleDoo.setAnimation(EnumStateEnemy.ATTACK);
        }

        waddleDoo.getBody().setLinearVelocity(movementSpeed, 0);
        waddleDoo.getBody().applyLinearImpulse(0,-40, waddleDoo.getBody().getPosition().x, waddleDoo.getBody().getPosition().y, true);
    }

    @Override
    public void end() {

    }
}

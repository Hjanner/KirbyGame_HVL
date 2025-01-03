package KirbyGame_HVL.git.entities.States.stateHotHead;

import KirbyGame_HVL.git.entities.enemis.hotHead.HotHead;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class WalkStateHotHead extends StateHotHead {

    private float movementSpeed;
    private float movementTime;
    private float cambioDireccion;
    private float detectionRadius;                                       // rango de detecccion del Kirby
    private boolean isAggressive;
    private float agresiveSpeed;
    private float accumulatedtimer;
    private boolean flipX;

    public WalkStateHotHead(HotHead hotHead) {
        super(hotHead);
    }

    @Override
    public void start() {

        movementSpeed = 25f;
        movementTime = 0;
        cambioDireccion = MathUtils.random(1.5f, 3.0f);
        detectionRadius = 50f;
        isAggressive = false;
        agresiveSpeed = 40f;
        accumulatedtimer = 0;
        flipX = false;
    }

    @Override
    public void update(float delta) {

        movementTime += delta;
        accumulatedtimer += delta;

        if (movementTime > cambioDireccion) {                   //cambio de direccion
            movementTime = 0;
            hotHead.setFlipX(true);
            movementSpeed = -movementSpeed;
            cambioDireccion = MathUtils.random(1.5f, 5.0f);
        }

        if (movementSpeed > 0) {
            hotHead.setFlipX(false);
        }

        //manejo de deteccion de kirby cerca
        if (hotHead.getKirby().getBody() != null) {
            Vector2 kirbyPos = hotHead.getKirby().getBody().getPosition();
            Vector2 hotHeadPos = hotHead.getBody().getPosition();
            float distance = kirbyPos.dst(hotHeadPos);

            isAggressive = distance <= detectionRadius;

            if (isAggressive) {
                // se mueve hacia el kirby
                float direction = kirbyPos.x > hotHeadPos.x ? 1 : -1;
                movementSpeed = agresiveSpeed * direction;
                hotHead.setFlipX(direction < 0);
            }
        }

        hotHead.getBody().setLinearVelocity(movementSpeed, hotHead.getBody().getLinearVelocity().y);

    }

    @Override
    public void end() {

    }
}

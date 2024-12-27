package KirbyGame_HVL.git.entities.States.statesBrontoBurt;

import KirbyGame_HVL.git.entities.enemis.brontoBurt.BrontoBurt;

public class FlyStateBrontoBurt extends StateBrontoBurt {
    private float movementSpeed = 40f;
    private float movementTime = 0;
    private float cambioDireccionIntervalo = 3f;
    private float amplitude = 40f;
    private float frequency = 2f;
    private float time = 0;

    public FlyStateBrontoBurt(BrontoBurt brontoBurt) {
        super(brontoBurt);
    }

    @Override
    public void start() {
        brontoBurt.setAnimation(EnumStatesBrontoBurt.FLY);
    }

    @Override
    public void update(float delta) {
        movementTime += delta;
        time += delta;

        // Cambiar direccion rango
        if (movementTime > cambioDireccionIntervalo) {
            movementTime = 0;
            brontoBurt.setflipX(true);
            movementSpeed = -movementSpeed;
        }

        if (movementSpeed > 0) {
            brontoBurt.setflipX(false);
        }

        // mov horizontal
        brontoBurt.getBody().setLinearVelocity(movementSpeed, 0);

        // movimiento ondulado vertical
        float yOffset = amplitude * (float)Math.sin(time * frequency);
        float currentY = brontoBurt.getStartY() + yOffset;
        float currentX = brontoBurt.getBody().getPosition().x;
        brontoBurt.getBody().setTransform(currentX, currentY, 0);
    }

    @Override
    public void end() {
    }
}

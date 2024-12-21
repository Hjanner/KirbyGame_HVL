package KirbyGame_HVL.git.entities.net;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.States.EnumStates;
import KirbyGame_HVL.git.entities.player.Kirby;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.World;

public class RemoteKirby extends Kirby {
    private String remotePlayerId;

    public RemoteKirby(World world, Main main, String playerId) {
        super(world, main);
        this.remotePlayerId = playerId;
    }

    public void updateFromNetwork(float posX, float posY, String animation,
                                  String color, boolean flipX) {
        getBody().setTransform(posX, posY, 0);
        setAnimation(EnumStates.valueOf(animation));
        setFlipX(flipX);
        // Update color based on received string
    }
}

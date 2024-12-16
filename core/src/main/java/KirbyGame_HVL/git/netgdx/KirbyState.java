package KirbyGame_HVL.git.netgdx;


import KirbyGame_HVL.git.entities.player.Kirby;

import java.io.Serializable;

public class KirbyState implements Serializable {
    public String id;
    public int x;
    public int y;
    public String currentAnimation;
    public boolean flipX;
    public boolean flipY;
    public String color;

    public KirbyState() {}

    public KirbyState(Kirby kirby){
        this.id = kirby.getId();
        this.x = kirby.getPositioX();
        this.y = kirby.getPositioY();
        this.currentAnimation = kirby.getCurrentAnimationName();
        this.flipX = kirby.isFlipX();
        this.color = kirby.getCurrentColor();

        System.out.println("Estado Kirby - ID: " + id +
            ", X: " + x +
            ", Y: " + y +
            ", Current Animation: " + currentAnimation);

    }
}

package KirbyGame_HVL.git.entities.net;

import java.io.Serializable;

public class NetworkMessage implements Serializable {
    private String playerId;
    private float posX;
    private float posY;
    private String animation;
    private String color;
    private boolean flipX;
    private MessageType type;

    public enum MessageType {
        PLAYER_JOIN,
        PLAYER_LEAVE,
        PLAYER_UPDATE,
        GAME_STATE
    }

    public NetworkMessage(String playerId, float posX, float posY, String animation,
                          String color, boolean flipX, MessageType type) {
        this.playerId = playerId;
        this.posX = posX;
        this.posY = posY;
        this.animation = animation;
        this.color = color;
        this.flipX = flipX;
        this.type = type;
    }

    // Getters and setters
    public String getPlayerId() { return playerId; }
    public float getPosX() { return posX; }
    public float getPosY() { return posY; }
    public String getAnimation() { return animation; }
    public String getColor() { return color; }
    public boolean isFlipX() { return flipX; }
    public MessageType getType() { return type; }
}

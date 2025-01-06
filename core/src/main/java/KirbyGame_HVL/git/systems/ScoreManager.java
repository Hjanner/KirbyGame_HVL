package KirbyGame_HVL.git.systems;

import KirbyGame_HVL.git.entities.enemis.Enemy;
import KirbyGame_HVL.git.entities.items.EnumItemType;

public class ScoreManager {
    private static int currentScore;
    private final int POINTS_DELETE_WADDLE  = 20;
    private final int POINTS_DELETE_BRONTO  = 30;
    private final int POINTS_DELETE_HOTHEAD = 50;

    private final int POINTS_LOST_DAMAGE_ENEMY = 10;
    private final int POINTS_LOST_DAMAGE_SPIKES = 10;
    private final int POINTS_LOST_DAMAGE_HOLE = 20;

    private final int POINTS_TAKE_KEY  = 50;
    private final int POINTS_OPEN_DOOR = 100;

    private final int MIN_SCORE = 0;               // Minimum score limit

    public ScoreManager() {
        this.currentScore = 0;
    }

    public void addPoints(int points) {
        this.currentScore += points;
    }

    public void removePoints(int points) {
        this.currentScore = Math.max(MIN_SCORE, currentScore - points);
    }

    public void enemyDelete(Enemy enemy) {
        switch (enemy.getType()) {
            case WADDLE:
                addPoints(POINTS_DELETE_WADDLE);
                break;
            case BRONTO:
                addPoints(POINTS_DELETE_BRONTO);
                break;
            case HOTHEAD:
                addPoints(POINTS_DELETE_HOTHEAD);
                break;
            default:
                System.out.println("error ");
        }
    }

    public void recibirDamage(EnumItemType damageType) {
        switch (damageType) {
            case ENEMY:
                removePoints(POINTS_LOST_DAMAGE_ENEMY);
                break;
            case SPIKES:
                removePoints(POINTS_LOST_DAMAGE_SPIKES);
                break;
            case HOLE:
                removePoints(POINTS_LOST_DAMAGE_HOLE);
                break;
            default:
                System.out.println("Unknown damage type: " + damageType);
        }
    }

    public void takeItems(EnumItemType itemType) {
        switch (itemType) {
            case DOOR:
                addPoints(POINTS_OPEN_DOOR);
                break;
            case KEY:
                addPoints(POINTS_TAKE_KEY);
                break;
            default:
                System.out.println("error ");
                break;
        }
    }

    public void setCurrentScore(int score) {
        this.currentScore += score;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void resetScore() {
        currentScore = 0;
    }
}


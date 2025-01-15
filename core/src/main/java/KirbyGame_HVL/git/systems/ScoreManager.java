package KirbyGame_HVL.git.systems;

import KirbyGame_HVL.git.entities.enemis.Enemy;
import KirbyGame_HVL.git.entities.items.EnumItemType;

public class ScoreManager {

    // Atributos
    private static int currentScore;
    private final int POINTS_DELETE_WADDLEDEE  = 20;
    private final int POINTS_DELETE_BRONTO  = 30;
    private final int POINTS_DELETE_HOTHEAD = 50;
    private final int POINTS_DELETE_WADDLEDOO = 40;

    private final int POINTS_LOST_DAMAGE_ENEMY = 10;
    private final int POINTS_LOST_DAMAGE_SPIKES = 10;
    private final int POINTS_LOST_DAMAGE_HOLE = 20;
    private final int POINTS_LOST_DAMAGE_ATTACK = 30;

    private final int POINTS_TAKE_KEY  = 50;
    private final int POINTS_OPEN_DOOR = 100;

    // Minimum score limit
    private final int MIN_SCORE = 0;

    // Constructor
    public ScoreManager() {
        this.currentScore = 0;
    }

    // Setters y Getters

    public void setCurrentScore(int score) {
        this.currentScore += score;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    // Agregamos puntos
    public void addPoints(int points) {
        this.currentScore += points;
    }

    // Restamos puntos
    public void removePoints(int points) {
        this.currentScore = Math.max(MIN_SCORE, currentScore - points);
    }

    // Dependiendo del enemigo agregamos una cantidad de puntos
    public void enemyDelete(Enemy enemy) {
        switch (enemy.getType()) {
            case WADDLEDEE:
                addPoints(POINTS_DELETE_WADDLEDEE);
                break;
            case BRONTO:
                addPoints(POINTS_DELETE_BRONTO);
                break;
            case HOTHEAD:
                addPoints(POINTS_DELETE_HOTHEAD);
                break;
            case WADDLEDOO:
                addPoints(POINTS_DELETE_WADDLEDOO);
                break;
            default:
                System.out.println("error ");
                break;
        }
    }

    // Restamos puntos dependiendo del objeto
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
            case ATTACK:
                removePoints(POINTS_LOST_DAMAGE_ATTACK);
                break;
            default:
                System.out.println("Unknown damage type: " + damageType);
                break;
        }
    }

    // Agregamos puntos dependiendo del item obtenido
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
}


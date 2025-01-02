package KirbyGame_HVL.git.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import java.util.concurrent.atomic.AtomicInteger;

public class MinigameManager {
    private static AtomicInteger minigameScore = new AtomicInteger(0);
    private Thread minigameThread;
    private boolean isMinigameRunning = false;

    public void launchMinigame(final MinigameWindow minigame) {
        if (isMinigameRunning) {
            return;
        }

        minigameThread = new Thread(() -> {
            Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
            config.setTitle("Minigame");
            config.setWindowedMode(800, 600);
            config.setResizable(false);

            new Lwjgl3Application(minigame, config);
        });

        minigameThread.start();
        isMinigameRunning = true;
    }

    public static void setScore(int score) {
        minigameScore.set(score);
    }

    public static int getScore() {
        return minigameScore.get();
    }

    public boolean isMinigameActive() {
        return isMinigameRunning && minigameThread != null && minigameThread.isAlive();
    }

    public void closeMinigame() {
        if (minigameThread != null) {
            minigameThread.interrupt();
            isMinigameRunning = false;
        }
    }
}

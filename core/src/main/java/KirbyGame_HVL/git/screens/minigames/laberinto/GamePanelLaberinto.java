package KirbyGame_HVL.git.screens.minigames.laberinto;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.screens.gameplay.GameScreen;
import KirbyGame_HVL.git.systems.MinigameManager;
import KirbyGame_HVL.git.systems.MinigameWindow;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;
import java.util.Random;

public class GamePanelLaberinto extends MinigameWindow implements Screen {
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture playerTexture;
    private Texture wallTexture;
    private Texture odsTexture;
    private Texture backgroundTexture;

    private static final float WORLD_WIDTH = 1000;
    private static final float WORLD_HEIGHT = 600;
    private static final int CELL_SIZE = 40;
    private static final float PLAYER_SPEED = 200;

    private Rectangle player;
    private ArrayList<Rectangle> walls;
    private Rectangle odsItem;
    private boolean gameOver = false;
    private Random random;
    private int score = 0;
    private int odsCollected = 0;
    private ArrayList<String> odsImages;
    private final String ODS_IMAGES_PATH = "assets/art/minijuegos/ods/";
    private Main main;
    private MinigameManager minigameManager;

    public GamePanelLaberinto(Main main, MinigameManager manager) {
        super(manager);
        this.main = main;
        this.minigameManager = manager;
        this.random = new Random();
        this.odsImages = new ArrayList<>();
        loadODSImages();
        create();
    }

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();

        loadAssets();
        initGame();
    }

    private void loadAssets() {
        try {
            backgroundTexture = new Texture(Gdx.files.internal("assets/art/minijuegos/background.png"));
            wallTexture = new Texture(Gdx.files.internal("assets/art/minijuegos/ladrillos.png"));
            playerTexture = new Texture(Gdx.files.internal("lwjgl3/src/main/resources/logito.png"));
            odsTexture = getRandomODSTexture();
        } catch (Exception e) {
            System.err.println("Error loading textures: " + e.getMessage());
            Gdx.app.exit();
        }
    }

    private void loadODSImages() {
        try {
            FileHandle dirHandle = Gdx.files.internal(ODS_IMAGES_PATH);
            for (FileHandle entry : dirHandle.list()) {
                if (entry.extension().equals("png") || entry.extension().equals("jpg")) {
                    odsImages.add(entry.path());
                }
            }
        } catch (Exception e) {
            System.err.println("Error cargar de ODS: " + e.getMessage());
            odsImages.add("default_ods.png");
        }
    }

    private Texture getRandomODSTexture() {
        if (odsTexture != null) {
            odsTexture.dispose();
        }
        String randomPath = odsImages.get(random.nextInt(odsImages.size()));
        return new Texture(Gdx.files.internal(randomPath));
    }

    private void initGame() {
        player = new Rectangle(CELL_SIZE, CELL_SIZE, CELL_SIZE, CELL_SIZE);
        walls = new ArrayList<>();
        generateMaze();
        spawnODSItem();
        gameOver = false;
        score = 0;
        odsCollected = 0;
    }

    private void generateMaze() {
        walls.clear();
        // paredes
        for (int x = 0; x < WORLD_WIDTH; x += CELL_SIZE * 2) {
            for (int y = 0; y < WORLD_HEIGHT; y += CELL_SIZE * 2) {
                if (random.nextBoolean()) {
                    walls.add(new Rectangle(x, y, CELL_SIZE, CELL_SIZE));
                } else {
                    walls.add(new Rectangle(x + CELL_SIZE, y, CELL_SIZE, CELL_SIZE));
                }
            }
        }
        // bordes
        for (int x = 0; x < WORLD_WIDTH; x += CELL_SIZE) {
            walls.add(new Rectangle(x, 0, CELL_SIZE, CELL_SIZE));
            walls.add(new Rectangle(x, WORLD_HEIGHT - CELL_SIZE, CELL_SIZE, CELL_SIZE));
        }
        for (int y = 0; y < WORLD_HEIGHT; y += CELL_SIZE) {
            walls.add(new Rectangle(0, y, CELL_SIZE, CELL_SIZE));
            walls.add(new Rectangle(WORLD_WIDTH - CELL_SIZE, y, CELL_SIZE, CELL_SIZE));
        }
    }

    private void spawnODSItem() {
        boolean validPosition;
        Rectangle newODS;

        do {
            validPosition = true;
            float x = random.nextInt((int)(WORLD_WIDTH/CELL_SIZE)) * CELL_SIZE;
            float y = random.nextInt((int)(WORLD_HEIGHT/CELL_SIZE)) * CELL_SIZE;

            newODS = new Rectangle(x, y, CELL_SIZE, CELL_SIZE);

            for (Rectangle wall : walls) {
                if (newODS.overlaps(wall)) {
                    validPosition = false;
                    break;
                }
            }

            if (newODS.overlaps(player)) {
                validPosition = false;
            }
        } while (!validPosition);

        odsItem = newODS;
        odsTexture = getRandomODSTexture();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!gameOver) {
            updateGame(delta);
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        renderGame();

        if (gameOver) {
            handleGameOver();
        }
    }

    private void updateGame(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.x -= PLAYER_SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.x += PLAYER_SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.y += PLAYER_SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.y -= PLAYER_SPEED * delta;
        }

        for (Rectangle wall : walls) {             //colisiones paredes
            if (player.overlaps(wall)) {
                if (player.x < wall.x) player.x = wall.x - player.width;
                if (player.x > wall.x) player.x = wall.x + wall.width;
                if (player.y < wall.y) player.y = wall.y - player.height;
                if (player.y > wall.y) player.y = wall.y + wall.height;
            }
        }

        if (player.overlaps(odsItem)) {                         //        Checka ODS colisiones
            score += 100;
            odsCollected++;
            if (odsCollected >= 10) {
                gameOver = true;
            } else {
                spawnODSItem();
            }
        }
    }

    private void renderGame() {
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);

        for (Rectangle wall : walls) {
            batch.draw(wallTexture, wall.x, wall.y, wall.width, wall.height);                   // dibuja paredes
        }

        batch.draw(playerTexture, player.x, player.y, player.width, player.height);                 // dibuja ods y jugadores
        batch.draw(odsTexture, odsItem.x, odsItem.y, odsItem.width, odsItem.height);

        //  UI
        font.getData().setScale(1.5f);
        font.setColor(1, 1, 1, 1);
        font.draw(batch, "SCORE: " + score, 20, WORLD_HEIGHT - 20);
        font.draw(batch, "ODS: " + odsCollected + " / 10", 20, WORLD_HEIGHT - 60);

        if (gameOver) {
            renderGameOverScreen();
        }

        batch.end();
    }

    private void renderGameOverScreen() {
        font.getData().setScale(3);
        font.setColor(0, 1, 0, 1);
        String message = odsCollected >= 10 ? "¡GANADOR!" : "GAME OVER";
        font.draw(batch, message, WORLD_WIDTH/2 - 100, WORLD_HEIGHT/2 + 50);
        font.draw(batch, "Final Score: " + score, WORLD_WIDTH/2 - 80, WORLD_HEIGHT/2);
        font.draw(batch, "Presiona ESPACIO para continuar", WORLD_WIDTH/2 - 200, WORLD_HEIGHT/2 - 40);
    }

    private void handleGameOver() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (odsCollected >= 10) {
                gameCompleted();
            } else {
                initGame();
            }
        }
    }

    @Override
    public void sendScore(int score) {
        MinigameManager.setScore(score);
    }

    private void gameCompleted() {
        int totalScore = minigameManager.getSavedKirbyScore() + score;
        sendScore(totalScore);

        GameScreen game = new GameScreen(
            main,
            minigameManager.getPosKirbyX(),
            minigameManager.getPosKirbyY(),
            totalScore,
            3
        );
        main.setScreen(game);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {
        if (shapeRenderer == null) {
            create();
        }
    }

    @Override
    public void dispose() {
        if (shapeRenderer != null) shapeRenderer.dispose();
        if (batch != null) batch.dispose();
        if (font != null) font.dispose();
        if (playerTexture != null) playerTexture.dispose();
        if (wallTexture != null) wallTexture.dispose();
        if (odsTexture != null) odsTexture.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose();
    }
}

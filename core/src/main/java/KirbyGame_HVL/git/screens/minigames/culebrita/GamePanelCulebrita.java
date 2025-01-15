package KirbyGame_HVL.git.screens.minigames.culebrita;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.screens.gameplay.GameScreen;
import KirbyGame_HVL.git.systems.MinigameManager;
import KirbyGame_HVL.git.systems.MinigameWindow;
import KirbyGame_HVL.git.systems.MusicManager;
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

public class GamePanelCulebrita extends MinigameWindow implements Screen {

    // Atributos
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture foodTexture;
    private Texture backgroundTexture;
    private Texture obstacleTexture;
    private final int OBSTACLE_SIZE_MIN = 40;
    private final int OBSTACLE_SIZE_MAX = 80;

    private static final float WORLD_WIDTH = 1000;
    private static final float WORLD_HEIGHT = 600;
    private static final int GRID_SIZE = 30;
    private static final float MOVE_TIME = 0.1f;

    private ArrayList<Rectangle> snakeBody;
    private Rectangle food;
    private ArrayList<Rectangle> obstacles;
    private int direction = Input.Keys.RIGHT;
    private float timer = 0;
    private boolean gameOver = false;
    private Random random;
    private boolean shouldGrow = false;
    private int score = 0;
    private int ods_image = 0;
    private Main main;
    private MinigameManager minigameManager;

    private ArrayList<String> odsImages;
    private final String ODS_IMAGES_PATH = "assets/art/minijuegos/ods/";

    // Constructor
    public GamePanelCulebrita(Main main, MinigameManager manager) {
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

    private void renderGame() {
        //fondo
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        // dibujo serpiente
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0.41f, 0.71f, 1);
        for (Rectangle segment : snakeBody) {
            shapeRenderer.rect(segment.x, segment.y, segment.width, segment.height);
        }
        shapeRenderer.end();

        // obstaculos y ods
        batch.begin();
        for (Rectangle obstacle : obstacles) {
            batch.draw(obstacleTexture, obstacle.x, obstacle.y, obstacle.width, obstacle.height);
        }
        batch.draw(foodTexture, food.x, food.y, food.width, food.height);

        // UI
        renderUI();
        batch.end();
    }

    // Dibujamos el puntaje
    private void renderUI() {
        font.getData().setScale(1.5f);
        font.setColor(1, 1, 1, 1);
        font.draw(batch, "SCORE : " + score, 20, viewport.getWorldHeight() - 20);
        font.draw(batch, "ODS   : " + ods_image + " / 10", 20, viewport.getWorldHeight() - 60);

        if (ods_image == 10) {
            gameOver = true;
            renderWinScreen();
        } else if (gameOver) {
            renderGameOverScreen();
        }
    }

    // Mensaje de ganador
    private void renderWinScreen() {
        font.getData().setScale(3);
        font.setColor(0, 1, 0, 1);
        font.draw(batch, "GANADOR", viewport.getWorldWidth()/2 - 100, viewport.getWorldHeight()/2 + 50);
        font.draw(batch, "Final Score: " + score, viewport.getWorldWidth()/2 - 80, viewport.getWorldHeight()/2);
        font.draw(batch, "Presiona ESPACIO para avanzar", viewport.getWorldWidth()/2 - 200, viewport.getWorldHeight()/2 - 40);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            gameCompleted();
        }
    }

    // Mensaje de perdedor
    private void renderGameOverScreen() {
        font.getData().setScale(3);
        font.setColor(1, 0, 0, 1);
        font.draw(batch, "GAME OVER", viewport.getWorldWidth()/2 - 140, viewport.getWorldHeight()/2 + 50);
        font.getData().setScale(2);
        font.draw(batch, "Final Score: " + score, viewport.getWorldWidth()/2 - 80, viewport.getWorldHeight()/2);
        font.draw(batch, "Presiona SPACE para reiniciar", viewport.getWorldWidth()/2 - 180, viewport.getWorldHeight()/2 - 40);
    }

    private void handleGameOver() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (ods_image == 10) {
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
            2
        );
        main.setScreen(game);
    }

    // Iniciamos el juego
    private void initGame() {
        snakeBody = new ArrayList<>();
        obstacles = new ArrayList<>();
        Rectangle head = new Rectangle(WORLD_WIDTH/2, WORLD_HEIGHT/2, GRID_SIZE, GRID_SIZE);
        snakeBody.add(head);

        createObstacles();
        spawnFood();            //cargar ods
        gameOver = false;
        ods_image = 0;
        score = 0;
        direction = Input.Keys.RIGHT;
    }

    // Cargamos las imagenes de los ODS
    private void loadODSImages() {
        try {
            FileHandle dirHandle = Gdx.files.internal(ODS_IMAGES_PATH);
            for (FileHandle entry : dirHandle.list()) {
                if (entry.extension().equals("png") || entry.extension().equals("jpg")) {
                    odsImages.add(entry.path());
                }
            }
            if (odsImages.isEmpty()) {
                throw new RuntimeException("No se encontraron imagenes de IDS en " + ODS_IMAGES_PATH);
            }
        } catch (Exception e) {
            System.err.println("Error cargando imagenes de comida: " + e.getMessage());
            odsImages.add("logito.png");
        }
    }

    private Texture getRandomFoodTexture() {
        if (foodTexture != null) {
            foodTexture.dispose();
        }
        String randomPath = odsImages.get(random.nextInt(odsImages.size()));
        return new Texture(Gdx.files.internal(randomPath));
    }

    private void loadAssets(){
        try {
            backgroundTexture = new Texture(Gdx.files.internal("assets/art/minijuegos/background.png"));
            backgroundTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            obstacleTexture = new Texture(Gdx.files.internal("assets/art/minijuegos/imagen.png"));
            foodTexture = getRandomFoodTexture(); // Inicializar con una imagen aleatoria
        } catch (Exception e) {
            System.err.println("Error al cargar las textura: " + e.getMessage());
            Gdx.app.exit();
        }
    }

    private void createObstacles() {
        obstacles.clear();
        for (int i = 0; i < 10; i++) {
            boolean validPosition;
            Rectangle obstacle;

            int obstacle_size = random.nextInt(OBSTACLE_SIZE_MAX - OBSTACLE_SIZE_MIN + 1) + OBSTACLE_SIZE_MIN;

            do {
                validPosition = true;
                float x = random.nextInt((int)(700/GRID_SIZE)) * GRID_SIZE + 50;
                float y = random.nextInt((int)(500/GRID_SIZE)) * GRID_SIZE + 50;

                obstacle = new Rectangle(x, y, obstacle_size, obstacle_size);

                for (Rectangle segment : snakeBody) {
                    if (obstacle.overlaps(segment)) {
                        validPosition = false;
                        break;
                    }
                }

                for (Rectangle existingObstacle : obstacles) {
                    if (obstacle.overlaps(existingObstacle)) {
                        validPosition = false;
                        break;
                    }
                }
            } while (!validPosition);

            obstacles.add(obstacle);
        }
    }

    private void spawnFood() {
        boolean validPosition;
        Rectangle newFood;

        do {
            validPosition = true;
            float x = random.nextInt((int)(700/GRID_SIZE)) * GRID_SIZE + 75;
            float y = random.nextInt((int)(500/GRID_SIZE)) * GRID_SIZE + 50;

            newFood = new Rectangle(x, y, GRID_SIZE*4, GRID_SIZE*4);

            for (Rectangle segment : snakeBody) {
                if (newFood.overlaps(segment)) {
                    validPosition = false;
                    break;
                }
            }

            for (Rectangle obstacle : obstacles) {
                if (newFood.overlaps(obstacle)) {
                    validPosition = false;
                    break;
                }
            }
        } while (!validPosition);

        food = newFood;
        foodTexture = getRandomFoodTexture();
    }

    private void updateGame(float delta) {
        handleInput();

        timer += delta;
        if (timer >= MOVE_TIME) {
            timer = 0;
            moveSnake();
            checkCollisions();

            if (shouldGrow) {
                growSnake();
                shouldGrow = false;
            }
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && direction != Input.Keys.RIGHT) {
            direction = Input.Keys.LEFT;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && direction != Input.Keys.LEFT) {
            direction = Input.Keys.RIGHT;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && direction != Input.Keys.DOWN) {
            direction = Input.Keys.UP;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && direction != Input.Keys.UP) {
            direction = Input.Keys.DOWN;
        }
    }

    private void moveSnake() {
        Rectangle head = snakeBody.get(0);
        float newX = head.x;
        float newY = head.y;

        // mueve el cuerpo
        for (int i = snakeBody.size() - 1; i > 0; i--) {
            Rectangle current = snakeBody.get(i);
            Rectangle previous = snakeBody.get(i - 1);
            current.x = previous.x;
            current.y = previous.y;
        }

        // mueve la cabeza
        switch (direction) {
            case Input.Keys.LEFT:
                newX -= GRID_SIZE;
                break;
            case Input.Keys.RIGHT:
                newX += GRID_SIZE;
                break;
            case Input.Keys.UP:
                newY += GRID_SIZE;
                break;
            case Input.Keys.DOWN:
                newY -= GRID_SIZE;
                break;
        }

        if (newX >= WORLD_WIDTH) newX = 0;
        if (newX < 0) newX = WORLD_WIDTH - GRID_SIZE;
        if (newY >= WORLD_HEIGHT) newY = 0;
        if (newY < 0) newY = WORLD_HEIGHT - GRID_SIZE;

        head.x = newX;
        head.y = newY;
    }

    private void checkCollisions() {
        Rectangle head = snakeBody.get(0);

        // ods colision
        if (head.overlaps(food)) {
            shouldGrow = true;
            score += 100;
            ods_image++;
            spawnFood();
        }

        // obstaculo colision
        for (Rectangle obstacle : obstacles) {
            if (head.overlaps(obstacle)) {
                gameOver = true;
                return;
            }
        }

        // Self collision
        for (int i = 1; i < snakeBody.size(); i++) {
            if (head.overlaps(snakeBody.get(i))) {
                gameOver = true;
                return;
            }
        }
    }

    private void growSnake() {
        Rectangle tail = snakeBody.get(snakeBody.size() - 1);
        Rectangle newSegment = new Rectangle(tail.x, tail.y, GRID_SIZE, GRID_SIZE);
        snakeBody.add(newSegment);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);
    }

    @Override
    public void show() {
        if (shapeRenderer == null) {
            create();
        }
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    // Eliminamos las imagenes y lineas
    @Override
    public void dispose() {
        if (shapeRenderer != null) shapeRenderer.dispose();
        if (batch != null) batch.dispose();
        if (font != null) font.dispose();
        if (foodTexture != null) foodTexture.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose();
        if (obstacleTexture != null) obstacleTexture.dispose();
    }
}
